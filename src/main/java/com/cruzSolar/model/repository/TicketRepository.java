package com.cruzSolar.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cruzSolar.model.entity.Client;
import com.cruzSolar.model.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

	@Query("select t from Ticket t where t.emissionDate like ?1")
	List <Ticket> fetchTicketByFechaEmission(String emision);
}
