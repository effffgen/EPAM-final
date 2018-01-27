package ua.nure.baranov.dao.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.AttendantsDAO;
import ua.nure.baranov.dao.CityDAO;
import ua.nure.baranov.dao.FlightDAO;
import ua.nure.baranov.dao.FlightTeamDAO;
import ua.nure.baranov.dao.PlaneDAO;
import ua.nure.baranov.dao.RequestDAO;
import ua.nure.baranov.dao.UserDAO;
import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.dao.support.DatabaseException;

public class MysqlDAOFactory extends DAOFactory {
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public UserDAO getUserDAO() {
		return MySQLUserDAO.getInstance();
	}

	@Override
	public FlightDAO getFlightDAO() {
		return MySQLFlightDAO.getInstance();
	}

	@Override
	public CityDAO getCityDAO() {
		return MySQLCityDAO.getInstance();
	}

	@Override
	public FlightTeamDAO getFlightTeamDAO() {
		return MySQLFLightTeamDAO.getInstance();
	}

	@Override
	public PlaneDAO getPlaneDAO() {
		return MySQLPlaneDAO.getInstance();
	}

	@Override
	public AttendantsDAO getAttendantsDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDAO getRequestsDAO() {
		return MySQLRequestDAO.getInstance();
	}

	private static DataSource ds = null;

	static Connection getConnection() throws SQLException {
		Connection con = null;
		try {
		if (ds == null) {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/mysql");
		}
		con = ds.getConnection();
		}catch(SQLException | NamingException e) {
			LOGGER.fatal("Cannot obtain connection : " + e.getMessage());
		}
		return con;
	}

	static void close(AutoCloseable autoClosable) throws DatabaseException {
		try {
			autoClosable.close();
		} catch (Exception e) {
			LOGGER.error("Error while closing database resource: " + e.getMessage());
			throw new DatabaseException(e);
		}

	}
}
