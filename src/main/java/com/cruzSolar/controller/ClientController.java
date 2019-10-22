package com.cruzSolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cruzSolar.model.entity.Client;
import com.cruzSolar.service.ClientService;

 

@Controller
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	
	private Client client;
	
	@GetMapping
    public String showAllClients(Model model){
		try {
        model.addAttribute("clients", clientService.getAll());
		}catch (Exception e){
			model.addAttribute("Error Cliente:", e.getMessage());
		}
        return "/tickets/new";
    }
	
	@GetMapping("/search")
	public void searchClient(@RequestParam("dni") String dni, Model model){
		try {
			if(!dni.isEmpty()) {
				List<Client> clients=clientService.fetchClientByDni(dni);
			if(!clients.isEmpty()) {
				model.addAttribute("clients", clients);
			}else {
				model.addAttribute("info", "No existen coincidencias");
				model.addAttribute("clients",clientService.getAll());
				}
			}else {
				model.addAttribute("info", "Debe completar el campo de búsqueda.");
				model.addAttribute("clients",clientService.getAll());
			}
		}catch(Exception e) {
			model.addAttribute("Error Cliente:", e.getMessage());
			
		}	
	}
	
	@GetMapping("/new")
    public String newClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "/clients/new";
    }
	
	@PostMapping("/save")
    public String saveNewClient(Client client) throws Exception {
        long id=clientService.create(client);
        return "tickets/new";
    }

	
	@GetMapping("/edit/{id}")
    public String editClientForm(@PathVariable("id") long id, Model model) throws Exception {
        Client client = clientService.getOneById(id);
        model.addAttribute("client", client);
        return "clients/edit";
    }
	

	
	@PostMapping("/update/{id}")
    public String updateClient(@PathVariable("id") long id, Client client) throws Exception {
        clientService.update(id, client);
        return "tickets/new";    
    }

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
