package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.SwingConstants;
import javax.swing.JButton;

public class prueba extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					prueba frame = new prueba();
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
	public prueba() {
		setTitle("aitorugarte@opendeusto.es");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 174);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int y = (int) rect.getMaxY() - getHeight() - getHeight()/3;
        setLocation(0, y);
        
		JLabel labelTitle = new JLabel("Deusto Box Client");
		labelTitle.setBounds(5, 5, 434, 20);
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(labelTitle);
		
		JButton button = new JButton("Folder");
		button.setBounds(10, 111, 89, 23);
		contentPane.add(button);
		
		JButton btnSync = new JButton("Sync");
		btnSync.setBounds(354, 111, 80, 23);
		contentPane.add(btnSync);
		
		JButton btnSms = new JButton("Sms");
		btnSms.setBounds(255, 111, 89, 23);
		contentPane.add(btnSms);
		
		JButton btnPerfil = new JButton("Perfil");
		btnPerfil.setBounds(156, 111, 89, 23);
		contentPane.add(btnPerfil);
	}
}
