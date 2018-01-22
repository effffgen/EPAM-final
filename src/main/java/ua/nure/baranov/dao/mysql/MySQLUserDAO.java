package ua.nure.baranov.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.DatabaseException;
import ua.nure.baranov.dao.UserDAO;
import ua.nure.baranov.entity.Role;
import ua.nure.baranov.entity.User;

public class MySQLUserDAO implements UserDAO {
	private static final int ID_FIELD = 1;
	private static final int USERNAME_FIELD = 2;
	private static final int PASSWORD_FIELD = 3;
	private static final int FIRSTNAME_FIELD = 4;
	private static final int LASTNAME_FIELD = 5;
	private static final int ROLE_FIELD = 6;
	private static final int EMAIL_FIELD = 7;
	private static final int CREATION_TIME_FIELD = 8;

	private static MySQLUserDAO instance;
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String FIND_BY_LOGIN_PASS_QUERY = "SELECT * FROM user WHERE user.username=? AND user.password = ?";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM user WHERE user.id = ?";
	private static final String IF_USERNAME_EXISTS_QUERY = "SELECT count(*) FROM user WHERE user.username = ?";
	private static final String CREATE_USER_QUERY = "INSERT INTO user (id, username, password, first_name, last_name, role, email, creation_time) VALUES (DEFAULT, ?, ?, ?, ?, 'none', ? , DEFAULT)";

	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new MySQLUserDAO();
		}
		return instance;
	}

	private MySQLUserDAO() {
	}

	@Override
	public User createUser(User user) throws DatabaseException {
		LOGGER.trace("User creation started");
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int n = 0;
		try {
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			n = stmt.executeUpdate();
			
			if(n == 1) {
				rs = stmt.getGeneratedKeys();
				user.setRole(Role.NONE);
				if(rs.next()) {
				user.setId(rs.getInt(1));}
				return user;
			}
			else return null;
		} catch (SQLException e) {
			LOGGER.error("Error during createUser: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
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
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(FIND_BY_LOGIN_PASS_QUERY);
			stmt.setString(1, login);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(ID_FIELD));
				user.setUsername(rs.getString(USERNAME_FIELD));
				user.setPassword(rs.getString(PASSWORD_FIELD));
				user.setFirstName(rs.getString(FIRSTNAME_FIELD));
				user.setLastName(rs.getString(LASTNAME_FIELD));
				user.setRole(Role.valueOf(rs.getString(ROLE_FIELD).toUpperCase()));
				user.setEmail(rs.getString(EMAIL_FIELD));
				user.setCreationTime(rs.getDate(CREATION_TIME_FIELD));
				LOGGER.trace("Got user: " + user);
				return user;
			} else {
				LOGGER.trace("No appropriate users found");
				return null;
			}
		} catch (SQLException e) {
			LOGGER.error("Error during findByLoginPass: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
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
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(FIND_BY_ID_QUERY);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(ID_FIELD));
				user.setUsername(rs.getString(USERNAME_FIELD));
				user.setFirstName(rs.getString(FIRSTNAME_FIELD));
				user.setLastName(rs.getString(LASTNAME_FIELD));
				user.setPassword(rs.getString(PASSWORD_FIELD));
				user.setRole(Role.valueOf(rs.getString(ROLE_FIELD).toUpperCase()));
				user.setEmail(rs.getString(EMAIL_FIELD));
				user.setCreationTime(rs.getDate(CREATION_TIME_FIELD));
				LOGGER.trace("Got user: " + user);
				return user;
			} else {
				LOGGER.trace("No appropriate users found");
				return null;
			}
		} catch (SQLException e) {
			LOGGER.error("Error during findById: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
	}

	@Override
	public boolean isUsernameTaken(String username) throws DatabaseException {
		LOGGER.trace("Checking username for availability");
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(IF_USERNAME_EXISTS_QUERY);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getBoolean(1);
			} else {
				LOGGER.error("Count query returned no results, there might be an error in SQL syntax");
				throw new DatabaseException("Count query returned no results, there might be an error in SQL syntax");
			}
		} catch (SQLException e) {
			LOGGER.error("Error during isUsernameTaken: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);

		}
	}

}
