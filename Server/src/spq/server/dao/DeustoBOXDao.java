package spq.server.dao;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import spq.server.data.User;

public class DeustoBOXDao implements IDeustoBoxDAO {
	PersistenceManagerFactory pmf;

	public DeustoBOXDao() { // Constructor vacío por si hay que añadir algo
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

	}

	@Override
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

	

	@Override
	public User getUser(String email) {
		// Persistence Manager
				PersistenceManager pm = null;
				// Transaction to group DB operations
				Transaction tx = null;
				pm = pmf.getPersistenceManager();
				tx = pm.currentTransaction();
				User ret = null;
				try {
					System.out.println("Obtaining the user with the email: " + email);

					// Start the transaction
					tx.begin();
					Extent<User> extent = pm.getExtent(User.class, true);

					for (User es : extent) {
						if (es.getEmail().equals(email)) {
							ret = new User(es.getEmail(), es.getName(), es.getPassword());

						}
					}


				} catch (Exception ex) {
					System.out.println("# Error getting the user " + ex.getMessage());
				} finally {
					if (tx.isActive()) {
						tx.rollback();
					}

					pm.close();
				}
				return ret;
			}
	}


