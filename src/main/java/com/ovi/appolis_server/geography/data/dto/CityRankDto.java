package com.ovi.appolis_server.geography.data.dto;

import com.ovi.appolis_server.geography.data.model.City;

public class CityRankDto implements Comparable<CityRankDto> {
	private City city;

	private Double rank;

	private Long noOfProblems;

	public Long getNoOfProblems() {
		return noOfProblems;
	}

	public void setNoOfProblems(Long noOfProblems) {
		this.noOfProblems = noOfProblems;
	}

	public CityRankDto() {
		super();
	}

	public CityRankDto(City city, Double rank, Long noOfProblems) {
		super();
		this.city = city;
		this.rank = rank;
		this.noOfProblems = noOfProblems;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Double getRank() {
		return rank;
	}

	public void setRank(Double rank) {
		this.rank = rank;
	}

	@Override
	public int compareTo(CityRankDto other) {
		if (this.rank != other.rank) {
			return (int) (100 * (this.rank - other.rank));
		} else {
			return (int) (this.noOfProblems - other.noOfProblems);
		}
	}

}
