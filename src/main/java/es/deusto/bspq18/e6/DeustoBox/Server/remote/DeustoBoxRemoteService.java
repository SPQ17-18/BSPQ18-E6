package es.deusto.bspq18.e6.DeustoBox.Server.remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import es.deusto.bspq18.e6.DeustoBox.Server.Log.DeustoBoxLog;
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
		private DeustoBoxLog log;
		
		public DeustoBoxRemoteService(DeustoBoxLog log) throws RemoteException {
			this.log = log;
			db = new DeustoBoxDAO();
			assemble = new Assembler();
		}

		public UserDTO signUp(String username, String email, String password) throws RemoteException {
			boolean correcto = false;
			
			log.getLogger().info("Received a register request with the email: " + email );
			User user = new User(username, email, password);
			System.out.println(user);
			correcto = db.addUser(user);
			
			if (correcto) {
				return assemble.userDTO(user);
			} else {
				log.getLogger().error("The email is already registered");
				return null;
			}
		}

		public UserDTO login(String email, String password) throws RemoteException {
			log.getLogger().info("Received a login request with the email: " + email );
			User e = db.getUser(email, password);
			if(e.equals(null)){
				log.getLogger().error("BAD LOGIN");
			return null;	
				
			}
			else{
			return assemble.userDTO(e);
		}
		}

}
