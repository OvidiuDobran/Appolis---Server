package com.ovi.appolis_server.geography.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.citizen.repository.CitizenRepository;
import com.ovi.appolis_server.geography.data.dto.CityRankDto;
import com.ovi.appolis_server.geography.data.dto.CityStatusDto;
import com.ovi.appolis_server.geography.data.model.City;
import com.ovi.appolis_server.geography.repository.CityRepository;
import com.ovi.appolis_server.problem.data.model.Problem;
import com.ovi.appolis_server.problem.repository.ProblemStatusRepository;
import com.ovi.appolis_server.problem.service.ProblemService;

@Service
public class GeographyService {
	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CitizenRepository citizenRepository;

	@Autowired
	private ProblemService problemService;

	public List<City> getCities() {
		return cityRepository.findAll();
	}

	public List<City> getUserCities(String citizenEmail) {
		Citizen citizen = citizenRepository.findByEmail(citizenEmail);
		if (citizen == null) {
			return new ArrayList<City>();
		}

		return cityRepository.findByCitizen(citizen);
	}

	public List<CityRankDto> calculateCitiesRanking() {
		List<City> allCities = cityRepository.findAll();
		Stream<CityRankDto> citiesRanking = allCities.stream().map(city -> calculateCityRank(city));
		citiesRanking = citiesRanking.sorted(Comparator.reverseOrder());
		return citiesRanking.collect(Collectors.toList());
	}

	private CityRankDto calculateCityRank(City city) {
		// all problems except the rejected ones
		List<Problem> validProblems = problemService.getProblemsFromCity(city).stream()
				.filter(problem -> !ProblemStatusRepository.Status.REJECTED.toString()
						.equals(problem.getCurrentStatus().getName()))
				.collect(Collectors.toList());
		List<Problem> solvedProblems = validProblems.stream().filter(problem -> ProblemStatusRepository.Status.RESOLVED
				.toString().equals(problem.getCurrentStatus().getName().toString())).collect(Collectors.toList());
		List<Problem> closedProblems = validProblems.stream().filter(problem -> ProblemStatusRepository.Status.CLOSED
				.toString().equals(problem.getCurrentStatus().getName().toString())).collect(Collectors.toList());

		double solvedParam = validProblems.isEmpty() ? 0
				: (solvedProblems.size() + closedProblems.size()) * 5.0 / validProblems.size();
		solvedParam = solvedParam * (40.0 / 100);

		double ratingParam = 0;
		if (!closedProblems.isEmpty()) {
			ratingParam = closedProblems.stream().map(problem -> problem.getRating()).reduce(0l, Long::sum);
			ratingParam = ratingParam * 1.0 / closedProblems.size();
			ratingParam = ratingParam * (60.0 / 100);
		}

		return new CityRankDto(city, solvedParam + ratingParam, new Long(validProblems.size()));
	}

	public CityStatusDto calculateCityStatus(String cityName, String countryName) {
		City city = cityRepository.findByNameAndCountry(cityName, countryName);
		List<Problem> allProblems = problemService.getProblemsFromCity(city);
		List<Problem> newProblems = allProblems.stream().filter(problem -> ProblemStatusRepository.Status.NEW.toString()
				.equals(problem.getCurrentStatus().getName().toString())).collect(Collectors.toList());
		List<Problem> inProgressProblems = allProblems.stream()
				.filter(problem -> ProblemStatusRepository.Status.IN_PROGRESS.toString()
						.equals(problem.getCurrentStatus().getName().toString()))
				.collect(Collectors.toList());
		List<Problem> solvedProblems = allProblems.stream().filter(problem -> ProblemStatusRepository.Status.RESOLVED
				.toString().equals(problem.getCurrentStatus().getName().toString())).collect(Collectors.toList());
		List<Problem> closedProblems = allProblems.stream().filter(problem -> ProblemStatusRepository.Status.CLOSED
				.toString().equals(problem.getCurrentStatus().getName().toString())).collect(Collectors.toList());
		List<Problem> rejectedProblems = allProblems.stream().filter(problem -> ProblemStatusRepository.Status.REJECTED
				.toString().equals(problem.getCurrentStatus().getName().toString())).collect(Collectors.toList());
		CityRankDto cityRank = calculateCityRank(city);

		return new CityStatusDto(city, cityRank.getRank(), allProblems.size(), newProblems.size(),
				inProgressProblems.size(), solvedProblems.size(), closedProblems.size(), rejectedProblems.size());
	}
}
