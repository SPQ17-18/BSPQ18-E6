package es.deusto.bspq18.e6.DeustoBox.Client.controller;

import java.rmi.RemoteException;

import es.deusto.bspq18.e6.DeustoBox.Client.gui.v_login;
import es.deusto.bspq18.e6.DeustoBox.Client.remote.RMIServiceLocator;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;


public class Controller {

	private RMIServiceLocator rsl;
	public UserDTO userdto;
	
	public Controller(String[] args) throws RemoteException {
		// Inicializar
		rsl = new RMIServiceLocator();
		rsl.setService(args);
		new v_login(this);

	}
	
	
	public UserDTO SignUp(String Username, String Email, String password) {
		UserDTO res = null;
		try {
			res = rsl.getService().SignUp(Username, Email, password);
		} catch (Exception ex) {
			System.err.println("- Exception " + ex.getMessage());
			ex.printStackTrace();
		}
		
		return res;
	}
	

	public UserDTO Login(String email, String password) {
		UserDTO res = null;
		try {
			res = rsl.getService().Login(email, password);
		} catch (Exception ex) {
			System.err.println("- Exception " + ex.getMessage());
			ex.printStackTrace();
		}

		if (res.equals(null)) {
			System.out.println("El usuario no existe");

		}
		return res;
	}
	
	
	public static void main(String[] args) throws RemoteException {
		new Controller(args);

	}
}
