package es.deusto.bspq18.e6.DeustoBox.Server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.bspq18.e6.DeustoBox.Server.dto.DConnectionDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DMessageDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DConnection;

public interface IDeustoBoxRemoteService extends Remote{

	public DUserDTO signUp(String username, String email, String password)throws RemoteException;
	public DUserDTO login(String email, String password, String os) throws RemoteException;
	public ArrayList<DFileDTO> getFiles(String email) throws RemoteException;
	public boolean sendData(String filename, byte[] data, int len) throws RemoteException;
	public int getNumberOfUserFiles(String email) throws RemoteException;
	public boolean checkPassword(String email,String password)throws RemoteException;
	public boolean updatePassword(String email,String password)throws RemoteException;
	public IDeustoBoxDAO getDb() throws RemoteException;
	public void ReceiveFiles(String file, String email) throws RemoteException;
	public boolean addConnection(String email, String systema) throws RemoteException;
	public ArrayList<DConnectionDTO> getConnections(String email) throws RemoteException;
	public ArrayList<DMessageDTO> getMessages(String email) throws RemoteException;
	public boolean addMessage(DMessageDTO messagedto) throws RemoteException;
	public int getNewMessageId() throws RemoteException;

	
}
