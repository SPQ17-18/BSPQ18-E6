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
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
		button.setBounds(27, 111, 89, 23);
		contentPane.add(button);
		
		JButton btnSync = new JButton("");
		btnSync.setBounds(392, 5, 47, 44);
		URL url2 = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/sync.png");
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(url2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg2 = img2.getScaledInstance(btnSync.getWidth()/2, btnSync.getHeight()/2,
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon2 = new ImageIcon(dimg2);
		btnSync.setIcon(imageIcon2);
		contentPane.add(btnSync);
		
		JButton btnSms = new JButton("");
		btnSms.setBounds(392, 60, 47, 23);
		URL url3 = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/write.png");
		BufferedImage img3 = null;
		try {
			img3 = ImageIO.read(url3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg3 = img3.getScaledInstance(btnSync.getWidth()/2, btnSync.getHeight()/2,
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon3 = new ImageIcon(dimg3);
		btnSms.setIcon(imageIcon3);
		contentPane.add(btnSms);
		
		JButton btnPerfil = new JButton("");
		URL url = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/perfil.png");
		BufferedImage img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(btnSms.getWidth()/2, btnSms.getHeight()/2,
		        Image.SCALE_SMOOTH);
		
		ImageIcon imageIcon = new ImageIcon(dimg);
		btnPerfil.setIcon(imageIcon);
		btnPerfil.setBounds(5, 5, 35, 30);
		contentPane.add(btnPerfil);
		
		JLabel lblNmeroDeArchivos = new JLabel("Número de archivos: ");
		lblNmeroDeArchivos.setBounds(27, 39, 125, 20);
		contentPane.add(lblNmeroDeArchivos);
		
		JLabel lblMisMensajes = new JLabel("Mis mensajes:");
		lblMisMensajes.setBounds(27, 59, 101, 20);
		contentPane.add(lblMisMensajes);
		
		JButton btnLeerSms = new JButton("Leer sms");
		btnLeerSms.setBounds(350, 94, 89, 23);
		contentPane.add(btnLeerSms);
	}
}
