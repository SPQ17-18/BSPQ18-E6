package es.deusto.bspq18.e6.DeustoBox.Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.rmi.Naming;

import es.deusto.bspq18.e6.DeustoBox.Server.gui.v_installer;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.DeustoBoxRemoteService;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.IDeustoBoxRemoteService;

public class DeustoBoxServer {
	
	public static void main(String[] args) {

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

		try {
			Socket so = new Socket();
			IDeustoBoxRemoteService deustoBox = new DeustoBoxRemoteService(so);
			Naming.rebind(name, deustoBox);
			
			System.out.println("- DeustoBoxServer:  '" + name + "' active and waiting...");
			InputStreamReader inputStreamReader = new InputStreamReader(System.in);
			BufferedReader stdin = new BufferedReader(inputStreamReader);
			String line = stdin.readLine();
			
		} catch (Exception e) {
			System.err.println("$ DeustoBox exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
