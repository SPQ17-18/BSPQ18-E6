package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;
import javax.swing.JButton;

public class v_client extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller control;
	private v_profile profile;
	private JButton btnMyProfile;


	/**
	 * Create the frame.
	 */
	public v_client(Controller controlador) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.control = controlador;
		
		btnMyProfile = new JButton("My Profile");
		btnMyProfile.setBounds(6, 6, 117, 29);
		btnMyProfile.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			profile = new v_profile(control);
			
			
			}
		}
			);
		contentPane.add(btnMyProfile);
		this.setVisible(true);
		
	}
}
