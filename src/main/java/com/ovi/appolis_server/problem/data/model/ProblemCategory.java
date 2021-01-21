package com.ovi.appolis_server.problem.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProblemCategory {

	@Id
	@GeneratedValue
	private Long id;

	private String description;

	public ProblemCategory() {
		super();
	}

	public ProblemCategory(String description) {
		super();
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
