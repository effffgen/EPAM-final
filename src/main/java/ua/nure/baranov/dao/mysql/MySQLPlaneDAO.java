package ua.nure.baranov.dao.mysql;

import ua.nure.baranov.dao.PlaneDAO;
import ua.nure.baranov.entity.Plane;

public class MySQLPlaneDAO implements PlaneDAO{

	@Override
	public Plane getPlaneByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static PlaneDAO instance;
	
	public static PlaneDAO getInstance() {
		if(instance == null) {
			instance = new MySQLPlaneDAO();
		}
		return instance;
	}

	private MySQLPlaneDAO() {}
	
}
