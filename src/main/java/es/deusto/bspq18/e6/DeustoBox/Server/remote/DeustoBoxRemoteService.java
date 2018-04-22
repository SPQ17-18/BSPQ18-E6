package es.deusto.bspq18.e6.DeustoBox.Server.remote;
import java.io.File;
import java.io.FileOutputStream;
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
				return assemble.userDTO(user);
			} else {
				return null;
			}
		}

		public DUserDTO login(String email, String password) throws RemoteException {
			
			DUser user = db.getUser(email, password);
			System.out.println("aqui hay " + user.toString());
			DUserDTO us = assemble.userDTO(user);
			
			return us;
		}

		public DFileDTO getFiles(String email) throws RemoteException {
		String path = getInstaller().getTxtPath().getText();
		path = path + "\\" + "Deusto-Box" + "\\" + email + "\\";
		
		ArrayList<DFile> files = db.getAllFilesOfAUser(email);
		ArrayList<DFileDTO> filesDTO = null;
		filesDTO = assemble.createFilesDTO(files, path);
		
		
			return null;
		}
		
		
		
		public boolean sendData(String filename, byte[] data, int len) throws RemoteException{
	        try{
	        	File f=new File(filename);
	        	System.out.println("Aqui llega");
	        	FileOutputStream out=new FileOutputStream("C:\\prueba.txt",true);
	        	out.write(data,0,len);
	        	out.flush();
	        	out.close();
	        	System.out.println("Done writing data...");
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
			return true;
		}
		
			
			
			
		

		public void setPath(String path) {
			this.path = path;
		}

		public v_installer getInstaller() {
			return installer;
		}
		
		

		
		
		
		
		
		
		
}
