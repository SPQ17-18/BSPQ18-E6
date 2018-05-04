import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assembler;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;

public class AssemblerTest {

	private static Assembler assembler;
	private static DUser user;
	private static DUserDTO userdto;
	private static DFile file;
	private static DFileDTO filedto;
	private static Error_log logger;

	@BeforeClass
	public static void setUpClass() {
		assembler = new Assembler();
		user = new DUser("goros", "gorosinha@deusto.es", "12345");
		file = new DFile(user, "123", "nombre", "ayer");

	}

	@Test
	public void testUserDTO() {
		userdto = assembler.createUserDTO(user);
		assertEquals(user.getEmail(), userdto.getEmail());
		assertEquals(user.getPassword(), userdto.getPassword());
		assertEquals(user.getRegisterDate(), userdto.getRegisteredDate());
		userdto.setEmail("email2");
		userdto.setPassword("password2");
		assertEquals("email2", userdto.getEmail());
		assertEquals("password2", userdto.getPassword());

	}

	@Test
	public void testUser() {
		userdto = assembler.createUserDTO(user);
		user = null;
		user = assembler.createUser(userdto);
		assertEquals(user.getEmail(), userdto.getEmail());
		assertEquals(user.getPassword(), userdto.getPassword());
	}

	@Test
	public void testFileDTO() {
		userdto = assembler.createUserDTO(user );
		filedto = assembler.createFileDTO(file, "path" );
		assertEquals("123", filedto.getHash());
		assertEquals(file.getLastModified(), filedto.getLastModified());
		assertEquals(filedto.getName(), filedto.getName());
		filedto.setHash("hash2");
		assertEquals("hash2", filedto.getHash());
		filedto.setLastModified("ayer3");
		assertEquals("ayer3", filedto.getLastModified());
		filedto.setName("nombre3");
		assertEquals("nombre3", filedto.getName());
		filedto.setUserEmail("email3");
		assertEquals("email3", filedto.getUserEmail());

	}

	@Test
	public void testFile() {
		userdto = assembler.createUserDTO(user);
		filedto = assembler.createFileDTO(file, "C//");
		assertEquals(file.getHash(), filedto.getHash());
		assertEquals(file.getLastModified(), filedto.getLastModified());

	}

	@Test
	public void testFilesDTO() {
		ArrayList<DFile> files = new ArrayList<DFile>();
		ArrayList<DFileDTO> filesdto = new ArrayList<DFileDTO>();

		DFile file1 = new DFile(user, "123", "nombre", "ayer");
		DFile file2 = new DFile(user, "456", "nombre2", "ayer2");
		System.out.println(file1.getHash());
		files.add(file1);
		files.add(file2);

		filesdto = assembler.createFilesDTO(files, "C://");

		assertEquals(files.get(0).getHash(), filesdto.get(0).getHash());
		assertEquals(files.get(1).getLastModified(), filesdto.get(1).getLastModified());

	}

	@Test
	public void testFiles() {
		ArrayList<DFile> files = new ArrayList<DFile>();
		ArrayList<DFileDTO> filesdto = new ArrayList<DFileDTO>();
		userdto = assembler.createUserDTO(user);
		DFileDTO file1 = new DFileDTO(userdto, "123", "nombre", "ayer");
		DFileDTO file2 = new DFileDTO(userdto, "456", "nombre2", "ayer2");
		filesdto.add(file1);
		filesdto.add(file2);
		files = assembler.createFiles(filesdto);
		assertEquals(files.get(0).getHash(), filesdto.get(0).getHash());
		assertEquals(files.get(1).getLastModified(), filesdto.get(1).getLastModified());

	}

}
