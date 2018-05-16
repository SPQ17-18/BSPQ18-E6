package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DFileDTO;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DMessageDTO;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class v_messageReceived extends JFrame {

	private JPanel contentPane, panel;
	private JLabel lblNewMessage, lblFrom, lblFromtxt, lblSubjecttxt, lblSubject;
	private JTextArea txtMessage;
	private JButton btnErrase;
	private Controller controlador;
	private ArrayList<DMessageDTO> messages;
	private int position;
	private JButton btnNext;
	private JButton btnPreview;

	/**
	 * Create the frame.
	 */
	public v_messageReceived(Controller controlador) {
		//setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Joseba\\git\\BSPQ18-E6\\target\\classes\\deusto\\bspq18\\e6\\DeustoBox\\Client\\images\\logo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.controlador = controlador;
		messages = controlador.downloadMessages();
		System.out.println("Tamanio " + messages.size());
		position = 0;
		InitComponents();
		
		
	}
	
	
	public void InitComponents(){
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblFrom = new JLabel("From:");
		lblFrom.setBounds(10, 25, 46, 14);
		panel.add(lblFrom);
		
		lblSubject = new JLabel("Subject:");
		lblSubject.setBounds(10, 50, 46, 14);
		panel.add(lblSubject);
		
		lblNewMessage = new JLabel("New message");
		lblNewMessage.setBounds(10, 0, 86, 14);
		panel.add(lblNewMessage);
		
		btnErrase = new JButton("Remove");
		btnErrase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnErrase.setBounds(311, 228, 89, 23);
		panel.add(btnErrase);
		
		lblFromtxt = new JLabel("");
		lblFromtxt.setBounds(50, 25, 364, 14);
		lblFromtxt.setText(messages.get(position).getEmailfrom());
		panel.add(lblFromtxt);
		
		lblSubjecttxt = new JLabel("");
		lblSubjecttxt.setBounds(60, 50, 354, 14);
		lblSubjecttxt.setText(messages.get(position).getSubject());
		panel.add(lblSubjecttxt);
		
		txtMessage = new JTextArea();
		txtMessage.setEditable(false);
		txtMessage.setWrapStyleWord(true);
		txtMessage.setLineWrap(true);
		txtMessage.setBounds(10, 75, 404, 152);
		txtMessage.setText(messages.get(position).getText());
		panel.add(txtMessage);
		
		btnNext = new JButton("Next");
		btnNext.setBounds(164, 228, 89, 23);
		panel.add(btnNext);
		
		btnPreview = new JButton("Preview");
		btnPreview.setBounds(50, 228, 89, 23);
		panel.add(btnPreview);
	}
}
