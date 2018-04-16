package es.deusto.bspq18.e6.DeustoBox.Server.assembler;

import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.User;

public class Assembler {

	public UserDTO userDTO(User user) {
		UserDTO userdto =  new UserDTO(user.getEmail(), user.getUsername(), user.getPassword());
		userdto.setFiles(user.getFiles());
		return userdto;
	}
	
	public User user(UserDTO userdto) {
		User user = new User(userdto.getEmail(), userdto.getUsername(), userdto.getPassword());
		user.setFiles(userdto.getFiles());
		return user;
	}
	
}
