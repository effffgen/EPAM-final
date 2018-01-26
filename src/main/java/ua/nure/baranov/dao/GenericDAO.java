package ua.nure.baranov.dao;

import java.sql.Connection;
import java.util.List;

import ua.nure.baranov.dao.mysql.MySQLDAOUtils;
import ua.nure.baranov.entity.Entity;

public interface GenericDAO<T extends Entity> {
	T create(T t) throws DatabaseException;

	T getById(Integer id, Connection con) throws DatabaseException;

	default T getById(Integer id) throws DatabaseException {
		Connection con = MySQLDAOUtils.getConnection(true);
		T t = getById(id, con);
		MySQLDAOUtils.close(con);
		return t;
	}

	List<T> getAll() throws DatabaseException;

	boolean update(T t) throws DatabaseException;

	boolean delete(Integer id) throws DatabaseException;

}
