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
		System.out.println("Hola");
		InitComponents();
		this.setVisible(true);
		
	}
		
		public void InitComponents(){
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 11, 76, 14);
		contentPane.add(lblUsername);
		
		lblUser = new JLabel(getControlador().getUserdto().getEmail());
		lblUser.setBounds(140, 11, 46, 14);
		contentPane.add(lblUser);
		
		lblNumberOfFiles = new JLabel("Number Of Files:");
		lblNumberOfFiles.setBounds(10, 48, 105, 14);
		contentPane.add(lblNumberOfFiles);
		
		
		lblNumber = new JLabel("");
		//Connect to the DB and check the number of files that the user have
		int files = getControlador().getNumberOfFiles("MODIFICAR");
		lblNumber.setText(String.valueOf(files));
		lblNumber.setBounds(140, 48, 46, 14);
		contentPane.add(lblNumber);
		
		SyncFiles = new JButton("SyncFiles");
		SyncFiles.setBounds(10, 100, 89, 23);
		contentPane.add(SyncFiles);
		
		this.setVisible(true);
		SyncFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getControlador().getFiles(getControlador().getUserdto().getEmail());
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
