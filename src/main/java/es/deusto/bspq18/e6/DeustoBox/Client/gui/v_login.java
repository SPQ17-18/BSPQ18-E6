package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class v_login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JPanel panel;
	private JLabel lblLogin;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JButton btnEnter;
	private JLabel lblRegister;
	private JButton btnCreateAccount;
	private Controller controlador;
	
	/**
	 * Create the frame.
	 */
	public v_login(Controller controlador) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.controlador = controlador;
		InitComponents();
		this.setVisible(true);
	}
	public void InitComponents() {
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblLogin = new JLabel("Log In to DeustoBox");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblLogin.setBounds(98, 16, 224, 35);
		panel.add(lblLogin);
		
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(58, 74, 83, 20);
		panel.add(lblUsername);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(58, 110, 83, 20);
		panel.add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(156, 71, 146, 26);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(156, 107, 146, 26);
		panel.add(passwordField);
		
		btnEnter = new JButton("Sign In");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String u = txtUsername.getText();
				String p = passwordField.getText();
				if(u.equals("")){
					JOptionPane.showMessageDialog(null,"Insert a Username");
				}else if(p.equals("")){
					JOptionPane.showMessageDialog(null,"Insert a password");
				}else {
					v_register vp= new v_register();
					vp.setVisible(true);
					dispose();
				}
			}
		});
		btnEnter.setBounds(169, 149, 115, 29);
		panel.add(btnEnter);
		
		lblRegister = new JLabel("Don't have an account yet?");
		lblRegister.setBounds(29, 198, 190, 20);
		panel.add(lblRegister);
		
		btnCreateAccount = new JButton("Create an account");
		btnCreateAccount.setForeground(Color.BLUE);
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCreateAccount.setBounds(218, 194, 163, 29);
		btnCreateAccount.setBorderPainted(false);
		btnCreateAccount.setContentAreaFilled(false);
		panel.add(btnCreateAccount);
	}

}
