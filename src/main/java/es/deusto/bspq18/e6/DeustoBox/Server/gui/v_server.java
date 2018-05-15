package es.deusto.bspq18.e6.DeustoBox.Server.gui;

import java.awt.Desktop;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;

public class v_server extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblDeustoBoxServer, lblLogo;
	private JButton btnNewButton, btnFolder;
	
	/**
	 * Create the frame.
	 */
	public v_server(String path) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(v_server.class.getResource("/es/deusto/bspq18/e6/DeustoBox/Server/images/logo.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 131);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - getWidth();
        int y = (int) rect.getMaxY() - getHeight() - getHeight()/3;
        setLocation(x, y);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblDeustoBoxServer = new JLabel("Deusto Box Server");
		lblDeustoBoxServer.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDeustoBoxServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeustoBoxServer.setBounds(10, 11, 414, 22);
		contentPane.add(lblDeustoBoxServer);
		
		btnNewButton = new JButton("Desconectar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(293, 58, 117, 23);
		contentPane.add(btnNewButton);
		
		btnFolder = new JButton("Folder");
		btnFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Desktop desktop = Desktop.getDesktop();
			        File dirToOpen = null;
			        try {
			            dirToOpen = new File("D:\\aitor\\Escritorio\\Deusto-Box\\aitorugarte@opendeusto.es");
			            desktop.open(dirToOpen);
			        } catch (IllegalArgumentException | IOException iae) {
			            System.out.println("File Not Found");
			        }
			}
		});
		btnFolder.setBounds(22, 58, 89, 23);
		contentPane.add(btnFolder);
		
		lblLogo = new JLabel("New label");
		lblLogo.setBounds(345, 11, 89, 38);
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
		contentPane.add(lblLogo);
	}
}