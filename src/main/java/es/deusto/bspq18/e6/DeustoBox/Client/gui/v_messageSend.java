package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class v_messageSend extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane, panel;
	private JTextField txtReciever;
	private JTextField txtSubject;
	private JLabel lblNewMessage, lblSubject, lblTo;
	private JButton btnSend;
	private JTextArea txtMessage;
	private Controller controlador;

	/**
	 * Create the frame.
	 */
	public v_messageSend(Controller controlador) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.controlador = controlador;
		InitComponents();
		
	}
	
	public void InitComponents(){
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblTo = new JLabel("To:");
		lblTo.setBounds(10, 25, 46, 14);
		panel.add(lblTo);
		
		lblSubject = new JLabel("Subject:");
		lblSubject.setBounds(10, 50, 46, 14);
		panel.add(lblSubject);
		
		txtReciever = new JTextField();
		txtReciever.setBounds(34, 22, 380, 20);
		panel.add(txtReciever);
		txtReciever.setColumns(10);
		
		txtSubject = new JTextField();
		txtSubject.setBounds(59, 50, 355, 20);
		panel.add(txtSubject);
		txtSubject.setColumns(10);
		
		lblNewMessage = new JLabel("New message");
		lblNewMessage.setBounds(10, 0, 86, 14);
		panel.add(lblNewMessage);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnSend.setBounds(311, 228, 89, 23);
		panel.add(btnSend);

		txtMessage = new JTextArea();
		txtMessage.setWrapStyleWord(true);
		txtMessage.setLineWrap(true);
		txtMessage.setBounds(10, 75, 404, 152);
		panel.add(txtMessage);
	}

}
