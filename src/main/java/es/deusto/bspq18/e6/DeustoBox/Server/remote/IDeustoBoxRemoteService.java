package es.deusto.bspq18.e6.DeustoBox.Server.remote;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;

public interface IDeustoBoxRemoteService extends Remote{

	public DUserDTO signUp(String username, String email, String password)throws RemoteException;
	public DUserDTO login(String email, String password) throws RemoteException;
	public ArrayList<DFileDTO> getFiles(String email) throws RemoteException;
	public boolean sendData(String filename, byte[] data, int len) throws RemoteException;
	public int getNumberOfUserFiles(String email) throws RemoteException;
	public boolean checkPassword(String email,String password)throws RemoteException;
	
}
