import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assembler;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;


public class AssemblerTest {
	
	private static Assembler assembler;
	private static DUser user;
	private static DUserDTO userdto;

	@BeforeClass
	public static void setUpClass() {
		assembler = new Assembler();
		user = new DUser("goros" ,"gorosinha@deusto.es", "12345");
		
		
	}
	
	@Test
	public void testUserDTO() {
		userdto = assembler.createUserDTO(user);
		assertEquals(user.getEmail(), userdto.getEmail());
		assertEquals(user.getPassword(), userdto.getPassword());
		assertEquals(user.getRegisterDate(), userdto.getRegisteredDate());
		
		
	}
	@Test
	public void testUser(){
	userdto = assembler.createUserDTO(user);
	user = null;
	user = assembler.createUser(userdto);
	assertEquals(user.getEmail(), userdto.getEmail());
	assertEquals(user.getPassword(), userdto.getPassword());
	}

}
