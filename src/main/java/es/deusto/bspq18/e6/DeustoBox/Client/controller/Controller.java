package es.deusto.bspq18.e6.DeustoBox.Client.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import es.deusto.bspq18.e6.DeustoBox.Client.gui.v_login;
import es.deusto.bspq18.e6.DeustoBox.Client.remote.RMIServiceLocator;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DConnectionDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DMessageDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DUserDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;

public class Controller {

	private static RMIServiceLocator rsl;
	private DUserDTO userdto;
	private String path;
	private ArrayList<DFileDTO> filesDTO;
	private Error_log logger;
	private ResourceBundle resourcebundle;
	private Locale currentLocale;

	
	/**
	 * Class Constructor.
	 * @param args Command line arguments.
	 * @param language language.
	 * @throws RemoteExeption
	 */
	public Controller(String[] args, int language) throws RemoteException {
		if (language == 0) {
			currentLocale = new Locale("es", "ES");
		} else if (language == 1) {
			currentLocale = new Locale("en", "US");
		}
		if (language == 2) {
			currentLocale = new Locale("fr", "FR");
		}
		resourcebundle = ResourceBundle.getBundle("lang/translations", currentLocale);
		rsl = new RMIServiceLocator();
		rsl.setService(args);
		new v_login(this);
		filesDTO = null;
		logger = new Error_log();

	}

	/**
	 * Register the user.
	 * @param username username of the user.
	 * @param email email of the user.
	 * @param password password of the user.
	 * @return Successful or not.
	 */
	public boolean signUp(String username, String email, String password) {
		DUserDTO res = null;
		try {
			res = rsl.getService().signUp(username, email, password);
		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_excepcion"), ex);
		}
		if (res != null) {
			this.userdto = res;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Login.
	 * @param email email of the user.
	 * @param password password of the user.
	 * @return Successful or not.
	 */
	public boolean login(String email, String password) {
		String os = System.getProperty("os.name");
		DUserDTO res = null;
		try {
			res = rsl.getService().login(email, password, os);
			System.out.println(res);
		} catch (Exception ex) {
			return false;
		}

		if (res.equals(null)) {
			logger.getLogger().error(resourcebundle.getString("non_existing_user"));
			return false;
		} else {
			this.userdto = res;
			logger.getLogger().error(resourcebundle.getString("user_successfully_loged"));
			return true;
		}
	}

	/**
	 * Get the files.
	 */
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
				logger.getLogger().error(resourcebundle.getString("msg_excepcion"), e);

			}
		}
	}

	/**
	 * Get the files.
	 * @param email email of the user.
	 * @return number of files.
	 */
	public int getListOfFiles(String email) {
		int number = 0;
		try {
			filesDTO = rsl.getService().getFiles(email);
			number = filesDTO.size();
		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_excepcion"), ex);
		}
		return number;
	}
	
	/**
	 * Updates the list of files.
	 */
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
					logger.getLogger().error(file);
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
				if (filesDTO.size() > 0) {
					downloads = filesDTO.size();
					getFiles();
				}
				// Now we have to sync our files with the Server
				arr_res = null;
				arr_res = getMyFiles(path);

				// Actualizamos filesDTO
				getListOfFiles(userdto.getEmail());
				ArrayList<String> filesToUpload = new ArrayList<String>();

				// Comparamos nuestros files con los que no hay en el server, y
				// los que no estén los guardamos en el array
				boolean existe = true;
				for (int i = 0; i < arr_res.length; i++) {
					existe = false;
					for (int j = 0; j < filesDTO.size(); j++) {

						if (arr_res[i].equals(filesDTO.get(j).getName().substring(1))) {
							logger.getLogger().error(arr_res[i] + " " + filesDTO.get(j).getName().substring(1));
							existe = true;

						}

					}
					if (!existe) {
						filesToUpload.add(arr_res[i]);
					}

				}

				uploads = filesToUpload.size();
				for (int i = 0; i < filesToUpload.size(); i++) {
					String pathFichero = path + filesToUpload.get(i);
					sendFiles(pathFichero, filesToUpload.get(i));
					Thread.sleep(2000);
				}

			}

		} catch (Exception ex) {

		}
		JOptionPane.showMessageDialog(null, downloads + " " + resourcebundle.getString("msg_file_download") + "\n "+ uploads 
				+ " " + resourcebundle.getString("msg_file_upload"));

	}

	/**
	 * Get the number of Files.
	 * @param email email of the user.
	 * @return number number of files.
	 */
	public int getNumberOfFiles(String email) {
		int files = 0;

		try {
			files = rsl.getService().getNumberOfUserFiles(email);
		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_excepcion"), ex);
		}
		return files;
	}

	/**
	 * Check if the password is correct.
	 * @param email
	 * @param password
	 * @return Correct or not.
	 */
	public boolean passwordCorrect(String email, String password) {
		boolean correct = false;
		try {
			correct = rsl.getService().checkPassword(email, password);
		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_excepcion"), ex);
		}
		return correct;
	}

	/**
	 * Update the password.
	 * @param email email of the user.
	 * @param password new password.
	 * @return Successful or not.
	 */
	public boolean updatePassword(String email, String password) {
		boolean correct = false;

		try {
			correct = rsl.getService().updatePassword(email, password);
		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_excepcion"), ex);
		}

		return correct;
	}

	/**
	 * Gets the connection of a certain user.
	 * @param email email of the user.
	 * @return ArayList with all the connections.
	 */
	public ArrayList<DConnectionDTO> getConnections(String email) {
		ArrayList<DConnectionDTO> connectionsDTO = new ArrayList<DConnectionDTO>();
		try {
			connectionsDTO = rsl.getService().getConnections(email);
		} catch (Exception ex) {
			logger.getLogger().error(resourcebundle.getString("msg_excepcion"), ex);
		}
		return connectionsDTO;

	}
	/**
	 * Add a message.
	 * @param emailfrom who writes the email.
	 * @param emailto who receives the email.
	 * @param subject subject of the email.
	 * @param text text of the email.
	 * @return Successful or not.
	 */
	
	public boolean addMessage(String emailfrom, String emailto, String subject, String text) {
		int id = 0;
		boolean correct = false;
		try {
			id = rsl.getService().getNewMessageId();
			DMessageDTO dto = new DMessageDTO(id, emailfrom, emailto, subject, text);
			correct = rsl.getService().addMessage(dto);
		} catch (RemoteException e) {
			logger.getLogger().error(resourcebundle.getString("msg_excepcion"), e);
		}
		return correct;
	}
	/**
	 * Download the messages send to a user.
	 * @param email email of the user.
	 * @return List with the messages.
	 */
	public ArrayList<DMessageDTO> downloadMessages(String email) {
		ArrayList<DMessageDTO> messages = null;
		try {
			messages = rsl.getService().getMessages(email);
		} catch (RemoteException e) {
			logger.getLogger().error(resourcebundle.getString("msg_excepcion"), e);
		}

		return messages;

	}
	/**
	 * Upload a file.
	 * @param pathFile local path of the file.
	 * @param fileName Name of the file.
	 * @return Successful or not.
	 */
	public boolean sendFiles(String pathFile, String fileName) {
		try {
			File file = new File(pathFile);
			long length = file.length();
			rsl.getService().ReceiveFiles(fileName, this.userdto.getEmail(), length);
			Socket so = new Socket("localhost", 5000);
			DataOutputStream out = new DataOutputStream(so.getOutputStream());

			if (length > Integer.MAX_VALUE) {
				logger.getLogger().error(resourcebundle.getString("msg_file_large"));
			}
			byte[] bytes = new byte[(int) length];
			out.write(bytes);
			out.close();
			so.close();
		} catch (IOException e) {
			logger.getLogger().error(resourcebundle.getString("msg_excepcion"), e);
		}

		return true;
	}

	/**
	 * Get the size of user inbox.
	 * @param email email of the user.
	 * @return size of the inbox.
	 */
	public int getNumberOfUserMessages(String email) {
		int number = 0;

		try {
			number = rsl.getService().getNumberOfUserMessages(email);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			logger.getLogger().error(resourcebundle.getString("msg_excepcion"), e);
		}
		return number;

	}
	/**
	 * Deletes a  message.
	 * @param id identificator of the message.
	 * @return Successful or not.
	 */
	public boolean deleteMessage(int id) {
		boolean correct = false;

		try {
			correct = rsl.getService().DeleteMessage(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			logger.getLogger().error(resourcebundle.getString("msg_excepcion"), e);
		}

		return correct;

	}

	/**
	 * Deletes a film.
	 * @param path of the local folder
	 */
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

	/**
	 * Get the current user dto.
	 */
	public DUserDTO getUserdto() {
		return userdto;
	}

	/**
	 * Update the userdto
	 * @param userdto new userdto.
	 */
	public void setUserdto(DUserDTO userdto) {
		this.userdto = userdto;
	}
	/**
	 * Get the current user path.
	 */
	public String getPath() {
		return path;
	}
	/**
	 * Update the path
	 * @param path new path.
	 */
	public void setPath(String path) {
		this.path = path + getUserdto().getEmail() + "\\";
	}
	/**
	 * Get the current resourcebundle.
	 */
	public ResourceBundle getResourcebundle() {
		return resourcebundle;
	}

	public static void main(String[] args) {
		try {
			new Controller(args, 0);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
