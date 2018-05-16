import static org.junit.Assert.*;

import java.util.ArrayList;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DConnection;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DMessage;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;
@PerfTest(invocations = 5)
@Required(max = 1200, average = 250)
public class DAOTest {

	private static IDeustoBoxDAO db;
	private static DUser e;
	private static DFile file;
	
	@Rule 
	public ContiPerfRule rule1 = new ContiPerfRule();
	
	@BeforeClass
	public static void setUpClass() {
		db = new DeustoBoxDAO(new Error_log());

	}

	@Test
	@PerfTest(duration= 2000)
	public void testUser() {
		
		e = new DUser("dipina4" ,"dipina4@deusto.es", "12345");
		db.addUser(e);
		DUser ret = db.getUser("dipina4@deusto.es", "12345");
		assertEquals("dipina4", ret.getUsername());
		assertEquals("dipina4@deusto.es", ret.getEmail());
		assertEquals("12345", ret.getPassword());
		ArrayList<DUser> users = null;
		users = db.getAllUsers();
		System.out.println("HAY " + users.size());
		assertEquals("dipina4", users.get(users.size() -1).getUsername());
		db.deleteAllUsers();
		
		
}
	
	@Test
	@PerfTest(duration= 2000)
	public void testUserPassword() {
		e = new DUser("dipina3" ,"dipina3@deusto.es", "12345");
		db.addUser(e);
		assertEquals(true, db.checkPassword("dipina3@deusto.es", "12345"));
		db.deleteAllUsers();
}
	
	@Test
	@PerfTest(duration= 2000)
	public void testNewPassword(){
		e = new DUser("dipina2" ,"dipina2@deusto.es", "12345");
		db.addUser(e);
		db.updatePassword(e.getEmail(), "nuevapassword");
		DUser ret = db.getUser("dipina2@deusto.es", "nuevapassword");
		assertEquals("nuevapassword", ret.getPassword());
		db.deleteAllUsers();
		
	}
	
	@Test
	@PerfTest(duration= 2000)
	public void addFile(){
	e = new DUser("dipina1" ,"dipina1@deusto.es", "12345");
	db.addUser(e);
	file = new DFile(e, "123", "nombre", "ayer");
	db.addFiles(file);
	
	ArrayList<DFile> ret = null;
	ret = db.getAllFilesOfAUser(e.getEmail());
	assertEquals(file.getName(), ret.get(0).getName());
	
	ret = db.getAllFiles();
	assertEquals(file.getName(), ret.get(0).getName());
	
	db.deleteAllFiles();
	
	
		
	}
	@Test
	@PerfTest(duration= 2000)
	public void addConnection(){
	DConnection con = new DConnection(23, "userEmail", "MAC");
	db.addConnection(con);
	ArrayList<DConnection> ret = null;
	ret = db.getConnections("userEmail");
	System.out.println("La conexion: " + ret.get(0).toString());
	assertEquals(con.getID(), ret.get(0).getID());
	db.deleteAllConnections();
	
		
	}
	
	
	
	@Test
	@PerfTest(duration = 2000)
	public void addMessage(){
		DMessage mes = new DMessage(1,"from", "to", "subject", "text");
		db.addMessage(mes);
		ArrayList<DMessage> ret = null;
		ret = db.getAllMessagesOfSendToAUser("to");
		System.out.println(ret.get(ret.size()-1).toString());
		assertEquals(ret.get(ret.size() -1).getEmailfrom(), "from");
		assertEquals(ret.get(ret.size() -1).getEmailTo(), "to");
		assertEquals(ret.get(ret.size() -1).getText(), "text");
		assertEquals(ret.get(ret.size() -1).getMessageId(), 1);
		assertEquals(ret.get(ret.size() -1).getSubject(), "subject");
		assertEquals(ret.get(ret.size() -1).getMessageId(), db.getLastMessageID());
		db.deleteAllMessages();
		
		
		
	}
	
	
	
	
	
	@AfterClass
	public static void tearDownClass() {
		db.deleteAllFiles();
		db.deleteAllConnections();
		db.deleteAllUsers();
		db.deleteAllMessages();
}

}
