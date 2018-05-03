import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assembler;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;

public class AssemblerTest {

	private static Assembler assembler;
	private static DUser user;
	private static DUserDTO userdto;
	private static DFile file;
	private static DFileDTO filedto;

	@BeforeClass
	public static void setUpClass() {
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
	
	@Test
	public void testUser(){
	userdto = assembler.createUserDTO(user, "C://");
	user = null;
	user = assembler.createUser(userdto);
	assertEquals(user.getEmail(), userdto.getEmail());
	assertEquals(user.getPassword(), userdto.getPassword());
}
	
	
	@Test
	public void testFileDTO() {
		userdto = assembler.createUserDTO(user, "C://");
		filedto = assembler.createFileDTO(file, "C//");
		assertEquals(file.getHash(), filedto.getHash());
		assertEquals(file.getLastModified(), filedto.getLastModified());
		assertEquals(file.getName(), filedto.getName());
		assertEquals(file.getUser().getEmail(), filedto.getUser().getEmail());
		
		
}
	
	@Test
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
	public void testFiles(){
		ArrayList<DFile> files = new ArrayList<DFile>();
		ArrayList<DFileDTO> filesdto = new ArrayList<DFileDTO>();
		userdto = assembler.createUserDTO(user, "c://");
		DFileDTO file1 = new DFileDTO(userdto, "123", "nombre", "ayer", "C://");
		DFileDTO file2 = new DFileDTO(userdto, "456", "nombre2", "ayer2","C://" );
		filesdto.add(file1);
		filesdto.add(file2);
		files = assembler.createFiles(filesdto);
		assertEquals(files.get(0).getHash(), filesdto.get(0).getHash());
		assertEquals(files.get(1).getLastModified(), filesdto.get(1).getLastModified());
		
		
		

	
		
		
}
	
	

}
