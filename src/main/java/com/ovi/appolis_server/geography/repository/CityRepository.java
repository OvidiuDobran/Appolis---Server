package com.ovi.appolis_server.geography.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.geography.data.model.City;

public interface CityRepository extends Repository<City, Long> {

	public City save(City city);

	public List<City> findAll();

	public List<City> findByName(String cityName);

	@Query("SELECT city FROM City city WHERE city.name=:cityName AND city.country IN "
			+ "	(SELECT country FROM Country country WHERE country.name=:countryName)")
	public City findByNameAndCountry(@Param("cityName") String city, @Param("countryName") String country);

	@Query("SELECT city FROM City city WHERE city IN (SELECT p.city FROM Problem p WHERE p.citizen=:citizen)")
	public List<City> findByCitizen(@Param("citizen") Citizen citizen);

}
