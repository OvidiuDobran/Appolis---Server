package com.ovi.appolis_server.reward.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ovi.appolis_server.reward.data.model.Reward;
import com.ovi.appolis_server.reward.data.model.RewardAcquistion;
import com.ovi.appolis_server.reward.service.RewardService;

@RestController
@RequestMapping("/reward")
@CrossOrigin(origins = "http://localhost:4200")
public class RewardController {

	@Autowired
	private RewardService rewardService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Reward createReward(@RequestBody Reward reward) {
		return rewardService.saveReward(reward);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Reward updateReward(@RequestBody Reward reward) {
		return rewardService.saveReward(reward);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Reward> getAllByCity(@RequestParam(required = true) String cityName,
			@RequestParam(required = true) String countryName) {
		return rewardService.getAllByCity(cityName, countryName);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Reward getById(@PathVariable Long id) {
		return rewardService.getById(id);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public Reward deleteReward(@PathVariable Long id) {
		return rewardService.delete(id);
	}

	@RequestMapping(value = "/acquisition", method = RequestMethod.POST)
	public RewardAcquistion createAcquisition(@RequestParam(required = true) String citizenEmail,
			@RequestParam(required = true) Long rewardId) {
		return rewardService.createAcquisition(citizenEmail, rewardId);
	}

	@RequestMapping(value = "/acquisition/all", method = RequestMethod.GET)
	public List<RewardAcquistion> getAcquisitionsByCitizenAndCity(@RequestParam(required = true) String citizenEmail,
			@RequestParam(required = true) String cityName, @RequestParam(required = true) String countryName) {
		return rewardService.getAcquisitionsByCitizenAndCity(citizenEmail, cityName, countryName);
	}

	@RequestMapping(value = "/budget", method = RequestMethod.GET)
	public Long calculateBudget(@RequestParam(required = true) String citizenEmail,
			@RequestParam(required = true) String cityName, @RequestParam(required = true) String countryName) {
		return rewardService.calculateBudget(citizenEmail, cityName, countryName);
	}

}
