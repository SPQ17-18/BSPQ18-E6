package es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.User;

public interface IDeustoBoxDAO {
	
	public void registerUser(User user);
	public User getUser(String email, String pass);
	public void storeDB(Object objeto);
}
