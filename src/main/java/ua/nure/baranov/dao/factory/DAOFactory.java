package ua.nure.baranov.dao.factory;

import ua.nure.baranov.dao.AttendantsDAO;
import ua.nure.baranov.dao.CityDAO;
import ua.nure.baranov.dao.FlightDAO;
import ua.nure.baranov.dao.FlightTeamDAO;
import ua.nure.baranov.dao.PlaneDAO;
import ua.nure.baranov.dao.UserDAO;
import ua.nure.baranov.dao.mysql.MysqlDAOFactory;

public abstract class DAOFactory {

	// List of DAO types supported by the factory
	public static final int MYSQL = 1;
	private static int defaultFactory = MYSQL;

	// There will be a method for each DAO that can be
	// created. The concrete factories will have to
	// implement these methods.

	
	
	public abstract UserDAO getUserDAO(); 
	
	public static DAOFactory getDAOFactory(int whichFactory) {

		switch (whichFactory) {
		case MYSQL:
			return new MysqlDAOFactory();
		default:
			return null;
		}
	}

	public static DAOFactory getDAOFactory() {
		return getDAOFactory(defaultFactory);
	}

	public static void setDefaultFactory(int whichFactory) {
		defaultFactory = whichFactory;
	}

	public abstract FlightDAO getFlightDAO();

	public abstract CityDAO getCityDAO();

	public abstract FlightTeamDAO getFlightTeamDAO();

	public abstract PlaneDAO getPlaneDAO();

	public abstract AttendantsDAO getAttendantsDAO();
}