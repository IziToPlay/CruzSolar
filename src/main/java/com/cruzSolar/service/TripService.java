package com.cruzSolar.service;

import java.util.List;

import com.cruzSolar.model.entity.Trip;

public interface TripService extends CrudService<Trip, Long>{
	
	//Listar los viajes según filtro de búsqueda de dptoArrival
	List<Trip> fetchTripByDptArrival(String dptArrival) throws Exception;
	
	
	List<Trip> fetchTripByDptArrival(String depa1,String depa2, String date) throws Exception;
	
	
}
