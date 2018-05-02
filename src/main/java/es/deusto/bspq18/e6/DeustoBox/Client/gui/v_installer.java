package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Container;

public class v_installer extends JFrame {

	private static final long serialVersionUID = 1L;
	protected JFrame frame;
	private JTextField txtPath;
	private JFileChooser fileChooser;
	private Controller controlador;
	private JPanel panel;

	/**
	 * Create the application.
	 */
	public v_installer(Controller controlador) {
		initialize();
		this.controlador = controlador;
		setIconImage(Toolkit.getDefaultToolkit().getImage(v_login.class.getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/logo.png")));
		setResizable(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
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
				String dir = fileChooser.getSelectedFile().toString();
				File directorio=new File(dir + "\\Client Deusto-Box");
				controlador.setPath(dir + "\\Client Deusto-Box\\");
				directorio.mkdir(); 
				v_client client = new v_client(controlador);
				client.setVisible(true);
				dispose();
			}
		});
		btnOk.setBounds(314, 180, 115, 29);
		frame.getContentPane().add(btnOk);
		
		JLabel lblDeustoboxInstaller = new JLabel("DestoBox Installer");
		lblDeustoboxInstaller.setBounds(163, 26, 220, 48);
//		BufferedImage img = null;
//		try {
//		    img = ImageIO.read(new File("/es/deusto/bspq18/e6/DeustoBox/Client/images/logo.png"));
//		} catch (IOException e) {
//		    e.printStackTrace();
//		}
//		Image dimg = img.getScaledInstance(lblDeustoboxInstaller.getWidth(), lblDeustoboxInstaller.getHeight(),
//		        Image.SCALE_SMOOTH);
//		ImageIcon imageIcon = new ImageIcon(dimg);
//		
//		lblDeustoboxInstaller.setIcon(imageIcon);
		JLabel lblLogo = new JLabel();	
		lblLogo.setBounds(15, 14, 137, 81);
		
		URL url = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/logo.png");
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
	
		panel.add(lblLogo);
		
		lblDeustoboxInstaller.setBackground(Color.WHITE);
		lblDeustoboxInstaller.setForeground(Color.BLUE);
		lblDeustoboxInstaller.setFont(new Font("Yu Gothic", Font.PLAIN, 24));
		lblDeustoboxInstaller.setHorizontalAlignment(SwingConstants.CENTER);
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
}
