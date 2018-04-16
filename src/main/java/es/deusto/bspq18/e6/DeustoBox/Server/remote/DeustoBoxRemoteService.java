package es.deusto.bspq18.e6.DeustoBox.Server.remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assembler;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.User;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.IDeustoBoxRemoteService;


public class DeustoBoxRemoteService extends UnicastRemoteObject implements IDeustoBoxRemoteService {
	
		private static final long serialVersionUID = 1L;
		private IDeustoBoxDAO db;
		private Assembler assemble;
		
		public DeustoBoxRemoteService() throws RemoteException {
			db = new DeustoBoxDAO();
			assemble = new Assembler();
		}

		public UserDTO signUp(String username, String email, String password) throws RemoteException {
			boolean correcto = false;
			System.out.println("User received");
			User user = new User(username, email, password);
			System.out.println(user);
			correcto = db.addUser(user);
			
			if (correcto) {
				return assemble.userDTO(user);
			} else {
				return null;
			}
		}

		public UserDTO login(String email, String password) throws RemoteException {
			return assemble.userDTO(db.getUser(email, password));
		}
		
}
