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

public class v_client extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller controlador;
	private JLabel lblUsername;
	private JLabel lblUser;
	private JLabel lblNumberOfFiles;
	private JLabel lblNumber;
	private JButton SyncFiles;
	private JButton btnMyProfile;
	private v_client_profile myProfile;
	private v_messageSend sendMessage;
	private v_messageReceived readMessages;
	private JLabel lblMessages;
	private JLabel lblNumberOfMessages;
	private JButton btnWriteAMessage;
	private JButton btnMyMessages;
	/**
	 * Create the frame.
	 */
	public v_client(Controller controlador) {
		setResizable(false);
		this.controlador = controlador;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		InitComponents();
		this.setVisible(true);
		this.myProfile = null;
		
	}
		
		public void InitComponents(){
		lblUsername = new JLabel(controlador.getResourcebundle().getBundle("msg_username").toString());
		lblUsername.setBounds(10, 11, 76, 14);
		contentPane.add(lblUsername);
		
		lblUser = new JLabel(getControlador().getUserdto().getEmail());
		lblUser.setBounds(208, 11, 140, 14);
		contentPane.add(lblUser);
		
		lblNumberOfFiles = new JLabel(controlador.getResourcebundle().getBundle("msg_number_files").toString());
		lblNumberOfFiles.setBounds(10, 48, 163, 14);
		contentPane.add(lblNumberOfFiles);
		
		
		lblNumber = new JLabel("");
		//Connect to the DB and check the number of files that the user have
		String email = controlador.getUserdto().getEmail();
		int files = getControlador().getNumberOfFiles(email);
		lblNumber.setText(String.valueOf(files));
		lblNumber.setBounds(208, 48, 46, 14);
		contentPane.add(lblNumber);
		
		SyncFiles = new JButton(controlador.getResourcebundle().getBundle("msg_sync_files").toString());
		SyncFiles.setBounds(10, 145, 89, 23);
		contentPane.add(SyncFiles);
		
		btnMyProfile = new JButton(controlador.getResourcebundle().getBundle("msg_profile").toString());
		btnMyProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myProfile = new v_client_profile(controlador);
				myProfile.setVisible(true);
				
			}
		});
		btnMyProfile.setBounds(168, 145, 117, 23);
		contentPane.add(btnMyProfile);
		
		lblMessages = new JLabel(controlador.getResourcebundle().getBundle("msg_message").toString());
		lblMessages.setBounds(10, 89, 89, 14);
		contentPane.add(lblMessages);
		
		lblNumberOfMessages = new JLabel(" ");
		lblNumberOfMessages.setBounds(208, 89, 46, 14);
		contentPane.add(lblNumberOfMessages);
		lblNumberOfMessages.setText(String.valueOf(controlador.getNumberOfUserMessages(email)));
		
		btnWriteAMessage = new JButton(controlador.getResourcebundle().getBundle("msg_write").toString());
		btnWriteAMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage = new v_messageSend(controlador);
				sendMessage.setVisible(true);
				
			}
		});
		btnWriteAMessage.setBounds(10, 201, 113, 23);
		contentPane.add(btnWriteAMessage);
		
		btnMyMessages = new JButton(controlador.getResourcebundle().getBundle("msg_message").toString());
		btnMyMessages.setBounds(168, 201, 117, 23);
		contentPane.add(btnMyMessages);
		btnMyMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Check if we have received any new messages
				int messages = 0;
				String email = controlador.getUserdto().getEmail();
					messages = controlador.getNumberOfUserMessages(email);
				//Update the label
				lblNumberOfMessages.setText(String.valueOf(messages));
				if(messages ==0){
					JOptionPane.showMessageDialog(null,controlador.getResourcebundle().getBundle("msg_empty").toString());
				}
				else{
					readMessages = new v_messageReceived(controlador);
					readMessages.setVisible(true);
				}
				
				
			}
		});
		
		
		SyncFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getControlador().getListOfUnknownFiles();
				String email = controlador.getUserdto().getEmail();
				lblNumber.setText(String.valueOf(controlador.getNumberOfFiles(email)));
			}
		});
	}
	
	public Controller getControlador() {
		return controlador;
	}
	public void setControlador(Controller controlador) {
		this.controlador = controlador;
	}
}
