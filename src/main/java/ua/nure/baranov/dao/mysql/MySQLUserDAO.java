package ua.nure.baranov.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.DatabaseException;
import ua.nure.baranov.dao.UserDAO;
import ua.nure.baranov.entity.Role;
import ua.nure.baranov.entity.User;

public class MySQLUserDAO implements UserDAO{
	private static final int ID_FIELD = 1;
	private static final int USERNAME_FIELD = 2;
	private static final int PASSWORD_FIELD = 3;
	private static final int ROLE_FIELD = 4;
	private static final int EMAIL_FIELD = 5;
	private static final int CREATION_TIME_FIELD = 6;
	
	private static MySQLUserDAO instance;
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String FIND_BY_LOGIN_PASS_QUERY = "SELECT * FROM user WHERE user.username=? AND user.password = ?";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM user WHERE user.id = ?";
	public static UserDAO getInstance() {
		if(instance == null) {
			instance = new MySQLUserDAO();
		}
		return instance;
	}
	
	private MySQLUserDAO(){}

	@Override
	public User createUser(User user) throws DatabaseException{
		
		return null;
	}

	@Override
	public List<User> findUsersByRole(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByLoginPass(String login, String password) throws DatabaseException {
		LOGGER.trace("Getting user from database by login and password started");
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection  = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(FIND_BY_LOGIN_PASS_QUERY);
			stmt.setString(1, login);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if(rs.next()) {
				User user = new User();
				user.setId(rs.getInt(ID_FIELD));
				user.setUsername(rs.getString(USERNAME_FIELD));
				user.setPassword(rs.getString(PASSWORD_FIELD));
				user.setRole(Role.valueOf(rs.getString(ROLE_FIELD).toUpperCase()));
				user.setEmail(rs.getString(EMAIL_FIELD));
				user.setCreationTime(rs.getDate(CREATION_TIME_FIELD));
				LOGGER.trace("Got user: " + user);
				return user;
			}
			else {
				LOGGER.trace("No appropriate users found");
				return null;
			}
		} catch (SQLException e) {
			LOGGER.error("Error during findByLoginPass: " + e.getMessage());
			throw new DatabaseException(e);
		}
		finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
	}

	@Override
	public User getUserByID(int id) throws DatabaseException {
		LOGGER.trace("Getting user from database by id started");
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection  = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(FIND_BY_ID_QUERY);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) {
				User user = new User();
				user.setId(rs.getInt(ID_FIELD));
				user.setUsername(rs.getString(USERNAME_FIELD));
				user.setPassword(rs.getString(PASSWORD_FIELD));
				user.setRole(Role.valueOf(rs.getString(ROLE_FIELD).toUpperCase()));
				user.setEmail(rs.getString(EMAIL_FIELD));
				user.setCreationTime(rs.getDate(CREATION_TIME_FIELD));
				LOGGER.trace("Got user: " + user);
				return user;
			}
			else {
				LOGGER.trace("No appropriate users found");
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
	
	


}
