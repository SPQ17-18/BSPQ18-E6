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

public class v_profile extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private Controller controlador;
	private v_client window;
	private JLabel lblNombre;
	private JLabel lblEmail;
	private JLabel lblPassword;
	private JButton btnBack;
	private JLabel label;
	private JLabel lblNewLabel;
	private JLabel lbl_password;
	
	/**
	 * Create the frame.
	 */
	public v_profile(Controller controlador) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.controlador = controlador;
		InitComponents();
		this.setVisible(true);
	}
	public void InitComponents() {
		contentPane.setLayout(null);
		panel = new JPanel();
		panel.setBounds(5, 5, 1, 268);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(18, 31, 61, 16);
		contentPane.add(lblNombre);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(18, 82, 61, 16);
		contentPane.add(lblEmail);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(18, 140, 90, 16);
		contentPane.add(lblPassword);
		
		btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setBounds(18, 204, 117, 29);
		contentPane.add(btnBack);
		
		label = new JLabel("");
		label.setBounds(151, 31, 61, 16);
		contentPane.add(label);
		label.setText(controlador.getUserdto().getName());
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(108, 82, 61, 16);
		contentPane.add(lblNewLabel);
		lblNewLabel.setText(controlador.getUserdto().getEmail());
		
		lbl_password = new JLabel("New label");
		lbl_password.setBounds(108, 140, 61, 16);
		contentPane.add(lbl_password);
		lbl_password.setText(controlador.getUserdto().getPassword());
	}
}
