package es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao;

import java.util.ArrayList;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;

public interface IDeustoBoxDAO {
	
	public DUser getUser(String email, String pass);
	public boolean addUser(DUser e);
	public ArrayList<DFile> getAllFiles();
	public ArrayList<DFile> getAllFilesOfAUser(String email);
	public ArrayList<DUser> getAllUsers();
	public void addFiles(DFile file);
}
