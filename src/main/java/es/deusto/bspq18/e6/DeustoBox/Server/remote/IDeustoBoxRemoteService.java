package es.deusto.bspq18.e6.DeustoBox.Server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;

public interface IDeustoBoxRemoteService extends Remote{

	public DUserDTO signUp(String username, String email, String password)throws RemoteException;
	public DUserDTO login(String email, String password) throws RemoteException;
	
}
