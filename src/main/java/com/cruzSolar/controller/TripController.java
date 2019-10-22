package com.cruzSolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cruzSolar.model.entity.Client;
import com.cruzSolar.model.entity.Department;
import com.cruzSolar.model.entity.Seat;
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
	
	List<Trip> trips;
	
	@GetMapping
	public String showAllTrips(Model model) throws Exception {
		model.addAttribute("trips", tripService.getAll());
		model.addAttribute("buses", busService.getAll());
		model.addAttribute("departments", departmentService.getAll());
		return "trips/list";
	}
	
	
	public  List<Trip> searchTrip(String dptDeparture, Model model){
		try {
			if(!dptDeparture.isEmpty()) {
				trips=tripService.fetchTripByDptDeparture(dptDeparture);
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
		return trips;
	}
	
	@GetMapping("/searchTrip")
	public String searchTrips(@RequestParam("dptDeparture") String dptDeparture, Model model) throws Exception{
		model.addAttribute("trip", new Trip());
		List<Trip> trips=searchTrip(dptDeparture, model);
		model.addAttribute("trips", trips);
		return "trips/list";
		
	}
}
