package boundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MainMenu extends RootLayout {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
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
	public MainMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		updateMenuSelectionHome();
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MainMenu.class.getResource("/boundary/images/logo.png")));
		
		JButton btnNewButton_1 = new JButton("Print sales Report");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE))
					.addGap(20))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(lblNewLabel))
					.addContainerGap(58, Short.MAX_VALUE))
		);
		
		JButton btnNewButton = new JButton("Categories");
		btnNewButton.setEnabled(false);
		btnNewButton.setPreferredSize(new Dimension(100, 25));
		
		JButton btnEmployees = new JButton("Employees");
		btnEmployees.setEnabled(false);
		/*btnEmployees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrmEmployees().setVisible(true);
				JFrame f1 = (JFrame) SwingUtilities.windowForComponent(panel);
				f1.dispose();
			}
		});*/
		btnEmployees.setPreferredSize(new Dimension(100, 25));
		
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
		
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel.add(btnNewButton);
		panel.add(btnEmployees);
		panel.add(btnSuppliers);
		panel.add(btnOrders);
		panel.add(btnProducts);
		btnOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.setLayout(gl_contentPane);
	}
}
