package ua.nure.baranov.dao;

import java.sql.Connection;
import java.util.List;

import ua.nure.baranov.entity.Flight;

public interface FlightDAO extends GenericDAO<Flight>{
	@Override
	List<Flight> getAll() throws DatabaseException;
	
	@Override
	Flight getById(Integer id, Connection connection) throws DatabaseException;
	
	@Override
	boolean delete(Integer id) throws DatabaseException;
}
