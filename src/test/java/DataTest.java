import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;

@PerfTest(invocations = 5)
@Required(max = 1200, average = 250)
public class DataTest {
	private static DUser user;
	private static DFile file;
	private static DUserDTO userdto;
	private static DFileDTO filedto;
	@Rule
	public ContiPerfRule rule = new ContiPerfRule();
	@BeforeClass
	public static void setUpClass() {
	 user = new DUser("username","email","password", new Date());
	 file = new DFile(user, "hash", "name", "lastModified");
	 userdto = new DUserDTO("email", "username", "password", null);
	 filedto = new DFileDTO(userdto, "hash", "name", "lastModified", "path");
	 
	 
	}
	
	@Test
	@PerfTest(duration = 2000)
	public void TestUser() {
		user = new DUser("username","email","password", new Date());
		assertEquals(user.getUsername(), "username");
		user.setUsername("username2");
		assertEquals(user.getUsername(), "username2");
		assertEquals(user.getEmail(), "email");
		user.setEmail("email2");
		assertEquals(user.getEmail(), "email2");
		assertEquals(user.getPassword(), "password");
		user.setPassword("password2");
		assertEquals(user.getPassword(), "password2");
		user.addFile(file);
		assertEquals(user.getFiles().get(user.getFiles().size()-1).getName(), file.getName());
		Date e = new Date();
		user.setRegisterDate(e);
		assertEquals(user.getRegisterDate(), e);
		System.out.println(user.toString());
		
		

		
	}
	
	@Test
	@PerfTest(duration = 2000)
	public void TestFile() {
		file = new DFile(user, "hash", "name", "lastModified");
		DUser user2 = new DUser("username3","email3","password3", new Date());
		assertEquals(file.getUser(), user);
		file.setUser(user2);
		assertEquals(file.getUser(), user2);
		assertEquals(file.getHash(), "hash");
		file.setHash("hash2");
		assertEquals(file.getHash(), "hash2");
		file.setLastModified("ayer3");
		assertEquals(file.getLastModified(), "ayer3");
		System.out.println(file.toString());
		
		

	
	}
	@Test
	@PerfTest(duration = 2000)
	public void testUserDTO() {
		userdto = new DUserDTO("email", "username", "password", null);
		assertEquals(userdto.getUsername(), "username");
		userdto.setUsername("username2");
		assertEquals(userdto.getUsername(), "username2");
		assertEquals(userdto.getEmail(), "email");
		userdto.setEmail("email2");
		assertEquals(userdto.getEmail(), "email2");
		assertEquals(userdto.getPassword(), "password");
		userdto.setPassword("password2");
		assertEquals(userdto.getPassword(), "password2");
		Date e = new Date();
		userdto.setRegisteredDate(e);
		assertEquals(userdto.getRegisteredDate(), e);
		System.out.println(userdto.toString());
		
	}
	
	@Test
	@PerfTest(duration= 2000)
	public void testFileDTO() {
		filedto = new DFileDTO(userdto, "hash", "name", "lastModified", "path");
		assertEquals(filedto.getUser(), userdto);
		assertEquals(filedto.getHash(), "hash");
		filedto.setHash("hash2");
		assertEquals(filedto.getHash(), "hash2");
		filedto.setLastModified("ayer3");
		assertEquals(filedto.getLastModified(), "ayer3");
		Date e = new Date();
		filedto.setCreationDate(e);
		assertEquals(filedto.getCreationDate(), e);
		assertEquals(filedto.getName(), "name");
		System.out.println(filedto.toString());
		
	}

}
