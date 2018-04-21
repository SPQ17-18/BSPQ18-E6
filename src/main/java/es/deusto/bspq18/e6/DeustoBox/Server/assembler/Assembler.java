package es.deusto.bspq18.e6.DeustoBox.Server.assembler;

import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;

public class Assembler {

	public DUserDTO userDTO(DUser user) {
		DUserDTO userdto =  new DUserDTO(user.getEmail(), user.getUsername(), user.getPassword());
		userdto.setFiles(user.getFiles());
		return userdto;
	}
	
	public DUser user(DUserDTO userdto) {
		DUser user = new DUser(userdto.getEmail(), userdto.getUsername(), userdto.getPassword());
		user.setFiles(userdto.getFiles());
		return user;
	}
	
}
