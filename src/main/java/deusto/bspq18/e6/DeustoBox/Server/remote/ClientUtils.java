package deusto.bspq18.e6.DeustoBox.Server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import deusto.bspq18.e6.DeustoBox.Server.assembler.Assemble;
import deusto.bspq18.e6.DeustoBox.Server.dao.DeustoBoxDAO;
import deusto.bspq18.e6.DeustoBox.Server.dao.IDeustoBoxDAO;
import deusto.bspq18.e6.DeustoBox.Server.data.User;
import deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;

public class ClientUtils extends UnicastRemoteObject implements IClientUtils {
	
	private static final long serialVersionUID = 1L;
	private IDeustoBoxDAO dao;
	private Assemble transform = new Assemble();

	public ClientUtils(String serverIp, int port) throws RemoteException {
		dao = new DeustoBoxDAO();
		
		User user1 = new User("aitorugarte@opendeusto.es", "aitorugarte", "123");
		User user2 = new User("markelalva@opendeusto.es", "markelalva", "123");
		
		dao.storeDB(user1);
		dao.storeDB(user2);		
		
	}
	
	public UserDTO login(String username, String password) throws RemoteException {
		System.out.println("Checking user...");
		User user = dao.getUser(username, password);
		if(user == null) {
			return null;
		}else {
			return transform.userDTO(user);
		}
	}

	public void registerUser(UserDTO userdto) throws RemoteException {
		System.out.println("Registrando usuario...");
		dao.registerUser(transform.user(userdto));
	}

}
