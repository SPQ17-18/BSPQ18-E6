package es.deusto.bspq18.e6.DeustoBox.Server.jdo.data;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class DFile implements Serializable  {

	private static final long serialVersionUID = 5302060193237346315L;
	@PrimaryKey
	private String hash;
	private String name;
	private String lastModified;
	@Persistent(defaultFetchGroup="true")
	private DUser user;
	
	public DFile(DUser user, String hash, String name, String lastModified) {
		this.user = user;
		this.hash = hash;
		this.name = name;
		this.lastModified = lastModified;
	}
	
	public DUser getUser() {
		return user;
	}

	public void setUser(DUser user) {
		this.user = user;
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
	
	@Override
	public String toString() {
		return "DFile [name=" + name + ", lastModified=" + lastModified + "]";
	}
	
	
	
	
	
}
