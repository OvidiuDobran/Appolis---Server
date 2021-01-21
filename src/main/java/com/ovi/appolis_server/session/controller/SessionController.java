package com.ovi.appolis_server.session.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.citizen.service.CitizenService;
import com.ovi.appolis_server.clerk.data.model.Clerk;
import com.ovi.appolis_server.clerk.service.ClerkService;
import com.ovi.appolis_server.session.data.dto.LoginForm;

@RestController
@RequestMapping("/session")
@CrossOrigin(origins = "http://localhost:4200")
public class SessionController {

	@Autowired
	private CitizenService citizenService;

	@Autowired
	private ClerkService clerkService;

	@RequestMapping(value = "/login/citizen", method = RequestMethod.POST)
	public ResponseEntity<Citizen> loginCitizen(@RequestBody LoginForm userSessionDto) throws Exception {
		Citizen loggedCitizen = citizenService.authenticate(userSessionDto);
		return loggedCitizen == null ? new ResponseEntity<Citizen>(HttpStatus.FORBIDDEN)
				: ResponseEntity.ok(loggedCitizen);
	}

	@RequestMapping(value = "/login/clerk", method = RequestMethod.POST)
	public ResponseEntity<Clerk> loginClerk(@RequestBody LoginForm loginForm) throws Exception {
		Clerk loggedClerk = clerkService.authenticate(loginForm);
		return loggedClerk == null ? 
				new ResponseEntity<Clerk>(HttpStatus.FORBIDDEN) 
				: ResponseEntity.ok(loggedClerk);
	}

}
