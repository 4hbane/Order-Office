package com.ensa.blockchainApp.Repositories;

import com.ensa.blockchainApp.Business.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RestResource( exported = false)
public interface ContactRepo extends JpaRepository<Contact, Long> {

    @Query("SELECT b from Contact b order by b.id desc ")
    public List<Contact> findAllByDateDesc();

}
