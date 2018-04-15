package es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.User;

public interface IDeustoBoxDAO {
	
	public User getUser(String email, String pass);
	public boolean AddUser(User e);
}
