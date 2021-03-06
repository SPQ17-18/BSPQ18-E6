package es.deusto.bspq18.e6.DeustoBox.Server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import es.deusto.bspq18.e6.DeustoBox.Server.jdo.dao.IDeustoBoxDAO;
import es.deusto.bspq18.e6.DeustoBox.Server.utils.Error_log;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

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
	private ResourceBundle resourcebundle;


	public v_installer(IDeustoBoxDAO dao, Error_log logger, ResourceBundle resource) {
		resourcebundle = resource;
		initialize();
		this.dao = dao;
		this.logger = logger;
		
	}

	private void initialize() {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(v_installer.class.getResource("/es/deusto/bspq18/e6/DeustoBox/Server/images/logo.png")));
		frame.setTitle(resourcebundle.getString("msg_txt_installer"));
		frame.setBounds(100, 100, 610, 237);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		txtPath = new JTextField();
		txtPath.setBounds(29, 101, 398, 30);
		frame.getContentPane().add(txtPath);
		txtPath.setColumns(10);

		btnBrowse = new JButton(resourcebundle.getString("txt_browse"));
		btnBrowse.setBounds(463, 94, 87, 48);
		frame.getContentPane().add(btnBrowse);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancel.setBounds(118, 154, 115, 37);
		frame.getContentPane().add(btnCancel);

		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dir = fileChooser.getSelectedFile().toString() + "\\Deusto-Box";
				//createFolders(dir);
				installer = new installerController(dir, dao, logger, resourcebundle);
				installer.manageFolders();
				if(!updateServer.isAlive()) {
 					updateServer.start();
 				}	

				v_server server = new v_server(dir);

 				server.setVisible(true);
 
 				frame.dispose();
 			
 			}
		});
		btnOk.setBounds(312, 154, 115, 37);
		frame.getContentPane().add(btnOk);

		lblDeustoboxInstaller = new JLabel(resourcebundle.getString("txt_dt"));
		lblDeustoboxInstaller.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblDeustoboxInstaller.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeustoboxInstaller.setBounds(163, 26, 220, 48);
		frame.getContentPane().add(lblDeustoboxInstaller);

		JLabel lblLogo = new JLabel("New label");
		lblLogo.setBounds(507, 11, 87, 48);
		
		URL url = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Server/images/logo.png");
		BufferedImage img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(),
		        Image.SCALE_SMOOTH);
		
		ImageIcon imageIcon = new ImageIcon(dimg);
		lblLogo.setIcon(imageIcon);
		frame.getContentPane().add(lblLogo);
		
		
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
