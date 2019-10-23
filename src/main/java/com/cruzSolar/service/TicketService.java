package com.cruzSolar.service;

import java.util.List;

import com.cruzSolar.model.entity.Client;
import com.cruzSolar.model.entity.Ticket;

public interface TicketService extends CrudService<Ticket, Long> {
	
	
	List<Ticket> fetchTicketByFechaEmission(String emision);
}
