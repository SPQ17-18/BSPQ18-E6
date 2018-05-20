package es.deusto.bspq18.e6.DeustoBox.Server.remote;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assembler;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DConnectionDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DMessageDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.gui.v_installer;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DConnection;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DMessage;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.IDeustoBoxRemoteService;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;

/**
 * @author Markel
 *
 */
public class DeustoBoxRemoteService extends UnicastRemoteObject implements IDeustoBoxRemoteService {

	private static final long serialVersionUID = 1L;
	private IDeustoBoxDAO db;
	private Assembler assemble;
	private String path;
	private v_installer installer;
	private Socket so;
	private ServerSocket sc;
	private DataInputStream in;
	private String FiletoWrite;
	private Error_log logger;
	private Locale currentLocale;
	private ResourceBundle resourcebundle;

	/**
	 * Class Constructor.
	 * @param resourcebundle language.
	 * @throws RemoteException.
	 */
	public DeustoBoxRemoteService(ResourceBundle resourcebundle) throws RemoteException {
		this.resourcebundle = resourcebundle;
		this.logger = new Error_log();
		this.db = new DeustoBoxDAO(logger, resourcebundle);
		this.assemble = new Assembler();
		this.installer = new v_installer(db, logger, resourcebundle);
		this.installer.frame.setVisible(true);
		this.FiletoWrite = " ";
		serverReceive.start();
	}

	/**
	 * Register the user.
	 * 
	 * @param username
	 *            username of the new user.
	 * @param email
	 *            email of the new user.
	 * @param password
	 *            password of the new user.
	 * @return a new UserDTO.
	 * @throws RemoteException.
	 */
	public DUserDTO signUp(String username, String email, String password) throws RemoteException {
		System.out.println("HOLAAA");
		boolean correcto = false;
		DUser user = new DUser(username, email, password);
		correcto = db.addUser(user);
		System.out.println(correcto);

		if (correcto) {
			try {
				getInstaller().getInstaller().manageFolders();
			} catch (Exception ex) {

			}
			return assemble.createUserDTO(user, path);

		} else {
			return null;
		}
	}

	/**
	 * Login the user.
	 * 
	 * @param email
	 *            email of the user.
	 * @param password
	 *            password of the user.
	 * @param os
	 *            OS used by the client
	 * @return a new UserDTO.
	 * @throws RemoteException.
	 */
	public DUserDTO login(String email, String password, String os) throws RemoteException {

		DUser user = db.getUser(email, password);
		DUserDTO us = assemble.createUserDTO(user, path);
		if (!us.equals(null))
			addConnection(email, os);

		return us;
	}

	/**
	 * Get the files of a certain user.
	 * 
	 * @param email
	 *            email of the user.
	 * @return a list with all the files.
	 * @throws RemoteException.
	 */
	public ArrayList<DFileDTO> getFiles(String email) throws RemoteException {
		String path = getInstaller().getTxtPath().getText();

		ArrayList<DFileDTO> filesDTO = new ArrayList<DFileDTO>();
		filesDTO = installer.getInstaller().sendAllFiles(email, path);

		return filesDTO;
	}

	/**
	 * Send data to the client.
	 * 
	 * @param filename
	 *            name of the file.
	 * @param data
	 *            bytes of the file.
	 * @param len
	 *            size of the file.
	 * @return Succesful or not.
	 * @throws RemoteException.
	 */
	public boolean sendData(String filename, byte[] data, int len) throws RemoteException {
		try {
			FileOutputStream out = new FileOutputStream(filename, true);
			@SuppressWarnings("unused")
			File f = new File(filename);
			out.write(data, 0, len);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Checks if a password for a user is correct.
	 * 
	 * @param email
	 *            email of the user.
	 * @param password
	 *            password of the user.
	 * @return Succesful or not.
	 * @throws RemoteException.
	 */
	public boolean checkPassword(String email, String password) throws RemoteException {
		boolean correct = false;
		correct = db.checkPassword(email, password);
		return correct;
	}

	/**
	 * Change the password for an user.
	 * 
	 * @param email
	 *            email of the user.
	 * @param password
	 *            password of the user.
	 * @return Succesful or not.
	 * @throws RemoteException.
	 */
	public boolean updatePassword(String email, String password) throws RemoteException {
		boolean correct = false;
		correct = db.updatePassword(email, password);
		return correct;
	}

	/**
	 * Specify the user path.
	 * 
	 * @param email
	 *            email of the user.
	 */
	public void setPath(String email) {
		this.path = getInstaller().getInstaller().getPath() + "\\" + email + "\\";
	}

	public v_installer getInstaller() {
		return installer;
	}

	public void setFiletoWrite(String filetoWrite) {
		FiletoWrite = filetoWrite;
	}

	/**
	 * Returns the number of user's files.
	 * 
	 * @param email
	 *            email of the user.
	 * @return number of files
	 * @throws RemoteException.
	 */
	public int getNumberOfUserFiles(String email) throws RemoteException {
		int number = 0;
		number = db.getNumberOfUserFiles(email);
		return number;
	}

	/**
	 * Prepare the server for receiving files.
	 * 
	 * @param file
	 *            file that will be received
	 * @param email
	 *            email of the user.
	 * @throws RemoteException.
	 */
	public void ReceiveFiles(String file, String email) throws RemoteException {
		setPath(email);
		setFiletoWrite(file);

	}

	/**
	 * Get the DB.
	 */
	public IDeustoBoxDAO getDb() {
		return db;
	}

	/**
	 * Set the DB.
	* @param db new Data Base.
	 */
	public void setDb(IDeustoBoxDAO db) {
		this.db = db;
	}

	Thread serverReceive = new Thread() {
		public void run() {
			while (true) {
				try {
					sc = new ServerSocket(5000);
					so = sc.accept();
					in = new DataInputStream(new BufferedInputStream(so.getInputStream()));
					byte[] bytes = new byte[1024];
					in.read(bytes);
					String write = path + FiletoWrite;
					FileOutputStream fos = new FileOutputStream(write);
					fos.write(bytes);
					fos.close();
					installer.getInstaller().manageFolders();
					in.close();
					so.close();
					sc.close();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	};

	/**
	 * Add connections to the DB.
	* @param email email of the user.
	* @param systema System used by the client.
	* @return Succesful or not.
	* @throws RemoteException.
	 */
	public boolean addConnection(String email, String systema) throws RemoteException {
		boolean correct = false;
		int number = db.getLastConnectionID();
		DConnection con = new DConnection(number + 1, email, systema);
		correct = db.addConnection(con);

		return correct;
	}

	/**
	 * Gets the connections of a certain user.
	* @param email email of the user.
	* @return List of the user connections.
	* @throws RemoteException.
	 */
	public ArrayList<DConnectionDTO> getConnections(String email) throws RemoteException {
		ArrayList<DConnection> connections = new ArrayList<DConnection>();
		connections = db.getConnections(email);
		ArrayList<DConnectionDTO> connectionsDTO;
		connectionsDTO = assemble.createConnectionsDTO(connections);
		return connectionsDTO;
	}

	/**
	 * Gets the messages of a certain user.
	* @param email email of the user.
	* @return List of the user messages.
	* @throws RemoteException.
	 */
	public ArrayList<DMessageDTO> getMessages(String email) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<DMessageDTO> messagesdto = new ArrayList<DMessageDTO>();
		ArrayList<DMessage> messages = new ArrayList<DMessage>();
		messages = db.getAllMessagesOfSendToAUser(email);
		messagesdto = assemble.createMessagesDTO(messages);
		return messagesdto;
	}

	/**
	 * Add a message to the DB.
	* @param messagedto
	* @return Succesful or not.
	* @throws RemoteException.
	 */
	public boolean addMessage(DMessageDTO messagedto) throws RemoteException {
		DMessage message = null;
		message = assemble.createMessage(messagedto);
		boolean correct = db.addMessage(message);
		return correct;
	}

	/**
	 * Gets the next message id.
	* @return next message id.
	* @throws RemoteException.
	 */
	public int getNewMessageId() throws RemoteException {
		int messages = 0;
		messages = db.getLastMessageID() + 1;
		return messages;
	}

	/**
	 * Gets the number of messages of a certain user.
	* @param email email of the user.
	* @return number of emails.
	* @throws RemoteException.
	 */
	public int getNumberOfUserMessages(String email) throws RemoteException {
		int number = 0;
		number = db.getNumberOfUserMessages(email);
		return number;
	}

	/**
	 * Delete a message.
	* @param id message to delete.
	* @return Succesful or not.
	* @throws RemoteException.
	 */
	public boolean DeleteMessage(int id) throws RemoteException {
		// TODO Auto-generated method stub
		boolean correct = db.deleteMessage(id);
		return correct;
	}

}
