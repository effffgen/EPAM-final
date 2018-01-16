package ua.nure.baranov.dao;

import java.util.List;

import ua.nure.baranov.entity.Flight;

public interface FlightDAO {
	List<Flight> getAllFlights() throws DatabaseException;

	Flight getFlightByID(int id) throws DatabaseException;
}
