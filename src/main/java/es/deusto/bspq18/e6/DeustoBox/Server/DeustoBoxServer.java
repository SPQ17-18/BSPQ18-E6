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
		System.out.println("- DeustoBoxServer:  '" + name + "' active and waiting...");
		java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(System.in);
		java.io.BufferedReader stdin = new java.io.BufferedReader(inputStreamReader);
		@SuppressWarnings("unused")
		String line = stdin.readLine();
		
	} catch (Exception e) {
		System.err.println("$ DeustoBox exception: " + e.getMessage());
		e.printStackTrace();
	}
}
}
