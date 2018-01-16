package ua.nure.baranov.dao;

import ua.nure.baranov.entity.Team;

public interface FlightTeamDAO {

	Team getTeamByID(int id) throws DatabaseException;

}
