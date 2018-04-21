package es.deusto.bspq18.e6.DeustoBox.Server.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import org.apache.tools.ant.util.FileUtils;

import android.text.format.DateFormat;

import java.awt.Font;

public class v_installer extends JFrame {

	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private JTextField txtPath;
	private JFileChooser fileChooser;
	private DeustoBoxDAO dao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					v_installer window = new v_installer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
			System.out.println("Directory exits");
			// Check if there are any user folders
			File[] userFiles = directorio.listFiles();
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
		} else {
			directorio.mkdir();
		}

		for (int i = 0; i < users.size(); i++) {
			File userFolder = new File(directorio + "\\" + users.get(i).getEmail());
			userFolder.mkdir();
			HashMap<String, String> map = new HashMap<>();
			uploadFiles(map, users.get(i), userFolder, "/");
		}
	}

	/*
	 * Syncs files (TODO we need a thread here)
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

					DFile file = new DFile(user, 1, name, lastmodified.toString());
					ArrayList<DFile> files = user.getFiles();
					if (files != null) {
						// Check if the user has that file
						for (int i = 0; i < files.size(); i++) {
							if (file.getName().equals((file.getName()))) {
								System.out.println("hey");
								SimpleDateFormat format = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
										Locale.ENGLISH);
								try {
									// TODO optimizalo porque más arriba se usa el dateDB y
									// se parsea de una forma más fácil
									Date dateDB = format.parse(files.get(i).getLastModified());
									Date datePC = format.parse(file.getLastModified());
									if (file.getName().equals(files.get(i).getName()) && datePC.after(dateDB)) {
										System.out.println("Uploading the file");
										// Upload the File data
										map.put(name, lastmodified.toString());
										// TODO de momento hacemos la prueba con uno
										myfile = new DFile(user, (int) (Math.random() * 100) + 1, name,
												lastmodified.toString());
										myfile.setUser(user);
										System.out.println(map);
										dao.addFiles(myfile);
									} else {
										// nothing, no puede ser
									}
								} catch (ParseException e) {
									e.printStackTrace();
								}
								break;
							}
						}
					} else {
						System.out.println("Uploading the file");
						// Upload the File data
						map.put(name, lastmodified.toString());
						// TODO de momento hacemos la prueba con uno
						myfile = new DFile(user, (int) (Math.random() * 100) + 1, name, lastmodified.toString());
						myfile.setUser(user);
						System.out.println(map);
						dao.addFiles(myfile);
					}
				}
			}
		}
	}

}
