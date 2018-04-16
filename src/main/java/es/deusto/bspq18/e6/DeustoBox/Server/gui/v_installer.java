package es.deusto.bspq18.e6.DeustoBox.Server.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.DeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.data.User;
import es.deusto.bspq18.e6.DeustoBox.Server.remote.DeustoBoxRemoteService;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class v_installer extends JFrame {

	private JFrame frame;
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
		directorio.mkdir();
		ArrayList<User> users = dao.getAllUsers();
		for (int i = 0; i < users.size(); i++) {
			File userFolder = new File(directorio + "\\" + users.get(i).getEmail());
			userFolder.mkdir();
			uploadFiles(users.get(i), userFolder, "/");
		}
	}

	/*
	 * Syncs files (TODO we need a thread here)
	 */
	public void uploadFiles(User user, File userFolder, String prefix) {
		File[] list  = userFolder.listFiles();
		if(list != null) {
			for(File element : list) {
				if(element.isDirectory()) {
					uploadFiles(user, element, element.getName()+"/");
				}else { // It's file
					// Get the name
					String name = prefix+element.getName();
					// Get the last modified date
					Date lastmodified = new Date(element.lastModified());
					// Upload all info to user's database
					HashMap<String, String> map = new HashMap<>();
					map.put(name, lastmodified.toString());
					user.setFiles(map);
					dao.addFiles(user);
				}
			}
		}

	}

}
