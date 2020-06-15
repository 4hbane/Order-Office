package com.ensa.blockchainApp.Repositories;

import com.ensa.blockchainApp.Business.Traceability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RestResource( exported = false)
public interface TracabilityRepository extends JpaRepository<Traceability, Long> {


    @Query("SELECT t from Traceability t order by t.id desc ")
    public List<Traceability> findAllByDateDesc();
}
