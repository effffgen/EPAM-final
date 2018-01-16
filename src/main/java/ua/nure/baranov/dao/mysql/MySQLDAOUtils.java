package ua.nure.baranov.dao.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.DatabaseException;

public class MySQLDAOUtils {
	private static final Logger LOGGER = LogManager.getLogger();

	static void close(AutoCloseable autoClosable) throws DatabaseException {
		try {
			autoClosable.close();
		} catch (Exception e) {
			LOGGER.error("Error while closing database resource: " + e.getMessage());
			throw new DatabaseException(e);
		}

	}

	public static Connection getConnection(boolean autoCommit) throws SQLException {
		Connection connection = MysqlDAOFactory.getConnection();
		connection.setAutoCommit(autoCommit);
		return connection;
	}
}
