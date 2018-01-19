package ua.nure.baranov.dao.mysql;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.DatabaseException;

public class MySQLDAOUtils {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String salt;

	static {
		Properties properties = new Properties();
		try {
			properties.load(MySQLDAOUtils.class.getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			LOGGER.fatal("Failed to found properties file, unable to get salt");
			throw new RuntimeException(e);
		}
		salt = properties.getProperty("salt");
	}
	
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
	
	public static String saltedPassword(String password) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No such algorithm, consider rewriting");
			return null;
		}
		return DatatypeConverter.printHexBinary(digest.digest((salt + password).getBytes()));
	}
	
	private MySQLDAOUtils() {}
	
}
