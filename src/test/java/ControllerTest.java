import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;
import es.deusto.bspq18.e6.DeustoBox.Server.DeustoBoxServer;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.DeustoBoxRemoteService;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.IDeustoBoxRemoteService;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;
import junit.framework.JUnit4TestAdapter;

public class ControllerTest {

private static String cwd = ControllerTest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	private  Controller controller;
	private DeustoBoxServer service;
	private static Error_log logger;


	@BeforeClass
	public static void setUpClass() {
		//Run the logger
		logger = new Error_log();
		// Launch the RMI registry
		class RMIRegistryRunnable implements Runnable {
			public void run() {
				try {
					java.rmi.registry.LocateRegistry.createRegistry(1099);
					logger.getLogger().info("BeforeClass: RMI registry ready.");
				} catch (Exception e) {
					logger.getLogger().error("Exception starting RMI registry:");
					e.printStackTrace();
				}
			}
		}

		rmiRegistryThread = new Thread(new RMIRegistryRunnable());
		rmiRegistryThread.start();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

		// Launch the server
		class RMIServerRunnable implements Runnable {
			public void run() {
				System.setProperty("java.rmi.server.codebase", "file:" + cwd);
				System.setProperty("java.security.policy", "target//test-classes//security//java.policy");

				if (System.getSecurityManager() == null) {
					System.setSecurityManager(new SecurityManager());
				}

				String args = "//127.0.0.1:1099/DeustoBox";
				logger.getLogger().info("BeforeClass - Setting the server ready name: " + cwd);

				try {
					IDeustoBoxRemoteService rf = new DeustoBoxRemoteService();
					Naming.rebind(args, rf);
				} catch (RemoteException re) {
					logger.getLogger().error(" # Server RemoteException: " + re.getMessage());
					re.printStackTrace();
					System.exit(-1);
				} catch (MalformedURLException murle) {
					logger.getLogger().error(" # Server MalformedURLException: " + murle.getMessage());
					murle.printStackTrace();
					System.exit(-1);
				}
			}
		}
		
		rmiServerThread = new Thread(new RMIServerRunnable());
		rmiServerThread.start();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
		
		
		@Before
		public void startClient() {
			// Launch the client
			try {
				System.setProperty("java.security.policy", "target//test-classes//security//java.policy");
				if (System.getSecurityManager() == null) {
					System.setSecurityManager(new SecurityManager());
				}
				String args[] = new String[3];
				args[0] = "127.0.0.1";
				args[1] = "1099";
				args[2] = "CDeustoBox";
				logger.getLogger().info("BeforeTest - Setting the client ready for calling name: " + args[2]);
				controller = new Controller(args);
			} catch (Exception re) {
				logger.getLogger().error(" # Client RemoteException: " + re.getMessage());
				System.exit(-1);
			}
	}
		
		@Before
		public void setUpServer() {
			service = new DeustoBoxServer();
			
			
		}
		
		@Test
		public void testCreateUser() {
			assertEquals(true,controller.signUp("username", "email", "password"));
			
			
		}
}

