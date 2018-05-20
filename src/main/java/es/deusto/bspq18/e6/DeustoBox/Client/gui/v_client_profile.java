package es.deusto.bspq18.e6.DeustoBox.Client.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class v_client_profile extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller controlador;
	private JLabel lblMyProfile;
	private JLabel lblEmail;
	private JLabel lblUsername;
	private JLabel lblMemberSince;
	private JLabel txtEmail;
	private JLabel txtUsername;
	private JLabel txtMemberSince;
	private JButton btnChangePassword;
	private JPasswordField passwordField;
	private JLabel lblIntroduceTuContrasea;
	private JButton btnVerify;
	private JButton btnCheckConnections;

	/**
	 * Create the frame.
	 */
	public v_client_profile(Controller controlador) {
		setResizable(false);
		this.controlador = controlador;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		InitComponents();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	
	}
	public void InitComponents() {
		lblMyProfile = new JLabel(controlador.getResourcebundle().getString("msg_profile") );
		lblMyProfile.setBounds(223, 6, 112, 16);
		contentPane.add(lblMyProfile);
		
		lblEmail = new JLabel(controlador.getResourcebundle().getString("msg_email") );
		lblEmail.setBounds(6, 37, 75, 16);
		contentPane.add(lblEmail);
		
		lblUsername = new JLabel(controlador.getResourcebundle().getString("msg_username") );
		lblUsername.setBounds(6, 85, 75, 16);
		contentPane.add(lblUsername);
		
		lblMemberSince = new JLabel(controlador.getResourcebundle().getString("msg_member") );
		lblMemberSince.setBounds(6, 139, 91, 16);
		contentPane.add(lblMemberSince);
		
		txtEmail = new JLabel(controlador.getUserdto().getEmail());
		txtEmail.setBounds(153, 37, 145, 16);
		contentPane.add(txtEmail);
		
		txtUsername = new JLabel(controlador.getUserdto().getUsername());
		txtUsername.setBounds(153, 85, 145, 16);
		contentPane.add(txtUsername);
		
		txtMemberSince = new JLabel(controlador.getUserdto().getRegisteredDate().toString() );
		txtMemberSince.setBounds(153, 139, 202, 16);
		contentPane.add(txtMemberSince);
		
		btnChangePassword = new JButton(controlador.getResourcebundle().getString("msg_change_pass") );
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//Mostramos los nuevos botones
				ActivateBotons();
				
			}
		});
		btnChangePassword.setBounds(6, 179, 145, 29);
		contentPane.add(btnChangePassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(160, 212, 138, 26);
		contentPane.add(passwordField);
		passwordField.setVisible(false);
		
		lblIntroduceTuContrasea = new JLabel(controlador.getResourcebundle().getString("msg_insert_pass2") );
		lblIntroduceTuContrasea.setBounds(163, 184, 192, 16);
		contentPane.add(lblIntroduceTuContrasea);
		lblIntroduceTuContrasea.setVisible(false);
		
		
		btnVerify = new JButton((controlador.getResourcebundle().getString("msg_verify") ));
		btnVerify.setBounds(327, 212, 117, 29);
		contentPane.add(btnVerify);
		

		btnVerify.setVisible(false);
		btnVerify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!String.valueOf(passwordField.getPassword()).equals("")) {
					String email = controlador.getUserdto().getEmail();
					if(controlador.passwordCorrect(email,String.valueOf(passwordField.getPassword()))){
						v_client_profile_changepassword window = new v_client_profile_changepassword(controlador);
						window.setVisible(true);
						
					}
					
					
				}
				
			}
		});
		
		btnCheckConnections = new JButton((controlador.getResourcebundle().getString("msg_checkC") ));
		btnCheckConnections.setBounds(299, 32, 145, 29);
		btnCheckConnections.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				v_client_connections connections = new v_client_connections(controlador);
				connections.setVisible(true);
				
			}
		});
		contentPane.add(btnCheckConnections);
	}
		
	
	
	public void ActivateBotons() {
		passwordField.setVisible(true);
		lblIntroduceTuContrasea.setVisible(true);
		btnVerify.setVisible(true);
		
	}
		
	
	public Controller getControlador() {
		return controlador;
	}
	public void setControlador(Controller controlador) {
		this.controlador = controlador;
	}
}
