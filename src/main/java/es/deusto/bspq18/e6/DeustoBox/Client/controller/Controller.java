package es.deusto.bspq18.e6.DeustoBox.Client.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import es.deusto.bspq18.e6.DeustoBox.Client.gui.v_login;
import es.deusto.bspq18.e6.DeustoBox.Client.remote.RMIServiceLocator;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;

public class Controller {

	private static RMIServiceLocator rsl;
	private DUserDTO userdto;
	private String path;
	private ArrayList<DFileDTO> filesDTO;
	private Error_log logger;

	public Controller(String[] args) throws RemoteException {
		rsl = new RMIServiceLocator();
		rsl.setService(args);
		new v_login(this);
		filesDTO = null;
		logger = new Error_log();
	}

	public boolean signUp(String username, String email, String password) {
		DUserDTO res = null;
		try {
			res = rsl.getService().signUp(username, email, password);
		} catch (Exception ex) {
			logger.getLogger().error("- Exception", ex);
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
			logger.getLogger().error("The user doesn't exist");
			return false;
		} else {
			this.userdto = res;
			return true;
		}
	}

	public void getFiles() {
		
		File directorio = new File(path);
		if (!directorio.exists())
			directorio.mkdir();

		for (DFileDTO file : filesDTO) {
			logger.getLogger().error(file.getFile().toString());
			String pathFichero = path + file.getName();
			File f1 = new File(file.getFile().getPath());

			try {
				f1.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			try {
				FileInputStream in = new FileInputStream(f1);
				byte[] mydata = new byte[1024 * 1024];
				int mylen = in.read(mydata);
				while (mylen > 0) {
					rsl.getService().sendData(pathFichero, mydata, mylen);
					mylen = in.read(mydata);
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

	public void getListOfFiles(String email) {
		try {
			filesDTO = rsl.getService().getFiles(email);
		} catch (Exception ex) {

		}
	}

	public void getListOfUnknownFiles() {
		int downloads = 0;
		int uploads = 0;
		try {
			
			// Pido todos los archivos
			String[] arr_res = null;
			arr_res = getMyFiles(path);

			// Miro que archivos tengo

			if (arr_res[0].equals("EMPTY")) {
				getListOfFiles(userdto.getEmail());
				downloads = filesDTO.size();
				getFiles();

			} else {
				getListOfFiles(userdto.getEmail());
				for (DFileDTO file : filesDTO) {
					logger.getLogger().error(file.toString());
				}

				ArrayList<DFileDTO> filesToRemove = new ArrayList<DFileDTO>();
				for (int i = 0; i < arr_res.length; i++) {
					for (int j = 0; j < filesDTO.size(); j++) {
						if (arr_res[i].equals(filesDTO.get(j).getName().substring(1))) {
							filesToRemove.add(filesDTO.get(j));

						}
					}
				}

				for (int i = 0; i < filesToRemove.size(); i++) {
					filesDTO.remove(filesToRemove.get(i));

				}
				if(filesDTO.size() > 0){
				downloads = filesDTO.size();
				getFiles();
				}
				//Now we have to sync our files with the Server
				arr_res = null;
				arr_res = getMyFiles(path);
				
				//Actualizamos filesDTO
				getListOfFiles(userdto.getEmail());
				ArrayList<String> filesToUpload = new ArrayList<String>();
				
				//Comparamos nuestros files con los que no hay en el server, y los que no estén los guardamos en el array
				boolean existe = true;
				for (int i = 0; i < arr_res.length; i++) {
					existe = false;
					for (int j = 0; j < filesDTO.size(); j++) {
						
						if (arr_res[i].equals(filesDTO.get(j).getName().substring(1))) {
							logger.getLogger().error(arr_res[i] + " " + filesDTO.get(j).getName().substring(1) );
							existe = true;

						}
						
					}
					if(!existe){
						filesToUpload.add(arr_res[i]);
					}
					
				}
				
				uploads = filesToUpload.size();
				for(int i = 0; i< filesToUpload.size(); i++){
					String pathFichero =  path + filesToUpload.get(i);
					sendFiles(pathFichero, filesToUpload.get(i) );
					Thread.sleep(2000);
				}
				
	
			
			}

		} catch (Exception ex) {
			
		}
		JOptionPane.showMessageDialog(null, downloads + " files have been downloaded and " + uploads + " files have been uploaded");
		
		
	}

	public int getNumberOfFiles() {
		int files = 0;
		String email = userdto.getEmail();

		try {
			files = rsl.getService().getNumberOfUserFiles(email);
		} catch (Exception ex) {
		}
		return files;
	}

	public boolean passwordCorrect(String password) {
		boolean correct = false;
		try {
			correct = rsl.getService().checkPassword(userdto.getEmail(), password);
		} catch (Exception ex) {

		}
		return correct;
	}

	public boolean updatePassword(String password) {
		boolean correct = false;

		try {
			correct = rsl.getService().updatePassword(userdto.getEmail(), password);
		} catch (Exception ex) {

		}

		return correct;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path + getUserdto().getEmail() + "\\";
	}
	
	
	
	
	public boolean sendFiles(String pathFile, String fileName){
		try {
			rsl.getService().ReceiveFiles(fileName, this.userdto.getEmail());
			Socket so = new Socket("localhost", 5000);
			DataOutputStream out = new DataOutputStream(so.getOutputStream());
			File file = new File(pathFile);
			long length = file.length();
		    if (length > Integer.MAX_VALUE) {
				logger.getLogger().error("File is too large.");
		    }
		    byte[] bytes = new byte[(int) length];
		    out.write(bytes);
		    out.close();
		    so.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	

	public static String[] getMyFiles(String dir_path) {
		System.out.println(dir_path);
		String[] arr_res = null;

		File f = new File(dir_path);

		if (f.isDirectory() && (f.listFiles().length > 0)) {

			List<String> res = new ArrayList<>();
			File[] arr_content = f.listFiles();

			int size = arr_content.length;

			for (int i = 0; i < size; i++) {

				if (arr_content[i].isFile())
					res.add(arr_content[i].getName());
			}

			arr_res = res.toArray(new String[0]);

		} else {
			arr_res = new String[1];
			arr_res[0] = "EMPTY";

		}

		return arr_res;
	}

}
