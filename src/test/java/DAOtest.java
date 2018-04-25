import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;

public class DAOtest {
	private static IDeustoBoxDAO db;
	private static DUser e;
	private static DFile file;
	private static int number;
	
	@BeforeClass
	public static void setUpClass() {
		db = new DeustoBoxDAO();
		Random randomgenerator = new Random();
		number = randomgenerator.nextInt(1000);
	}
	
	
	@Test
	public void testUser() {
		e = new DUser("dipina" ,"dipina@deusto.es", "12345");
		db.addUser(e);
		DUser ret = db.getUser("dipina@deusto.es", "12345");
		
		assertEquals("dipina", ret.getUsername());
		assertEquals("dipina@deusto.es", ret.getEmail());
		assertEquals("12345", ret.getPassword());
		
	}
	@Test
	public void testFiles() {
		
		
		e = new DUser("dipina" ,"dipina@deusto.es", "12345");
		file = new DFile(e,number,"archivo.txt","Ayer");
		db.addFiles(file);
		ArrayList<DFile> alfiles = db.getAllFilesOfAUser("dipina@deusto.es");
		int pos = alfiles.size() -1;
		System.out.println(alfiles.get(pos).toString());
		assertEquals(number, alfiles.get(pos).getId_file());
		assertEquals("archivo.txt", alfiles.get(pos).getName());
		assertEquals("Ayer", alfiles.get(pos).getLastModified());
		
	}
	
	@AfterClass
	public void deleteDB(){
		db.deleteAllFiles();
		db.deleteAllUsers();
		
		
	}
	
	
	
	
	
	

}
