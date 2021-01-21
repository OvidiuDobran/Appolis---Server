package com.ovi.appolis_server.citizen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.citizen.service.CitizenService;

@RestController
@RequestMapping("/citizen")
public class CitizenController {

	@Autowired
	private CitizenService citizenService;

	@RequestMapping(value = "/favorite-cities", method = RequestMethod.PUT)
	public Citizen updateFavoriteCities(@RequestBody Citizen citizen) {
		return citizenService.updateFavoriteCities(citizen);
	}
}
