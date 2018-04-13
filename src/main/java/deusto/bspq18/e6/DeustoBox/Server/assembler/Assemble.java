package deusto.bspq18.e6.DeustoBox.Server.assembler;

import deusto.bspq18.e6.DeustoBox.Server.data.User;
import deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;

public class Assemble {

	public UserDTO userDTO(User user) {
		return new UserDTO(user.getEmail(), user.getName(), user.getPassword());
	}
	
	public User user(UserDTO userdto) {
		return new User(userdto.getEmail(), userdto.getName(), userdto.getPassword());
	}
	
}
