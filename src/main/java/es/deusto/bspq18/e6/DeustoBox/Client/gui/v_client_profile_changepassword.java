package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPasswordField;


public class v_client_profile_changepassword extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller controlador;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JLabel lblChangePassword;
	private JLabel lblInsertThePassword;
	private JLabel lblrepeatPassword;
	private JButton btnConfirm;

	/**
	 * Create the frame.
	 */
	public v_client_profile_changepassword(Controller controlador) {
		this.controlador = controlador;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 332, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		InitComponents();
		this.setVisible(true);
		
	}
	public void InitComponents() {
		lblChangePassword = new JLabel(controlador.getResourcebundle().getString("msg_change_pass"));
		lblChangePassword.setBounds(113, 6, 126, 16);
		contentPane.add(lblChangePassword);
		
		lblInsertThePassword = new JLabel(controlador.getResourcebundle().getString("msg_insert_pass"));
		lblInsertThePassword.setBounds(107, 51, 132, 16);
		contentPane.add(lblInsertThePassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(92, 79, 147, 26);
		contentPane.add(passwordField);
		
		lblrepeatPassword = new JLabel(controlador.getResourcebundle().getString("msg_repeat_pass"));
		lblrepeatPassword.setBounds(107, 126, 132, 16);
		contentPane.add(lblrepeatPassword);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(92, 154, 147, 26);
		contentPane.add(passwordField_1);
		
		btnConfirm = new JButton(controlador.getResourcebundle().getString("confirm_btn").toString());
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(String.valueOf(passwordField.getPassword()).equals(String.valueOf(passwordField_1.getPassword()))) {
					String email = controlador.getUserdto().getEmail();
					boolean correct = controlador.updatePassword(email, String.valueOf(passwordField.getPassword()));
					if(correct){
						JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_confirm_change"));
						dispose();
						
					}
					else{
						JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_error_pass"));
						
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_match_pass"));
					
				}
			}
		});
		btnConfirm.setBounds(102, 192, 117, 29);
		contentPane.add(btnConfirm);

	

	}

		
	
	public Controller getControlador() {
		return controlador;
	}
	public void setControlador(Controller controlador) {
		this.controlador = controlador;
	}
}
