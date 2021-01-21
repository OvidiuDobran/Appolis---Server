package com.ovi.appolis_server.problem.repository;

import org.springframework.data.repository.Repository;

import com.ovi.appolis_server.problem.data.model.ProblemStatus;

public interface ProblemStatusRepository extends Repository<ProblemStatus, Long> {

	public static enum Status {
		NEW, IN_PROGRESS, RESOLVED, CLOSED, REJECTED
	}

	public ProblemStatus save(ProblemStatus status);

	public ProblemStatus findByName(String name);

}
