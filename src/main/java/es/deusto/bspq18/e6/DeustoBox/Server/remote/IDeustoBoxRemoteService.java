package es.deusto.bspq18.e6.DeustoBox.Server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;

public interface IDeustoBoxRemoteService extends Remote{

	public UserDTO SignUp(String Username, String Email, String password)throws RemoteException;
	public UserDTO Login(String email, String password) throws RemoteException;
	
}
