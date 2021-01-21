package com.ovi.appolis_server.session.data.dto;

public class LoginForm {

	private String email;

	private String password;

	private String cityName;

	private String countryName;

	public LoginForm(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public LoginForm() {
		super();
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
