package com.ovi.appolis_server.problem.data.dto;

import com.ovi.appolis_server.problem.data.model.ProblemHistory;

public class ProblemHistoryDto {
	private long id;

	private long creationDate;

	private String comment;

	private String status;

	private String clerkEmail;

	private String changeType;

	public ProblemHistoryDto() {
		super();
	}

	public ProblemHistoryDto(ProblemHistory problemHistory) {
		id = problemHistory.getId();
		creationDate = problemHistory.getCreationDate().getTime();
		comment = problemHistory.getComment();
		status = problemHistory.getStatus().getName();
		clerkEmail = problemHistory.getClerk() == null ? null : problemHistory.getClerk().getEmail();
		changeType = problemHistory.getChangeType();
	}

	public ProblemHistoryDto(long creationDate, String comment, String status, String clerkEmail) {
		super();
		this.creationDate = creationDate;
		this.comment = comment;
		this.status = status;
		this.clerkEmail = clerkEmail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClerk() {
		return clerkEmail;
	}

	public void setClerk(String clerkEmail) {
		this.clerkEmail = clerkEmail;
	}

	public String getClerkEmail() {
		return clerkEmail;
	}

	public void setClerkEmail(String clerkEmail) {
		this.clerkEmail = clerkEmail;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

}
