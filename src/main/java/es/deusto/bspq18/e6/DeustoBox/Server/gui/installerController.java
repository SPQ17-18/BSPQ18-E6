package es.deusto.bspq18.e6.DeustoBox.Server.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Date;

import es.deusto.bspq18.e6.DeustoBox.Server.assembler.Assembler;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;

public class installerController {

	private IDeustoBoxDAO dao;
	private Assembler transform;
	private String path;
	private Error_log logger;
	private ResourceBundle resourcebundle;

	public installerController(String path, IDeustoBoxDAO dao, Error_log logger) {
		this.path = path;
		this.dao = dao;
		this.transform = new Assembler();
		this.logger = logger;
		resourcebundle = ResourceBundle.getBundle("SystemMessages", Locale.getDefault());
		resourcebundle = ResourceBundle.getBundle("SystemMessages",	Locale.forLanguageTag("en"));
	}
	

	/*
	 * Creates Deusto-Box-Server folder and one folder for each user stored in the
	 * database
	 */
	public void manageFolders() {
		logger.getLogger().info(resourcebundle.getBundle("msg_manage_folders").toString());
		File directorio = new File(path);
		ArrayList<DUser> users = dao.getAllUsers();

		if (users != null) {
			if (directorio.exists()) {
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
				uploadFiles(map, users.get(i), userFolder, "/");
			}
		} else {
			directorio.mkdir();
		}
	}

	/*
	 * Upload files to the DB
	 */
	public void uploadFiles(HashMap<String, String> map, DUser user, File userFolder, String prefix) {
		File[] list = userFolder.listFiles();
		DFile myfile = null;
		if (list != null) {
			for (File element : list) {

				if (element.isDirectory()) {

					uploadFiles(map, user, element, element.getName() + "/");
				} else { // It's file

					// Get the name
					String name = prefix + element.getName();
					// Get the last modified date
					Date lastmodified = new Date(element.lastModified());
					// Upload all info to user's database
					// Check if it exits
					// Get the MD5 Hash
					String hash = generateMD5(element);
					DFile file = new DFile(user, hash, name, lastmodified.toString());
					ArrayList<DFile> files = dao.getAllFiles();
					if (files.contains(file)) {
						// TODO Check the lastmodified

					} else {

						// Upload the File data
						map.put(name, lastmodified.toString());
						// TODO de momento hacemos la prueba con uno
						myfile = new DFile(user, hash, name, lastmodified.toString());
						myfile.setUser(user);

						dao.addFiles(myfile);
					}
				}
			}
		}
	}

	public static String generateMD5(File file) {

		String md5 = null;
		FileInputStream fileInputStream = null;

		try {
			Random r = new Random();
			fileInputStream = new FileInputStream(file);
			md5 = String.valueOf(r.nextInt(1000000));
			fileInputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return md5;
	}

	/*
	 * Receives the list of data files
	 */
	public void receiveList(ArrayList<DFileDTO> files, String path) {
		ArrayList<DFile> userFiles = transform.createFiles(files);
		ArrayList<DFile> toSend = new ArrayList<DFile>();
		ArrayList<DFile> toReceive = new ArrayList<DFile>();
		if (userFiles != null) {
			ArrayList<DFile> filesDB = dao.getAllFilesOfAUser(files.get(0).getUser().getEmail());
			if (filesDB != null) {
				SimpleDateFormat format = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

				for (int i = 0; i < userFiles.size(); i++) {
					Date inClient = null;
					Date inDB = null;
					boolean found = false;
					for (int q = 0; q < filesDB.size(); q++) {
						// Find the file
						if (userFiles.get(i).getName().equals(filesDB.get(q).getName())) {
							found = true;
							// Check if they are the same version
							if (!userFiles.get(i).getHash().equals(filesDB.get(q).getHash())) {
								// Compare dates
								try {
									inClient = format.parse(userFiles.get(i).getLastModified());
									inDB = format
											.parse(filesDB.get(filesDB.indexOf(userFiles.get(i))).getLastModified());
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
								// Compare dates
								try {
									inClient = format.parse(userFiles.get(i).getLastModified());
									inDB = format
											.parse(filesDB.get(filesDB.indexOf(userFiles.get(i))).getLastModified());
								} catch (ParseException e) {
									e.printStackTrace();
								}
								if (inDB.after(inClient)) {
									toSend.add(userFiles.get(i));
								} else {
									toReceive.add(userFiles.get(i));
								}
							}
						} else {
							// File doesn't exit in DB
							found = false;
						}

					}
					if (found == false) {
						// Check if the lastModficiaction is later than the last connection to the
						// server
					}
				}
			} else {
				receiveAllFiles();
			}
		} else {
			sendAllFiles(files.get(0).getUserEmail(), path);
		}
	}

	public ArrayList<DFileDTO> sendAllFiles(String email, String path) {

		path = path + "\\" + "Deusto-Box" + "\\" + email + "\\";

		ArrayList<DFile> files = new ArrayList<DFile>();
		files = dao.getAllFilesOfAUser(email);
		ArrayList<DFileDTO> filesDTO = null;
		filesDTO = transform.createFilesDTO(files, path);

		return filesDTO;
	}

	public ArrayList<DFileDTO> sendFiles(ArrayList<DFile> toSend, String path) {
		ArrayList<DFileDTO> filesDTO = null;
		filesDTO = transform.createFilesDTO(toSend, path);
		return filesDTO;
	}

	public void receiveAllFiles() {

	}

	public void receiveFiles(ArrayList<DFile> toReceive) {

	}

	public void deleteFiles(ArrayList<DFile> toDelete) {

	}

	public String getPath() {
		return path;
	}

	public ResourceBundle getResourcebundle() {
		return resourcebundle;
	}
}
