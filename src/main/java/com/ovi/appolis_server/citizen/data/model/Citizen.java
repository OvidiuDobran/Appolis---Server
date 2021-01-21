package com.ovi.appolis_server.citizen.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.ovi.appolis_server.geography.data.model.City;

@Entity
public class Citizen {
	@Id
	@GeneratedValue
	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	@Transient
	private String token;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "favorite_cities", joinColumns = { @JoinColumn(name = "citizen_id") }, inverseJoinColumns = {
			@JoinColumn(name = "city_id") })
	private List<City> favoriteCities = new ArrayList<City>();

	public Citizen(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public Citizen() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<City> getFavoriteCities() {
		return favoriteCities;
	}

	public void setFavoriteCities(List<City> favoriteCities) {
		this.favoriteCities = favoriteCities;
	}

}
