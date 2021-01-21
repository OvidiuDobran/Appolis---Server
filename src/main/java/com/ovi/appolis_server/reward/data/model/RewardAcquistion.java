package com.ovi.appolis_server.reward.data.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.geography.data.model.City;

@Entity
public class RewardAcquistion {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "citizen_id")
	private Citizen citizen;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;

	private long cost;

	private String title;

	private String description;

	private Date creationDate;

	private Date dueDate;

	private String code;

	public RewardAcquistion() {
		super();
	}

	public RewardAcquistion(Citizen citizen, City city, long cost, String title, String description, Date creationDate,
			String code) {
		super();
		this.citizen = citizen;
		this.city = city;
		this.cost = cost;
		this.title = title;
		this.description = description;
		this.creationDate = creationDate;
		this.code = code;
	}

	public RewardAcquistion(Citizen citizen, Reward reward, String code) {
		this(citizen, reward.getCity(), reward.getCost(), reward.getTitle(), reward.getDescription(), new Date(), code);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Citizen getCitizen() {
		return citizen;
	}

	public void setCitizen(Citizen citizen) {
		this.citizen = citizen;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty(value = "creationDate")
	public long getCreationDateToMillis() {
		return creationDate.getTime();
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

}
