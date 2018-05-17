package es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao;

import java.util.ArrayList;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DConnection;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DMessage;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;

public interface IDeustoBoxDAO {
	
	public DUser getUser(String email, String pass);
	public boolean addUser(DUser e);
	public ArrayList<DFile> getAllFiles();
	public ArrayList<DFile> getAllFilesOfAUser(String email);
	public ArrayList<DMessage> getAllMessagesOfSendToAUser(String email);
	public ArrayList<DUser> getAllUsers();
	public void addFiles(DFile file);
	public int getNumberOfUserFiles(String email);
	public boolean checkPassword(String email, String password);
	public boolean updatePassword(String email, String password);
	public boolean addConnection(DConnection connection);
	public boolean addMessage(DMessage message);
	public boolean deleteMessage(int id);
	public int getLastConnectionID();
	public int getLastMessageID();
	public int getNumberOfUserMessages(String email);
	public ArrayList<DConnection> getConnections(String email);
	public void deleteAllFiles();
	public void deleteAllUsers();
	public void deleteAllConnections();
	public void deleteAllMessages();
	
	
}
