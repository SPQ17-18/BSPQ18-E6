package es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.User;

public class DeustoBoxDAO implements IDeustoBoxDAO{
	
	PersistenceManagerFactory pmf;

	public DeustoBoxDAO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}



	public User getUser(String email, String pass) {
		// Persistence Manager
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		// Transaccion para agrupar las operaciones con la bd
		Transaction tx = pm.currentTransaction();
		// El usuario que buscamos
		User myUser = null;
		try {

			System.out.println("- Recuperando los Usuarios usando un 'Extent'...");

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			tx.begin();

			Extent<User> extent = pm.getExtent(User.class, true);

			for (User usuario : extent) {
				if (usuario.getEmail().equals(email) && usuario.getPassword().equals(pass)) {
					System.out.println("  -> " + usuario);
					myUser = usuario;
					break;
				}
			}

			tx.commit();

		} catch (Exception ex) {
			System.out.println("# Error obteniendo los Usuarios usando un Extent: " + ex.getMessage());
		} finally {

			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return myUser;
	}

	
	public boolean AddUser(User e) { 
		boolean correcto = true;
		// Persistence Manager
		PersistenceManager pm = null;
		// Transaction to group DB operations
		Transaction tx = null;

		try {
			System.out.println("- Store objects in the DB");
			// Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			// Obtain the current transaction
			tx = pm.currentTransaction();
			// Start the transaction
			tx.begin();

			// Users
			pm.makePersistent(e);
			tx.commit();
			System.out.println("Inserting user into the database: SUCCESFUL");

		} catch (Exception ex) {
			System.out.println("# Error storing objects: " + ex.getMessage());
			correcto = false;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}

			pm.close();

		}

		return correcto;
	}
	
	/*public static void main(String[] args) {
		IDeustoBoxDAO dao= new DeustoBoxDAO();
		
		User user1 = new User("aitorugarte@opendeusto.es", "aitorugarte", "123");
		User user2 = new User("markelalva@opendeusto.es", "markelalva", "123");
		
		dao.AddUser(user1);
		dao.AddUser(user2);
	
		
	}*/

}
