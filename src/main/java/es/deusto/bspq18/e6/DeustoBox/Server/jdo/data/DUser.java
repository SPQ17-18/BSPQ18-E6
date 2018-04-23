package es.deusto.bspq18.e6.DeustoBox.Server.jdo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(detachable="true")
public class DUser implements Serializable {

	private static final long serialVersionUID = -3653362043946570733L;
	@PrimaryKey
	private String email;
	private String username;
	private String password;
	private Date registerDate;
	
	@Persistent(defaultFetchGroup="true", mappedBy="user", dependentElement = "true")
	@Join
	private ArrayList<DFile> files;

	public DUser(String username,String email, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.files = new ArrayList<DFile>();
		this.registerDate = new Date();
	}
	
	public DUser(String username,String email, String password, Date registerDate) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.files = new ArrayList<DFile>();
		this.registerDate = registerDate;
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
		
	public ArrayList<DFile> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<DFile> files) {
		this.files = files;
	}
	
	public void addFile(DFile file) {	
		files.add(file);
		file.setUser(this);
	}
	public void removeFile(DFile file) {
		files.remove(file);
	}
	
	

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", username=" + username + ", password=" + password + "]";
	}

}
