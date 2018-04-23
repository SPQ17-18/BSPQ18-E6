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
	private String hash;
	private String userEmail;
	private File file;
	private String lastModified;

	
	public DFileDTO(String hash, String userEmail, String path, String lastModified) {
		this.hash = hash;
		this.userEmail = userEmail;
		this.file = new File(path);
		this.lastModified = lastModified;
	}
	
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	
	
	
	

}
