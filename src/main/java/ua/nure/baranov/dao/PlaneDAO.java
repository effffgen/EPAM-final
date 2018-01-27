package ua.nure.baranov.dao;

import ua.nure.baranov.dao.support.DatabaseException;
import ua.nure.baranov.entity.Plane;

public interface PlaneDAO {

	Plane getPlaneByID(int id) throws DatabaseException;

}
