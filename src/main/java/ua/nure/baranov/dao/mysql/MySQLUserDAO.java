package ua.nure.baranov.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.UserDAO;
import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.dao.support.DatabaseException;
import ua.nure.baranov.dao.support.ResultSetProcessor;
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
	private static final String CREATE_USER_QUERY = "INSERT INTO user (id, username, password, first_name, last_name, role, email, creation_time) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SET_PASSWORD_BY_ID_QUERY = "UPDATE user SET password = ? WHERE id = ?";
	private static final String FIND_ALL_QUERY = "SELECT * FROM user";
	private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
	private static final String UPDATE_USER = "UPDATE user SET username=?, first_name=?, last_name=?, role=?, email=? WHERE id=?";
	private static final String FIND_WITHOUT_ROLE_QUERY = "SELECT * FROM user WHERE role != ?";
	private static final String FIND_BY_ROLE_QUERY = "SELECT * FROM user WHERE role = ?";

	public static synchronized UserDAO getInstance() {
		if (instance == null) {
			instance = new MySQLUserDAO();
		}
		return instance;
	}

	private MySQLUserDAO() {
	}

	@Override
	public User create(User user) throws DatabaseException {
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
			stmt.setString(5, user.getRole().toString().toLowerCase());
			stmt.setString(6, user.getEmail());
			stmt.setTimestamp(7, new Timestamp(user.getCreationDate().getTimeInMillis()));
			n = stmt.executeUpdate();
			if (n == 1) {
				rs = stmt.getGeneratedKeys();
				user.setRole(Role.NONE);
				if (rs.next()) {
					user.setId(rs.getInt(1));
				}
				return user;
			} else
				return null;
		} catch (SQLException e) {
			LOGGER.error("Error during userCreate: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
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
				User user = assembleUser.process(rs);
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
	public User getById(Integer id, Connection connection) throws DatabaseException {
		LOGGER.trace("Getting user from database by id started");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(FIND_BY_ID_QUERY);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				User user = assembleUser.process(rs);
				return user;
			} else {
				LOGGER.trace("No appropriate users found");
				return null;
			}
		} catch (SQLException e) {
			LOGGER.error("Error during User.getById: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
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

	@Override
	public boolean setPasswordById(Integer id, String password) throws DatabaseException {
		LOGGER.debug("Updating user's password");
		LOGGER.trace("id --> " + id);
		LOGGER.trace("New password --> " + password);
		Connection connection = null;
		PreparedStatement stmt = null;
		int n;
		try {
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(SET_PASSWORD_BY_ID_QUERY);
			stmt.setString(1, password);
			stmt.setInt(2, id);
			n = stmt.executeUpdate();
			if (n != 1) {
				LOGGER.warn("Something went wrong during the password changing");
				return false;
			}
			LOGGER.trace("Password was successfully updated");
			return true;
		} catch (SQLException e) {
			LOGGER.error("Error during setPasswordById: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);

		}
	}

	@Override
	public boolean update(User user) throws DatabaseException {
		LOGGER.debug("Updating user");
		LOGGER.trace("id --> " + user.getId());
		LOGGER.trace("New user data --> " + user);
		Connection connection = null;
		PreparedStatement stmt = null;
		int n;
		try {
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(UPDATE_USER);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getFirstName());
			stmt.setString(3, user.getLastName());
			stmt.setString(4, user.getRole().toString().toLowerCase());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getId());
			n = stmt.executeUpdate();
			if (n != 1) {
				LOGGER.warn("Something went wrong during the user updating");
				return false;
			}
			LOGGER.trace("User data has been successfully updated");
			return true;
		} catch (SQLException e) {
			LOGGER.error("Error during User.update: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);

		}
	}

	@Override
	public boolean delete(Integer id) throws DatabaseException {
		LOGGER.debug("Deleting user");
		LOGGER.trace("id --> " + id);
		Connection connection = null;
		PreparedStatement stmt = null;
		int n;
		try {
			connection = MySQLDAOUtils.getConnection(false);
			DAOFactory.getDAOFactory().getRequestsDAO().deleteByOperatorId(id, connection);
			stmt = connection.prepareStatement(DELETE_USER);
			stmt.setInt(1, id);
			n = stmt.executeUpdate();
			if (n != 1) {
				LOGGER.warn("Something went wrong during user deletion");
				connection.rollback();
				return false;
			}
			LOGGER.trace("User has been successfully deleted");
			connection.commit();
			return true;
		} catch (SQLException e) {
			LOGGER.error("Error during User.delete: " + e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOGGER.trace("Cannot process transaction rollback: " + e.getMessage());
				throw new DatabaseException(e);
			}
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);

		}
	}

	@Override
	public List<User> getAll() throws DatabaseException {
		LOGGER.trace("Getting all users from database started");
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<>();
		try {
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.createStatement();
			rs = stmt.executeQuery(FIND_ALL_QUERY);
			while (rs.next()) {
				User user = assembleUser.process(rs);
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			LOGGER.error("Error during User.getAll: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
	}

	private static final ResultSetProcessor<User> assembleUser = (ResultSet rs) -> {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(rs.getTimestamp(CREATION_TIME_FIELD).getTime());
		User user = User.builder().setId(rs.getInt(ID_FIELD)).setUsername(rs.getString(USERNAME_FIELD))
				.setFirstName(rs.getString(FIRSTNAME_FIELD)).setLastName(rs.getString(LASTNAME_FIELD))
				.setPassword(rs.getString(PASSWORD_FIELD)).setRole(Role.valueOf(rs.getString(ROLE_FIELD).toUpperCase()))
				.setEmail(rs.getString(EMAIL_FIELD)).setCreationDate(calendar).build();
		LOGGER.trace("Got user: " + user);
		return user;
	};

	@Override
	public List<User> getByRole(Role role, boolean withoutRole) throws DatabaseException {
		LOGGER.trace("Getting users by the roles started");
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<>();
		try {
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement((withoutRole) ? FIND_WITHOUT_ROLE_QUERY : FIND_BY_ROLE_QUERY);
			stmt.setString(1, role.toString().toLowerCase());
			rs = stmt.executeQuery();
			while (rs.next()) {
				User user = assembleUser.process(rs);
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			LOGGER.error("Error during findByLoginPass: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
	}
}
