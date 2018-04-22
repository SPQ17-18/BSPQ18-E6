package es.deusto.bspq18.e6.DeustoBox.Client.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;

import es.deusto.bspq18.e6.DeustoBox.Client.gui.v_login;
import es.deusto.bspq18.e6.DeustoBox.Client.remote.RMIServiceLocator;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;

public class Controller {

	private RMIServiceLocator rsl;
	private DUserDTO userdto;

	public Controller(String[] args) throws RemoteException {
		rsl = new RMIServiceLocator();
		rsl.setService(args);
		new v_login(this);
	}

	public boolean signUp(String username, String email, String password) {
		DUserDTO res = null;
		try {
			res = rsl.getService().signUp(username, email, password);
		} catch (Exception ex) {
			System.err.println("- Exception " + ex.getMessage());
			ex.printStackTrace();
		}
		if (res != null) {
			this.userdto = res;
			return true;
		} else {
			return false;
		}
	}

	public boolean login(String email, String password) {
		DUserDTO res = null;
		try {
			res = rsl.getService().login(email, password);
		} catch (Exception ex) {
			return false;
		}

		if (res.equals(null)) {
			System.out.println("The user doesn't exist");
			return false;
		} else {
			this.userdto = res;
			return true;
		}

	}
	
	public void getFiles(String email){
		String path = "C:\\Users\\Markel\\Documents\\Deusto-Box\\markel_al@hotmail.com\\prueba.txt";
		
		File f1=new File(path);
		try {
			f1.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 try{
			 System.out.println("Hola");	 
			 FileInputStream in=new FileInputStream(f1);
			 byte [] mydata=new byte[1024*1024];						
			 int mylen= in.read(mydata);
			 while(mylen>0){
				 rsl.getService().sendData(f1.getName(), mydata, mylen);	 
				 mylen=in.read(mydata);				 
			 }
		 }catch(Exception e){
			 e.printStackTrace();
			 
		 }
		 System.out.println();
		 
		
	}	
		
		
	
	
	
	
	
	public int getNumberOfFiles(String email){
		
		
	return 69;
	}

	public DUserDTO getUserdto() {
		return userdto;
	}

	public void setUserdto(DUserDTO userdto) {
		this.userdto = userdto;
	}

	public static void main(String[] args) throws RemoteException {
		new Controller(args);
	}

}
