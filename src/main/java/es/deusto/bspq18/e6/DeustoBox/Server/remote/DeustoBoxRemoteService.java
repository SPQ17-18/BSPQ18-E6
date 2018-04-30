package es.deusto.bspq18.e6.DeustoBox.Server.remote;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
	private Socket so;
	private ServerSocket sc;
	private DataInputStream in;

	public DeustoBoxRemoteService(Socket so) throws RemoteException {
		db = new DeustoBoxDAO();
		assemble = new Assembler();
		installer = new v_installer();
		installer.frame.setVisible(true);
		
	}
	
	Thread t = new Thread(){
		public void run(){
	try {
		System.out.println("PAPS1");
			sc = new ServerSocket(5000);

		so = sc.accept();
		in = new DataInputStream(new BufferedInputStream(so.getInputStream()));
		
		System.out.println("Una alu");
		byte[] bytes = new byte[1024];

		    in.read(bytes);
		    
		    FileOutputStream fos = new FileOutputStream("C:\\test3.html");
		    System.out.println("Hola");
		    fos.write(bytes);
		    fos.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		}
};
	
	

	public DUserDTO signUp(String username, String email, String password) throws RemoteException {
		boolean correcto = false;

		DUser user = new DUser(username, email, password);
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

	
	
	public boolean sendData(String filename, byte[] data, int len) throws RemoteException{
        try{
        	File f = new File(filename);
        	FileOutputStream out=new FileOutputStream(filename,true);
        	out.write(data,0,len);
        	out.flush();
        	out.close();
        }catch(Exception e){
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
		return number;
	}
	
	public void ReceiveFiles(String file) throws RemoteException {
		t.start();

	}

	

}
