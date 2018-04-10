package spq.server.dao;

import spq.server.data.User;

public interface IDeustoBoxDAO {

	public boolean AddUser(User e);
	public User getUser(String email);
}


