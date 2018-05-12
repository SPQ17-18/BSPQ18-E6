import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.databene.contiperf.Required;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assembler;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DConnectionDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DConnection;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;

@PerfTest(invocations = 5)
@Required(max = 1200, average = 250)
public class AssemblerTest {

	private static Assembler assembler;
	private static DUser user;
	private static DUserDTO userdto;
	private static DFile file;
	private static DFileDTO filedto;
	private static DConnection connection;
	private static DConnectionDTO connectiondto;
	private static Error_log logger;
	@Rule
	public ContiPerfRule rule = new ContiPerfRule();

	@BeforeClass
	public static void setUpClass() {
		assembler = new Assembler();
		user = new DUser("goros", "gorosinha@deusto.es", "12345");
		file = new DFile(user, "123", "nombre", "ayer");
		connection = new DConnection(1, "gorosinha@deusto.es", "Windows");

	}

	@Test
	@PerfTest(duration = 2000)
	public void testUserDTO() {
		userdto = assembler.createUserDTO(user, "C://");
		assertEquals(user.getEmail(), userdto.getEmail());
		assertEquals(user.getPassword(), userdto.getPassword());
		assertEquals(user.getRegisterDate(), userdto.getRegisteredDate());
		userdto.setEmail("email2");
		userdto.setPassword("password2");
		assertEquals("email2", userdto.getEmail());
		assertEquals("password2", userdto.getPassword());

	}

	@Test
	@PerfTest(invocations = 1000, threads = 20)
	@Required(max = 120, average = 30)
	public void testUser() {
		userdto = assembler.createUserDTO(user, "C://");
		user = assembler.createUser(userdto);
		assertEquals(user.getEmail(), userdto.getEmail());
		assertEquals(user.getPassword(), userdto.getPassword());
	}

	@Test
	@PerfTest(duration = 2000)
	public void testFileDTO() {
		userdto = assembler.createUserDTO(user, "C://");
		filedto = assembler.createFileDTO(file, "C//");
		assertEquals(file.getHash(), filedto.getHash());
		assertEquals(file.getLastModified(), filedto.getLastModified());
		assertEquals(file.getName(), filedto.getName());
		assertEquals(file.getUser().getEmail(), filedto.getUser().getEmail());
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
	@PerfTest(invocations = 1000, threads = 20)
	@Required(max = 120, average = 30)
	public void testFile() {
		userdto = assembler.createUserDTO(user, "C//");
		filedto = assembler.createFileDTO(file, "C//");
		file = assembler.createFile(filedto);
		assertEquals(file.getHash(), filedto.getHash());
		assertEquals(file.getLastModified(), filedto.getLastModified());
		assertEquals(file.getName(), filedto.getName());
		assertEquals(file.getUser().getEmail(), filedto.getUser().getEmail());

	}

	@Test
	@PerfTest(duration = 2000)
	public void testFilesDTO() {
		ArrayList<DFile> files = new ArrayList<DFile>();
		ArrayList<DFileDTO> filesdto = new ArrayList<DFileDTO>();

		DFile file1 = new DFile(user, "123", "nombre", "ayer");
		DFile file2 = new DFile(user, "456", "nombre2", "ayer2");
		files.add(file1);
		files.add(file2);

		filesdto = assembler.createFilesDTO(files, "C://");

		assertEquals(files.get(0).getHash(), filesdto.get(0).getHash());
		assertEquals(files.get(1).getLastModified(), filesdto.get(1).getLastModified());

	}

	@Test
	@PerfTest(duration = 2000)
	public void testFiles() {
		ArrayList<DFile> files = new ArrayList<DFile>();
		ArrayList<DFileDTO> filesdto = new ArrayList<DFileDTO>();
		userdto = assembler.createUserDTO(user, "c://");
		DFileDTO file1 = new DFileDTO(userdto, "123", "nombre", "ayer", "C://");
		DFileDTO file2 = new DFileDTO(userdto, "456", "nombre2", "ayer2", "C://");
		filesdto.add(file1);
		filesdto.add(file2);
		files = assembler.createFiles(filesdto);
		assertEquals(files.get(0).getHash(), filesdto.get(0).getHash());
		assertEquals(files.get(1).getLastModified(), filesdto.get(1).getLastModified());

	}

	@Test
	@PerfTest(duration = 2000)
	public void testConnectionDTO() {
		connectiondto = assembler.createConnectionDTO(connection);
		assertEquals(connectiondto.getConnectionDate(), connection.getConnectionDate());
		assertEquals(connectiondto.getId(), connection.getID());
		assertEquals(connectiondto.getOSUsed(), connection.getOSUsed());
		assertEquals(connectiondto.getUserEmail(), connection.getUserEmail());
		Date e = new Date();
		connection.setConnectionDate(e);
		assertEquals(connection.getConnectionDate(), e);
		connection.setID(2);
		assertEquals(connection.getID(), 2);
		connection.setOSUsed("MAC");
		assertEquals(connection.getOSUsed(), "MAC");
		connection.setUserEmail("email");
		assertEquals(connection.getUserEmail(), "email");

		connectiondto.setConnectionDate(e);
		assertEquals(connectiondto.getConnectionDate(), e);
		connectiondto.setOSUsed("MAC");
		assertEquals(connectiondto.getOSUsed(), "MAC");
		connectiondto.setUserEmail("email");
		assertEquals(connectiondto.getUserEmail(), "email");
		System.out.println(connection.toString());
		System.out.println(connectiondto.toString());

	}

}
