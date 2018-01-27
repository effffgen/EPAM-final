package ua.nure.baranov.dao;

import java.sql.Connection;
import java.util.List;

import ua.nure.baranov.dao.support.DatabaseException;
import ua.nure.baranov.entity.City;

public interface CityDAO extends GenericDAO<City>{

	@Override
	City getById(Integer id, Connection con) throws DatabaseException;
	
	@Override
	List<City> getAll() throws DatabaseException;
}
