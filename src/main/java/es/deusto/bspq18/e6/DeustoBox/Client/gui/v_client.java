package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class v_client extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller controlador;
	private JLabel lblNumber;
	private JButton SyncFiles;
	private JButton btnMyProfile;
	private v_client_profile myProfile;
	private v_messageSend sendMessage;
	private v_messageReceived readMessages;
	private JButton btnWriteAMessage;
	private JLabel labelTitle;
	private JButton button;
	private JLabel lblNmeroDeArchivos;
	private JLabel lblMisMensajes;
	private JButton btnMyMessages;

	/**
	 * Create the frame.
	 */
	public v_client(Controller controlador) {
		controlador = this.controlador;
		setTitle(controlador.getUserdto().getEmail());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 174);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	public void Initialize() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int y = (int) rect.getMaxY() - getHeight() - getHeight() / 3;
		setLocation(0, y);

		labelTitle = new JLabel("Deusto Box Client");
		labelTitle.setBounds(5, 5, 434, 20);
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(labelTitle);

		button = new JButton("Folder");
		button.setBounds(27, 111, 89, 23);
		contentPane.add(button);

		SyncFiles = new JButton("");
		SyncFiles.setBounds(392, 5, 47, 44);
		URL url2 = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/sync.png");
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(url2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image dimg2 = img2.getScaledInstance(SyncFiles.getWidth() / 2, SyncFiles.getHeight() / 2, Image.SCALE_SMOOTH);
		ImageIcon imageIcon2 = new ImageIcon(dimg2);
		SyncFiles.setIcon(imageIcon2);
		SyncFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getControlador().getListOfUnknownFiles();
				String email = controlador.getUserdto().getEmail();
				lblNumber.setText(String.valueOf(controlador.getNumberOfFiles(email)));
			}
		});

		contentPane.add(SyncFiles);

		btnWriteAMessage = new JButton("");
		btnWriteAMessage.setBounds(392, 60, 47, 23);
		URL url3 = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/write.png");
		BufferedImage img3 = null;
		try {
			img3 = ImageIO.read(url3);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image dimg3 = img3.getScaledInstance(SyncFiles.getWidth() / 2, SyncFiles.getHeight() / 2, Image.SCALE_SMOOTH);
		ImageIcon imageIcon3 = new ImageIcon(dimg3);
		btnWriteAMessage.setIcon(imageIcon3);
		contentPane.add(btnWriteAMessage);

		SyncFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getControlador().getListOfUnknownFiles();
				String email = controlador.getUserdto().getEmail();
				lblNumber.setText(String.valueOf(controlador.getNumberOfFiles(email)));
			}
		});

		btnMyProfile = new JButton("");
		URL url = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/perfil.png");
		BufferedImage img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(btnWriteAMessage.getWidth() / 2, btnWriteAMessage.getHeight() / 2,
				Image.SCALE_SMOOTH);

		btnWriteAMessage = new JButton(controlador.getResourcebundle().getString("msg_write"));
		btnWriteAMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage = new v_messageSend(controlador);
				sendMessage.setVisible(true);

			}
		});
		contentPane.add(btnWriteAMessage);

		ImageIcon imageIcon = new ImageIcon(dimg);
		btnMyProfile.setIcon(imageIcon);
		btnMyProfile.setBounds(5, 5, 35, 30);
		btnMyProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myProfile = new v_client_profile(controlador);
				myProfile.setVisible(true);

			}
		});
		contentPane.add(btnMyProfile);

		lblNmeroDeArchivos = new JLabel("Número de archivos: ");
		lblNmeroDeArchivos.setBounds(27, 39, 125, 20);
		contentPane.add(lblNmeroDeArchivos);

		lblMisMensajes = new JLabel("Mis mensajes:");
		lblMisMensajes.setBounds(27, 59, 101, 20);
		contentPane.add(lblMisMensajes);

		btnMyMessages = new JButton("");
		btnMyMessages.setBounds(392, 94, 47, 40);
		URL url4 = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/entry.png");
		BufferedImage img4 = null;
		try {
			img4 = ImageIO.read(url4);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg4 = img4.getScaledInstance(btnMyMessages.getWidth() / 2, btnMyMessages.getHeight() / 2, Image.SCALE_SMOOTH);

		ImageIcon imageIcon4 = new ImageIcon(dimg4);
		btnMyMessages.setIcon(imageIcon4);
		btnMyMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Check if we have received any new messages
				int messages = 0;
				String email = controlador.getUserdto().getEmail();
					messages = controlador.getNumberOfUserMessages(email);
				//Update the label
				lblMisMensajes.setText(String.valueOf(messages));
				if(messages ==0){
					JOptionPane.showMessageDialog(null,controlador.getResourcebundle().getString("msg_empty") );
				}
				else{
					readMessages = new v_messageReceived(controlador);
					readMessages.setVisible(true);
				}
				
				
			}
});
		contentPane.add(btnMyMessages);
	}

	public Controller getControlador() {
		return controlador;
	}

	public void setControlador(Controller controlador) {
		this.controlador = controlador;
	}

}
