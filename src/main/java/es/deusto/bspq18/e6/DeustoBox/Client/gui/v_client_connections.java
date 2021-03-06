package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;
import es.deusto.bspq18.e6.DeustoBox.Server.dto.DConnectionDTO;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;

public class v_client_connections extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel txtMyConnections;
	@SuppressWarnings("rawtypes")
	private JList listR;
	private JButton btnBack;
	private Controller controlador;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ })
	public v_client_connections(Controller controlador) {
		setResizable(false);
		this.controlador = controlador;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 524, 317);
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		InitComponents();
		this.setVisible(true);
		setLocationRelativeTo(null);
		updateJlist();
	}

	@SuppressWarnings("rawtypes")
	public void InitComponents() {
		txtMyConnections = new JLabel();
		txtMyConnections.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtMyConnections.setHorizontalAlignment(SwingConstants.CENTER);
		txtMyConnections.setText(controlador.getResourcebundle().getString("msg_connections"));
		txtMyConnections.setBounds(25, 6, 483, 26);
		contentPane.add(txtMyConnections);

		listR = new JList();
		listR.setBounds(25, 44, 483, 187);
		getContentPane().add(listR);

		btnBack = new JButton(controlador.getResourcebundle().getString("back_btn"));
		btnBack.setBounds(199, 243, 117, 34);
		contentPane.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
	}

	public DefaultListModel<String> modelList(ArrayList<DConnectionDTO> connections) {
		DefaultListModel<String> model = new DefaultListModel<>();
		for (DConnectionDTO con : connections) {
			System.out.println(con.toString());
			model.addElement(con.toString());

		}
		return model;

	}

	@SuppressWarnings({ "unused", "unchecked" })
	public void updateJlist() {
		String email = controlador.getUserdto().getEmail();
		ArrayList<DConnectionDTO> connections = new ArrayList<DConnectionDTO>();
		connections = controlador.getConnections(email);
		listR.setModel(modelList(this.controlador.getConnections(email)));

	}
}
