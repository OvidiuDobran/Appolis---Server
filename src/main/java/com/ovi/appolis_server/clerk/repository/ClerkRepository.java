package com.ovi.appolis_server.clerk.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.ovi.appolis_server.clerk.data.model.Clerk;
import com.ovi.appolis_server.geography.data.model.City;

public interface ClerkRepository extends Repository<Clerk, Long> {

	public Clerk save(Clerk clerk);

	public Clerk findByEmail(String email);

	@Query("SELECT clerk FROM Clerk clerk JOIN City city ON clerk.city=city AND city.name=:cityName "
			+ "JOIN Country country ON clerk.city.country=country AND country.name=:countryName "
			+ "WHERE clerk.email=:email")
	public Clerk findByEmailAndCityAndCountry(@Param("email") String email, @Param("cityName") String cityName,
			@Param("countryName") String countryName);

	public Clerk findByEmailAndCity(String clerkEmail, City city);

}
