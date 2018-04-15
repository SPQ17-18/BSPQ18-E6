package es.deusto.bspq18.e6.DeustoBox.Client.controller;

import java.rmi.RemoteException;

import es.deusto.bspq18.e6.DeustoBox.Client.gui.v_login;
import es.deusto.bspq18.e6.DeustoBox.Client.remote.RMIServiceLocator;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;

public class Controller {

	private RMIServiceLocator rsl;
	private UserDTO userdto;

	public Controller(String[] args) throws RemoteException {
		rsl = new RMIServiceLocator();
		rsl.setService(args);
		new v_login(this);
	}

	public boolean signUp(String username, String email, String password) {
		UserDTO res = null;
		try {
			res = rsl.getService().signUp(username, email, password);
		} catch (Exception ex) {
			System.err.println("- Exception " + ex.getMessage());
			ex.printStackTrace();
		}
		if (res != null){
			this.userdto = res;
			return true;
		}else
			return false;
	}

	public boolean login(String email, String password) {
		UserDTO res = null;
		try {
			res = rsl.getService().login(email, password);
		} catch (Exception ex) {
			System.err.println("- Exception " + ex.getMessage());
			ex.printStackTrace();
		}

		if (res.equals(null)) {
			System.out.println("The user doesn't exist");
			return false;
		}
		else{
			this.userdto = res;
			return true;
		}
		
	}

	public static void main(String[] args) throws RemoteException {
		new Controller(args);
	}
	
}
