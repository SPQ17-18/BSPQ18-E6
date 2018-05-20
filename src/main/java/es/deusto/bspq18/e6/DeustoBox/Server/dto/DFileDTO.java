package es.deusto.bspq18.e6.DeustoBox.Server.dto;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;


public class DFileDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userEmail;
	private String hash;
	private String name;
	private String lastModified;
	private File file;
	private Date creationDate;
	private DUserDTO user;
	
	Locale currentLocale = new Locale("en", "US");
	ResourceBundle resourcebundle = ResourceBundle.getBundle("lang/translations", currentLocale);
	
	public DFileDTO(String userEmail, String path, String string) {
		this.userEmail = userEmail;
		this.file = new File(path);
		this.creationDate = new Date();
	}
	
	public DFileDTO(DUserDTO user, String hash, String name, String lastModified, String path) {
		this.user = user;
		this.hash = hash;
		this.name = name;
		this.lastModified = lastModified;
		this.file = new File(path);
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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public DUserDTO getUser() {
		return user;
	}

	public void setUser(DUserDTO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return resourcebundle.getString("msg_dfile_email") + userEmail + resourcebundle.getString("msg_hash") + hash + resourcebundle.getString("msg_dfile_name") + name + resourcebundle.getString("msg_dfile_mod")
				+ lastModified + resourcebundle.getString("msg_dfile_file") + file + resourcebundle.getString("msg_dfile_date") + creationDate + resourcebundle.getString("msg_dfile_user") + user + "]";
	}
		
	
	
}
