package es.deusto.bspq18.e6.DeustoBox.Server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DFile;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.DUser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import java.awt.Font;

public class v_installer extends JFrame {

	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private JTextField txtPath;
	private JFileChooser fileChooser;
	private DeustoBoxDAO dao;

	/**
	 * Create the application.
	 */
	public v_installer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		dao = new DeustoBoxDAO();
		frame = new JFrame();
		frame.setTitle("Installer");
		frame.setBounds(100, 100, 610, 309);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txtPath = new JTextField();
		txtPath.setBounds(31, 119, 398, 21);
		frame.getContentPane().add(txtPath);
		txtPath.setColumns(10);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(465, 112, 87, 37);
		frame.getContentPane().add(btnBrowse);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancel.setBounds(120, 180, 115, 29);
		frame.getContentPane().add(btnCancel);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dir = fileChooser.getSelectedFile().toString() + "\\Deusto-Box";
				createFolders(dir);
			}
		});
		btnOk.setBounds(314, 180, 115, 29);
		frame.getContentPane().add(btnOk);

		JLabel lblDeustoboxInstaller = new JLabel("DeustoBox installer");
		lblDeustoboxInstaller.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblDeustoboxInstaller.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeustoboxInstaller.setBounds(163, 26, 220, 48);
		frame.getContentPane().add(lblDeustoboxInstaller);

		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);

				int rVal = fileChooser.showOpenDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					txtPath.setText(fileChooser.getSelectedFile().toString());
				}
			}
		});
	}

	/*
	 * Creates empty folders for each user registered
	 */
	public void createFolders(String path) {
		File directorio = new File(path);
		ArrayList<DUser> users = dao.getAllUsers();

		if (directorio.exists()) {
			checkOldFolders(directorio, users);
		} else {
			directorio.mkdir();
		}
		// Create one folder for each user stored in the DB
		for (int i = 0; i < users.size(); i++) {
			File userFolder = new File(directorio + "\\" + users.get(i).getEmail());
			userFolder.mkdir();
			System.out.println("Current user: " + users.get(i).getEmail());
			updateFileDB(users.get(i), userFolder, "/");
		}
	}

	public void checkOldFolders(File directorio, ArrayList<DUser> users) {
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
				if (!emails.contains(userFiles[i].getName())) {
					// Delete all files of that non-existing user
					String[] entries = userFiles[i].list();
					if (entries != null) {
						for (String s : entries) {
							File currentFile = new File(userFiles[i].getPath(), s);
							currentFile.delete();
						}
					}
					// Delete user folder
					userFiles[i].delete();
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
	 * Syncs files (TODO we need a thread here)
	 */
	public void updateFileDB(DUser user, File userFolder, String prefix) {
		File[] list = userFolder.listFiles();
		DFile myfile = null;

		if (list != null) {
			// For each element in this directory...
			for (File element : list) {
				System.out.println("File: " + element.getName());
				if (element.isDirectory()) {
					System.out.println("It's directory");
					updateFileDB(user, element, element.getName() + "/");
				} else {
					System.out.println("It's file");
					// Get the name
					String name = prefix + element.getName();
					// Get the last modified date
					SimpleDateFormat format = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
					Date lastmodified = new Date(element.lastModified());
					// Get the MD5 Hash
					String hash = generateMD5(element);
					// Create the file
					DFile file = new DFile(user, hash, name, lastmodified.toString());
					ArrayList<DFile> files = user.getFiles();
					System.out.println("User files: " + files);
					if (files != null) {
						System.out.println("User has at least one file");
						// Check if the user has that file
						for (int i = 0; i < files.size(); i++) {
							// Find that file
							if (file.getName().equals((files.get(i).getName()))) {
								System.out.println("Same file found");
								if (!hash.equals(files.get(i).getHash())) {
									System.out.println("Has isn't the same");
									try {
										// Check who has the lastest version
										Date dateDB = format.parse(files.get(i).getLastModified());
										if (lastmodified.after(dateDB)) {
											System.out.println("Uploading the file");
											// Upload the File data
											myfile = new DFile(user, hash, name, lastmodified.toString());
											myfile.setUser(user);
											// Delete old version
											dao.deleteFiles(files.get(i));
											// Upload new version
											dao.addFile(myfile);
										} else {
											// Last modified is the same or is older
											break;
										}
									} catch (ParseException e) {
										e.printStackTrace();
									}
									break;
								} else {
									// Same Hash, no nothing
									System.out.println("Same Hash, do nothing");
									break;
								}
							}else {
								// No file in userDB coincides with this in Server
							}
						}
					} else {
						System.out.println("Uploading the file2");
						// Upload the File data
						myfile = new DFile(user, hash, name, lastmodified.toString());
						myfile.setUser(user);
						dao.addFile(myfile);
					}
				}
			}
		}
	}
	
	public JTextField getTxtPath() {
		return txtPath;
	}
	
	

}
