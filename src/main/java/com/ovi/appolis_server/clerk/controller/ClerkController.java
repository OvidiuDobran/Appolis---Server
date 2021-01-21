package com.ovi.appolis_server.clerk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ovi.appolis_server.clerk.data.model.Clerk;
import com.ovi.appolis_server.clerk.repository.ClerkRepository;

@RestController
@RequestMapping("/clerk")
@CrossOrigin(origins = "http://localhost:4200")
public class ClerkController {

	@Autowired
	private ClerkRepository clerkRepository;

	@RequestMapping(value = "/{email}", method = RequestMethod.GET)
	public Clerk getClerkByEmail(@PathVariable String email) {
		return clerkRepository.findByEmail(email);
	}
}
