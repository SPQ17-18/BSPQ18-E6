package deusto.bspq18.e6.DeustoBox.Server.dao;

import deusto.bspq18.e6.DeustoBox.Server.data.User;

public interface IDeustoBoxDAO {
	
	public void registerUser(User user);
	public User getUser(String email, String pass);
	public void storeDB(Object objeto);
}
