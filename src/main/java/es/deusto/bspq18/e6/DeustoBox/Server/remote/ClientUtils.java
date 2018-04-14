package es.deusto.bspq18.e6.DeustoBox.Server.remote;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assemble;
import es.deusto.bspq18.e6.DeustoBox.Server.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.data.User;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;

public class ClientUtils extends UnicastRemoteObject implements IClientUtils {
	
	private static final long serialVersionUID = 1L;
	private IDeustoBoxDAO dao;
	private Assemble transform;

	public ClientUtils() throws RemoteException {
		dao = new DeustoBoxDAO();
		transform = new Assemble();
		
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

	public static void main(String[] args) {
		if (args.length != 3) {
			System.exit(0);
		}
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

		try {

			ClientUtils server = new ClientUtils();
			Naming.rebind(name, server);
			System.out.println("Server '" + name + "' active and waiting...");
			
		} catch (Exception e) {
			System.err.println("Server Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}
