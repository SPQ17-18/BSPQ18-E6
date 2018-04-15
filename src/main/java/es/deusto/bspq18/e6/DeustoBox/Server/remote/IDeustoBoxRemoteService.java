package es.deusto.bspq18.e6.DeustoBox.Server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;

public interface IDeustoBoxRemoteService extends Remote{

	public UserDTO signUp(String username, String email, String password)throws RemoteException;
	public UserDTO login(String email, String password) throws RemoteException;
	
}
