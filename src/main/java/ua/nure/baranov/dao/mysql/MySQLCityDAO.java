package ua.nure.baranov.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.CityDAO;
import ua.nure.baranov.dao.DatabaseException;
import ua.nure.baranov.entity.City;

public class MySQLCityDAO implements CityDAO {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String GET_BY_ID_QUERY = "SELECT * FROM City WHERE City.id = ?";
	private static MySQLCityDAO instance = null;

	@Override
	public City getCityByID(int id) throws DatabaseException {
		
		LOGGER.trace("Getting user from database by id started");
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection  = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(GET_BY_ID_QUERY);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) {
				City city = new City();
				city.setId(rs.getInt(1));
				city.setName(rs.getString(2));
				LOGGER.trace("Got city: " + city);
				return city;
			}
			else {
				LOGGER.warn("No cities with that id found, id = "+id);
				return null;
			}
		} catch (SQLException e) {
			LOGGER.error("Error during findById: " + e.getMessage());
			throw new DatabaseException(e);
		}
		finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
	}

	
	public static synchronized CityDAO getInstance() {
		if(instance == null) {
			instance  = new MySQLCityDAO();
		}
		return instance;
	}

	private MySQLCityDAO() {}

	
}
