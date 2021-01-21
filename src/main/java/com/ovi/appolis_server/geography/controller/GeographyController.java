package com.ovi.appolis_server.geography.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ovi.appolis_server.geography.data.dto.CityRankDto;
import com.ovi.appolis_server.geography.data.dto.CityStatusDto;
import com.ovi.appolis_server.geography.data.model.City;
import com.ovi.appolis_server.geography.service.GeographyService;

@RestController
@RequestMapping("/geography")
@CrossOrigin(origins = "http://localhost:4200")
public class GeographyController {

	@Autowired
	private GeographyService geographyService;

	@RequestMapping(value = "/cities", method = RequestMethod.GET)
	@ResponseBody
	public List<City> getCities() {
		return geographyService.getCities();
	}

	@RequestMapping(value = "/cities/{citizenEmail}", method = RequestMethod.GET)
	@ResponseBody
	public List<City> getUserCities(@PathVariable String citizenEmail) {
		return geographyService.getUserCities(citizenEmail);
	}

	@RequestMapping(value = "/cities/ranking", method = RequestMethod.GET)
	@ResponseBody
	public List<CityRankDto> getRanking() {
		return geographyService.calculateCitiesRanking();
	}

	@RequestMapping(value = "/city/status", method = RequestMethod.GET)
	@ResponseBody
	public CityStatusDto getCityStatus(@RequestParam(required = true) String cityName,
			@RequestParam(required = true) String countryName) {
		return geographyService.calculateCityStatus(cityName, countryName);
	}
}
