package com.ovi.appolis_server.problem.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ovi.appolis_server.geography.data.model.City;
import com.ovi.appolis_server.problem.data.model.Problem;
import com.ovi.appolis_server.problem.data.model.ProblemImage;

public class ProblemDto {
	private long id;

	private String category;

	private City city;

	private String description;

	private Double latitude;

	private Double longitude;

	private String address;

	private String citizenEmail;

	private List<ProblemHistoryDto> historyDtos;

	private List<ProblemImage> images;

	private Long rating;

	public ProblemDto() {
		super();
	}

	public ProblemDto(long id, String category, City city, String description, Double latitude, Double longitude,
			String address, String citizenEmail, List<ProblemHistoryDto> historyDtos, List<ProblemImage> images,
			Long rating) {
		super();
		this.id = id;
		this.category = category;
		this.city = city;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.citizenEmail = citizenEmail;
		this.images = images;
		this.rating = rating;
	}

	public ProblemDto(Problem problem) {
		id = problem.getId();
		category = problem.getCategory().getDescription();
		city = problem.getCity();
		description = problem.getDescription();
		latitude = problem.getLatitude();
		longitude = problem.getLongitude();
		address = problem.getAddress();
		citizenEmail = problem.getCitizen().getEmail();
		historyDtos = problem.getHistories().stream().map(history -> new ProblemHistoryDto(history))
				.collect(Collectors.toList());
		images = problem.getImages();
		rating = problem.getRating();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getCitizenEmail() {
		return citizenEmail;
	}

	public void setCitizenEmail(String citizenEmail) {
		this.citizenEmail = citizenEmail;
	}

	@JsonProperty("histories")
	public List<ProblemHistoryDto> getHistoryDtos() {
		return historyDtos;
	}

	public void setHistoryDtos(List<ProblemHistoryDto> historyDtos) {
		this.historyDtos = historyDtos;
	}

	public List<ProblemImage> getImages() {
		return images;
	}

	public void setImages(List<ProblemImage> images) {
		this.images = images;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
