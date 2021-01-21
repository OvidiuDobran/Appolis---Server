package com.ovi.appolis_server.problem.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.ovi.appolis_server.problem.data.model.ProblemHistory;
import com.ovi.appolis_server.problem.data.model.ProblemStatus;

public interface ProblemHistoryRepository extends Repository<ProblemHistory, Long> {

	public enum ChangeType {
		COMMENT, STATUS, CREATION;
	}

	public ProblemHistory save(ProblemHistory history);

	public List<ProblemHistory> findByStatus(ProblemStatus status);

}
