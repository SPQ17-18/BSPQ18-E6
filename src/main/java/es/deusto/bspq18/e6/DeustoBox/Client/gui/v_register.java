package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
	private JTextField txtPassword;
	private JTextField txtPassword2;
	private JPanel panel;
	private JLabel lblJoinDeustobox;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblPassword2;
	private JLabel lblEmail;
	private JButton btnRegister;
	private Controller controlador;
	private v_client window;

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

		txtPassword = new JTextField();
		txtPassword.setBounds(98, 129, 146, 22);
		panel.add(txtPassword);
		txtPassword.setColumns(10);

		txtPassword2 = new JTextField();
		txtPassword2.setBounds(146, 165, 146, 22);
		panel.add(txtPassword2);
		txtPassword2.setColumns(10);

		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Ae) {
				String e = txtEmail.getText();
				String u = txtUsername.getText();
				String p = txtPassword.getText();
				String p2 = txtPassword2.getText();
				if (e.equals("") || u.equals("") || p.equals("") || p2.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in all of the gaps");
				} else if (!p.equals(p2)) {
					JOptionPane.showMessageDialog(null, "Make sure you entered the password correctly");
				} else {
					// Los datos son correctos, registramos
					boolean user = controlador.signUp(txtUsername.getText(), txtEmail.getText(), txtPassword.getText());
					
					if(user){ //El usuario ha sido registrado con exito
						JOptionPane.showMessageDialog(null, "Welcome to DeustoBOX " + u + ".");
						window = new v_client(controlador);
						setVisible(false);
						
					}
					else{
						JOptionPane.showMessageDialog(null, "An error happens while registering, check if the email is not already used.");
					}
					
				}

			}
		});
		btnRegister.setBounds(166, 203, 115, 29);
		panel.add(btnRegister);
	}

}
