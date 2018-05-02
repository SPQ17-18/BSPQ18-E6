package gui;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class v_browser {

	private JFrame frmInstaller;
	  private JTextField txtPath;
	 
	  /**
	   * Launch the application.
	   */
	  public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	      public void run() {
	       try {
	          v_browser window = new v_browser();
	          window.frmInstaller.setVisible(true);
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      }
	    });
	  }
	 
	  /**
	   * Create the application.
	   */
	  public v_browser() {
	    initialize();
	  }
	 
	  /**
	   * Initialize the contents of the frame.
	   */
	  private void initialize() {
	    frmInstaller = new JFrame();
	    frmInstaller.setTitle("Installer");
	    frmInstaller.setBounds(100, 100, 610, 456);
	    frmInstaller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frmInstaller.getContentPane().setLayout(null);
	         
	    txtPath = new JTextField();
	    txtPath.setBounds(15, 153, 414, 21);
	    frmInstaller.getContentPane().add(txtPath);
	    txtPath.setColumns(10);
	         
	    JButton btnBrowse = new JButton("Browse");
	    btnBrowse.setBounds(470, 153, 87, 23);
	    frmInstaller.getContentPane().add(btnBrowse);
	    
	    JButton btnCancel = new JButton("Cancel");
	    btnCancel.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		System.exit(0);
	    	}
	    });
	    btnCancel.setBounds(124, 318, 115, 29);
	    frmInstaller.getContentPane().add(btnCancel);
	    
	    JButton btnOk = new JButton("Ok");
	    btnOk.setBounds(347, 318, 115, 29);
	    frmInstaller.getContentPane().add(btnOk);
	         
	    btnBrowse.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        fileChooser.setAcceptAllFileFilterUsed(false);
	 
	        int rVal = fileChooser.showOpenDialog(null);
	        if (rVal == JFileChooser.APPROVE_OPTION) {
	          txtPath.setText(fileChooser.getSelectedFile().toString());
	          
	        }
	      }
	    });
	  }
	}
	

