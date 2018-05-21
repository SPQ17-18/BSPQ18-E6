package es.deusto.bspq18.e6.DeustoBox.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.util.Locale;
import java.util.ResourceBundle;

import es.deusto.bspq18.e6.DeustoBox.Server.remote.DeustoBoxRemoteService;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.IDeustoBoxRemoteService;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;

public class DeustoBoxServer {
	
	


	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Locale currentLocale = new Locale("en", "US");
		ResourceBundle resourcebundle = ResourceBundle.getBundle("lang/translations", currentLocale);
		Error_log logger = new Error_log();

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

		try {
			IDeustoBoxRemoteService deustoBox = new DeustoBoxRemoteService(resourcebundle);
			Naming.rebind(name, deustoBox);
			
			
			logger.getLogger().info(resourcebundle.getString("server_DeustoBox") + name + resourcebundle.getString("server_active"));
			InputStreamReader inputStreamReader = new InputStreamReader(System.in);
			BufferedReader stdin = new BufferedReader(inputStreamReader);
			String line = stdin.readLine();
			
		} catch (Exception e) {
			logger.getLogger().error(resourcebundle.getString("server_excep") + e.getMessage());
			e.printStackTrace();
		}
	}
}
