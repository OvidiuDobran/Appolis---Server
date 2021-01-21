package com.ovi.appolis_server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.internal.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.citizen.repository.CitizenRepository;
import com.ovi.appolis_server.citizen.service.CitizenService;
import com.ovi.appolis_server.clerk.data.model.Clerk;
import com.ovi.appolis_server.clerk.repository.ClerkRepository;
import com.ovi.appolis_server.clerk.service.ClerkService;
import com.ovi.appolis_server.geography.data.model.City;
import com.ovi.appolis_server.geography.data.model.Country;
import com.ovi.appolis_server.geography.repository.CityRepository;
import com.ovi.appolis_server.geography.repository.CountryRepository;
import com.ovi.appolis_server.problem.data.model.ProblemCategory;
import com.ovi.appolis_server.problem.data.model.ProblemStatus;
import com.ovi.appolis_server.problem.repository.ProblemCategoryRepository;
import com.ovi.appolis_server.problem.repository.ProblemImageRepository;
import com.ovi.appolis_server.problem.repository.ProblemStatusRepository;
import com.ovi.appolis_server.problem.repository.ProblemStatusRepository.Status;

@Component
@ConditionalOnProperty(name = "app.init-data", havingValue = "true")
public class AppolisInitializer implements CommandLineRunner {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ClerkRepository clerkRepository;

	@Autowired
	private ClerkService clerkService;

	@Autowired
	private CitizenRepository citizenRepository;

	@Autowired
	private ProblemCategoryRepository problemCategoryRepository;

	@Autowired
	private ProblemStatusRepository problemStatusRepository;

	@Autowired
	private CitizenService citizenService;

	@Override
	public void run(String... args) throws Exception {
		createClerk("Nicolae", "Robu", "nicolae.robu@gmail.com", "PAROLA_DE_ZECE", "Timisoara", "Romania");
		createClerk("Astrid", "Fodor", "astrid.fodor@gmail.com", "afodor_pass", "Sibiu", "Romania");
		createClerk("Emil", "Boc", "emil.boc@gmail.com", "eboc_pass", "Cluj-Napoca", "Romania");
		createClerk("Falca", "Gheorghe", "gheorghe.falca@gmail.com", "gfalca_pass", "Arad", "Romania");
		createClerk("Chirica", "Mihai", "mihai.chirica@gmail.com", "mchirica_pass", "Iasi", "Romania");
		createClerk("Bolojan", "Ilie", "ilie.bolojan@gmail.com", "ibolojan_pass", "Oradea", "Romania");
		createClerk("Genoiu", "Mihail", "mihail.genoiu@gmail.com", "mgenoiu_pass", "Craiova", "Romania");
		createCitizen("Ovidiu", "Dobran", "ovidiu.dobran16@gmail.com", "odobran_pass");
		initCategories();
		initStatusList();
		createDirectories();
	}

	private void createClerk(String firstName, String lastName, String email, String password, String cityName,
			String countryName) {
		List<Country> countries = countryRepository.findByName(countryName);
		Country country = null;
		if (countries == null || countries.isEmpty()) {
			country = new Country(countryName);
			countryRepository.save(country);
		} else {
			country = countries.get(0);
		}
		City city = cityRepository.findByNameAndCountry(cityName, countryName);
		if (city == null) {
			city = new City(cityName, country);
			cityRepository.save(city);
		}
		Clerk clerk = clerkRepository.findByEmailAndCity(email, city);
		if (clerk == null) {
			clerk = new Clerk(firstName, lastName, email, password, city);
			clerkService.save(clerk);
		}
	}

	private void createCitizen(String firstName, String lastName, String email, String password) {
		Citizen citizen = citizenRepository.findByEmail(email);
		if (citizen == null) {
			citizen = new Citizen(firstName, lastName, email, password);
			citizenService.save(citizen);
		}
	}

	private void createDirectories() {
		Path dirPath = Paths.get(ProblemImageRepository.UPLOAD_DIR);
		if (Files.exists(dirPath)) {
		} else {
			try {
				Files.createDirectories(dirPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void initStatusList() {
		List<Status> statusList = Arrays.asList(ProblemStatusRepository.Status.values());
		statusList.forEach(status -> {
			if (problemStatusRepository.findByName(status.toString()) == null) {
				problemStatusRepository.save(new ProblemStatus(status.toString()));
			}
		});
	}

	private void initCategories() {
		List<ProblemCategory> categories = Arrays.asList(new ProblemCategory[] { new ProblemCategory("Sanitation"),
				new ProblemCategory("Public transport"), new ProblemCategory("Bicycles"),
				new ProblemCategory("Vandalism"), new ProblemCategory("Traffic, road signs and parking"),
				new ProblemCategory("Utilities (sewerage, gas, electricity etc.)"), new ProblemCategory("Other") });
		categories.forEach(c -> {
			if (problemCategoryRepository.findByDescription(c.getDescription()) == null) {
				problemCategoryRepository.save(c);
			}
		});
	}

}
