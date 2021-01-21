package com.ovi.appolis_server.reward.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.ovi.appolis_server.geography.data.model.City;
import com.ovi.appolis_server.reward.data.model.Reward;

public interface RewardRepository extends Repository<Reward, Long> {

	public Reward save(Reward reward);

	public Reward delete(Reward reward);

	public Reward findById(Long id);

	public List<Reward> findByCity(City city);

}
