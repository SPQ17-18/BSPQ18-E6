import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;
import es.deusto.bspq18.e6.DeustoBox.Server.DeustoBoxServer;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.DeustoBoxRemoteService;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.IDeustoBoxRemoteService;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;
import junit.framework.JUnit4TestAdapter;
@PerfTest(invocations = 1)
@Required(max = 1200, average = 800)
public class ControllerTest {

private static String cwd = ControllerTest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
private static Thread rmiRegistryThread = null;
private static Thread rmiServerThread = null;
private Controller controller;
private static Error_log logger = new Error_log();
@Rule
public ContiPerfRule rule = new ContiPerfRule();


	
	@SuppressWarnings("unused")
	private IDeustoBoxRemoteService messenger;

	public static junit.framework.Test suite() {
			return new JUnit4TestAdapter(ControllerTest.class);
		}


		@BeforeClass 
		static public void setUp() {
			// Launch the RMI registry
			class RMIRegistryRunnable implements Runnable {

				public void run() {
					try {
						java.rmi.registry.LocateRegistry.createRegistry(1099);
						logger.getLogger().info("BeforeClass: RMI registry ready.");
					} catch (Exception e) {
						logger.getLogger().error("CONTROLLER TEST -- Exception starting RMI registry:");
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
			
			class RMIServerRunnable implements Runnable {

				public void run() {
					logger.getLogger().info("This is a test to check how mvn test executes this test without external interaction; JVM properties by program");
					logger.getLogger().info("**************: " + cwd);
					System.setProperty("java.rmi.server.codebase", "file:" + cwd);
					System.setProperty("java.security.policy", "target\\test-classes\\security\\java.policy");

					if (System.getSecurityManager() == null) {
						System.setSecurityManager(new SecurityManager());
					}

					String name = "//127.0.0.1:1099/DeustoBox";
					logger.getLogger().info("BeforeClass - Setting the server ready TestServer name: " + name);

					try {
						
						IDeustoBoxRemoteService messenger = new DeustoBoxRemoteService();
						Naming.rebind(name, messenger);
					} catch (RemoteException re) {
						logger.getLogger().error("CONTROLLER TEST --# Messenger RemoteException: " + re.getMessage());
						re.printStackTrace();
						System.exit(-1);
					} catch (MalformedURLException murle) {
						logger.getLogger().error("CONTROLLER TEST -- # Messenger MalformedURLException: " + murle.getMessage());
						
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
		public void setUpClient() {
			try {
			System.setProperty("java.security.policy", "target\\test-classes\\security\\java.policy");

			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			String name = "//127.0.0.1:1099/DeustoBox";
			String args[] = new String[3];
			args[0] = "127.0.0.1";
			args[1] = "1099";
			args[2] = "DeustoBox";
			logger.getLogger().info("BeforeTest - Setting the client ready for calling TestServer name: " + name);
			messenger = (IDeustoBoxRemoteService)java.rmi.Naming.lookup(name);
			controller = new Controller(args);
			}
			catch (Exception re) {
				logger.getLogger().error("CONTROLLER TEST -- # Messenger RemoteException: " + re.getMessage());
				re.printStackTrace();
				System.exit(-1);
			} 
			
		}
		
		
		@Test
		public void testCreateUser() {
			IDeustoBoxDAO bd = new DeustoBoxDAO(new Error_log());
			bd.deleteAllUsers();
			assertEquals(true,controller.signUp("username", "email", "password"));
			logger.getLogger().info("CONTROLLER TEST -- Test CreateUser done");

			
		}
		@Test
		@PerfTest(duration = 2000)
		public void testLoginUser() {
			assertNotSame(controller.login("email", "password"), true);
			logger.getLogger().info("CONTROLLER TEST -- Test LoginUser done");
		}
		@Test

		public void testChangePassword(){
			assertEquals(controller.passwordCorrect("email","password12"), false);
			logger.getLogger().info("CONTROLLER TEST -- Test ChangePassword done");
		}
		@Test
		public void testgetConnections(){
			assertTrue(controller.getConnections("email").size() >=0) ;
			logger.getLogger().info("CONTROLLER TEST -- Test getConnections done");
			
			
		}
		@Test
		public void testSendMessage(){
			assertEquals(controller.addMessage("from", "emailto", "subject", "text"), true);
			logger.getLogger().info("CONTROLLER TEST -- Test SendMessage done");
			
		}
		@Test
		public void testgetNumberOfMessages(){
		assertTrue(controller.getNumberOfUserMessages("noEmail") == 0);
		assertTrue(controller.getNumberOfUserMessages("emailto")> 0);
		logger.getLogger().info("CONTROLLER TEST -- Test getNumberOfMessages done");
		}
		@Test
		public void testgetToDownloadMessages(){
		assertEquals(controller.downloadMessages("emailto").get(0).getSubject(), "subject");
		assertEquals(controller.downloadMessages("emailto").get(0).getText(), "text");
		logger.getLogger().info("CONTROLLER TEST -- Test DownloadMessages done");
		}
		
		@Test
		public void testgetNumberOfFiles(){
		assertTrue(controller.getListOfFiles("email") ==0);
		assertTrue(controller.getNumberOfFiles("email") ==0);
		logger.getLogger().info("CONTROLLER TEST -- Test getNumberOfFiles done");
		}
		
		@AfterClass
		public static void end() {
			IDeustoBoxDAO bd = new DeustoBoxDAO(new Error_log());
			bd.deleteAllFiles();
			bd.deleteAllConnections();
			bd.deleteAllUsers();
			
			try {
				rmiServerThread.join();
				rmiRegistryThread.join();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
	}
			
			
			
		}
		
	
}

