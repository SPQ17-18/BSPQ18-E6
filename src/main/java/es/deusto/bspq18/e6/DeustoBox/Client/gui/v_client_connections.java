package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

public class v_client_connections extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel txtMyConnections;
	private JList listR;
	private JButton btnBack;
	private Controller controlador;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public v_client_connections(Controller controlador) {
		this.controlador = controlador;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		InitComponents();
		this.setVisible(true);
		updateJlist();
	}

	public void InitComponents() {
		txtMyConnections = new JLabel();
		txtMyConnections.setText("My Connections");
		txtMyConnections.setBounds(152, 6, 130, 26);
		contentPane.add(txtMyConnections);

		listR = new JList();
		listR.setBounds(25, 44, 405, 187);
		getContentPane().add(listR);

		btnBack = new JButton("BACK");
		btnBack.setBounds(152, 243, 117, 29);
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

	public void updateJlist() {
		String email = controlador.getUserdto().getEmail();
		ArrayList<DConnectionDTO> connections = new ArrayList<DConnectionDTO>();
		connections = controlador.getConnections(email);
		listR.setModel(modelList(this.controlador.getConnections(email)));

	}
}
