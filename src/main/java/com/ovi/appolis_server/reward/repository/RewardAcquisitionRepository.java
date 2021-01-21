package com.ovi.appolis_server.reward.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.geography.data.model.City;
import com.ovi.appolis_server.reward.data.model.RewardAcquistion;

public interface RewardAcquisitionRepository extends Repository<RewardAcquistion, Long> {

	public RewardAcquistion save(RewardAcquistion rewardAcquistion);

	public List<RewardAcquistion> findByCitizen(Citizen citizen);

	public List<RewardAcquistion> findByCode(String code);

	public List<RewardAcquistion> findByCitizenAndCity(Citizen citizen, City city);

}
