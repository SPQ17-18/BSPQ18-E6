package es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.datanucleus.api.jdo.JDOQuery;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DConnection;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DMessage;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;

public class DeustoBoxDAO implements IDeustoBoxDAO {

	private PersistenceManagerFactory pmf;
	private Error_log logger;
	private ResourceBundle resourcebundle;

	public DeustoBoxDAO(Error_log logger) {
		
		this.pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.logger = logger;
		Locale currentLocale = new Locale("en", "US");
		resourcebundle = ResourceBundle.getBundle("lang/translations", currentLocale);
	}

	public DUser getUser(String email, String pass) {

		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		DUser myUser = null;
		try {
			logger.getLogger().info(resourcebundle.getString("msg_users") );

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			tx.begin();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser usuario : extent) {
				if (usuario.getEmail().equals(email) && usuario.getPassword().equals(pass)) {

					myUser = new DUser(usuario.getUsername(), usuario.getEmail(), usuario.getPassword(),
							usuario.getRegisterDate());
					break;
				}
			}

			tx.commit();

		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_users") , ex);
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
			logger.getLogger().info(resourcebundle.getString("msg_files") );

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			tx.begin();

			Extent<DFile> extent = pm.getExtent(DFile.class, true);

			for (DFile file : extent) {
			files.add(new DFile(file.getUser(), file.getHash(), file.getName(), file.getLastModified()));
			}

			tx.commit();

		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_files") , ex);
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
		DUser user = null;
		try {
			logger.getLogger().info(resourcebundle.getString("msg_all_users") );

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			tx.begin();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser usuario : extent) {
				user = new DUser(usuario.getUsername(), usuario.getEmail(), usuario.getPassword(),
						usuario.getRegisterDate());
				users.add(user);
			}

			tx.commit();

		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_all_users") , ex);
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
			logger.getLogger().info(resourcebundle.getString("msg_store_user") );
			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();
			tx.begin();

			pm.makePersistent(user);
			tx.commit();
			logger.getLogger().info(resourcebundle.getString("msg_insert_users") );

		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_user") , ex);
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
			logger.getLogger().info(resourcebundle.getString("msg_add_file") );

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
			logger.getLogger().error(resourcebundle.getString("msg_error_files") , ex);
		}
	}

	@Override
	public ArrayList<DFile> getAllFilesOfAUser(String email) {

		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();

		DUser e = null;
		try {
			logger.getLogger().info(resourcebundle.getString("msg_all_files_users") );

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
			logger.getLogger().error(resourcebundle.getString("msg_error_all_files") , ex);
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
			logger.getLogger().info(resourcebundle.getString("msg_number") );

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			Extent<DUser> extent = pm.getExtent(DUser.class, true);

			for (DUser user : extent) {
				if (user.getEmail().equals(email)) {
					number = user.getFiles().size();
				}
			}

		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_number_files") , ex);
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
			logger.getLogger().info(resourcebundle.getString("msg_check_password") );

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
			logger.getLogger().error(resourcebundle.getString("msg_error_check_pass") , ex);
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
			logger.getLogger().info(resourcebundle.getString("msg_update_pass") );

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
			logger.getLogger().error(resourcebundle.getString("msg_error_update_pass") , ex);
		}
		return correcto;
	}


	
	@Override
	public void deleteAllFiles() {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			JDOQuery<DFile> query = (JDOQuery<DFile>) pm.newQuery(DFile.class);
			query.deletePersistentAll();
			logger.getLogger().info(resourcebundle.getString("msg_delete_files") );
			tx.commit();
		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_delete_files") , ex);
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
	public void deleteAllConnections() {
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			JDOQuery<DConnection> query = (JDOQuery<DConnection>) pm.newQuery(DConnection.class);
			query.deletePersistentAll();
			logger.getLogger().info(resourcebundle.getString("msg_delete_cons") );
			tx.commit();
		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_delete_cons") , ex);
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
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			JDOQuery<DUser> query = (JDOQuery<DUser>) pm.newQuery(DUser.class);
			query.deletePersistentAll();
			logger.getLogger().info(resourcebundle.getString("msg_delete_users") );
			tx.commit();
		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_delete_users") , ex);
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
	public void deleteAllMessages() {
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			JDOQuery<DMessage> query = (JDOQuery<DMessage>) pm.newQuery(DMessage.class);
			query.deletePersistentAll();
			logger.getLogger().info(resourcebundle.getString("msg_delete_msg") );
			tx.commit();
		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_delete_msg") , ex);
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
	public boolean addConnection(DConnection connection) {
		boolean correct = true;
		PersistenceManager pm = null;
		Transaction tx = null;

		try {
			logger.getLogger().info(resourcebundle.getString("msg_add_con") );
			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();
			tx.begin();

			pm.makePersistent(connection);
			tx.commit();
			logger.getLogger().info(resourcebundle.getString("msg_insert_con") );

		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_add_con") , ex);
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
	public int getLastConnectionID() {
		int connections = 0;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();
		try {
			logger.getLogger().info(resourcebundle.getString("msg_get_con") );


			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			Extent<DConnection> extent = pm.getExtent(DConnection.class, true);
			
			for (DConnection con : extent) {
				if(con.getID() > connections) {
					connections = con.getID();
				}
			}

		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_con_id") , ex);
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return connections;
	}

	@Override
	public ArrayList<DConnection> getConnections(String email) {
		ArrayList<DConnection> connections = new ArrayList<>();
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();
		DConnection dec = null;

		try {
			logger.getLogger().info(resourcebundle.getString("msg_cons") );


			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			Extent<DConnection> extent = pm.getExtent(DConnection.class, true);

			for (DConnection con : extent) {
				if( con.getUserEmail().equals(email))
					dec = new DConnection(con.getID(), con.getUserEmail(), con.getConnectionDate(), con.getOSUsed());
				connections.add(dec);
			}

		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_con") , ex);
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		
		return connections;
	}


	@Override
	public boolean addMessage(DMessage message) {
		
		boolean correct = true;
		PersistenceManager pm = null;
		Transaction tx = null;

		try {
			logger.getLogger().info(resourcebundle.getString("msg_add_msg") );

			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();
			tx.begin();

			pm.makePersistent(message);
			tx.commit();
			logger.getLogger().info(resourcebundle.getString("msg_insert_msm") );


		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_message") , ex);
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
	public ArrayList<DMessage> getAllMessagesOfSendToAUser(String email) {
		ArrayList<DMessage> messages = new ArrayList <>();
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();
		DMessage dec = null;

		try {
			logger.getLogger().info(resourcebundle.getString("msg_all_msg_user") );


			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			Extent<DMessage> extent = pm.getExtent(DMessage.class, true);

			for (DMessage mes : extent) {
				if( mes.getEmailTo().equals(email))
					dec = new DMessage(mes.getMessageId(), mes.getEmailfrom(), mes.getEmailTo(), mes.getSubject(), mes.getText(), mes.getDate());
					messages.add(dec);
			}

		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_msg_user") , ex);			
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		
		return messages;
	}

	@Override
	public int getLastMessageID() {
		int messages = 0;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();
		try {
			logger.getLogger().info(resourcebundle.getString("msg_get_id") );


			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			Extent<DMessage> extent = pm.getExtent(DMessage.class, true);
			
			for (DMessage mes : extent) {
				if(mes.getMessageId() > messages) {
					messages = mes.getMessageId();
				}
			}

		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_id") , ex);
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return messages;
	}

	@Override
	public int getNumberOfUserMessages(String email) {
		int messages = 0;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();
		try {
			logger.getLogger().info(resourcebundle.getString("msg_num_msg") );


			pm = pmf.getPersistenceManager();
			tx = pm.currentTransaction();

			Extent<DMessage> extent = pm.getExtent(DMessage.class, true);
			
			for (DMessage mes : extent) {
				if(mes.getEmailTo().equals(email)) {
					messages++;
				}
			}

		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_user_msg") , ex);
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return messages;
	}
	
	public boolean deleteMessage(int id) {
		boolean correct = true;
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();

			Query<DMessage> query = pm.newQuery(DMessage.class, "messageId ==" + id);

			Collection<?> result = (Collection<?>) query.execute();

			DMessage mes = (DMessage) result.iterator().next();

			query.close(result);

			pm.deletePersistent(mes);

			tx.commit();
		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_error_delete_msg") , ex);
			correct = false;
		
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		
		}
		return correct;
}


}
