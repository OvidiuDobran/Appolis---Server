package com.ovi.appolis_server.problem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.ovi.appolis_server.citizen.data.model.Citizen;
import com.ovi.appolis_server.geography.data.model.City;
import com.ovi.appolis_server.problem.data.model.Problem;
import com.ovi.appolis_server.problem.data.model.ProblemStatus;

public interface ProblemRepository extends Repository<Problem, Long> {

	public Problem save(Problem problem);

	public List<Problem> findAll();

	public Problem findById(Long id);

	@Query("SELECT DISTINCT ph.problem FROM ProblemHistory ph WHERE ph.status=:status")
	public List<Problem> findByStatus(@Param("status") ProblemStatus status);

	@Query("SELECT DISTINCT ph.problem FROM ProblemHistory ph WHERE ph.status=:status AND ph.problem.city=:city")
	public List<Problem> findByStatusAndCity(@Param("status") ProblemStatus status, @Param("city") City city);

	@Query("SELECT DISTINCT ph.problem FROM ProblemHistory ph "
			+ "WHERE ph.status=:status AND ph.problem.city=:city AND ph.problem.citizen=:citizen")
	public List<Problem> findByStatusAndCityAndCitizen(@Param("status") ProblemStatus status, @Param("city") City city,
			@Param("citizen") Citizen citizen);

	@Query("SELECT DISTINCT ph.problem FROM ProblemHistory ph WHERE ph.status IN :status AND ph.problem.citizen=:citizen AND ph.problem.city=:city")
	public List<Problem> findByCitizenAndStatusListInCity(@Param("citizen") Citizen citizen,
			@Param("status") List<ProblemStatus> statusList, @Param("city") City city);

	public List<Problem> findByCitizen(Citizen citizen);

	public List<Problem> findByCity(City city);

}
