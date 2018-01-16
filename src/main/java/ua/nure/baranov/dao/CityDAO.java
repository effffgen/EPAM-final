package ua.nure.baranov.dao;

import ua.nure.baranov.entity.City;

public interface CityDAO {

	City getCityByID(int id) throws DatabaseException;
	
}
