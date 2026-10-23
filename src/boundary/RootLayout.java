package boundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.UIManager;

public class RootLayout extends JFrame {

	private JPanel contentPane;
	static RootLayout frame;
	private FrmOrders ordersPanel;
	private FrmEmployees mngEmployees;
	private JMenu mnHome;
	private JMenu mnOrders;
	private JMenu mnEmployees;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new RootLayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RootLayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 753, 521);
		
		
		    
		    
		createMenuBar() ;
		

	}
	
	 public void createMenuBar() {

	        JMenuBar menuBar = new JMenuBar();
	        ImageIcon icon = new ImageIcon("exit.png");

	        JMenu file = new JMenu("File");

	        file.setMnemonic(KeyEvent.VK_F);

	        JMenuItem eMenuItem = new JMenuItem("Exit", icon);
	        eMenuItem.setMnemonic(KeyEvent.VK_E);
	        eMenuItem.setToolTipText("Exit application");
	        eMenuItem.addActionListener((ActionEvent event) -> {
	            System.exit(0);
	            //System.out.println("in");
	        });

	        file.add(eMenuItem);

	        menuBar.add(file);
	    
			
	        mnHome = new JMenu("Home");	        
			menuBar.add(mnHome);	
			mnHome.addMenuListener(new MenuListener() {
				public void menuCanceled(MenuEvent e) {
				}
				public void menuDeselected(MenuEvent e) {
				}
				public void menuSelected(MenuEvent e) {
					new MainMenu().setVisible(true);
					JFrame f1 = (JFrame) SwingUtilities.windowForComponent(menuBar);
					f1.dispose();
					
					
					
				}
			});

			

			
			JMenuItem mnCategories = new JMenu("Categories");
			menuBar.add(mnCategories);
			mnCategories.setEnabled(false);
			
			JMenuItem mnSuppliers = new JMenu("Suppliers");
			menuBar.add(mnSuppliers);
			mnSuppliers.setEnabled(false);
			
			mnOrders = new JMenu("Orders");
			mnOrders.addMenuListener(new MenuListener() {
				public void menuCanceled(MenuEvent e) {
				}
				public void menuDeselected(MenuEvent e) {
				}
				public void menuSelected(MenuEvent e) {
					new FrmOrders().setVisible(true);
					JFrame f1 = (JFrame) SwingUtilities.windowForComponent(menuBar);
					f1.dispose();
					
					
					
				}
			});
			mnOrders.addActionListener((ActionEvent event) -> {
	            System.exit(0);
	           
	        });

			
			menuBar.add(mnOrders);
			
			JMenu mnProducts = new JMenu("Products");
			menuBar.add(mnProducts);
			mnProducts.setEnabled(false);
			
			mnEmployees = new JMenu("Employees");
			mnEmployees.addMenuListener(new MenuListener() {
				public void menuCanceled(MenuEvent e) {
				}
				public void menuDeselected(MenuEvent e) {
				}
				public void menuSelected(MenuEvent e) {
					mngEmployees = new FrmEmployees();
					mngEmployees.setVisible(true);
					JFrame f1 = (JFrame) SwingUtilities.windowForComponent(menuBar);
					f1.dispose();
					
				}
			});
			menuBar.add(mnEmployees);

	        setJMenuBar(menuBar);
	        

      
	        
	       
	      

	       
			
			
			
	    }
	 
	 private void changePanel(JPanel panel) {
		    getContentPane().removeAll();
			System.out.println("in");
		    getContentPane().add(panel);
			System.out.println("in");
		    getContentPane().doLayout();
		    update(getGraphics());
		}
	 
	 public void updateMenuSelectionHome() {
		 mnHome.setOpaque(true);
		 mnHome.setBackground(SystemColor.activeCaption);
	 
	 }
	 
	 public void updateMenuSelectionOrders() {
		 mnOrders.setOpaque(true);
		 mnOrders.setBackground(SystemColor.activeCaption);
		 
		 
	 }
	 
	 public void updateMenuSelectionEmployee() {
		 mnEmployees.setOpaque(true);
		 mnEmployees.setBackground(SystemColor.activeCaption);
		 
		 
	 }
	 
	 
   


}
