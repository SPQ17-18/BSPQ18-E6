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
	private String FiletoWrite;
	private int contadorSocket;

	public DeustoBoxRemoteService(Socket so) throws RemoteException {
		this.db = new DeustoBoxDAO();
		this.assemble = new Assembler();
		this.installer = new v_installer();
		this.installer.frame.setVisible(true);
		this.FiletoWrite = " ";
		this.contadorSocket = 0;
		t.start();
		
	}
	
	Thread t = new Thread(){
		public void run(){
		while(true){	
	try {
		System.out.println("EJECUTANDO EL HILO" );
		sc = new ServerSocket(5000);
		so = sc.accept();
		in = new DataInputStream(new BufferedInputStream(so.getInputStream()));
		byte[] bytes = new byte[1024];
		    in.read(bytes);
		    String write = path + FiletoWrite;
		    FileOutputStream fos = new FileOutputStream(write);
		    fos.write(bytes);
		    fos.close();
		    System.out.println("Recibido un archivo");
		    contadorSocket++;
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
	
	

	public DUserDTO signUp(String username, String email, String password) throws RemoteException {
		boolean correcto = false;

		DUser user = new DUser(username, email, password);
		correcto = db.addUser(user);

		if (correcto) {
			getInstaller().getInstaller().manageFolders();
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


	

}
