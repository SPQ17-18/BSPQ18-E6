package es.deusto.bspq18.e6.DeustoBox.Server.jdo.data;

import java.io.Serializable;
import java.util.HashMap;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(detachable="true")
public class User implements Serializable {

	private static final long serialVersionUID = -3653362043946570733L;
	@PrimaryKey
	private String email;
	private String username;
	private String password;
	private HashMap<String, String> files;

	public User(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
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
	
	public HashMap<String, String> getFiles() {
		return files;
	}

	public void setFiles(HashMap<String, String> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", username=" + username + ", password=" + password + "]";
	}

}
