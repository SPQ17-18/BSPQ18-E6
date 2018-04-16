package es.deusto.bspq18.e6.DeustoBox.Server.assembler;

import es.deusto.bspq18.e6.DeustoBox.Server.dto.UserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.User;

public class Assembler {

	public UserDTO userDTO(User user) {
		
		UserDTO us = new UserDTO(user.getEmail(), user.getName(), user.getPassword());
		return us;
	}
	
	public User user(UserDTO userdto) {
		return new User(userdto.getEmail(), userdto.getName(), userdto.getPassword());
	}
	
}
