package com.ovi.appolis_server.citizen.repository;

import org.springframework.data.repository.Repository;

import com.ovi.appolis_server.citizen.data.model.Citizen;

public interface CitizenRepository extends Repository<Citizen, Long> {

	public Citizen save(Citizen citizen);

	public Citizen findByEmail(String email);

}
