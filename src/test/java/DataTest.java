import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;


public class DataTest {
	private static DUser user;
	private static DFile file;
	@BeforeClass
	public static void setUpClass() {
	 user = new DUser("username","email","password", new Date(), null);
	 file = new DFile(user, "hash", "name", "lastModified");

	}
	
	@Test
	public void TestUser() {
		assertEquals(user.getUsername(), "username");
		user.setUsername("username2");
		assertEquals(user.getUsername(), "username2");
		assertEquals(user.getEmail(), "email");
		user.setEmail("email2");
		assertEquals(user.getEmail(), "email2");
		assertEquals(user.getPassword(), "password");
		user.setPassword("password2");
		assertEquals(user.getPassword(), "password2");

		
	}
	
	@Test
	public void TestFile() {
		DUser user2 = new DUser("username3","email3","password3", new Date(), null);
		assertEquals(file.getUser(), user);
		file.setUser(user2);
		assertEquals(file.getUser(), user2);
		assertEquals(file.getHash(), "hash");
		file.setHash("hash2");
		assertEquals(file.getHash(), "hash2");

	
	}

}
