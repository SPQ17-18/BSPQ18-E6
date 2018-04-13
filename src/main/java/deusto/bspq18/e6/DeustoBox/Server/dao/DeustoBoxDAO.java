package deusto.bspq18.e6.DeustoBox.Server.dao;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import deusto.bspq18.e6.DeustoBox.Server.data.User;

public class DeustoBoxDAO implements IDeustoBoxDAO{
	
	private PersistenceManagerFactory pmf;

	public DeustoBoxDAO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}

	public void registerUser(User user) {
		System.out.println("Registrando nuevo usuario: " + user);
		storeDB(user);
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

	public void storeDB(Object objet) {
		// Persistence Manager
		PersistenceManager pm = pmf.getPersistenceManager();
		// Transaccion para agrupar las operaciones con la bd
		Transaction tx = pm.currentTransaction();

		try {
			// Empezamos la transaccion
			tx.begin();
			System.out.println("Introduciendo el contenido en la base de datos...");
			pm.makePersistent(objet);
			// Fin de la transccion
			tx.commit();
		} catch (Exception ex) {
			System.out.println("# Error al almacenar los objetos: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

	}

}
