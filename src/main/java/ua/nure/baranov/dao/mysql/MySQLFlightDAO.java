package ua.nure.baranov.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.DatabaseException;
import ua.nure.baranov.dao.FlightDAO;
import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.entity.Flight;

public class MySQLFlightDAO implements FlightDAO{
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String FIND_FLIGHTS_QUERY = "SELECT * from flight";
	private static FlightDAO instance = null;
	
	private static final int ID_FIELD = 1;
	private static final int DEST_ID_FIELD = 2;
	private static final int DEPART_ID_FIELD = 3;
	private static final int FLIGHT_DATE_FIELD = 4;
	private static final int PLANE_ID_FIELD = 5;
	private static final int TEAM_ID_FIELD = 6;
	private static final String FIND_BY_ID_QUERY = null;
	
	
	
	public static FlightDAO getInstance() {
		if(instance == null) {
			instance = new MySQLFlightDAO();
		}
		return instance;
	}

	private MySQLFlightDAO() {}

	@Override
	public List<Flight> getAllFlights() throws DatabaseException {
		LOGGER.trace("Getting all flights from database started");
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection  = MySQLDAOUtils.getConnection(true);
			stmt = connection.createStatement();
			rs = stmt.executeQuery(FIND_FLIGHTS_QUERY);
			List<Flight> flights = new ArrayList<Flight>();
			while(rs.next()) {
				Flight flight = new Flight();
				flight.setId(Integer.valueOf(rs.getInt(ID_FIELD)));
				flight.setFlightDate(rs.getDate(FLIGHT_DATE_FIELD));
				flight.setDepart(DAOFactory.getDAOFactory().getCityDAO().getCityByID(rs.getInt(DEPART_ID_FIELD)));
				flight.setDestination(DAOFactory.getDAOFactory().getCityDAO().getCityByID(rs.getInt(DEST_ID_FIELD)));
				//flight.setFlightTeam(DAOFactory.getDAOFactory().getFlightTeamDAO().getTeamByID(rs.getInt(TEAM_ID_FIELD)));
				flight.setPlane(DAOFactory.getDAOFactory().getPlaneDAO().getPlaneByID(rs.getInt(PLANE_ID_FIELD)));
				flights.add(flight);
			}
			return flights;
		} catch (SQLException e) {
			LOGGER.error("Error during finding flights: " + e.getMessage());
			throw new DatabaseException(e);
		}
		finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
	}

	@Override
	public Flight getFlightByID(int id) throws DatabaseException {
		LOGGER.trace("Getting single flight from database started");
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection  = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(FIND_BY_ID_QUERY);
			rs = stmt.executeQuery();
			Flight flight = null;
			if(rs.next()) {
				flight = new Flight();
				flight.setId(Integer.valueOf(rs.getInt(ID_FIELD)));
				flight.setFlightDate(rs.getDate(FLIGHT_DATE_FIELD));
				flight.setDepart(DAOFactory.getDAOFactory().getCityDAO().getCityByID(rs.getInt(DEPART_ID_FIELD)));
				flight.setDestination(DAOFactory.getDAOFactory().getCityDAO().getCityByID(rs.getInt(DEST_ID_FIELD)));
				//flight.setFlightTeam(DAOFactory.getDAOFactory().getFlightTeamDAO().getTeamByID(rs.getInt(TEAM_ID_FIELD)));
				flight.setPlane(DAOFactory.getDAOFactory().getPlaneDAO().getPlaneByID(rs.getInt(PLANE_ID_FIELD)));
			}
			
			return flight;
		} catch (SQLException e) {
			LOGGER.warn("Error during findByLoginPass: " + e.getMessage());
			throw new DatabaseException(e);
		}
		finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
	}
	
}
