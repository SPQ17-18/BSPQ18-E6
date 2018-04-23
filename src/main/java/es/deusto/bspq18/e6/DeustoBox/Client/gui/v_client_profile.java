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
import javax.swing.JPasswordField;

public class v_client_profile extends JFrame {

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

	/**
	 * Create the frame.
	 */
	public v_client_profile(Controller controlador) {
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
	public void InitComponents() {
		lblMyProfile = new JLabel("My Profile");
		lblMyProfile.setBounds(223, 6, 112, 16);
		contentPane.add(lblMyProfile);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(6, 37, 75, 16);
		contentPane.add(lblEmail);
		
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(6, 85, 75, 16);
		contentPane.add(lblUsername);
		
		lblMemberSince = new JLabel("Member since:");
		lblMemberSince.setBounds(6, 139, 91, 16);
		contentPane.add(lblMemberSince);
		
		txtEmail = new JLabel(controlador.getUserdto().getEmail());
		txtEmail.setBounds(153, 37, 145, 16);
		contentPane.add(txtEmail);
		
		txtUsername = new JLabel(controlador.getUserdto().getUsername());
		txtUsername.setBounds(153, 85, 145, 16);
		contentPane.add(txtUsername);
		
		txtMemberSince = new JLabel(controlador.getUserdto().getRegisteredDate().toString());
		txtMemberSince.setBounds(153, 139, 202, 16);
		contentPane.add(txtMemberSince);
		
		btnChangePassword = new JButton("Change Password");
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
		
		lblIntroduceTuContrasea = new JLabel("Introduce tu contraseña actual");
		lblIntroduceTuContrasea.setBounds(163, 184, 192, 16);
		contentPane.add(lblIntroduceTuContrasea);
		lblIntroduceTuContrasea.setVisible(false);
		
		
		btnVerify = new JButton("Verify");
		btnVerify.setBounds(327, 212, 117, 29);
		contentPane.add(btnVerify);
		btnVerify.setVisible(false);
		btnVerify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!passwordField.getText().equals("")) {
					if(controlador.passwordCorrect(passwordField.getText())){
						v_client_profile_changepassword window = new v_client_profile_changepassword(controlador);
						
					}
					
					
				}
				
			}
		});
		
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
