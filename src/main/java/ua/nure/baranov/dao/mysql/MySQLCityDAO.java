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

import ua.nure.baranov.dao.CityDAO;
import ua.nure.baranov.dao.support.DatabaseException;
import ua.nure.baranov.dao.support.ResultSetProcessor;
import ua.nure.baranov.entity.City;

public class MySQLCityDAO implements CityDAO {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String GET_BY_ID_QUERY = "SELECT * FROM City WHERE City.id = ?";
	private static final String GET_ALL_BY_QUERY = null;
	private static MySQLCityDAO instance = null;

	@Override
	public City getById(Integer id, Connection connection) throws DatabaseException {
		LOGGER.trace("Getting city from database by id started");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(GET_BY_ID_QUERY);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				City city = assembleCity.process(rs);
				LOGGER.trace("Got city: " + city);
				return city;
			} else {
				LOGGER.warn("No cities with that id found, id = " + id);
				return null;
			}
		} catch (SQLException e) {
			LOGGER.error("Error during City.findById: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
		}
	}

	public static synchronized CityDAO getInstance() {
		if (instance == null) {
			instance = new MySQLCityDAO();
		}
		return instance;
	}

	private MySQLCityDAO() {
	}

	@Override
	public List<City> getAll() throws DatabaseException {
		LOGGER.trace("Getting all cities from database started");
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<City> cities = new ArrayList<>();
		try {
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.createStatement();
			rs = stmt.executeQuery(GET_ALL_BY_QUERY);
			while (rs.next()) {
				City city = assembleCity.process(rs);
				LOGGER.trace("Got city: " + city);
				cities.add(city);
			}
			return cities;
		} catch (SQLException e) {
			LOGGER.error("Error during City.findAll: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
		}
	}

	private static final ResultSetProcessor<City> assembleCity = (ResultSet rs) ->
	{
		City city = new City();
		city.setId(rs.getInt(1));
		city.setName(rs.getString(2));
		return city;
	};

}
