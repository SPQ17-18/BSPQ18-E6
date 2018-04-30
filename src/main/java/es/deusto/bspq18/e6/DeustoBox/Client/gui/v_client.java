package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;
import javax.swing.JLabel;
import javax.swing.JButton;

public class v_client extends JFrame {

	private JPanel contentPane;
	private Controller controlador;
	private JLabel lblUsername;
	private JLabel lblUser;
	private JLabel lblNumberOfFiles;
	private JLabel lblNumber;
	private JButton SyncFiles;
	private JButton btnMyProfile;
	private v_client_profile myProfile;
	/**
	 * Create the frame.
	 */
	public v_client(Controller controlador) {
		this.controlador = controlador;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		InitComponents();
		this.setVisible(true);
		
	}
		
		public void InitComponents(){
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 11, 76, 14);
		contentPane.add(lblUsername);
		
		lblUser = new JLabel(getControlador().getUserdto().getEmail());
		lblUser.setBounds(208, 11, 140, 14);
		contentPane.add(lblUser);
		
		lblNumberOfFiles = new JLabel("Number Of Files:");
		lblNumberOfFiles.setBounds(10, 48, 163, 14);
		contentPane.add(lblNumberOfFiles);
		
		
		lblNumber = new JLabel("");
		//Connect to the DB and check the number of files that the user have
		int files = getControlador().getNumberOfFiles();
		lblNumber.setText(String.valueOf(files));
		lblNumber.setBounds(208, 48, 46, 14);
		contentPane.add(lblNumber);
		
		SyncFiles = new JButton("SyncFiles");
		SyncFiles.setBounds(10, 92, 89, 23);
		contentPane.add(SyncFiles);
		
		btnMyProfile = new JButton("My profile");
		btnMyProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myProfile = new v_client_profile(controlador);
				
			}
		});
		btnMyProfile.setBounds(170, 89, 117, 29);
		contentPane.add(btnMyProfile);
		
		SyncFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getControlador().getFiles();
				lblNumber.setText(String.valueOf(controlador.getNumberOfFiles()));
			}
		});
	}
	
	public Controller getControlador() {
		return controlador;
	}
	public void setControlador(Controller controlador) {
		this.controlador = controlador;
	}
}
