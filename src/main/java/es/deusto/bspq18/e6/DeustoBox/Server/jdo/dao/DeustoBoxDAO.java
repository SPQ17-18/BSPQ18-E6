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

import org.datanucleus.api.jdo.JDOQuery;

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

					myUser = new DUser(usuario.getUsername(), usuario.getEmail(), usuario.getPassword(), usuario.getRegisterDate());
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

	public ArrayList<DFile> getAllFiles() {
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

	public ArrayList<DUser> getAllUsers() {
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
				if (file.getUser().getEmail().equals(usuario.getEmail())) {
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

	@Override
	public ArrayList<DFile> getAllFilesOfAUser(String email) {

		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		DUser e = null;
		try {
			System.out.println("- Retrieving Files of a certain User using an 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser user : extent) {
				if (user.getEmail().equals(email)) {
					e = new DUser(user.getUsername(), user.getEmail(), user.getPassword());
					System.out.println("En el DAO: " + user.getFiles().get(0).toString());
					e.setFiles(user.getFiles());
					System.out.println("Hay archivos" + e.getFiles().size());
				}
			}

		} catch (Exception ex) {
			System.out.println("# Error retrieving Files of a certain User using an 'Extent': " + ex.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return e.getFiles();
	}

	public static void main(String[] args) {
		IDeustoBoxDAO dao = new DeustoBoxDAO();

		DUser user1 = new DUser("aitorugarte@opendeusto.es", "aitorugarte", "123");
		DUser user2 = new DUser("markelalva@opendeusto.es", "markelalva", "123");

		dao.addUser(user1);
		dao.addUser(user2);

	}

	@Override
	public int getNumberOfUserFiles(String email) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();
		int number = 0;
		try {
			System.out.println("- Retrieving the number of files of a certain User using an 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser user : extent) {
				if (user.getEmail().equals(email)) {
					number = user.getFiles().size();
				}
			}

		} catch (Exception ex) {
			System.out.println(
					"# Error retrieving the number of  Files of a certain User using an 'Extent': " + ex.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return number;
	}

	@Override
	public boolean checkPassword(String email, String password) {
		boolean correct = false;

		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		try {
			System.out.println("- Checking the password of the  User using an 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser user : extent) {
				if (user.getEmail().equals(email)) {
					if(user.getPassword().equals(password))
					correct = true;
				}
			}

		} catch (Exception ex) {
			System.out.println("Checking the password of the  User using an 'Extent': " + ex.getMessage());

		}
		return correct;

	}

	@Override
	public boolean updatePassword(String email, String password) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();
		boolean correcto = true;

		try {
			tx.begin();
			System.out.println("Modifying the password...");

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser usuario : extent) {
				if (email.equals(usuario.getEmail())) {
					usuario.setPassword(password);
					pm.makePersistent(usuario);
					break;
				}
			}
			tx.commit();
		} catch (Exception ex) {
			correcto = false;
			System.out.println("# Error modifying the password: " + ex.getMessage());
		}
		return correcto;
	}

	@Override
	public boolean deleteAllFiles() {
		boolean correcto = true;
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			JDOQuery<DFile> query = (JDOQuery<DFile>) pm.newQuery(DFile.class);
			tx.commit();
		} catch (Exception ex) {
			correcto = false;
			
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}

		return correcto;
	}

	@Override
	public boolean deleteAllUsers() {
		boolean correcto = true;
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			JDOQuery<DUser> query = (JDOQuery<DUser>) pm.newQuery(DUser.class);
			tx.commit();
		} catch (Exception ex) {
			correcto = false;
			
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}

		return correcto;
	}


}
