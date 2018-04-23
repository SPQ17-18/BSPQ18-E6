package deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class v_register extends JFrame {

	private JPanel contentPane, panel;
	private JTextField txtEmail, txtUsername;
	private JPasswordField txtPassword, txtPassword2;
	private JLabel lblJoinDeustobox, lblUsername, lblPassword, lblPassword2, lblEmail, lblLogo;
	private JButton btnRegister;
	private ImageIcon logo;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					v_register frame = new v_register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public v_register() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
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
		
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent Ae) {
				String e = txtEmail.getText();
				String u = txtUsername.getText();
				String p = txtPassword.getText();
				String p2 = txtPassword2.getText();
				if(e.equals("")||u.equals("")||p.equals("")||p2.equals("")){
					JOptionPane.showMessageDialog(null,"Please fill in all of the gaps");
				}else if(!p.equals(p2)) {
					JOptionPane.showMessageDialog(null,"Make sure you entered the password correctly");
				}
				
			}
		});
		btnRegister.setBounds(166, 203, 115, 29);
		panel.add(btnRegister);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(98, 132, 146, 21);
		panel.add(txtPassword);
		
		txtPassword2 = new JPasswordField();
		txtPassword2.setBounds(146, 165, 146, 23);
		panel.add(txtPassword2);
		
		logo = new ImageIcon("src/main/java/deusto/bspq18/e6/DeustoBox/Client/images/logo.png");
		lblLogo = new JLabel(logo);
		lblLogo.setBounds(268, 16, 135, 76);
		panel.add(lblLogo);
	}
}
