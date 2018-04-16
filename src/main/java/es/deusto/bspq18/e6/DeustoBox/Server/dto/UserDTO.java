package es.deusto.bspq18.e6.DeustoBox.Server.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 2367563927332146572L;
	private String email;
	private String username;
	private String password;
	private Date fechaInicio;
	private HashMap<String, String> files;

	public UserDTO(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.fechaInicio = new Date();
		this.files = new HashMap<String, String>();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public HashMap<String, String> getFiles() {
		return files;
	}

	public void setFiles(HashMap<String, String> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "UserDTO [email=" + email + ", username=" + username + ", password=" + password + ", fechaInicio="
				+ fechaInicio + "]";
	}

	

}
