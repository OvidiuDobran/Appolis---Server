package com.ovi.appolis_server.reward.data.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ovi.appolis_server.clerk.data.model.Clerk;
import com.ovi.appolis_server.geography.data.model.City;

@Entity
public class Reward {
	@Id
	@GeneratedValue
	private Long id;

	private String title;

	private Timestamp creationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private City city;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private Clerk author;

	private long cost;

	private String description;

	public Reward() {
		super();
	}

	public Reward(String title, Timestamp creationDate, City city, Clerk author, long cost, String description) {
		super();
		this.title = title;
		this.creationDate = creationDate;
		this.city = city;
		this.author = author;
		this.cost = cost;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@JsonProperty(value = "author")
	public Clerk getAuthor() {
		return author;
	}

	public void setAuthor(Clerk author) {
		this.author = author;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public Timestamp getCreationDate() {
		return creationDate;
	}

	@JsonProperty(value = "creationDate")
	public Long getCreationDateToMillis() {
		return creationDate.getTime();
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

}
