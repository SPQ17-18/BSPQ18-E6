package spq.server.data;

import java.io.File;
import java.util.ArrayList;

import spq.server.data.PersistenceCapable;
import spq.server.data.PrimaryKey;
@PersistenceCapable
public class Archivo {
	@PrimaryKey
	private int FileID;
	private String name;
	private File file;
	private ArrayList <String> Users;
	
	public Archivo(int fileID, String name, File file, ArrayList<String> users) {
		this.FileID = fileID;
		this.name = name;
		this.file = file;
		this.Users = users;
	}
	
	public Archivo() {
		this.FileID = 1;
		this.name = " ";
		this.file = new File(" ");
		this.Users = new ArrayList<String> ();	
		
	}

	public int getFileID() {
		return FileID;
	}

	public void setFileID(int fileID) {
		FileID = fileID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public ArrayList<String> getUsers() {
		return Users;
	}

	public void setUsers(ArrayList<String> users) {
		Users = users;
	}

	@Override
	public String toString() {
		return "Archivo [FileID=" + FileID + ", name=" + name + ", file=" + file + ", Users=" + Users + "]";
	}
	
	

	
	
}
