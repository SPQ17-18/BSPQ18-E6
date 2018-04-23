package es.deusto.bspq18.e6.DeustoBox.Server.dto;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class DFileDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userEmail;
	private File file;
	private Date creationDate;
	
	
	
	public DFileDTO(String userEmail, String path, String string) {
		this.userEmail = userEmail;
		this.file = new File(path);
		this.creationDate = new Date();
	}

	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
	
	

}
