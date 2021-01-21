package com.ovi.appolis_server.problem.data.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ovi.appolis_server.clerk.data.model.Clerk;

@Entity
public class ProblemHistory {

	@Id
	@GeneratedValue
	private Long Id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "problem_id", nullable = false)
	private Problem problem;

	private Timestamp creationDate;

	private String changeType;

	private String comment;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "status_id")
	private ProblemStatus status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "clerk_id")
	private Clerk clerk;

	public ProblemHistory(Problem problem, Timestamp creationDate, String changeType, ProblemStatus status) {
		super();
		this.problem = problem;
		this.creationDate = creationDate;
		this.changeType = changeType;
		this.status = status;
	}

	public ProblemHistory(Problem problem, Timestamp creationDate, String changeType, ProblemStatus status,
			Clerk clerk) {
		this(problem, creationDate, changeType, status);
		this.clerk = clerk;
	}

	public ProblemHistory(Problem problem, Timestamp creationDate, String changeType, ProblemStatus status, Clerk clerk,
			String comment) {
		this(problem, creationDate, changeType, status, clerk);
		this.comment = comment;
	}

	public ProblemHistory() {
		super();
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ProblemStatus getStatus() {
		return status;
	}

	public void setStatus(ProblemStatus status) {
		this.status = status;
	}

	public Clerk getClerk() {
		return clerk;
	}

	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

}
