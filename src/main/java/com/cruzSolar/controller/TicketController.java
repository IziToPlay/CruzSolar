package com.cruzSolar.controller;

import java.util.List;

import javax.persistence.Access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cruzSolar.model.entity.Client;
import com.cruzSolar.model.entity.Seat;
import com.cruzSolar.model.entity.Ticket;
import com.cruzSolar.model.entity.Trip;
import com.cruzSolar.service.ClientService;
import com.cruzSolar.service.SeatService;
import com.cruzSolar.service.TicketService;
import com.cruzSolar.service.TripService;

@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private SeatService seatService;
	
	@Autowired
	private TripService tripService;
	
	private Client client;
	private Trip trip;
	
	@GetMapping("/list")
    public String showAllTickets(Model model) throws Exception {
        model.addAttribute("tickets", ticketService.getAll());
        return "tickets/list";
    }
	
	@GetMapping("/new/{id}")
	 public String newTicketForm(@PathVariable("id") long id, Model model) throws Exception{
		model.addAttribute("ticket", new Ticket());
		List<Client> clients = clientService.getAll();
		model.addAttribute("clients",clients);
		trip=tripService.getOneById(id);
		List<Seat> seats = seatService.findAllSeatsAvailables(id);
		model.addAttribute("seats",seats);
		return "tickets/new";
	}
	
	@GetMapping("/searchClient")
	public void searchClient(@RequestParam("dni") String dni, Model model){
		
		List<Client> clients=clientService.fetchClientByDni(dni);
		model.addAttribute("clients", clients);
		
	}
	
	@GetMapping("/connect/{id}")
	public void connectClient(@PathVariable("id") long id) throws Exception {
		client=clientService.getOneById(id);
	}
	
	
	
	@PostMapping("/save")
    public String saveNewTicket(Ticket ticket) throws Exception {
		ticket.setTrip(trip);
		ticket.setClient(client);
        long id = ticketService.create(ticket);
        return "redirect:/tickets";
    }
	
	@GetMapping("/edit/{id}")
    public String editTicketForm(@PathVariable("id") long id, Model model) throws Exception {
        
		Ticket ticket= ticketService.getOneById(id);
        List<Client> clients = clientService.getAll();
		model.addAttribute("clients",clients);
		List<Seat> seats = seatService.findAllSeatsAvailables(ticket.getTrip().getBus().getId());
		model.addAttribute("seats",seats);
		model.addAttribute("ticket",ticket);
		
        return "tickets/edit";
    }

	@PostMapping("/update/{id}")
    public String updateTicket(@PathVariable("id") long id, Ticket ticket) throws Exception {
        ticketService.update(id, ticket);
        return "redirect:/tickets";    
    }

	public TicketService getTicketService() {
		return ticketService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	public SeatService getSeatService() {
		return seatService;
	}

	public void setSeatService(SeatService seatService) {
		this.seatService = seatService;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	

}
