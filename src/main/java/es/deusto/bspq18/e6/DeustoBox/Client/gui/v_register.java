package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class v_register extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmail;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JPasswordField txtPassword2;
	private JPanel panel;
	private JLabel lblJoinDeustobox;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblPassword2;
	private JLabel lblEmail;
	private JButton btnRegister;
	private Controller controlador;
	private v_client window;
	private JButton Back;

	/**
	 * Create the frame.
	 */
	public v_register(Controller controlador) {
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

		lblJoinDeustobox = new JLabel("Join DeustoBox");
		lblJoinDeustobox.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblJoinDeustobox.setBounds(98, 16, 162, 35);
		panel.add(lblJoinDeustobox);

		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(15, 96, 77, 20);
		panel.add(lblUsername);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(15, 132, 77, 20);
		panel.add(lblPassword);

		lblPassword2 = new JLabel("Repeat Password:");
		lblPassword2.setBounds(15, 168, 135, 20);
		panel.add(lblPassword2);

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(15, 60, 77, 20);
		panel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(98, 59, 155, 22);
		panel.add(txtEmail);
		txtEmail.setColumns(10);

		txtUsername = new JTextField();
		txtUsername.setBounds(98, 93, 146, 22);
		panel.add(txtUsername);
		txtUsername.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(98, 129, 146, 22);
		panel.add(txtPassword);
		txtPassword.setColumns(10);

		txtPassword2 = new JPasswordField();
		txtPassword2.setBounds(146, 165, 146, 22);
		panel.add(txtPassword2);
		txtPassword2.setColumns(10);

		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Ae) {
				String email = txtEmail.getText();
				String username = txtUsername.getText();
				
				char pass1[] = txtPassword.getPassword();
				char pass2[] = txtPassword2.getPassword();
				String password1 = new String(pass1);
				String password2 = new String(pass2);
				
				if (email.trim().equals("") || username.trim().equals("") || password1.trim().equals("") || password2.trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in all of the gaps");
				} else if (!password1.equals(password2)) {
					JOptionPane.showMessageDialog(null, "Make sure you entered the password correctly");
				} else {
					boolean user = controlador.signUp(txtUsername.getText(), txtEmail.getText(), password1);
					if(user){ 
						JOptionPane.showMessageDialog(null, "You have been registered successfully");
						new v_login(controlador);
						dispose();
					}else{
						JOptionPane.showMessageDialog(null, "An error happens while registering, check if the email is not already used.");
					}
				}
			}
		});
		btnRegister.setBounds(67, 199, 107, 29);
		panel.add(btnRegister);
		
		Back = new JButton("Back");
		Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			new v_login(controlador);
			dispose();
			}
		});
		Back.setBounds(175, 199, 117, 29);
		panel.add(Back);
	}
}
