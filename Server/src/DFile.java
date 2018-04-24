package es.deusto.bspq18.e6.DeustoBox.Server.jdo.data;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class DFile implements Serializable  {

	private static final long serialVersionUID = 5302060193237346315L;
	@PrimaryKey
	private int id_file;
	private String name;
	private String lastModified;
	@Persistent(defaultFetchGroup="true")
	private DUser user;
	
	public DFile(DUser user, int id_file, String name, String lastModified) {
		this.user = user;
		this.id_file = id_file;
		this.name = name;
		this.lastModified = lastModified;
	}
	
	public DUser getUser() {
		return user;
	}

	public void setUser(DUser user) {
		this.user = user;
	}

	public int getId_file() {
		return id_file;
	}

	public void setId_file(int id_file) {
		this.id_file = id_file;
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
