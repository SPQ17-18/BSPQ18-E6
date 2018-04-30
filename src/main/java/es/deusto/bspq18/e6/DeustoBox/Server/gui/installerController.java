package es.deusto.bspq18.e6.DeustoBox.Server.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
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
		System.out.println("Current user: " + user.getEmail());
		File[] list = userFolder.listFiles();
		DFile myfile = null;
		if (list != null) {
			for (File element : list) {
				System.out.println("File: " + element.getName());
				if (element.isDirectory()) {
					System.out.println("Is directory");
					uploadFiles(map, user, element, element.getName() + "/");
				} else { // It's file
					System.out.println("Is file");
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
					// System.out.println(files);
					if (files.contains(file)) {
						// TODO Check the lastmodified
						System.out.println("Checking the last modified");
					} else {
						System.out.println("Uploading the file");
						// Upload the File data
						map.put(name, lastmodified.toString());
						// TODO de momento hacemos la prueba con uno
						myfile = new DFile(user, hash, name, lastmodified.toString());
						myfile.setUser(user);
						System.out.println(map);
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
			fileInputStream = new FileInputStream(file);
			md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fileInputStream));
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
		ArrayList<DFile> toDelete = new ArrayList<DFile>();

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
						try {
							inClient = format.parse(userFiles.get(i).getLastModified());
							if (inClient.after(dao.getLastConnection(userFiles.get(i).getUser()))) {
								// Ask for it to the client
								toReceive.add(userFiles.get(i));
							} else {
								// Tell the client to delete
								toDelete.add(userFiles.get(i));
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
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
		
		System.out.println("Getting files");
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

}
