import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;

public class DAOtest {

	private static IDeustoBoxDAO db;
	private static DUser e;
	private static DFile file;
	
	@BeforeClass
	public static void setUpClass() {
		db = new DeustoBoxDAO(new Error_log());

	}

	@Test
	public void testUser() {
		e = new DUser("dipina4" ,"dipina4@deusto.es", "12345");
		db.addUser(e);
		DUser ret = db.getUser("dipina4@deusto.es", "12345");
		
		assertEquals("dipina4", ret.getUsername());
		assertEquals("dipina4@deusto.es", ret.getEmail());
		assertEquals("12345", ret.getPassword());
		db.deleteAllUsers();
		
}
	
	@Test
	public void testUserPassword() {
		e = new DUser("dipina3" ,"dipina3@deusto.es", "12345");
		db.addUser(e);
		System.out.println();
		assertEquals(true, db.checkPassword("dipina3@deusto.es", "12345"));
		db.deleteAllUsers();
}
	
	@Test
	public void testNewPassword(){
		e = new DUser("dipina2" ,"dipina2@deusto.es", "12345");
		db.addUser(e);
		db.updatePassword(e.getEmail(), "nuevapassword");
		DUser ret = db.getUser("dipina2@deusto.es", "nuevapassword");
		assertEquals("nuevapassword", ret.getPassword());
		db.deleteAllUsers();
		
	}
	
	@Test
	public void addFile(){
	e = new DUser("dipina1" ,"dipina1@deusto.es", "12345");
	db.addUser(e);
	file = new DFile(e, "123", "nombre", "ayer");
	db.addFiles(file);
	
	ArrayList<DFile> ret = null;
	ret = db.getAllFilesOfAUser(e.getEmail());
	assertEquals(file.getName(), ret.get(0).getName());
	db.deleteAllUsers();
	
		
	}
	
	
	
	@AfterClass
	public static void tearDownClass() {
		db.deleteAllFiles();
		db.deleteAllConnections();
		db.deleteAllUsers();
}

}
