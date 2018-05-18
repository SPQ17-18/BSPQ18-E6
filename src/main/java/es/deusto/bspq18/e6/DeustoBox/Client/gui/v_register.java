package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Toolkit;

public class v_register extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, panel;
	private JTextField txtEmail, txtUsername;
	private JPasswordField txtPassword, txtPassword2;
	private JLabel lblJoinDeustobox, lblUsername, lblPassword, lblPassword2, lblEmail, lblLogo;
	private JButton btnRegister;
	private Controller controlador;
	private JButton back;

	public v_register(Controller controlador) {
		//setIconImage(Toolkit.getDefaultToolkit().getImage(v_register.class.getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.controlador = controlador;
		initComponents();
		this.setVisible(true);
	}
	
	public void initComponents() {
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblJoinDeustobox = new JLabel(controlador.getResourcebundle().getString("msg_join") );
		lblJoinDeustobox.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblJoinDeustobox.setBounds(98, 16, 162, 35);
		panel.add(lblJoinDeustobox);
		
		lblUsername = new JLabel(controlador.getResourcebundle().getString("msg_username") );
		lblUsername.setBounds(15, 96, 77, 20);
		panel.add(lblUsername);
		
		lblPassword = new JLabel(controlador.getResourcebundle().getString("msg_pass") );
		lblPassword.setBounds(15, 132, 77, 20);
		panel.add(lblPassword);
		
		lblPassword2 = new JLabel(controlador.getResourcebundle().getString("msg_repeat_pass") );
		lblPassword2.setBounds(15, 168, 135, 20);
		panel.add(lblPassword2);
		
		lblEmail = new JLabel(controlador.getResourcebundle().getString("msg_email") );
		lblEmail.setBounds(15, 60, 77, 20);
		panel.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(98, 59, 155, 25);
		panel.add(txtEmail);
		txtEmail.setColumns(10);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(98, 93, 146, 25);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		btnRegister = new JButton(controlador.getResourcebundle().getString("msg_register") );
		btnRegister.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent Ae) {
				String email = txtEmail.getText();
				String username = txtUsername.getText();
				
				char pass1[] = txtPassword.getPassword();
				char pass2[] = txtPassword2.getPassword();
				String password1 = new String(pass1);
				String password2 = new String(pass2);
				
				if (email.trim().equals("") || username.trim().equals("") || password1.trim().equals("") || password2.trim().equals("")) {
					JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_error_gaps") );
				} else if (!password1.equals(password2)) {
					JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_error_pass") );
				} else {
					boolean user = controlador.signUp(txtUsername.getText(), txtEmail.getText(), password1);
					if(user){ 
						JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_regis") );
						new v_login(controlador);
						dispose();
					}else{
						JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_error_email") );
					}
				}
			}
		});
		btnRegister.setBounds(109, 199, 115, 31);
		panel.add(btnRegister);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(98, 132, 146, 25);
		panel.add(txtPassword);
		
		txtPassword2 = new JPasswordField();
		txtPassword2.setBounds(146, 165, 146, 25);
		panel.add(txtPassword2);
		
		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			new v_login(controlador);
			dispose();
			}
		});
		back.setBounds(234, 199, 117, 31);
		panel.add(back);
		
		lblLogo = new JLabel();
		lblLogo.setBounds(268, 16, 135, 76);
		
		/*URL url = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/logo.png");
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
