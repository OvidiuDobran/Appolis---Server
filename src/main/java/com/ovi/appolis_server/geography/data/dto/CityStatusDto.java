package com.ovi.appolis_server.geography.data.dto;

import com.ovi.appolis_server.geography.data.model.City;

public class CityStatusDto {
	private City city;
	private double rank;
	private long allCount;
	private long newCount;
	private long inProgressCount;
	private long solvedCount;
	private long closedCount;
	private long rejectedCount;

	public CityStatusDto() {
		super();
	}

	public CityStatusDto(City city, double rank, long allCount, long newCount, long inProgressCount, long solvedCount,
			long closedCount, long rejectedCount) {
		super();
		this.city = city;
		this.rank = rank;
		this.allCount = allCount;
		this.newCount = newCount;
		this.inProgressCount = inProgressCount;
		this.solvedCount = solvedCount;
		this.closedCount = closedCount;
		this.rejectedCount = rejectedCount;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public double getRank() {
		return rank;
	}

	public void setRank(double rank) {
		this.rank = rank;
	}

	public long getAllCount() {
		return allCount;
	}

	public void setAllCount(long allCount) {
		this.allCount = allCount;
	}

	public long getNewCount() {
		return newCount;
	}

	public void setNewCount(long newCount) {
		this.newCount = newCount;
	}

	public long getInProgressCount() {
		return inProgressCount;
	}

	public void setInProgressCount(long inProgressCount) {
		this.inProgressCount = inProgressCount;
	}

	public long getSolvedCount() {
		return solvedCount;
	}

	public void setSolvedCount(long solvedCount) {
		this.solvedCount = solvedCount;
	}

	public long getClosedCount() {
		return closedCount;
	}

	public void setClosedCount(long closedCount) {
		this.closedCount = closedCount;
	}

	public long getRejectedCount() {
		return rejectedCount;
	}

	public void setRejectedCount(long rejectedCount) {
		this.rejectedCount = rejectedCount;
	}

}
