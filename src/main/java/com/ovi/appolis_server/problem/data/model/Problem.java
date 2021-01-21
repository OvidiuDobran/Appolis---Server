package com.ovi.appolis_server.problem.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.geography.data.model.City;

@Entity
@Table(name = "problem")
public class Problem {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private ProblemCategory category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private City city;

	private String description;

	private Double latitude;

	private Double longitude;

	private String address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "citizen_id")
	private Citizen citizen;

	@OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ProblemHistory> histories = new ArrayList<ProblemHistory>();

	@OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ProblemImage> images = new ArrayList<ProblemImage>();

	private Long rating;

	public Problem(ProblemCategory category, City city, String description, Double latitude, Double longitude,
			String address, Citizen citizen) {
		super();
		this.category = category;
		this.city = city;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.citizen = citizen;
	}

	public Problem() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProblemCategory getCategory() {
		return category;
	}

	public void setCategory(ProblemCategory category) {
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

	public Citizen getCitizen() {
		return citizen;
	}

	public void setCitizen(Citizen citizen) {
		this.citizen = citizen;
	}

	public List<ProblemHistory> getHistories() {
		return histories;
	}

	public void setHistories(List<ProblemHistory> histories) {
		this.histories = histories;
	}

	public void addHistory(ProblemHistory history) {
		if (histories == null) {
			histories = new ArrayList<>();
		}
		histories.add(history);
	}

	public List<ProblemImage> getImages() {
		return images;
	}

	public void setImages(List<ProblemImage> images) {
		this.images = images;
	}

	public void addImage(ProblemImage image) {
		if (images == null) {
			images = new ArrayList<>();
		}
		images.add(image);
	}

	public ProblemStatus getCurrentStatus() {
		ProblemHistory currentHistory = getCurrentHistory();
		if (currentHistory == null) {
			return null;
		}
		return currentHistory.getStatus();
	}

	public ProblemHistory getCurrentHistory() {
		if (histories == null || histories.isEmpty()) {
			return null;
		}
		ProblemHistory currentHistory = histories.get(0);
		for (ProblemHistory history : histories) {
			if (history.getCreationDate().after(currentHistory.getCreationDate())) {
				currentHistory = history;
			}
		}
		return currentHistory;
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
