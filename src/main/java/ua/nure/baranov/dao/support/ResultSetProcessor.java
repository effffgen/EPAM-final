package ua.nure.baranov.dao.support;

import java.sql.ResultSet;
import java.sql.SQLException;

import ua.nure.baranov.entity.Entity;

@FunctionalInterface
public interface ResultSetProcessor<T extends Entity> {

    public T process(ResultSet resultSet) 
                        throws SQLException, DatabaseException;

}
