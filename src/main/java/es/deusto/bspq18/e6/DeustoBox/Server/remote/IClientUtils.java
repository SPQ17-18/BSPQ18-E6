package es.deusto.bspq18.e6.DeustoBox.Server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;

public interface IClientUtils extends Remote{

	public UserDTO login(String username, String password) throws RemoteException;
	public void registerUser(UserDTO userdto) throws RemoteException;
	
}
