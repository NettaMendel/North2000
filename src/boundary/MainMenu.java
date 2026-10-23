package boundary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import control.CustomerLogic;

public class MainMenu extends RootLayout {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		updateMenuSelectionHome();
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MainMenu.class.getResource("/boundary/images/logo.png")));
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton btnNewButton = new JButton("Categories");
		btnNewButton.setEnabled(false);
		btnNewButton.setPreferredSize(new Dimension(100, 25));
		
		JButton btnEmployees = new JButton("Employees");
		btnEmployees.setPreferredSize(new Dimension(100, 25));
		btnEmployees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrmEmployees().setVisible(true);
				JFrame f1 = (JFrame) SwingUtilities.windowForComponent(panel);
				f1.dispose();
			}
		});
		
		JButton btnSuppliers = new JButton("Suppliers");
		btnSuppliers.setEnabled(false);
		btnSuppliers.setPreferredSize(new Dimension(100, 25));
		
		JButton btnOrders = new JButton("Orders");
		btnOrders.setPreferredSize(new Dimension(100, 25));
		btnOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrmOrders().setVisible(true);
				JFrame f1 = (JFrame) SwingUtilities.windowForComponent(panel);
				f1.dispose();
			}
		});
		
		JButton btnProducts = new JButton("Products");
		btnProducts.setEnabled(false);
		btnProducts.setPreferredSize(new Dimension(100, 25));
		
		panel.add(btnNewButton);
		panel.add(btnEmployees);
		panel.add(btnSuppliers);
		panel.add(btnOrders);
		panel.add(btnProducts);

		JButton btnReports = new JButton("Reports Menu");
		btnReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmReportMenu menu = new FrmReportMenu();
				menu.setLocationRelativeTo(null);
				menu.setVisible(true);
			}
		});

		JPanel panelDataTransfer = new JPanel();
		panelDataTransfer.setBorder(new TitledBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY), "Customer Data (XML / JSON)"));
		panelDataTransfer.setLayout(new GridLayout(2, 2, 5, 5));

		JButton btnExportJson = new JButton("Export JSON");
		btnExportJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CustomerLogic.getInstance().exportCustomersToJSON();
					JOptionPane.showMessageDialog(MainMenu.this, 
						"Customers successfully exported to JSON (json/customers.json)!", 
						"Export Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(MainMenu.this, 
						"Error exporting to JSON: " + ex.getMessage(), 
						"Export Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton btnImportJson = new JButton("Import JSON");
		btnImportJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CustomerLogic.getInstance().importCustomersFromJSON("json/customers.json");
					JOptionPane.showMessageDialog(MainMenu.this, 
						"Customers successfully imported from JSON!", 
						"Import Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(MainMenu.this, 
						"Error importing from JSON: " + ex.getMessage(), 
						"Import Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton btnExportXml = new JButton("Export XML");
		btnExportXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CustomerLogic.getInstance().exportCustomersToXML();
					JOptionPane.showMessageDialog(MainMenu.this, 
						"Customers successfully exported to XML (xml/customers.xml)!", 
						"Export Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(MainMenu.this, 
						"Error exporting to XML: " + ex.getMessage(), 
						"Export Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton btnImportXml = new JButton("Import XML");
		btnImportXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CustomerLogic.getInstance().importCustomersFromXML("xml/customers.xml");
					JOptionPane.showMessageDialog(MainMenu.this, 
						"Customers successfully imported from XML!", 
						"Import Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(MainMenu.this, 
						"Error importing from XML: " + ex.getMessage(), 
						"Import Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		panelDataTransfer.add(btnExportJson);
		panelDataTransfer.add(btnImportJson);
		panelDataTransfer.add(btnExportXml);
		panelDataTransfer.add(btnImportXml);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnReports, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
						.addComponent(panelDataTransfer, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
					.addGap(20))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnReports, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelDataTransfer, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
