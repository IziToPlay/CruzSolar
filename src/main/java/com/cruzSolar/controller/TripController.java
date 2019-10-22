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

import com.cruzSolar.model.entity.Trip;
import com.cruzSolar.service.BusService;
import com.cruzSolar.service.DepartmentService;
import com.cruzSolar.service.TripService;

@Controller
@RequestMapping("/trips")
public class TripController {

	@Autowired
	private TripService tripService;
	
	@Autowired
	private BusService busService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping
	public String showAllTrips(Model model) throws Exception {
		model.addAttribute("trips", tripService.getAll());
		model.addAttribute("buses", busService.getAll());
		model.addAttribute("departments", departmentService.getAll());
		return "trips/list";
	}
	
	@GetMapping("/search")
	public String searchTrip(@RequestParam("dptArrival") String dptArrival, Model model){
		try {
			if(!dptArrival.isEmpty()) {
				List<Trip> trips=tripService.fetchTripByDptArrival(dptArrival);
			if(!trips.isEmpty()) {
				model.addAttribute("trips", trips);
			}else {
				model.addAttribute("info", "No existen coincidencias");
				model.addAttribute("trips",tripService.getAll());
				}
			}else {
				model.addAttribute("info", "Debe completar el campo de búsqueda.");
				model.addAttribute("trips",tripService.getAll());
			}
		}catch(Exception e) {
			model.addAttribute("Error Trip:", e.getMessage());
		}
		return "/trips/list";
	}
}
