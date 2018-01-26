package ua.nure.baranov.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.DatabaseException;
import ua.nure.baranov.dao.RequestDAO;
import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.entity.Request;
import ua.nure.baranov.entity.RequestStatus;

public class MySQLRequestDAO implements RequestDAO {

	private static final int REQUEST_TEXT_FIELD = 3;
	private static final int REQUEST_STATUS_FIELD = 4;
	private static final int OPERATOR_ID_FIELD = 2;
	private static final int ID_FIELD = 1;
	private static final int CREATION_TIME_FIELD = 5;
	private static final int FLIGHT_ID_FIELD = 6;
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String FIND_REQUESTS_BY_STATUS_QUERY = "SELECT * FROM request WHERE status = ?";
	private static final String UPDATE_STATUS_BY_ID = "UPDATE request SET status = ? WHERE id = ?";
	private static final String FIND_REQUEST_BY_ID_QUERY = "SELECT * FROM request WHERE id = ?";
	private static final String DELETE_REQUESTS_BY_FLIGHT_ID = "DELETE FROM request WHERE flight_id=?";
	private static RequestDAO instance = null;

	@Override
	public List<Request> getRequestsByStatus(RequestStatus status) throws DatabaseException {
		LOGGER.debug("Getting requests by status started");
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Request> requests = new ArrayList<>();
		try {
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(FIND_REQUESTS_BY_STATUS_QUERY);
			stmt.setString(1, status.toString().toLowerCase());
			rs = stmt.executeQuery();

			while (rs.next()) {
				Request request = collectRequest(rs, connection);
				LOGGER.trace("Got request: " + request);
				requests.add(request);
			}
			return requests;
		} catch (SQLException e) {
			LOGGER.error("Error during getRequestsByStatus: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
	}

	public static synchronized RequestDAO getInstance() {
		if (instance == null) {
			instance = new MySQLRequestDAO();
		}
		return instance;
	}

	private MySQLRequestDAO() {
	}

	@Override
	public boolean updateStatus(Integer id, RequestStatus status) throws DatabaseException {
		LOGGER.debug("Updating request status started");
		Connection connection = null;
		PreparedStatement stmt = null;
		int n;
		try {
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(UPDATE_STATUS_BY_ID);
			stmt.setString(1, status.toString().toLowerCase());
			stmt.setInt(2, id);
			n = stmt.executeUpdate();

			if (n == 1) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			LOGGER.error("Error during changeRequestStatus: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
	}

	@Override
	public Request getById(Integer id, Connection connection) throws DatabaseException {
		LOGGER.debug("Getting request by id started");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Request request = null;
		try {
			stmt = connection.prepareStatement(FIND_REQUEST_BY_ID_QUERY);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				request = collectRequest(rs, connection);
				LOGGER.trace("Got request: " + request);
			}
			return request;
		} catch (SQLException e) {
			LOGGER.error("Error during getRequestById: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
		}

	}

	private Request collectRequest(ResultSet rs, Connection connection) throws SQLException, DatabaseException {
		Request request = new Request();
		request.setId(rs.getInt(ID_FIELD));
		request.setOperator(DAOFactory.getDAOFactory().getUserDAO().getById(rs.getInt(OPERATOR_ID_FIELD), connection));
		request.setStatus(RequestStatus.valueOf(rs.getString(REQUEST_STATUS_FIELD).toUpperCase()));
		request.setText(rs.getString(REQUEST_TEXT_FIELD));
		request.setFlight(DAOFactory.getDAOFactory().getFlightDAO().getById(rs.getInt(FLIGHT_ID_FIELD), connection));
		request.setCreationDate(rs.getDate(CREATION_TIME_FIELD));
		return request;
	}

	@Override
	public Request create(Request t) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Request> getAll() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Integer id) throws DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Request request) throws DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteByFlightId(Integer id, Connection connection) throws DatabaseException {
		LOGGER.debug("Deleting requests associated with flight under deletion");
		LOGGER.trace("Flight id --> " + id);
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(DELETE_REQUESTS_BY_FLIGHT_ID);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOGGER.error("Error during User.delete: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(stmt);
		}
	}

}
