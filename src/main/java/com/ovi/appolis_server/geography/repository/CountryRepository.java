package com.ovi.appolis_server.geography.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.ovi.appolis_server.geography.data.model.Country;

public interface CountryRepository extends Repository<Country, Long> {

	public Country save(Country country);

	public List<Country> findByName(String countryName);

}
