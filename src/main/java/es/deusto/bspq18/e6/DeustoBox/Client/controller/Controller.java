package es.deusto.bspq18.e6.DeustoBox.Client.controller;

import java.rmi.RemoteException;

import es.deusto.bspq18.e6.DeustoBox.Client.gui.v_login;
import es.deusto.bspq18.e6.DeustoBox.Client.remote.RMIServiceLocator;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;

public class Controller {

	private RMIServiceLocator rsl;
	public UserDTO userdto;

	public Controller(String[] args) throws RemoteException {
		rsl = new RMIServiceLocator();
		rsl.setService(args);
		new v_login(this);
	}

	public UserDTO signUp(String username, String email, String password) {
		UserDTO res = null;
		try {
			res = rsl.getService().signUp(username, email, password);
		} catch (Exception ex) {
			System.err.println("- Exception " + ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}

	public UserDTO login(String email, String password) {
		UserDTO res = null;
		try {
			res = rsl.getService().login(email, password);
		} catch (Exception ex) {
			System.err.println("- Exception " + ex.getMessage());
			ex.printStackTrace();
		}

		if (res.equals(null)) {
			System.out.println("The user doesn't exist");
		}
		return res;
	}

	public static void main(String[] args) throws RemoteException {
		new Controller(args);
	}
}
