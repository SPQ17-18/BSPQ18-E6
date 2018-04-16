package es.deusto.bspq18.e6.DeustoBox.Server.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;

public class DUserDTO implements Serializable {

	private static final long serialVersionUID = 2367563927332146572L;
	private String email;
	private String username;
	private String password;
	private Date fechaInicio;
	private ArrayList<DFile> files;

	public DUserDTO(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.fechaInicio = new Date();
		this.files = new ArrayList<DFile>();
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
	
	public ArrayList<DFile> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<DFile> files) {
		this.files = files;
	}
	
	public void addFiles(DFile file) {
		DFile newFile = new DFile(this.getEmail(), file.getId_file(), file.getName(), file.getLastModified());
		newFile.setName(file.getName());
		newFile.setLastModified(file.getLastModified());		
		files.add(newFile);
	}
	public void removeFile(DFile file) {
		files.remove(file);
	}

	@Override
	public String toString() {
		return "UserDTO [email=" + email + ", username=" + username + ", password=" + password + ", fechaInicio="
				+ fechaInicio + "]";
	}

	

}
