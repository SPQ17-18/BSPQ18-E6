package es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.datanucleus.api.jdo.JDOQuery;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;

public class DeustoBoxDAO implements IDeustoBoxDAO {

	private PersistenceManagerFactory pmf;
	private Error_log logger;

	public DeustoBoxDAO(Error_log logger) {
		this.pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.logger = logger;
	}

	@Override
	public DUser getUser(String email, String pass) {

		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		DUser myUser = null;
		try {
			logger.getLogger().info("- Retrieving Users using an 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			tx.begin();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser usuario : extent) {
				if (usuario.getEmail().equals(email) && usuario.getPassword().equals(pass)) {

					myUser = new DUser(usuario.getUsername(), usuario.getEmail(), usuario.getPassword(),
							usuario.getRegisterDate(), usuario.getLastConnections());
					break;
				}
			}

			tx.commit();

		} catch (Exception ex) {
			logger.getLogger().error("# Error retrieving Users using an 'Extent': " + ex.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return myUser;
	}
	
	@Override
	public ArrayList<DFile> getAllFiles() {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		ArrayList<DFile> files = new ArrayList<DFile>();
		try {
			logger.getLogger().info("- Retrieving Files using an 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			tx.begin();

			Extent<DFile> extent = pm.getExtent(DFile.class, true);

			for (DFile file : extent) {
			files.add(new DFile(file.getUser(), file.getHash(), file.getName(), file.getLastModified()));
			}

			tx.commit();

		} catch (Exception ex) {
			logger.getLogger().error("# Error retrieving Files using an 'Extent': " + ex.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return files;
	}

	@Override
	public ArrayList<DUser> getAllUsers() {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		ArrayList<DUser> users = new ArrayList<DUser>();
		try {
			logger.getLogger().info("- Retrieving Users using an 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			tx.begin();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser usuario : extent) {
				users.add(usuario);
			}

			tx.commit();

		} catch (Exception ex) {
			logger.getLogger().error("# Error retrieving Users using an 'Extent': " + ex.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return users;
	}

	@Override
	public boolean addUser(DUser user) {

		boolean correct = true;
		PersistenceManager pm = null;
		Transaction tx = null;

		try {
			logger.getLogger().info("- Store objects in the DB");
			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();
			tx.begin();

			pm.makePersistent(user);
			tx.commit();
			logger.getLogger().info("Inserting user into the database: SUCCESFUL");

		} catch (Exception ex) {
			logger.getLogger().error("# Error storing objects: " + ex.getMessage());
			correct = false;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return correct;
	}

	@Override
	public void addFiles(DFile file) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			logger.getLogger().info("Adding files to the user...");

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser usuario : extent) {
				if (file.getUser().getEmail().equals(usuario.getEmail())) {
					file.setUser(usuario);
					boolean existe = false;
					for(DFile filebd : usuario.getFiles()){
						if(filebd.getName().equals(file.getName()))
							existe = true;	
					}
					if(!existe){
					usuario.addFile(file);
					pm.makePersistent(usuario);
					}
					break;
				}
			}
			tx.commit();
		} catch (Exception ex) {
			logger.getLogger().error("# Error storing objects: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<DFile> getAllFilesOfAUser(String email) {

		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		DUser e = null;
		try {
			logger.getLogger().info("- Retrieving Files of a certain User using an 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser user : extent) {
				if (user.getEmail().equals(email)) {
					e = new DUser(user.getUsername(), user.getEmail(), user.getPassword());
					e.setFiles(user.getFiles());
				}
			}

		} catch (Exception ex) {
			logger.getLogger().error("# Error retrieving Files of a certain User using an 'Extent': " + ex.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return e.getFiles();
	}

	public static void main(String[] args) {
		IDeustoBoxDAO dao = new DeustoBoxDAO(new Error_log());

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
			logger.getLogger().info("- Retrieving the number of files of a certain User using an 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser user : extent) {
				if (user.getEmail().equals(email)) {
					number = user.getFiles().size();
				}
			}

		} catch (Exception ex) {
			logger.getLogger().error("# Error retrieving the number of  Files of a certain User using an 'Extent': " + ex.getMessage());
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
		@SuppressWarnings("unused")
		Transaction tx = pm.currentTransaction();

		try {
			logger.getLogger().info("- Checking the password of the  User using an 'Extent'...");

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
			logger.getLogger().error("Error Checking the password of the  User using an 'Extent': " + ex.getMessage());

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
			logger.getLogger().info("Modifying the password...");

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
			logger.getLogger().error("# Error modifying the password: " + ex.getMessage());
		}
		return correcto;
	}

	@Override
	public Date getLastConnection(DUser user) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();
		
		SimpleDateFormat format = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		Date date = null;
		try {
			logger.getLogger().info("- Retrieving the last connection of a certain User using an 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser newUser : extent) {
				if(user.getEmail().equals(newUser.getEmail())) {
					// We get the last connection
					date = format.parse(newUser.getLastConnections().get(newUser.getLastConnections().size()-1));
					break;
				}
			}

		} catch (Exception ex) {
			logger.getLogger().error("# Error retrieving the last connection of a certain User using an 'Extent': " + ex.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return date;
	}

	
	@Override
	public void deleteAllFiles() {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			JDOQuery<DFile> query = (JDOQuery<DFile>) pm.newQuery(DFile.class);
			logger.getLogger().info("All files deleted from the DB.");
			tx.commit();
		} catch (Exception ex) {
			logger.getLogger().error("   $ Error cleaning the DB: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
}
		
	}

	@Override
	public void deleteAllUsers() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			JDOQuery<DUser> query = (JDOQuery<DUser>) pm.newQuery(DUser.class);
			logger.getLogger().info("All users deleted from the DB.");
			tx.commit();
		} catch (Exception ex) {
			logger.getLogger().error("   $ Error cleaning the DB: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
}
		
		
	}


}
