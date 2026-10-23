package boundary;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import control.ProductLogic;
import control.OrderLogic;
import entity.Product;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridLayout;
import javax.swing.JSeparator;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;

public class FrmReportMenu extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6870528587630075141L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FrmReportMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnCustomerOrdersReport = new JButton("Customer Orders Report");
		
		btnCustomerOrdersReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderLogic.getInstance()
										   .compileCustomerOrdersReport()
										   .setVisible(true);
			}
		});
		
		contentPane.add(btnCustomerOrdersReport);
		
		JSeparator separator = new JSeparator();
		contentPane.add(separator);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		JLabel lblProduct = new JLabel("Product:");
		lblProduct.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblProduct.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JComboBox<Product> cbProduct = new JComboBox<Product>();
		cbProduct.setModel(new DefaultComboBoxModel(ProductLogic.getInstance().getProducts().toArray()));
		
		JButton btnProductOrdersByCountry = new JButton("Product Orders By Country");
		btnProductOrdersByCountry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbProduct.getItemCount() == 0)
					return; // replace with error dialog!
				final Product product = (Product) cbProduct.getSelectedItem();
				OrderLogic.getInstance()
						.compileProductOrdersByCountryReport(product.getProductID(), product.getProductName())
						.setVisible(true);
			}
		});
		contentPane.add(btnProductOrdersByCountry);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblProduct)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cbProduct, 0, 135, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(56, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblProduct)
						.addComponent(cbProduct, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(55))
		);
		panel.setLayout(gl_panel);
	}
}
