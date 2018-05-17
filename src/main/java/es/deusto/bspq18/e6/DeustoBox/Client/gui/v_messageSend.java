package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
				if(!txtReciever.getText().equals("")){
					if(!txtSubject.getText().equals("")){
						if(!txtMessage.getText().equals("")){
							String email = controlador.getUserdto().getEmail();
							boolean correct = controlador.addMessage(email,txtReciever.getText(), txtSubject.getText(), txtMessage.getText());
							if(correct){
								JOptionPane.showMessageDialog(null, "The message to " + txtReciever.getText() + " has been sent correctly");
								dispose();
							}else{
								JOptionPane.showMessageDialog(null, "An error has happened when sending the message");
							}
							
						}
						else{
							JOptionPane.showMessageDialog(null, "The message cannot be empty");
						}
							
					} else{
						JOptionPane.showMessageDialog(null, "The subject cannot be empty");
					}
				} else{
					JOptionPane.showMessageDialog(null, "The receiver cannot be empty");
				}
				
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
