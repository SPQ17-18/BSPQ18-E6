package es.deusto.bspq18.e6.DeustoBox.Client.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;

import java.awt.Font;
import java.awt.Toolkit;

public class v_installer extends JFrame {

	private static final long serialVersionUID = 1L;
	protected JFrame frame;
	private JTextField txtPath;
	private JFileChooser fileChooser;
	private Controller controlador;
	private JButton btnBrowse;

	/**
	 * Create the application.
	 */
	public v_installer(Controller controlador) {
		this.controlador = controlador;
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(v_installer.class.getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/logo.png")));
		frame.setResizable(false);
		frame.setTitle("Installer");
		frame.setBounds(100, 100, 610, 309);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txtPath = new JTextField();
		txtPath.setBounds(31, 119, 398, 21);
		frame.getContentPane().add(txtPath);
		txtPath.setColumns(10);

		btnBrowse = new JButton(this.controlador.getResourcebundle().getString("msg_browse") );
		btnBrowse.setBounds(465, 112, 87, 37);
		frame.getContentPane().add(btnBrowse);

		JButton btnCancel = new JButton(this.controlador.getResourcebundle().getString("msg_cancel") );
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
				String dir = fileChooser.getSelectedFile().toString() ;
				File directorio=new File(dir + "\\Client Deusto-Box");
				controlador.setPath(dir + "\\Client Deusto-Box\\");
				directorio.mkdir(); 
				frame.dispose();
				v_client client = new v_client(controlador);
				client.setVisible(true);
				
			}
		});
		btnOk.setBounds(314, 180, 115, 29);
		frame.getContentPane().add(btnOk);
		
		JLabel lblDeustoboxInstaller = new JLabel("DeustoBox Client Installer");
		lblDeustoboxInstaller.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblDeustoboxInstaller.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeustoboxInstaller.setBounds(151, 27, 295, 48);
		frame.getContentPane().add(lblDeustoboxInstaller);

		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);

				int rVal = fileChooser.showOpenDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					txtPath.setText(fileChooser.getSelectedFile().toString() );
				}
			}
		});
	}
}
