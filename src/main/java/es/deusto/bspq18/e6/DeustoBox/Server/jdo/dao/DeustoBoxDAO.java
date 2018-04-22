package es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;

public class DeustoBoxDAO implements IDeustoBoxDAO {

	private PersistenceManagerFactory pmf;

	public DeustoBoxDAO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}

	public DUser getUser(String email, String pass) {

		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		DUser myUser = null;
		try {
			System.out.println("- Retrieving Users using an 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			tx.begin();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser usuario : extent) {
				if (usuario.getEmail().equals(email) && usuario.getPassword().equals(pass)) {
					System.out.println("  -> " + usuario);
					myUser = usuario;
					break;
				}
			}

			tx.commit();

		} catch (Exception ex) {
			System.out.println("# Error retrieving Users using an 'Extent': " + ex.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return myUser;
	}
	
	public ArrayList<DFile> getAllFiles(){
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		ArrayList<DFile> files = new ArrayList<DFile>();
		try {
			System.out.println("- Retrieving Files using an 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			tx.begin();

			Extent<DFile> extent = pm.getExtent(DFile.class, true);

			for (DFile file : extent) {
				files.add(file);
			}

			tx.commit();

		} catch (Exception ex) {
			System.out.println("# Error retrieving Files using an 'Extent': " + ex.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return files;
	}
	
	public ArrayList<DUser> getAllUsers(){
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		ArrayList<DUser> users = new ArrayList<DUser>();
		try {
			System.out.println("- Retrieving Users using an 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			tx.begin();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser usuario : extent) {
				users.add(usuario);
			}

			tx.commit();

		} catch (Exception ex) {
			System.out.println("# Error retrieving Users using an 'Extent': " + ex.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return users;
	}

	public boolean addUser(DUser user) {

		boolean correct = true;
		PersistenceManager pm = null;
		Transaction tx = null;

		try {
			System.out.println("- Store objects in the DB");
			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();
			tx.begin();

			pm.makePersistent(user);
			tx.commit();
			System.out.println("Inserting user into the database: SUCCESFUL");

		} catch (Exception ex) {
			System.out.println("# Error storing objects: " + ex.getMessage());
			correct = false;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return correct;
	}

	
	public void addFiles(DFile file) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction(); 

		try {
			tx.begin();
			System.out.println("Adding files to the user...");
			
			Extent<DUser> extent = pm.getExtent(DUser.class, true);
			
			for (DUser usuario : extent) {
				if(file.getUser().getEmail().equals(usuario.getEmail())) {
					file.setUser(usuario);
					usuario.addFile(file);
					pm.makePersistent(usuario);
					break;
				}
			}
			tx.commit();
		} catch (Exception ex) {
			System.out.println("# Error storing objects: " + ex.getMessage());
		}
	}
	
	public void deleteFiles(DFile file) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction(); 

		try {
			tx.begin();
			System.out.println("Deleting user files...");
			
			Extent<DUser> extent = pm.getExtent(DUser.class, true);
			
			for (DUser usuario : extent) {
				if(file.getUser().getEmail().equals(usuario.getEmail())) {
					System.out.println("Owner found");
					usuario.removeFile(file);
					pm.makePersistent(usuario);
					break;
				}
			}
			
			Extent<DFile> extent2 = pm.getExtent(DFile.class, true);
			
			for (DFile fileDB : extent2) {
				if(file.getHash().equals(fileDB.getHash())) {
					System.out.println("File found");
					file.setUser(null);
					pm.deletePersistent(fileDB);
					break;
				}
			}
			tx.commit();
			
		} catch (Exception ex) {
			System.out.println("# Error deleting objects: " + ex.getMessage());
		}
		
	}
	
	public static void main(String[] args) {
		IDeustoBoxDAO dao = new DeustoBoxDAO();

		DUser user1 = new DUser("aitorugarte@opendeusto.es", "aitorugarte", "123");
		DUser user2 = new DUser("markelalva@opendeusto.es", "markelalva", "123");

		dao.addUser(user1);
		dao.addUser(user2);

	}

}
