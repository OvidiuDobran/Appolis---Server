package com.ovi.appolis_server.citizen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.citizen.repository.CitizenRepository;
import com.ovi.appolis_server.geography.data.model.City;
import com.ovi.appolis_server.geography.repository.CityRepository;
import com.ovi.appolis_server.security.JwtTokenUtil;
import com.ovi.appolis_server.security.JwtUserDetailsService;
import com.ovi.appolis_server.session.data.dto.LoginForm;

@Service
public class CitizenService {

	@Autowired
	private CitizenRepository citizenRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public Citizen authenticate(LoginForm loginForm) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));
			final UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getEmail());
			final String token = jwtTokenUtil.generateToken(userDetails);
			Citizen citizen = citizenRepository.findByEmail(loginForm.getEmail());
			citizen.setToken(token);
			return citizen;
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

	}

	public Citizen save(Citizen newCitizen) {
		Citizen citizen = citizenRepository.findByEmail(newCitizen.getEmail());
		if (citizen != null) {
			citizen.setFirstName(newCitizen.getFirstName());
			citizen.setLastName(newCitizen.getLastName());
		}
		newCitizen.setPassword(bcryptEncoder.encode(newCitizen.getPassword()));
		return citizenRepository.save(newCitizen);
	}

	public Citizen updateFavoriteCities(Citizen updatedCitizen) {
		if (updatedCitizen == null) {
			return null;
		}
		Citizen citizenFromDb = citizenRepository.findByEmail(updatedCitizen.getEmail());
		if (citizenFromDb == null) {
			return null;
		}
		List<City> newFavorites = new ArrayList<City>();
		if (updatedCitizen.getFavoriteCities() != null && !updatedCitizen.getFavoriteCities().isEmpty()) {
			for (City updatedCity : updatedCitizen.getFavoriteCities()) {
				newFavorites.add(
						cityRepository.findByNameAndCountry(updatedCity.getName(), updatedCity.getCountry().getName()));
			}
		}
		citizenFromDb.setFavoriteCities(newFavorites);
		citizenRepository.save(citizenFromDb);
		return updatedCitizen;
	}

}
