package ua.nure.baranov.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.PlaneDAO;
import ua.nure.baranov.dao.support.DatabaseException;
import ua.nure.baranov.entity.Plane;

public class MySQLPlaneDAO implements PlaneDAO {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final int ID_FIELD = 1;
	private static final int NAME_FIELD = 2;
	private static final String FIND_PLANE_BY_ID_QUERY = "SELECT * FROM plane WHERE id=?";

	@Override
	public Plane getPlaneByID(int id) throws DatabaseException {
		LOGGER.debug("Getting plane from database started");
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = MySQLDAOUtils.getConnection(true);
			stmt = connection.prepareStatement(FIND_PLANE_BY_ID_QUERY);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			Plane plane = null;
			if (rs.next()) {
				plane = new Plane();
				plane.setId(rs.getInt(ID_FIELD));
				plane.setName(rs.getString(NAME_FIELD));
			}
			LOGGER.trace("Got plane -->" + plane);
			return plane;
		} catch (SQLException e) {
			LOGGER.error("Error during getPlaneById: " + e.getMessage());
			throw new DatabaseException(e);
		} finally {
			MySQLDAOUtils.close(rs);
			MySQLDAOUtils.close(stmt);
			MySQLDAOUtils.close(connection);
		}
	}

	private static PlaneDAO instance;

	public static PlaneDAO getInstance() {
		if (instance == null) {
			instance = new MySQLPlaneDAO();
		}
		return instance;
	}

	private MySQLPlaneDAO() {
	}

}
