package ua.nure.baranov.dao;

import java.sql.Connection;
import java.util.List;

import ua.nure.baranov.dao.mysql.MySQLDAOUtils;
import ua.nure.baranov.dao.support.DatabaseException;
import ua.nure.baranov.entity.Entity;

public interface GenericDAO<T extends Entity> {
	default T create(T t) throws DatabaseException{
		throw new UnsupportedOperationException("Cannot create new entity, functionality not implemented");
	}

	default T getById(Integer id, Connection con) throws DatabaseException{
		throw new UnsupportedOperationException("Cannot get entity, functionality not implemented");
	}

	default T getById(Integer id) throws DatabaseException {
		Connection con = MySQLDAOUtils.getConnection(true);
		T t = getById(id, con);
		MySQLDAOUtils.close(con);
		return t;
	}

	default List<T> getAll() throws DatabaseException{
		throw new UnsupportedOperationException("Cannot get entities, functionality not implemented");
	}

	default boolean update(T t) throws DatabaseException{
		throw new UnsupportedOperationException("Cannot update entity, functionality not implemented");
	}

	default boolean delete(Integer id) throws DatabaseException{
		throw new UnsupportedOperationException("Cannot delete entity, functionality not implemented");
	}

}
