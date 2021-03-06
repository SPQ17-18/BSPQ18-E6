package es.deusto.bspq18.e6.DeustoBox.Server.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;

public class DUserDTO implements Serializable {

	private static final long serialVersionUID = 2367563927332146572L;
	private String email;
	private String username;
	private String password;
	private ArrayList<DFile> files;
	private Date registeredDate;
	


	
	public DUserDTO(String email, String username, String password, Date registered) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.files = new ArrayList<DFile>();
		this.registeredDate = registered;
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

	
	
	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public ArrayList<DFile> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<DFile> files) {
		this.files = files;
	}
	
	public void removeFile(DFile file) {
		files.remove(file);
	}

	@Override
	public String toString() {
		return "DUserDTO [email=" + email + ", username=" + username + ", password=" + password + ", files=" + files
				+ ", registeredDate=" + registeredDate + "]";
	}



}
