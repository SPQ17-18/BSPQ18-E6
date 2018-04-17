package es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
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

	public void addFiles(HashMap<String, String> map, DUser user) {
		PersistenceManager pm = null;
		Transaction tx = null;
		int numero = 0;
		numero = getnewDFileID();
		int contador = 0;
		DUser auxiliar = null;
		System.out.println("El tamaño es : " + map.size());
		try {
			System.out.println("- Store user's files in the DB");
			pm = pmf.getPersistenceManager();
			pm.getFetchPlan().setMaxFetchDepth(3);
			tx = pm.currentTransaction();
			tx.begin();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);
			for(DUser user2 : extent) {
			System.out.println(user2.toString());
				if(user2.getEmail().equals(user.getEmail())) {
					auxiliar = new DUser(user2.getUsername(),user2.getEmail(),user2.getPassword());
					pm.deletePersistent(user2);
					tx.commit();
					for (Map.Entry<String, String> entry : map.entrySet()) {
						auxiliar.addFile(new DFile(user, (numero + contador), entry.getKey(), entry.getValue()));
						
						System.out.println("Archivo añadido");
						System.out.println(auxiliar.toString());
						contador++;
					}
					System.out.println("Let's make it persistent and delete the old user");
					System.out.println("Auxiliar es: " + auxiliar.toString());
					addUser(auxiliar);
					break;
					
				}
			}
			System.out.println("Inserting user's files into the database: SUCCESFUL");

		} catch (Exception ex) {
			System.out.println("# Error storing objects: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}

			pm.close();
			System.out.println(getAllUsers());
		}
		
	}
	
	public int getnewDFileID() { // Devuelve un User
		// Persistence Manager
		int number = 0;
		PersistenceManager pm = null;
		// Transaction to group DB operations
		Transaction tx = null;
		pm = pmf.getPersistenceManager();
		tx = pm.currentTransaction();
		try {

			// Start the transaction
			tx.begin();

			Extent<DFile> extent = pm.getExtent(DFile.class, true);

			for (DFile res : extent) {
				number = res.getId_file();
			}

			number++;

		} catch (Exception ex) {
			System.out.println("# Error getting the DFiles " + ex.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return number;
	}
	
	public static void main(String[] args) {
		IDeustoBoxDAO dao = new DeustoBoxDAO();

		DUser user1 = new DUser("aitorugarte@opendeusto.es", "aitorugarte", "123");
		DUser user2 = new DUser("markelalva@opendeusto.es", "markelalva", "123");

		dao.addUser(user1);
		dao.addUser(user2);

	}

}
