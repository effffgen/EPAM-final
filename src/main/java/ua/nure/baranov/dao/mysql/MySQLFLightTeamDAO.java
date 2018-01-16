package ua.nure.baranov.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.DatabaseException;
import ua.nure.baranov.dao.FlightTeamDAO;
import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.entity.Team;

public class MySQLFLightTeamDAO implements FlightTeamDAO {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String FIND_TEAM_BY_ID_QUERY = "SELECT * FROM flight_team";

	private static final int ID_FIELD = 1;
	private static final int NAVIGATOR_FIELD = 4;
	private static final int FIRST_PILOT_FIELD = 2;
	private static final int SECOND_PILOT_FIELD = 3;
	
	
	
	@Override
	public Team getTeamByID(int id) throws DatabaseException {
		LOGGER.trace("Getting team from database started");
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection  = MySQLDAOUtils.getConnection(true);
			stmt = connection.createStatement();
			rs = stmt.executeQuery(FIND_TEAM_BY_ID_QUERY);
			Team team = null;
			if(rs.next()) {
				team = new Team();
				team.setId(rs.getInt(ID_FIELD));
				team.setAeronavigator(DAOFactory.getDAOFactory().getUserDAO().getUserByID(rs.getInt(NAVIGATOR_FIELD)));
				team.setFirstPilot(DAOFactory.getDAOFactory().getUserDAO().getUserByID(rs.getInt(FIRST_PILOT_FIELD)));
				team.setSecondPilot(DAOFactory.getDAOFactory().getUserDAO().getUserByID(rs.getInt(SECOND_PILOT_FIELD)));
				team.setAttendants(DAOFactory.getDAOFactory().getAttendantsDAO().getAttendantsOfTeam(rs.getInt(id)));
			}
			return team;
		} catch (SQLException e) {
			LOGGER.error("Error during getTeamById: " + e.getMessage());
			throw new DatabaseException(e);
		}
		finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
	}
	

	private static FlightTeamDAO instance = null;
	
	public static FlightTeamDAO getInstance() {
		if (instance == null) {
			instance = new MySQLFLightTeamDAO();
		}
		return instance;
	}

	private MySQLFLightTeamDAO() {}
	
	
}
