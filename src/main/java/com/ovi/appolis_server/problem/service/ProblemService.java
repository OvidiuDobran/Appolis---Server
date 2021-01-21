package com.ovi.appolis_server.problem.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.citizen.repository.CitizenRepository;
import com.ovi.appolis_server.clerk.data.model.Clerk;
import com.ovi.appolis_server.clerk.repository.ClerkRepository;
import com.ovi.appolis_server.geography.data.model.City;
import com.ovi.appolis_server.geography.repository.CityRepository;
import com.ovi.appolis_server.problem.data.dto.ProblemDto;
import com.ovi.appolis_server.problem.data.model.Problem;
import com.ovi.appolis_server.problem.data.model.ProblemCategory;
import com.ovi.appolis_server.problem.data.model.ProblemHistory;
import com.ovi.appolis_server.problem.data.model.ProblemImage;
import com.ovi.appolis_server.problem.data.model.ProblemStatus;
import com.ovi.appolis_server.problem.repository.ProblemCategoryRepository;
import com.ovi.appolis_server.problem.repository.ProblemHistoryRepository;
import com.ovi.appolis_server.problem.repository.ProblemImageRepository;
import com.ovi.appolis_server.problem.repository.ProblemRepository;
import com.ovi.appolis_server.problem.repository.ProblemStatusRepository;

@Service
public class ProblemService {

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private ProblemCategoryRepository problemCategoryRepository;

	@Autowired
	private ProblemStatusRepository problemStatusRepository;

	@Autowired
	private ProblemHistoryRepository problemHistoryRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CitizenRepository citizenRepository;

	@Autowired
	private ClerkRepository clerkRepository;

	public List<ProblemCategory> getAllCategories() {
		return problemCategoryRepository.findAll();
	}

	public ProblemDto createProblem(ProblemDto requestedProblemDto) {

		Citizen citizen = citizenRepository.findByEmail(requestedProblemDto.getCitizenEmail());
		City city = cityRepository.findByNameAndCountry(requestedProblemDto.getCity().getName(),
				requestedProblemDto.getCity().getCountry().getName());
		ProblemStatus status = problemStatusRepository.findByName(ProblemStatusRepository.Status.NEW.toString());
		ProblemCategory category = problemCategoryRepository.findByDescription(requestedProblemDto.getCategory());

		Problem newProblem = new Problem(category, city, requestedProblemDto.getDescription(),
				requestedProblemDto.getLatitude(), requestedProblemDto.getLongitude(), requestedProblemDto.getAddress(),
				citizen);

		ProblemHistory history = new ProblemHistory(newProblem, new Timestamp(System.currentTimeMillis()),
				ProblemHistoryRepository.ChangeType.CREATION.toString(), status);
		newProblem.addHistory(history);

		problemHistoryRepository.save(history);

		return new ProblemDto(newProblem);
	}

	public List<ProblemDto> getProblemDtosWithStatusFromCity(String statusName, String cityName, String countryName) {
		List<Problem> problemWithLastStatus = getProblemsWithStatusFromCity(statusName, cityName, countryName);
		return problemWithLastStatus.stream().map(problem -> new ProblemDto(problem)).collect(Collectors.toList());
	}

	public List<Problem> getProblemsWithStatusFromCity(String statusName, String cityName, String countryName) {
		ProblemStatus status = problemStatusRepository.findByName(statusName);
		City city = cityRepository.findByNameAndCountry(cityName, countryName);
		if (status == null || city == null) {
			return new ArrayList<>();
		}

		List<Problem> problemsWithStatus = problemRepository.findByStatusAndCity(status, city);
		List<Problem> problemWithLastStatus = problemsWithStatus.stream()
				.filter(problem -> status.equals(problem.getCurrentStatus())).collect(Collectors.toList());
		return problemWithLastStatus;
	}

	public List<Problem> getProblemsFromCity(City city) {
		if (city == null) {
			return new ArrayList<>();
		}
		return problemRepository.findByCity(city);
	}

	public List<ProblemDto> getCityProblemsByStatusReportedBy(City city, ProblemStatus status, Citizen citizen) {
		if (citizen == null || status == null || city == null) {
			return new ArrayList<>();
		}

		List<Problem> problemsWithStatus = problemRepository.findByStatusAndCityAndCitizen(status, city, citizen);
		List<ProblemDto> problemDtosWithLastStatus = new ArrayList<ProblemDto>();
		for (Problem problem : problemsWithStatus) {
			if (status.equals(problem.getCurrentStatus())) {
				problemDtosWithLastStatus.add(new ProblemDto(problem));
			}
		}
		return problemDtosWithLastStatus;
	}

	public ProblemDto getProblemById(Long id) {
		Problem problem = problemRepository.findById(id);
		return problem == null ? null : new ProblemDto(problem);
	}

	public ProblemDto updateStatus(Long id, String statusName, String clerkEmail) {
		Problem problem = problemRepository.findById(id);
		if (problem == null) {
			return null;
		}
		Clerk clerk = clerkRepository.findByEmailAndCity(clerkEmail, problem.getCity());
		ProblemStatus newStatus = problemStatusRepository.findByName(statusName);
		if (clerk != null && newStatus != null) {
			if (!newStatus.equals(problem.getCurrentStatus())) {
				problem.addHistory(new ProblemHistory(problem, new Timestamp(System.currentTimeMillis()),
						ProblemHistoryRepository.ChangeType.STATUS.toString(), newStatus, clerk));
				problemRepository.save(problem);
			}
		}
		return new ProblemDto(problem);
	}

	public ProblemDto attachImages(Long problemId, MultipartFile imageFile) {
		Problem problem = problemRepository.findById(problemId);
		if (problem == null) {
			return null;
		}

		try {
			byte[] bytes = imageFile.getBytes();
			String imageName = problemId + "_" + imageFile.getOriginalFilename();
			Path path = Paths.get(ProblemImageRepository.UPLOAD_DIR + imageName);
			Files.write(path, bytes);

			ProblemImage image = new ProblemImage(problem, imageName);
			problem.addImage(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		problemRepository.save(problem);
		return new ProblemDto(problem);
	}

	public List<ProblemDto> getProblemsByCitizen(String email) {
		Citizen citizen = citizenRepository.findByEmail(email);
		if (citizen == null) {
			return new ArrayList<ProblemDto>();
		}
		List<Problem> problems = problemRepository.findByCitizen(citizen);
		return problems.stream().map(problem -> new ProblemDto(problem)).collect(Collectors.toList());
	}

	public List<ProblemDto> getProblemsByCitizenAndStatus(String citizenEmail, List<String> statusList, String cityName,
			String countryName) {
		Citizen citizen = citizenRepository.findByEmail(citizenEmail);
		City city = cityRepository.findByNameAndCountry(cityName, countryName);
		List<ProblemStatus> problemStatusList = statusList.stream().map(s -> problemStatusRepository.findByName(s))
				.collect(Collectors.toList());
		if (citizen == null || city == null || problemStatusList.isEmpty()) {
			return new ArrayList<ProblemDto>();
		}
		List<Problem> problems = problemRepository.findByCitizenAndStatusListInCity(citizen, problemStatusList, city);
		problems = problems.stream().filter(problem -> problemStatusList.contains(problem.getCurrentStatus()))
				.collect(Collectors.toList());
		return problems.stream().map(problem -> new ProblemDto(problem)).collect(Collectors.toList());
	}

	public byte[] downloadImage(String imgName) {
		try {
			Path imgDirPath = Paths.get(ProblemImageRepository.UPLOAD_DIR);
			Path file = imgDirPath.resolve(imgName);
			InputStream imgFile = new FileInputStream(file.toFile());
			byte[] bytes;
			bytes = StreamUtils.copyToByteArray(imgFile);
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProblemDto addComment(Long problemId, String comment, String clerkEmail) {
		Problem problem = problemRepository.findById(problemId);
		Clerk clerk = clerkRepository.findByEmail(clerkEmail);
		if (problem == null || clerk == null) {
			return null;
		}

		ProblemHistory newHistory = new ProblemHistory();
		newHistory.setCreationDate(new Timestamp(System.currentTimeMillis()));
		newHistory.setClerk(clerk);
		newHistory.setChangeType(ProblemHistoryRepository.ChangeType.COMMENT.toString());
		newHistory.setComment(comment);
		newHistory.setProblem(problem);

		ProblemHistory currentHistory = problem.getCurrentHistory();
		ProblemStatus status;
		if (currentHistory == null) {
			status = problemStatusRepository.findByName(ProblemStatusRepository.Status.NEW.toString());
		} else {
			status = currentHistory.getStatus();
		}
		newHistory.setStatus(status);

		problem.addHistory(newHistory);
		problemRepository.save(problem);

		return new ProblemDto(problem);
	}

	public ProblemDto closeProblem(Long problemId, Long rating) {
		if (problemId == null || rating == null) {
			return null;
		}
		Problem problem = problemRepository.findById(problemId);
		if (problem == null) {
			return null;
		}
		ProblemStatus closedStatus = problemStatusRepository
				.findByName(ProblemStatusRepository.Status.CLOSED.toString());
		ProblemHistory problemHistory = new ProblemHistory(problem, new Timestamp(System.currentTimeMillis()),
				ProblemHistoryRepository.ChangeType.STATUS.toString(), closedStatus);

		problem.addHistory(problemHistory);
		problem.setRating(rating);
		problemRepository.save(problem);
		return new ProblemDto(problem);
	}

}
