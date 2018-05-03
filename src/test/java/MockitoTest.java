import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assembler;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;

@RunWith(MockitoJUnitRunner.class)  
public class MockitoTest {
	
	private static Assembler assembler;
	private static DUser user;
	private static DUserDTO userdto;
	private static DFile file;
	private static DFileDTO filedto;
	
	@Before
	public  void setUpClass() {
		assembler = new Assembler();
		user = new DUser("goros" ,"gorosinha@deusto.es", "12345");
		file = new DFile(user, "123", "nombre", "ayer");
		
		
	}
	
	@Test
	public void testUserDTO() {
		userdto = assembler.createUserDTO(user, "C://");
		assertEquals(user.getEmail(), userdto.getEmail());
		assertEquals(user.getPassword(), userdto.getPassword());
		assertEquals(user.getRegisterDate(), userdto.getRegisteredDate());
		
	}
	
	
}
