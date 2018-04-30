package es.deusto.bspq18.e6.DeustoBox.Server.remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assembler;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.gui.v_installer;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.IDeustoBoxRemoteService;

public class DeustoBoxRemoteService extends UnicastRemoteObject implements IDeustoBoxRemoteService {

	private static final long serialVersionUID = 1L;
	private IDeustoBoxDAO db;
	private Assembler assemble;
	private String path;
	private v_installer installer;

	public DeustoBoxRemoteService() throws RemoteException {
		db = new DeustoBoxDAO();
		assemble = new Assembler();
		installer = new v_installer();
		installer.frame.setVisible(true);
	}

	public DUserDTO signUp(String username, String email, String password) throws RemoteException {
		boolean correcto = false;
		System.out.println("User received");
		DUser user = new DUser(username, email, password);
		System.out.println(user);
		correcto = db.addUser(user);

		if (correcto) {
			return assemble.createUserDTO(user, path);
		} else {
			return null;
		}
	}

	public DUserDTO login(String email, String password) throws RemoteException {

		DUser user = db.getUser(email, password);
		DUserDTO us = assemble.createUserDTO(user, path);
		

		return us;
	}

	public ArrayList<DFileDTO> getFiles(String email) throws RemoteException {
		String path = getInstaller().getTxtPath().getText();
			
		ArrayList<DFileDTO> filesDTO = new ArrayList<DFileDTO>();
		filesDTO = installer.getInstaller().sendAllFiles(email, path);
		return filesDTO;
	}

	
	
	public boolean sendData(String filename, byte[] data, int len) throws RemoteException {
		System.out.println("Sending data");
		try {
			File f = new File(filename);
			FileOutputStream out = new FileOutputStream(filename, true);
			out.write(data, 0, len);
			out.flush();
			out.close();
			System.out.println("Done writing data...");
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

	public void setPath(String path) {
		this.path = path;
	}

	public v_installer getInstaller() {
		return installer;
	}

	@Override
	public int getNumberOfUserFiles(String email) throws RemoteException {
		int number = 0;
		number = db.getNumberOfUserFiles(email);
		System.out.println("The user has " + number + " files.");
		return number;
	}
	
	public void receiveFiles(ArrayList<DFileDTO> filesDTO) throws RemoteException{
		for (DFileDTO file : filesDTO) {
			String pathFichero = path + file.getFile().getName();
			File f1 = new File(file.getFile().getPath());
			System.out.println(f1);
			try {
				f1.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
				FileInputStream in = new FileInputStream(f1);
				byte[] mydata = new byte[1024 * 1024];
				int mylen = in.read(mydata);
				System.out.println(mylen);
				while (mylen > 0) {
					System.out.println("Pedimos que nos envien los datos");
					rsl.getService().sendData(pathFichero, mydata, mylen);
					mylen = in.read(mydata);
				
		
		
	}

}
	}
}
