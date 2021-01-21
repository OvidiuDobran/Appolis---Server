package com.ovi.appolis_server.reward.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.citizen.repository.CitizenRepository;
import com.ovi.appolis_server.clerk.data.model.Clerk;
import com.ovi.appolis_server.clerk.repository.ClerkRepository;
import com.ovi.appolis_server.geography.data.model.City;
import com.ovi.appolis_server.geography.repository.CityRepository;
import com.ovi.appolis_server.problem.data.dto.ProblemDto;
import com.ovi.appolis_server.problem.data.model.ProblemStatus;
import com.ovi.appolis_server.problem.repository.ProblemStatusRepository;
import com.ovi.appolis_server.problem.service.ProblemService;
import com.ovi.appolis_server.reward.data.model.Reward;
import com.ovi.appolis_server.reward.data.model.RewardAcquistion;
import com.ovi.appolis_server.reward.repository.RewardAcquisitionRepository;
import com.ovi.appolis_server.reward.repository.RewardRepository;

@Service
public class RewardService {

	@Autowired
	private RewardRepository rewardRepository;

	@Autowired
	private RewardAcquisitionRepository acquisitionRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ClerkRepository clerkRepository;

	@Autowired
	private CitizenRepository citizenRepository;

	@Autowired
	private ProblemService problemService;

	@Autowired
	private ProblemStatusRepository problemStatusRepository;

	public Reward saveReward(Reward reward) {
		City city = cityRepository.findByNameAndCountry(reward.getCity().getName(),
				reward.getCity().getCountry().getName());
		if (city == null) {
			return null;
		}

		Clerk clerk = clerkRepository.findByEmailAndCity(reward.getAuthor().getEmail(), city);
		if (clerk == null) {
			return null;
		}

		String title = reward.getTitle();
		String description = reward.getDescription();
		long cost = reward.getCost();

		if (reward.getId() != null) {
			Reward rewardFromDb = rewardRepository.findById(reward.getId());
			if (rewardFromDb != null) {
				reward = rewardFromDb;
			}
		}

		reward.setCity(city);
		reward.setTitle(title);
		reward.setAuthor(clerk);
		reward.setCreationDate(new Timestamp(System.currentTimeMillis()));
		reward.setDescription(description);
		reward.setCost(cost);

		rewardRepository.save(reward);
		return reward;
	}

	public List<Reward> getAllByCity(String cityName, String countryName) {
		City city = cityRepository.findByNameAndCountry(cityName, countryName);
		if (city == null) {
			return new ArrayList<>();
		}
		List<Reward> rewards = rewardRepository.findByCity(city);
		rewards.sort(new Comparator<Reward>() {

			@Override
			public int compare(Reward arg0, Reward arg1) {
				return new Long(arg0.getCost()).compareTo(arg1.getCost());
			}
		});
		return rewards;
	}

	public Reward getById(Long id) {
		if (id == null) {
			return null;
		}
		return rewardRepository.findById(id);
	}

	public Reward delete(Long id) {
		Reward reward = rewardRepository.findById(id);
		if (reward != null) {
			return rewardRepository.delete(reward);
		}
		return null;
	}

	public RewardAcquistion createAcquisition(String citizenEmail, Long rewardId) {
		Citizen citizen = citizenRepository.findByEmail(citizenEmail);
		Reward reward = rewardRepository.findById(rewardId);
		if (citizen == null || reward == null) {
			return null;
		}
		String code;
		do {
			code = RandomStringUtils.random(10, true, true);
		} while (!acquisitionRepository.findByCode(code).isEmpty());
		RewardAcquistion acquistion = new RewardAcquistion(citizen, reward, code);
		return acquisitionRepository.save(acquistion);
	}

	public List<RewardAcquistion> getAcquisitionsByCitizenAndCity(String citizenEmail, String cityName,
			String countryName) {
		Citizen citizen = citizenRepository.findByEmail(citizenEmail);
		City city = cityRepository.findByNameAndCountry(cityName, countryName);

		if (citizen == null || city == null) {
			return new ArrayList<RewardAcquistion>();
		}
		return acquisitionRepository.findByCitizenAndCity(citizen, city);
	}

	public long calculateBudget(String citizenEmail, String cityName, String countryName) {
		Citizen citizen = citizenRepository.findByEmail(citizenEmail);
		ProblemStatus solvedStatus = problemStatusRepository
				.findByName(ProblemStatusRepository.Status.RESOLVED.toString());
		ProblemStatus closedStatus = problemStatusRepository
				.findByName(ProblemStatusRepository.Status.CLOSED.toString());
		City city = cityRepository.findByNameAndCountry(cityName, countryName);
		List<ProblemDto> solvedProblems = problemService.getCityProblemsByStatusReportedBy(city, solvedStatus, citizen);
		List<ProblemDto> closedProblems = problemService.getCityProblemsByStatusReportedBy(city, closedStatus, citizen);
		long problemsCount = 0;
		if (solvedProblems != null) {
			problemsCount += solvedProblems.size();
		}
		if (closedProblems != null) {
			// Since the closed problems were previously resolved, the user gets 2 points
			// for each close problem
			problemsCount += 2 * closedProblems.size();
		}
		List<RewardAcquistion> acquisitions = acquisitionRepository.findByCitizenAndCity(citizen, city);
		if (acquisitions == null) {
			return problemsCount;
		}
		long spendBudget = acquisitions.stream().map(acquisition -> acquisition.getCost()).reduce(0l, (a, b) -> a + b);
		long budget = problemsCount - spendBudget;
		return budget >= 0 ? budget : 0;
	}

}
