package es.deusto.bspq18.e6.DeustoBox.Server.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class v_server extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					v_server frame = new v_server();
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
	public v_server() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 131);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDeustoBoxServer = new JLabel("Deusto Box Server");
		lblDeustoBoxServer.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDeustoBoxServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeustoBoxServer.setBounds(10, 11, 414, 22);
		contentPane.add(lblDeustoBoxServer);
		
		JButton btnNewButton = new JButton("Desconectar");
		btnNewButton.setBounds(293, 58, 117, 23);
		contentPane.add(btnNewButton);
		
		JButton btnFolder = new JButton("Folder");
		btnFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnFolder.setBounds(22, 58, 89, 23);
		contentPane.add(btnFolder);
	}
}
