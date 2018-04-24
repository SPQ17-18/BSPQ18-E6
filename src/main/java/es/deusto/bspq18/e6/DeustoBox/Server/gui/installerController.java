package es.deusto.bspq18.e6.DeustoBox.Server.gui;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Date;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assembler;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;

public class installerController {

	private DeustoBoxDAO dao;
	private Assembler transform;
	private String path;

	public installerController(String path) {
		this.path = path;
		this.dao = new DeustoBoxDAO();
		this.transform = new Assembler();
	}

	/*
	 * Creates Deusto-Box-Server folder and one folder for each user stored in the
	 * database
	 */
	public void manageFolders() {
		File directorio = new File(path);
		ArrayList<DUser> users = dao.getAllUsers();

		if (users != null) {
			if (directorio.exists()) {
				System.out.println("Directory exits");
				// Check if there are any user folders
				File[] userFiles = directorio.listFiles();

				if (userFiles != null) {
					// Get all emails registered
					ArrayList<String> emails = new ArrayList<String>();
					for (int z = 0; z < users.size(); z++) {
						emails.add(users.get(z).getEmail());
					}
					// Check if the user exits or not
					for (int i = 0; i < userFiles.length; i++) {
						System.out.println(userFiles[i].getName());
						if (!emails.contains(userFiles[i].getName())) {
							// Delete all files of that non-existing user
							String[] entries = userFiles[i].list();
							if (entries != null) {
								for (String s : entries) {
									File currentFile = new File(userFiles[i].getPath(), s);
									currentFile.delete();
								}
							}
							userFiles[i].delete();
						}
					}
				}

			} else {
				directorio.mkdir();
			}
			// Create one folder for each user
			for (int i = 0; i < users.size(); i++) {
				File userFolder = new File(directorio + "\\" + users.get(i).getEmail());
				userFolder.mkdir();
				HashMap<String, String> map = new HashMap<>();
				// uploadFiles(map, users.get(i), userFolder, "/");
			}
		} else {
			directorio.mkdir();
		}
	}

	/*
	 * Receives the list of data files
	 */
	public void receiveList(ArrayList<DFileDTO> files) {
		ArrayList<DFile> userFiles = transform.createFiles(files);
		ArrayList<DFile> toSend = new ArrayList<DFile>();
		ArrayList<DFile> toReceive = new ArrayList<DFile>();
		ArrayList<DFile> toDelete = new ArrayList<DFile>();

		if (userFiles != null) {
			ArrayList<DFile> filesDB = dao.getAllFilesOfAUser(files.get(0).getUser().getEmail());
			if (filesDB != null) {
				SimpleDateFormat format = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
				Date inClient;
				Date inDB;
				// Check if db has that file
				for (int i = 0; i < userFiles.size(); i++) {
					if (filesDB.contains(userFiles.get(i))) {
						// Compare dates
						try {
							inClient = format.parse(userFiles.get(i).getLastModified());
							inDB = format.parse(filesDB.get(filesDB.indexOf(userFiles.get(i))).getLastModified());
							if (inClient.after(inDB)) {
								// Ask for it to the client
								toReceive.add(userFiles.get(i));
							} else {
								// Send to the client
								toSend.add(filesDB.get(filesDB.indexOf(userFiles.get(i))));
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}

					} else {
						// Check if the file lastMod is later than the last connection to the server
						/*if (inClient.after(dao.getLastConnection(userFiles.get(i).getUser()){
						 * toReceive.add(userFiles.get(i));
						 * } else {
						 * Delete because this file has been deleted by the user
						 * toDelete.add(userFiles.get(i));
						 * }
						 */
						
						// We need last connection of the user -> implement it in the User data
					}
				}
			}

		}
	}

	public void sendFiles(ArrayList<DFile> toSend) {

	}
	
	public void receiveFies(ArrayList<DFile> toReceive) {
		
	}
	
	public void deleteFiles(ArrayList<DFile> toDelete) {
		
	}

}
