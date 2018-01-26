package ua.nure.baranov.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
	

	private static FlightTeamDAO instance = null;
	
	public static synchronized FlightTeamDAO getInstance() {
		if (instance == null) {
			instance = new MySQLFLightTeamDAO();
		}
		return instance;
	}

	private MySQLFLightTeamDAO() {}

	@Override
	public Team create(Team t) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Team> getAll() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Team t) throws DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Integer id) throws DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Team getById(Integer id, Connection connection) throws DatabaseException {
		LOGGER.debug("Getting team from database started");
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
				team.setAeronavigator(DAOFactory.getDAOFactory().getUserDAO().getById(rs.getInt(NAVIGATOR_FIELD), connection));
				team.setFirstPilot(DAOFactory.getDAOFactory().getUserDAO().getById(rs.getInt(FIRST_PILOT_FIELD), connection));
				team.setSecondPilot(DAOFactory.getDAOFactory().getUserDAO().getById(rs.getInt(SECOND_PILOT_FIELD), connection));
			//	team.setAttendants(DAOFactory.getDAOFactory().getAttendantsDAO().getAttendantsOfTeam(rs.getInt(id)));
			}
			LOGGER.trace("Got team " + team);
			return team;
		} catch (SQLException e) {
			LOGGER.error("Error during getTeamById: " + e.getMessage());
			throw new DatabaseException(e);
		}
		finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
		}
	}


	
	
}
