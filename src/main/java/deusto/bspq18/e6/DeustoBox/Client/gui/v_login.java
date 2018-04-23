package deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class v_login extends JFrame {

	private JPanel contentPane, panel;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JLabel lblLogin, lblUsername, lblPassword, lblRegister, lblLogo;
	private JButton btnEnter, btnCreateAccount;
	private ImageIcon logo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					v_login frame = new v_login();
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

	public v_login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\usuario\\git\\BSPQ18-E6\\src\\main\\java\\deusto\\bspq18\\e6\\DeustoBox\\Client\\images\\logo.png"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblLogin = new JLabel("Log In to DeustoBox");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblLogin.setBounds(168, 38, 224, 35);
		panel.add(lblLogin);
		
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(47, 114, 83, 20);
		panel.add(lblUsername);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(47, 156, 83, 20);
		panel.add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(145, 111, 146, 26);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(145, 153, 146, 26);
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
		btnEnter.setBounds(168, 183, 115, 29);
		panel.add(btnEnter);
		
		lblRegister = new JLabel("Don't have an account yet?");
		lblRegister.setBounds(29, 214, 190, 20);
		panel.add(lblRegister);
		
		btnCreateAccount = new JButton("Create an account");
		btnCreateAccount.setForeground(Color.BLUE);
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCreateAccount.setBounds(220, 210, 163, 29);
		btnCreateAccount.setBorderPainted(false);
		btnCreateAccount.setContentAreaFilled(false);
		panel.add(btnCreateAccount);
		
	
		logo = new ImageIcon("src/main/java/deusto/bspq18/e6/DeustoBox/Client/images/logo.png");
		lblLogo = new JLabel(logo);	
		lblLogo.setBounds(15, 14, 137, 81);
		panel.add(lblLogo);
	}
}
