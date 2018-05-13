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

public class v_messageReceived extends JFrame {

	private JPanel contentPane, panel;
	private JLabel lblNewMessage, lblFrom, lblFromtxt, lblSubjecttxt, lblSubject;
	private JTextArea txtMessage;
	private JButton btnErrase;

	/**
	 * Create the frame.
	 */
	public v_messageReceived() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Joseba\\git\\BSPQ18-E6\\target\\classes\\deusto\\bspq18\\e6\\DeustoBox\\Client\\images\\logo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
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
		
		btnErrase = new JButton("Errase");
		btnErrase.setBounds(311, 228, 89, 23);
		panel.add(btnErrase);
		
		lblFromtxt = new JLabel("");
		lblFromtxt.setBounds(50, 25, 364, 14);
		panel.add(lblFromtxt);
		
		lblSubjecttxt = new JLabel("");
		lblSubjecttxt.setBounds(60, 50, 354, 14);
		panel.add(lblSubjecttxt);
		
		txtMessage = new JTextArea();
		txtMessage.setEditable(false);
		txtMessage.setWrapStyleWord(true);
		txtMessage.setLineWrap(true);
		txtMessage.setBounds(10, 75, 404, 152);
		panel.add(txtMessage);
	}

}
