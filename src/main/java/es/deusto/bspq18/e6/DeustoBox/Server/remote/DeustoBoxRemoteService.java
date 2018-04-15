package es.deusto.bspq18.e6.DeustoBox.Server.remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assemble;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.User;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.IDeustoBoxRemoteService;


public class DeustoBoxRemoteService extends UnicastRemoteObject implements IDeustoBoxRemoteService {
	
		private static final long serialVersionUID = 1L;
		private IDeustoBoxDAO db;
		private Assemble transform;
		
		
		public DeustoBoxRemoteService() throws RemoteException {
			db = new DeustoBoxDAO();
			transform = new Assemble();
				
		}

		public UserDTO SignUp(String Username, String Email, String password) throws RemoteException {
			boolean correcto = false;
			System.out.println("Recibido hermano");
			User e = new User(Username, Email, password);
			// Lo almacenamos en la BD
			//System.out.println(e.toString());
			correcto = db.AddUser(e);
			
			if (correcto) { // Devuelvo un userDTO
				UserDTO ret = null;
				ret = Assemble.assembleUser(e);
				
				return ret;
			} else {

				return null;
			}
		}


		public UserDTO Login(String email, String password) throws RemoteException {
			// TODO Auto-generated method stub
			User user1 = null;
			UserDTO userdto = null;

				user1 = db.getUser(email, password);
				userdto = Assemble.assembleUser(user1);

			return userdto;
		}

		

		
}
