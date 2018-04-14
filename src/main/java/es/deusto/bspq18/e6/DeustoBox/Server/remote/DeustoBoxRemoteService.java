package es.deusto.bspq18.e6.DeustoBox.Server.remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assemble;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.User;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.IDeustoBoxRemoteService;

public class DeustoBoxRemoteService extends UnicastRemoteObject implements IDeustoBoxRemoteService {
	
		private static final long serialVersionUID = 1L;
		private IDeustoBoxDAO dao;
		private Assemble transform;
		
		
		public DeustoBoxRemoteService() throws RemoteException {
			//dao = new DeustoBoxDAO();
			//transform = new Assemble();
			System.out.println("Hola");
				
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
