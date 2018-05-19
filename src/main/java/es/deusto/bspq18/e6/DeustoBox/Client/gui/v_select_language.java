package es.deusto.bspq18.e6.DeustoBox.Client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.bspq18.e6.DeustoBox.Client.controller.Controller;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class v_select_language extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<?> comboBox;
	private JLabel lblSelectLanguage;
	private String[] args;

	public static void main(String[] args) {
		
		new v_select_language(args);
		
	}

	/**
	 * Create the frame.
	 */
	public v_select_language(String[] args) {
		this.args = args;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Initialize();
		this.setVisible(true);
	}
	public void Initialize(){
		String[] languages = { "Spanish", "English", "French"};
		comboBox = new JComboBox(languages);
		comboBox.setBounds(139, 107, 134, 20);
		contentPane.add(comboBox);
		
		lblSelectLanguage = new JLabel("SELECT LANGUAGE");
		lblSelectLanguage.setBounds(139, 35, 190, 39);
		contentPane.add(lblSelectLanguage);
		
		JButton btnEnterDeustobox = new JButton("ENTER DEUSTOBOX");
		btnEnterDeustobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex() ==0){
					try {
						new Controller(args, 0);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else if (comboBox.getSelectedIndex() == 1){
					try {
						new Controller(args, 1);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if (comboBox.getSelectedIndex() == 2){
					try {
						new Controller(args, 2);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnEnterDeustobox.setBounds(139, 179, 134, 23);
		contentPane.add(btnEnterDeustobox);
	}
}
