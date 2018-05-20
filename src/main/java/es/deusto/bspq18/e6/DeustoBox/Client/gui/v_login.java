package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

public class v_login extends JFrame {

	private static final long serialVersionUID = 8444678133078858266L;
	private JPanel contentPane, panel;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JLabel lblLogin, lblEmail, lblPassword, lblRegister, lblLogo;
	private JButton btnEnter, btnCreateAccount;
	private Controller controlador;
	
	public v_login(Controller controlador) {
		//setIconImage(Toolkit.getDefaultToolkit().getImage(v_login.class.getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/logo.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 314);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.controlador = controlador;
		try {
			initComponents();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void initComponents() throws IOException {
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblLogin = new JLabel(controlador.getResourcebundle().getString("msg_login"));
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblLogin.setBounds(168, 38, 224, 35);
		panel.add(lblLogin);
		
		lblEmail = new JLabel(controlador.getResourcebundle().getString("msg_email"));
		lblEmail.setBounds(47, 114, 83, 20);
		panel.add(lblEmail);
		
		lblPassword = new JLabel(controlador.getResourcebundle().getString("msg_pass"));
		lblPassword.setBounds(47, 156, 83, 20);
		panel.add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(145, 111, 163, 26);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(145, 153, 163, 26);
		panel.add(passwordField);
		
		btnEnter = new JButton(controlador.getResourcebundle().getString("msg_signin"));
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtUsername.getText();
				char pass[] = passwordField.getPassword();
				String password = new String(pass);

				if (name.trim().equals("")) {
					JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_insert_user"));
				} else if (password.trim().equals("")) {
					JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_insert_pass"));
				} else {
					boolean user = controlador.login(name, password);
					if (user) {
						JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_back") + " " + name + ".");
						v_installer installer = new v_installer(controlador);
						installer.frame.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_data"));
						txtUsername.setText("");
						passwordField.setText("");
					}
				}
			}
		});
		btnEnter.setBounds(168, 190, 115, 29);
		panel.add(btnEnter);
		
		lblRegister = new JLabel(controlador.getResourcebundle().getString("msg_no_acc"));
		lblRegister.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRegister.setBounds(93, 239, 190, 20);
		panel.add(lblRegister);
		
		btnCreateAccount = new JButton(controlador.getResourcebundle().getString("msg_create_acc"));
		btnCreateAccount.setForeground(Color.BLUE);
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				v_register vp = new v_register(controlador);
				vp.setVisible(true);
				dispose();
			}
		});
		
		btnCreateAccount.setBounds(261, 235, 163, 29);
		btnCreateAccount.setBorderPainted(false);
		btnCreateAccount.setContentAreaFilled(false);
		panel.add(btnCreateAccount);
	
		lblLogo = new JLabel();	
		lblLogo.setBounds(15, 14, 137, 81);
		/*
		URL url = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Server/images/logo.png");
		BufferedImage img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(),
		        Image.SCALE_SMOOTH);
		
		ImageIcon imageIcon = new ImageIcon(dimg);
		lblLogo.setIcon(imageIcon);
		
		panel.add(lblLogo);
		*/
		
		
		
	}

}
