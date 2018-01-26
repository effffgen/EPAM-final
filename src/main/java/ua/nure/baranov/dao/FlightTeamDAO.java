package ua.nure.baranov.dao;

import java.sql.Connection;

import ua.nure.baranov.entity.Team;

public interface FlightTeamDAO extends GenericDAO<Team>{
	@Override
	Team getById(Integer id, Connection connection) throws DatabaseException;
	
	
	
}
