package com.ensa.blockchainApp.Repositories;

import com.ensa.blockchainApp.Business.ReportedProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RestResource( exported = false)
public interface ReportedProblemRepo extends JpaRepository<ReportedProblem, Long> {

    @Query("SELECT b from ReportedProblem b order by b.id desc ")
    public List<ReportedProblem> findAllByDateDesc();

}
