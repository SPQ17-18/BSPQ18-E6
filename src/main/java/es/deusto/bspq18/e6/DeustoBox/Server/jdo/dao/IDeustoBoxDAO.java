package es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;

public interface IDeustoBoxDAO {
	
	public DUser getUser(String email, String pass);
	public boolean addUser(DUser e);
}
