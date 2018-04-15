package es.deusto.bspq18.e6.DeustoBox.Server.assembler;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.User;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;

public class Assembler {

	public UserDTO userDTO(User user) {
		return new UserDTO(user.getEmail(), user.getName(), user.getPassword());
	}
	
	public User user(UserDTO userdto) {
		return new User(userdto.getEmail(), userdto.getName(), userdto.getPassword());
	}
	
}
