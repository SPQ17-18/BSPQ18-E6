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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.Font;
import java.awt.Toolkit;

public class v_select_language extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<?> comboBox;
	private JLabel lblSelectLanguage;
	private String[] args;

	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}
		new v_select_language(args);
	}

	/**
	 * Create the frame.
	 */
	public v_select_language(String[] args) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(v_login.class.getResource("/es/deusto/bspq18/e6/DeustoBox/Client/images/logo.png")));
		setResizable(false);
		this.args = args;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 237, 186);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Initialize();
		setLocationRelativeTo(null);
		this.setVisible(true);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void Initialize(){
		String[] languages = { "Spanish", "English", "French"};
		comboBox = new JComboBox(languages);
		comboBox.setBounds(50, 61, 134, 29);
		contentPane.add(comboBox);
		
		lblSelectLanguage = new JLabel(" DeustoBox Client");
		lblSelectLanguage.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSelectLanguage.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectLanguage.setBounds(10, 11, 211, 39);
		contentPane.add(lblSelectLanguage);
		
		JButton btnEnterDeustobox = new JButton("Enter");
		btnEnterDeustobox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEnterDeustobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex() ==0){
					try {
						new Controller(args, 0);
						dispose();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
				else if (comboBox.getSelectedIndex() == 1){
					try {
						new Controller(args, 1);
						dispose();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
				else if (comboBox.getSelectedIndex() == 2){
					try {
						new Controller(args, 2);
						dispose();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnEnterDeustobox.setBounds(50, 101, 134, 32);
		contentPane.add(btnEnterDeustobox);
	}
}
