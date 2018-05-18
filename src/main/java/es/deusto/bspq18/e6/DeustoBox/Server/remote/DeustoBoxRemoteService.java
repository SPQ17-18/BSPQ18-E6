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

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

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

	public DeustoBoxRemoteService() throws RemoteException {
		this.logger = new Error_log();
		this.db = new DeustoBoxDAO(logger);
		this.assemble = new Assembler();
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}
		this.installer = new v_installer(db, logger);
		this.installer.frame.setVisible(true);
		this.FiletoWrite = " ";
		serverReceive.start();
	}

	public DUserDTO signUp(String username, String email, String password) throws RemoteException {
		boolean correcto = false;
		DUser user = new DUser(username, email, password);
		correcto = db.addUser(user);
		

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

	public DUserDTO login(String email, String password, String os) throws RemoteException {

		DUser user = db.getUser(email, password);
		DUserDTO us = assemble.createUserDTO(user, path);
		if(!us.equals(null))
		addConnection(email, os);
		
		return us;
	}

	public ArrayList<DFileDTO> getFiles(String email) throws RemoteException {
		String path = getInstaller().getTxtPath().getText();

		ArrayList<DFileDTO> filesDTO = new ArrayList<DFileDTO>();
		filesDTO = installer.getInstaller().sendAllFiles(email, path);

		return filesDTO;
	}

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

	public boolean checkPassword(String email, String password) throws RemoteException {
		boolean correct = false;
		correct = db.checkPassword(email, password);
		return correct;
	}

	@Override
	public boolean updatePassword(String email, String password) throws RemoteException {
		boolean correct = false;
		correct = db.updatePassword(email, password);
		return correct;
	}

	public void setPath(String email) {
		this.path = getInstaller().getInstaller().getPath() + "\\" + email + "\\";
	}

	public v_installer getInstaller() {
		return installer;
	}

	public void setFiletoWrite(String filetoWrite) {
		FiletoWrite = filetoWrite;
	}

	@Override
	public int getNumberOfUserFiles(String email) throws RemoteException {
		int number = 0;
		number = db.getNumberOfUserFiles(email);
		return number;
	}

	public void ReceiveFiles(String file, String email) throws RemoteException {
		setPath(email);
		setFiletoWrite(file);

	}

	public IDeustoBoxDAO getDb() {
		return db;
	}

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

	@Override
	public boolean addConnection(String email, String systema)throws RemoteException {
		boolean correct = false;
		int number = db.getLastConnectionID();
		DConnection con = new DConnection(number +1, email, systema);
		correct =  db.addConnection(con);
		
		return correct;
	}

	@Override
	public ArrayList<DConnectionDTO> getConnections(String email)throws RemoteException {
		ArrayList<DConnection> connections = new ArrayList<DConnection>();
		connections = db.getConnections(email);
		ArrayList<DConnectionDTO> connectionsDTO;
		connectionsDTO = assemble.createConnectionsDTO(connections);
		return connectionsDTO;
	}

	@Override
	public ArrayList<DMessageDTO> getMessages(String email) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<DMessageDTO> messagesdto = new ArrayList<DMessageDTO>();
		ArrayList<DMessage> messages = new ArrayList<DMessage>();
		messages =db.getAllMessagesOfSendToAUser(email);
		messagesdto = assemble.createMessagesDTO(messages);
		return messagesdto;
	}

	@Override
	public boolean addMessage(DMessageDTO messagedto) throws RemoteException {
		DMessage message = null;
		message = assemble.createMessage(messagedto);
		boolean correct = db.addMessage(message);
		return correct;
	}

	@Override
	public int getNewMessageId() throws RemoteException {
		int messages = 0;
		messages = db.getLastMessageID() + 1;
		return messages;
	}

	@Override
	public int getNumberOfUserMessages(String email) throws RemoteException {
		int number = 0;
		number = db.getNumberOfUserMessages(email);
		return number;
	}

	@Override
	public boolean DeleteMessage(int id) throws RemoteException {
		// TODO Auto-generated method stub
		boolean correct = db.deleteMessage(id);
		return correct;
	}


}
