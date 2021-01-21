package com.ovi.appolis_server.problem.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.ovi.appolis_server.problem.data.model.ProblemCategory;

public interface ProblemCategoryRepository extends Repository<ProblemCategory, Long> {

	public ProblemCategory save(ProblemCategory category);

	public List<ProblemCategory> findAll();

	public ProblemCategory findByDescription(String description);

}
