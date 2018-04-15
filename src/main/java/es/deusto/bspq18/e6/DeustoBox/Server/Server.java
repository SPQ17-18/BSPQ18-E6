package es.deusto.bspq18.e6.DeustoBox.Server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assemble;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.User;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.IClientUtils;

public class Server extends UnicastRemoteObject implements IClientUtils{
	private static final long serialVersionUID = 1L;
	private IDeustoBoxDAO dao;
	private Assemble transform;

	public Server() throws RemoteException {
		dao = new DeustoBoxDAO();
		transform = new Assemble();
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
			Server server = new Server();
			Naming.rebind(name, server);
			System.out.println("Server '" + name + "' active and waiting...");	
		} catch (Exception e) {
			System.err.println("Server Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
