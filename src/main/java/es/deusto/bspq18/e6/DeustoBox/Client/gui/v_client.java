package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class v_client extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel labelTitle, lblNmeroDeArchivos, lblMisMensajes, lblNumFiles, lblNumMSM;
	private JButton buttonFolder, btnSync, btnSms, btnLeerSms, btnPerfil;
	private Controller controlador;
	private v_client_profile myProfile;
	private v_messageSend sendMessage;
	private v_messageReceived readMessages;
	private String path;

	/**
	 * Create the frame.
	 */
	public v_client(Controller controlador, String path) {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(v_register.class.getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/logo.png")));
		this.controlador = controlador;
		setTitle(getControlador().getUserdto().getEmail());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 174);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.path = path;
		initComponents();
		this.setVisible(true);
		this.myProfile = null;
	}

	public void initComponents() {
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

		buttonFolder = new JButton("Folder");
		buttonFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Desktop desktop = Desktop.getDesktop();
			        File dirToOpen = null;
			        try {
			            dirToOpen = new File(path);
			            desktop.open(dirToOpen);
			        } catch (IllegalArgumentException | IOException iae) {
			            System.out.println("File Not Found");
			        }
			}
		});
		buttonFolder.setBounds(27, 104, 89, 30);
		contentPane.add(buttonFolder);

		btnSync = new JButton("");
		btnSync.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getControlador().getListOfUnknownFiles();
				String email = controlador.getUserdto().getEmail();
				lblNumFiles.setText(String.valueOf(controlador.getNumberOfFiles(email)));
			}

		});
		btnSync.setBounds(392, 5, 47, 44);
		URL url2 = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/sync.png");
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(url2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg2 = img2.getScaledInstance(btnSync.getWidth() / 2, btnSync.getHeight() / 2, Image.SCALE_SMOOTH);
		ImageIcon imageIcon2 = new ImageIcon(dimg2);
		btnSync.setIcon(imageIcon2);
		contentPane.add(btnSync);

		btnSms = new JButton("");
		btnSms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage = new v_messageSend(controlador);
				sendMessage.setVisible(true);
			}
		});
		btnSms.setBounds(392, 60, 47, 23);
		URL url3 = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/write.png");
		BufferedImage img3 = null;
		try {
			img3 = ImageIO.read(url3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg3 = img3.getScaledInstance(btnSync.getWidth() / 2, btnSync.getHeight() / 2, Image.SCALE_SMOOTH);
		ImageIcon imageIcon3 = new ImageIcon(dimg3);
		btnSms.setIcon(imageIcon3);
		contentPane.add(btnSms);

		btnPerfil = new JButton("");
		btnPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myProfile = new v_client_profile(controlador);
				myProfile.setVisible(true);
			}
		});
		URL url = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/perfil.png");
		BufferedImage img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(btnSms.getWidth() / 2, btnSms.getHeight() / 2, Image.SCALE_SMOOTH);

		ImageIcon imageIcon = new ImageIcon(dimg);
		btnPerfil.setIcon(imageIcon);
		btnPerfil.setBounds(5, 5, 35, 30);
		contentPane.add(btnPerfil);

		lblNmeroDeArchivos = new JLabel(controlador.getResourcebundle().getString("msg_number_files"));
		lblNmeroDeArchivos.setBounds(27, 36, 125, 20);
		contentPane.add(lblNmeroDeArchivos);

		lblMisMensajes = new JLabel(controlador.getResourcebundle().getString("msg_message"));
		lblMisMensajes.setBounds(27, 56, 101, 20);
		contentPane.add(lblMisMensajes);

		btnLeerSms = new JButton("");
		btnLeerSms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check if we have received any new messages
				int messages = 0;
				String email = controlador.getUserdto().getEmail();
				messages = controlador.getNumberOfUserMessages(email);
				// Update the label
				lblNumMSM.setText(String.valueOf(messages));
				if (messages == 0) {
					JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_empty"));
				} else {
					readMessages = new v_messageReceived(controlador);
					readMessages.setVisible(true);
				}
			}
		});
		btnLeerSms.setBounds(392, 94, 47, 40);
		URL url4 = this.getClass().getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/entry.png");
		BufferedImage img4 = null;
		try {
			img4 = ImageIO.read(url4);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg4 = img4.getScaledInstance(btnLeerSms.getWidth() / 2, btnLeerSms.getHeight() / 2, Image.SCALE_SMOOTH);

		ImageIcon imageIcon4 = new ImageIcon(dimg4);
		btnLeerSms.setIcon(imageIcon4);
		contentPane.add(btnLeerSms);

		lblNumFiles = new JLabel("");
		// Connect to the DB and check the number of files that the user have
		String email = controlador.getUserdto().getEmail();
		int files = getControlador().getNumberOfFiles(email);
		lblNumFiles.setText(String.valueOf(files));
		lblNumFiles.setBounds(174, 39, 46, 14);
		contentPane.add(lblNumFiles);

		lblNumMSM = new JLabel("");
		lblNumMSM.setText(String.valueOf(controlador.getNumberOfUserMessages(email)));
		lblNumMSM.setBounds(174, 57, 46, 14);
		contentPane.add(lblNumMSM);
	}

	public Controller getControlador() {
		return controlador;
	}

	public void setControlador(Controller controlador) {
		this.controlador = controlador;
	}
}