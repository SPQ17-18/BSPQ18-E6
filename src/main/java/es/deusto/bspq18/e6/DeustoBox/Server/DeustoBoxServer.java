package es.deusto.bspq18.e6.DeustoBox.Server;

import java.rmi.Naming;

import es.deusto.bspq18.e6.DeustoBox.Server.remote.DeustoBoxRemoteService;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.IDeustoBoxRemoteService;





public class DeustoBoxServer {
	public static void main(String[] args) {


	if (System.getSecurityManager() == null) {
		System.setSecurityManager(new SecurityManager());
	}

	String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

	try {
		IDeustoBoxRemoteService deustoBox = new DeustoBoxRemoteService();
		Naming.rebind(name, deustoBox);
		System.out.println("- EasyBookingServer:  '" + name + "' active and waiting...");
	} catch (Exception e) {
		System.err.println("$ EasyBooking exception: " + e.getMessage());
		e.printStackTrace();
	}
}
}
