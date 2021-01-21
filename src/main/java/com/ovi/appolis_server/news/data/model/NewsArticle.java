package com.ovi.appolis_server.news.data.model;

import java.util.Date;

import javax.persistence.Column;
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
public class NewsArticle {

	@Id
	@GeneratedValue
	private Long id;

	private String title;

	@Column(length = 5000)
	private String content;

	private Date creationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private Clerk author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private City city;

	public NewsArticle() {
		super();
	}

	public NewsArticle(Long id, String title, String content, Date creationDate, Clerk author, City city) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.creationDate = creationDate;
		this.author = author;
		this.city = city;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonIgnore
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@JsonProperty(value = "creationDate")
	public Long getCreationDateToMillis() {
		return creationDate.getTime();
	}

	public Clerk getAuthor() {
		return author;
	}

	public void setAuthor(Clerk author) {
		this.author = author;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
