package com.ensa.blockchainApp.Repositories;

import com.ensa.blockchainApp.Core.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RestResource( exported = false)
public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query("SELECT b from Block b order by b.id desc ")
    public List<Block> findAllByDateDesc();
}
