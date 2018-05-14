package es.deusto.bspq18.e6.DeustoBox.Server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

public class v_installer extends JFrame {

	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private JTextField txtPath;
	private JFileChooser fileChooser;
	private installerController installer;
	private IDeustoBoxDAO dao;
	private JButton btnBrowse;
	private JButton btnCancel;
	private JButton btnOk;
	private JLabel lblDeustoboxInstaller;
	private Error_log logger;


	public v_installer(IDeustoBoxDAO dao, Error_log logger) {
		initialize();
		this.dao = dao;
		this.logger = logger;
		
	}

	private void initialize() {

		frame = new JFrame();
		frame.setTitle("Installer");
		frame.setBounds(100, 100, 610, 309);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txtPath = new JTextField();
		txtPath.setBounds(31, 119, 398, 21);
		frame.getContentPane().add(txtPath);
		txtPath.setColumns(10);

		btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(465, 112, 87, 37);
		frame.getContentPane().add(btnBrowse);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancel.setBounds(120, 180, 115, 29);
		frame.getContentPane().add(btnCancel);

		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dir = fileChooser.getSelectedFile().toString() + "\\Deusto-Box";
				//createFolders(dir);
				installer = new installerController(dir, dao, logger);
				installer.manageFolders();
				if(!updateServer.isAlive()) {
					updateServer.start();
				}	
				v_server server = new v_server(dir);
				server.setVisible(true);
				frame.dispose();
			}
		});
		btnOk.setBounds(314, 180, 115, 29);
		frame.getContentPane().add(btnOk);

		lblDeustoboxInstaller = new JLabel("DeustoBox installer");
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

	public JTextField getTxtPath() {
		return txtPath;
	}
	
	public installerController getInstaller() {
		return installer;
	}

	public void setInstaller(installerController installer) {
		this.installer = installer;
	}
	
	Thread updateServer = new Thread(){
		public void run(){
			
		while(true){	
			getInstaller().manageFolders();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
		}
	};


}
