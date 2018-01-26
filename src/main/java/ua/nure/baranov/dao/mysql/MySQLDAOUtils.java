package ua.nure.baranov.dao.mysql;

import java.sql.Connection;
import java.sql.SQLException;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.DatabaseException;

public class MySQLDAOUtils {
	private static final Logger LOGGER = LogManager.getLogger();

	
	public static void close(AutoCloseable autoClosable) throws DatabaseException {
		try {
			LOGGER.trace("Trying to close resource");
			LOGGER.trace("Resource --> " + autoClosable);
			if(autoClosable!=null) {
				autoClosable.close();
			}
		} catch (Exception e) {
			LOGGER.error("Error while closing database resource: " + e.getMessage());
			throw new DatabaseException(e);
		}

	}

	public static Connection getConnection(boolean autoCommit) throws DatabaseException {
		Connection connection;
		try {
			connection = MysqlDAOFactory.getConnection();
			connection.setAutoCommit(autoCommit);
			return connection;
		} catch (SQLException e) {
			LOGGER.error("Cannot obtain connection from db: " + e.getMessage());
			throw new DatabaseException(e);
		}
	}
	
	
	private MySQLDAOUtils() {}
	
}
