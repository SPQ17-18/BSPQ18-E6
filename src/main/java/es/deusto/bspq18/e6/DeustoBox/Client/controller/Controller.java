package es.deusto.bspq18.e6.DeustoBox.Client.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import es.deusto.bspq18.e6.DeustoBox.Client.gui.v_login;
import es.deusto.bspq18.e6.DeustoBox.Client.remote.RMIServiceLocator;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;

public class Controller {

	private static RMIServiceLocator rsl;
	private DUserDTO userdto;
	private static String path;
	private ArrayList<DFileDTO> filesDTO;

	public Controller(String[] args) throws RemoteException {
		rsl = new RMIServiceLocator();
		rsl.setService(args);
		new v_login(this);
		filesDTO = null;
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

	public void getFiles() {

		File directorio = new File(path);
		if (!directorio.exists())
			directorio.mkdir();

		for (DFileDTO file : filesDTO) {
			System.out.println("Dentro del for");
			String pathFichero = path + file.getFile().getName();
			File f1 = new File(file.getFile().getPath());
			System.out.println(f1);
			try {
				f1.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("1");
			try {
				System.out.println("2");
				FileInputStream in = new FileInputStream(f1);
				byte[] mydata = new byte[1024 * 1024];
				int mylen = in.read(mydata);
				System.out.println(mylen);
				while (mylen > 0) {
					System.out.println("Pedimos que nos envien los datos");
					rsl.getService().sendData(pathFichero, mydata, mylen);
					mylen = in.read(mydata);
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		System.out.println("Fuera del for");
	}

	public void getListOfFiles(String email) {
		try {
			filesDTO = rsl.getService().getFiles(email);
		} catch (Exception ex) {

		}
	}

	public void getListOfUnknownFiles() {
		try {
			// Pido todos los archivos
			String[] arr_res = null;
			arr_res = getMyFiles(path);

			// Miro que archivos tengo

			if (arr_res[0].equals("VACIO")) {
				getListOfFiles(userdto.getEmail());
				getFiles();

			} else {
				System.out.println("Asking for list of Files");
				getListOfFiles(userdto.getEmail());
				for (DFileDTO file : filesDTO) {
					System.out.println(file.toString());

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
				if(filesDTO.size() > 0)
				getFiles();
				
				//Now we have to sync our files with the Server
				wait(1000);
				arr_res = null;
				arr_res = getMyFiles(path);
				
				//Actualizamos filesDTO
				getListOfFiles(userdto.getEmail());
				ArrayList<String> filesToUpload = new ArrayList<String>();
				
				//Comparamos nuestros files con los que no hay en el server, y los que no estén los guardamos en el array
				boolean existe = true;
				for (int i = 0; i < arr_res.length; i++) {
					for (int j = 0; j < filesDTO.size(); j++) {
						existe = false;
						if (arr_res[i].equals(filesDTO.get(j).getName().substring(1))) {
							existe = true;

						}
						

					}
					if(!existe){
						filesToUpload.add(arr_res[i]);
					}

				}
				
		
			}

		} catch (Exception ex) {

		}
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
			arr_res[0] = "VACIO";
			System.out.println("Aqui estmaos");

		}

		return arr_res;
	}

}
