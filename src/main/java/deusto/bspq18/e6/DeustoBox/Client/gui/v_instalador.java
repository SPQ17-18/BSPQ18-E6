package deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class v_instalador extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					v_instalador frame = new v_instalador();
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
	public v_instalador() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblInstall = new JLabel("Deusto Box Installer");
		lblInstall.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblInstall.setBounds(88, 16, 220, 43);
		panel.add(lblInstall);
		
		JLabel lblWellcomeToDe = new JLabel("<html> Wellcome to the DeustoBox installer <br> please check the box bellow and press next in order to start the installation<html>");
		lblWellcomeToDe.setBounds(88, 63, 225, 91);
		panel.add(lblWellcomeToDe);
		
		JCheckBox chckbx = new JCheckBox("I accept the terms and conditions");
		chckbx.setBounds(67, 155, 274, 29);
		panel.add(chckbx);
		
		JButton btnNext = new JButton("Next>");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNext.setBounds(273, 196, 115, 29);
		panel.add(btnNext);
	}
}
