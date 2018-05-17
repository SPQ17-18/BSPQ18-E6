package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DMessageDTO;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class v_messageReceived extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		// setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Joseba\\git\\BSPQ18-E6\\target\\classes\\deusto\\bspq18\\e6\\DeustoBox\\Client\\images\\logo.png"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.controlador = controlador;
		String email = controlador.getUserdto().getEmail();
		messages = controlador.downloadMessages(email);
		position = 0;
		InitComponents();

	}

	public void InitComponents() {
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		lblFrom = new JLabel(controlador.getResourcebundle().getString("msg_from"));
		lblFrom.setBounds(10, 25, 46, 14);
		panel.add(lblFrom);

		lblSubject = new JLabel(controlador.getResourcebundle().getString("msg_subject"));
		lblSubject.setBounds(10, 50, 46, 14);
		panel.add(lblSubject);

		lblNewMessage = new JLabel(controlador.getResourcebundle().getString("msg_new_message"));
		lblNewMessage.setBounds(10, 0, 86, 14);
		panel.add(lblNewMessage);

		btnErrase = new JButton(controlador.getResourcebundle().getString("remove_btn"));
		btnErrase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Comprobamos si hay mas mensajes o no
				if (messages.size() == 1) {
					// Pedimos a la BD que se borre
					controlador.deleteMessage(messages.get(0).getMessageId());
					JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_no_messages"));
					dispose();
				} else {
					// Pedimos a la BD que lo borre
					controlador.deleteMessage(messages.get(position).getMessageId());
					messages.remove(position);
					if (position > 0)
						position--;
					JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_delete_message"));
					UpdateMessage();
				}

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

		btnNext = new JButton(controlador.getResourcebundle().getString("next_btn"));
		btnNext.setBounds(164, 228, 89, 23);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (position < messages.size() - 1) {
					position++;
					UpdateMessage();
				} else {
					JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_last"));

				}

			}
		});
		panel.add(btnNext);

		btnPreview = new JButton("Preview");
		btnPreview.setBounds(50, 228, 89, 23);

		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (position > 0) {
					position--;
					UpdateMessage();
				} else {
					JOptionPane.showMessageDialog(null, controlador.getResourcebundle().getString("msg_first"));

				}

			}
		});
		panel.add(btnPreview);
	}

	public void UpdateMessage() {
		txtMessage.setText(messages.get(position).getText());
		panel.add(txtMessage);
		lblSubjecttxt.setText(messages.get(position).getSubject());
		panel.add(lblSubjecttxt);
		lblFromtxt.setText(messages.get(position).getEmailfrom());
		panel.add(lblFromtxt);
	}
}
