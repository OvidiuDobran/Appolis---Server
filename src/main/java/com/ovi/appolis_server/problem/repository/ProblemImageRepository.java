package com.ovi.appolis_server.problem.repository;

import org.springframework.data.repository.Repository;

import com.ovi.appolis_server.problem.data.model.ProblemImage;

public interface ProblemImageRepository extends Repository<ProblemImage, Long> {

	public static final String UPLOAD_DIR = "/appolis/uploads/problems/";

	public ProblemImage save(ProblemImage image);
}
