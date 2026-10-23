package boundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.toedter.calendar.JDateChooser;

import entity.Employee;
import entity.Order;
import entity.Shipper;
import entity.Customer;
import control.CustomerLogic;
import control.EmployeeLogic;
import control.OrderLogic;
import util.JTextFieldLimiter;
import javax.swing.JComboBox;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.ListSelectionModel;
import java.awt.SystemColor;




public class FrmOrders extends RootLayout {

	private ArrayList<entity.Order> ordersArray;
	ArrayList<Customer> customers = new ArrayList<Customer>();
	ArrayList<Employee> employeeArray = new ArrayList<Employee>();
	ArrayList<Shipper> shippersArray = new ArrayList<Shipper>();
    private Integer currentOrder;
    private boolean inAddMode;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmOrders frame = new FrmOrders();
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
	public FrmOrders() {


		initComponents();
		fetchAndRefresh();
		createEvents();


	}

	private void initComponents() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 753, 521);
		contentPane = new JPanel();
		updateMenuSelectionOrders();
		contentPane.setForeground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//setIconImage(Toolkit.getDefaultToolkit().getImage(RootLayout.class.getResource("/boundary/images/logo.png")));
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmEmployees.class.getResource("/boundary/images/logo.png")));
		setTitle("Order Details");
		contentPane.setLayout(null);
		pnlEmpDetails.setBounds(21, 11, 345, 171);
		pnlEmpDetails.setBorder(null);
		contentPane.add(pnlEmpDetails);
		tfOrderID = new JTextField();
		tfOrderID.setBounds(105, 11, 160, 20);
		tfOrderID.setEditable(false);
		tfOrderID.setColumns(10);


		tfCustomerID = new JTextField();
		tfCustomerID.setColumns(20);

		tfCustomerID.setBounds(105, 37, 48, 20);

		tfEmployeeID = new JTextField();

		tfEmployeeID.setBounds(105, 63, 48, 20);
		tfEmployeeID.setColumns(10);
		pnlEmpContact.setBounds(370, 11, 355, 156);
		contentPane.add(pnlEmpContact);



        tfEmployeeID.setDocument(new JTextFieldLimiter(Employee.MAX_FNAME));
 		pnlActionBtn = new JPanel();
 		pnlActionBtn.setBounds(21, 400, 607, 33);
		tfNavigation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfNavigation.setText("showing X of Y");
		tfNavigation.setColumns(6);
		contentPane.add(pnlActionBtn);
		pnlActionBtn.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		btnFirst.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnFirst.setHorizontalAlignment(SwingConstants.LEFT);
		pnlActionBtn.add(btnFirst);
		btnPrev.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pnlActionBtn.add(btnPrev);
		pnlActionBtn.add(tfNavigation);
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pnlActionBtn.add(btnNext);
		btnLast.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pnlActionBtn.add(btnLast);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 10));
        tfSearch = new JTextField();
        tfSearch.setText("Search");
        pnlActionBtn.add(tfSearch);
        tfSearch.setColumns(10);
        pnlActionBtn.add(btnSave);
        btnSave.setEnabled(inAddMode);

        btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 10));

        btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 10));
        pnlActionBtn.add(btnRemove);
        btnRemove.setEnabled(!inAddMode);
        pnlActionBtn.add(btnAdd);
        btnAdd.setEnabled(!inAddMode);


        initSelectCustomers();
        initSelectEmployee();
        initSelectShipVia();


        initGroupLayoutleftPanel();
        initGroupLayoutRightPanel();

        panel = new FrmOrderDetailsInternal();
        panel.setForeground(SystemColor.control);
        panel.setBackground(SystemColor.activeCaptionBorder);
        panel.setBounds(31, 193, 515, 171);
        contentPane.add(panel);

		}

	private void createEvents() {
		 btnRemove.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		btnRemoveOnClick(e);
	        	}
	        });
		tfCustomerID.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {	updateChangedField();	}
			public void keyReleased(KeyEvent arg0) {

				tfCustomerID.setText(tfCustomerID.getText().toUpperCase());
				int customerByIdIndex = customers.indexOf(new Customer(tfCustomerID.getText().toUpperCase()));
				if(customerByIdIndex < customers.size() && customerByIdIndex >= 0 ){
					cbCustomerName.getModel().setSelectedItem(customers.get(customerByIdIndex));
				}
				else{cbCustomerName.getModel().setSelectedItem(null);}
			}
		});
		cbEmployeeName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshEmpIdOnNameChange(e);
				updateChangedField();
			}
		});
		cbCustomerName.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		refreshCustomerIdOnNameChange(e);
        		updateChangedField();
         	}
        });
		cbShipViaName.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		refreshtfShipViaIDOnNameChange(e);
        		updateChangedField();
         	}
        });



		tfShipViaID.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				int index=-1;
				try{
					long shipViaID = Long.valueOf(tfShipViaID.getText());
					index = shippersArray.indexOf(new Shipper(shipViaID));

					}
				catch(NumberFormatException e){
						e.printStackTrace();
					}

				if(index < shippersArray.size() && index >= 0 ){
					cbShipViaName.getModel().setSelectedItem(shippersArray.get(index));
				}
				else{cbShipViaName.getModel().setSelectedItem(null);}
			}
		});

		tfEmployeeID.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				int employeeByIdIndex = -1;
				try
				{
					employeeByIdIndex = employeeArray.indexOf(new Employee(Long.valueOf(tfEmployeeID.getText())));

				}
				catch(NumberFormatException e){e.printStackTrace();}

				if(employeeByIdIndex < employeeArray.size() && employeeByIdIndex >= 0 ){
					cbEmployeeName.getModel().setSelectedItem(employeeArray.get(employeeByIdIndex));
				}
				else{cbEmployeeName.getModel().setSelectedItem(null);}
			}
		});
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFirstOnClick(e);
			}
		});
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLastOnClick(e);
			}
		});
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnPrevOnClick(evt);
		}
		});
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				 btnNextOnClick(evt);
			}

		});
		dtcOrderDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				updateChangedField();
			}
		});
		dtcRequiredDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				updateChangedField();
			}
		});
		dtcShippedDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				updateChangedField();
			}
		});

		tfShipAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				updateChangedField();
			}
		});

		tfShipCity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				updateChangedField();
			}
		});
		tfCountry.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				updateChangedField();
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveOnClick(e);
			}
		});
		btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		btnAddOnClick(e);
        	}
        });

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// This method initializing the design structure
	// of form right side fields
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void initGroupLayoutRightPanel() {
			pnlEmpContact.setLayout(null);

			JLabel lblShipVia = new JLabel("Ship Via:");
			lblShipVia.setBounds(10, 14, 54, 14);
			pnlEmpContact.add(lblShipVia);

			tfShipViaID = new JTextField();
			tfShipViaID.setColumns(3);
			tfShipViaID.setBounds(84, 11, 37, 20);
			pnlEmpContact.add(tfShipViaID);


			cbShipViaName.setBounds(131, 11, 156, 20);
			pnlEmpContact.add(cbShipViaName);

			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Address", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(0, 39, 347, 112);
			pnlEmpContact.add(panel);
			panel.setLayout(null);

			JLabel lblShipAddress = new JLabel("Ship Address:");
			lblShipAddress.setBounds(10, 24, 79, 14);
			panel.add(lblShipAddress);


			tfShipAddress.setBounds(99, 21, 238, 20);
			tfShipAddress.setColumns(3);
			panel.add(tfShipAddress);

			JLabel lblShipCity = new JLabel("Ship City:");
			lblShipCity.setBounds(10, 51, 79, 14);
			panel.add(lblShipCity);

			tfShipCity = new JTextField();
			tfShipCity.setColumns(3);
			tfShipCity.setBounds(99, 48, 238, 20);
			panel.add(tfShipCity);

			JLabel lblShipCountry = new JLabel("Ship Country:");
			lblShipCountry.setBounds(10, 79, 79, 14);
			panel.add(lblShipCountry);

			tfCountry = new JTextField();
			tfCountry.setColumns(3);
			tfCountry.setBounds(99, 76, 238, 20);
			panel.add(tfCountry);

	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// This method initializing the design structure
	// of form left fields
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void initGroupLayoutleftPanel() {
			pnlEmpDetails.setLayout(null);
			lblOrderID.setBounds(10, 14, 90, 14);
			pnlEmpDetails.add(lblOrderID);
			lblOrderDate.setBounds(10, 91, 90, 14);
			pnlEmpDetails.add(lblOrderDate);
			lblCustomer.setBounds(10, 40, 90, 14);
			pnlEmpDetails.add(lblCustomer);
			lblEmployeeID.setBounds(10, 66, 90, 14);
			pnlEmpDetails.add(lblEmployeeID);
			lblRequiredDate.setBounds(10, 117, 90, 14);
			pnlEmpDetails.add(lblRequiredDate);
			dtcRequiredDate.setBounds(105, 117, 160, 20);
			pnlEmpDetails.add(dtcRequiredDate);
			dtcOrderDate.setBounds(105, 91, 160, 20);
			pnlEmpDetails.add(dtcOrderDate);
			pnlEmpDetails.add(tfEmployeeID);
			pnlEmpDetails.add(tfCustomerID);
			pnlEmpDetails.add(tfOrderID);
			cbCustomerName.setBounds(158, 37, 175, 20);
			pnlEmpDetails.add(cbCustomerName);

			cbEmployeeName.setBounds(158, 63, 175, 20);
			pnlEmpDetails.add(cbEmployeeName);
			lblShippedDate.setBounds(10, 144, 90, 14);
			pnlEmpDetails.add(lblShippedDate);
			dtcShippedDate = new JDateChooser();
			dtcShippedDate.setBounds(105, 142, 160, 20);
			pnlEmpDetails.add(dtcShippedDate);


	}


	private void initSelectCustomers()
	{
		customers = CustomerLogic.getInstance().getCustomers();
		Vector<Customer> model = new Vector<>(customers);
        cbCustomerName = new JComboBox( model );
        cbCustomerName.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}

	private void initSelectEmployee()
	{
		employeeArray = EmployeeLogic.getInstance().getEmployees();
		Vector<Employee> model = new Vector<>(employeeArray);
		cbEmployeeName = new JComboBox( model );
		cbEmployeeName.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}

	private void initSelectShipVia()
	{
		shippersArray = OrderLogic.getInstance().getshippers();
		Vector<Shipper> model = new Vector<>(shippersArray);
		cbShipViaName = new JComboBox( model );
		cbShipViaName.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// fetches primary form data - orders , and refreshes controls.
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void fetchAndRefresh() {
		ordersArray = OrderLogic.getInstance().getOrders();
		currentOrder = (!ordersArray.isEmpty()) ? 1 : null;
		inAddMode = (ordersArray == null);
		refreshControls();
	}

	   private void refreshControls() {
	        refreshNavigation();
	        refreshOrderFields();
	        refreshDataButtons();

	    }
	   private void refreshNavigation() {
	        tfNavigation.setText((!inAddMode) ?
	                "" + currentOrder + " of " + ordersArray.size() :
	                "" + (ordersArray.size() + 1) + " of " + (ordersArray.size() + 1));

	        btnFirst.setEnabled(currentOrder != null && currentOrder > 1);
	        btnPrev.setEnabled(currentOrder != null && currentOrder > 1);
	        btnNext.setEnabled(currentOrder != null && currentOrder < ordersArray.size());
	        btnLast.setEnabled(currentOrder != null && currentOrder < ordersArray.size());
	    }
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Updates the various order manipulation buttons,
	// according to form state
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	   private void refreshDataButtons() {
	    	 btnSave.setEnabled(inAddMode);
	         btnAdd.setEnabled(!inAddMode);
	         btnRemove.setEnabled(!inAddMode);
	         tfSearch.setEnabled(!inAddMode);



	    }
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// updates the empArray controls with a given employee's information.
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    private void refreshOrderFields() {
	    	Order order = (!inAddMode) ? ordersArray.get(currentOrder - 1) : null;

	        tfOrderID.setText((order != null) ? ("" + order.getOrderID()) : "(NEW)");
	        tfCustomerID.setText((order != null) ?  (order.getCustomerID()): null);
	        tfEmployeeID.setText((order != null) ?  Long.toString(order.getEmployeeID()): null);

	        dtcOrderDate.setDate((order != null) ? order.getOrderDate() : null);
	        dtcRequiredDate.setDate((order != null) ? order.getRequiredDate() : null);
	        dtcShippedDate.setDate((order != null) ? order.getShippedDate() : null);


	        tfShipViaID.setText((order != null && order.getShipVia() != null) ? Long.toString(order.getShipVia().getShipperID()) : null);
	        tfShipAddress.setText((order != null) ? order.getShipAddress() : null);
	        tfShipCity.setText((order != null) ? order.getShipCity() : null);
	        tfCountry.setText((order != null) ? order.getShipCountry() : null);
	        refreshCustomerComBoxSelectedByID() ;
	        refreshEmpComBoxSelectedByID();
	        refreshShipViaComBoxSelectedByID();




	        panel.refreshComp((order != null) ? (order.getOrderID()) : 0);





	    }
	    private void refreshCustomerComBoxSelectedByID ()
	    {
	    	if (!inAddMode)
	    	{
	    		int custemerByIdIndex = customers.indexOf(new Customer(tfCustomerID.getText()));
				if(custemerByIdIndex < customers.size() && custemerByIdIndex >= 0 ){
					cbCustomerName.getModel().setSelectedItem(customers.get(custemerByIdIndex));
				}
			}
			else{cbCustomerName.getModel().setSelectedItem(null);
			}
	   }

	    private void refreshEmpComBoxSelectedByID ()
	    {
	    	if (!inAddMode)
	    	{
	    		try {
	    			int empByIdIndex = employeeArray.indexOf(new Employee(Long.parseLong(tfEmployeeID.getText())));
					if(empByIdIndex < employeeArray.size() && empByIdIndex >= 0 ){
						cbEmployeeName.getModel().setSelectedItem(employeeArray.get(empByIdIndex));
					}
	    		} catch(Exception e) {
	    			cbEmployeeName.getModel().setSelectedItem(null);
	    		}
			}
			else{cbEmployeeName.getModel().setSelectedItem(null);
			}
	   }

	    private void refreshShipViaComBoxSelectedByID ()
	    {
	    	if (!inAddMode)
	    	{
	    		try {
	    			int index = shippersArray.indexOf(new Shipper(Long.parseLong(tfShipViaID.getText())));
					if(index < shippersArray.size() && index >= 0 ){
						cbShipViaName.getModel().setSelectedItem(shippersArray.get(index));
					}
	    		} catch(Exception e) {
	    			cbShipViaName.getModel().setSelectedItem(null);
	    		}
			}
			else{cbShipViaName.getModel().setSelectedItem(null);
			}
	   }

	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Updates the view to present previous order

		 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			private void btnPrevOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevOnClick
				inAddMode = false;
				currentOrder--;
				refreshControls();
			}//GEN-LAST:event_btnPrevOnClick

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Updates the view to present next order
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			private void btnNextOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextOnClick
				currentOrder++;
				refreshControls();
			}//GEN-LAST:event_btnNextOnClick

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Updates the view to present last order on list
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			private void btnLastOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastOnClick
				currentOrder = ordersArray.size();
				refreshControls();
			}//GEN-LAST:event_btnLastOnClick

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Updates the view to present first order on list
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			private void btnFirstOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstOnClick
				inAddMode = false;
				currentOrder = 1;
				refreshControls();
			}//GEN-LAST:event_btnFirstOnClick

	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// On any change on order fields updates the view to enable save
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    private void updateChangedField () {
	        btnSave.setEnabled(true);
	    }
	    private void btnSaveOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveOnClick
	    	if (inputValid()) {
	            Long id = (!inAddMode) ?Long.parseLong(tfOrderID.getText()) : -1;
	            boolean saveLastStateTemp = inAddMode;
	            boolean success = (!inAddMode) ?
	                    OrderLogic.getInstance().editOrder(id, tfCustomerID.getText(),Long.parseLong(tfEmployeeID.getText()),
					                    		dtcOrderDate.getDate(), dtcRequiredDate.getDate(), dtcShippedDate.getDate(),
					                    		Integer.parseInt(tfShipViaID.getText()), tfShipAddress.getText(),
					                    		tfShipCity.getText(), tfCountry.getText()) :

	                   OrderLogic.getInstance().addOrder(tfCustomerID.getText(),
	        	                    		Long.parseLong(tfEmployeeID.getText()),
	        	                    		dtcOrderDate.getDate(), dtcRequiredDate.getDate(), dtcShippedDate.getDate(),
	        	                    		Integer.parseInt(tfShipViaID.getText()), tfShipAddress.getText(),
	        	                    		tfShipCity.getText(), tfCountry.getText())
	                    			;
	            if (success) {
	                fetchAndRefresh(id);
//	                if  (!saveLastStateTemp) {msgbox("Changes Saved Successfully");}
//	                else
//	               msgbox("Order has been created successfully");

	            } else
	                JOptionPane.showMessageDialog(this, "Failure!"); // !!
	        } else
	            JOptionPane.showMessageDialog(this, "customerID & employeeID & shipType  are required!");

	    }//GEN-LAST:event_btnSaveOnClick
	    private void btnAddOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOnClick
	        inAddMode = true;
	        currentOrder = ordersArray.size() + 1;
	        refreshControls();
	        tfCustomerID.requestFocus();
	    }//GEN-LAST:event_btnAddOnClick
	    private void btnRemoveOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveOnClick
	        String tempLastOrderID = Long.toString(ordersArray.get(currentOrder - 1).getOrderID());

	    	if (OrderLogic.getInstance().removeOrder(ordersArray.get(currentOrder - 1).getOrderID())) {
	            fetchAndRefresh((currentOrder != 1) ?
	            		ordersArray.get(currentOrder - 2).getEmployeeID() : null);
	            msgbox("Order "+tempLastOrderID+"  has been removed");
	        } else
	        	msgbox("Something went wrong!");
	    }//GEN-LAST:event_btnRemoveOnClick



	 	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//  Used after updating\adding employee - it retrieved updated data without loosing current employee location by its id
		//  fetches empArray, and tries to refresh controls to given employee.
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		private void fetchAndRefresh(Long id) {
			ordersArray = OrderLogic.getInstance().getOrders();

		if (!ordersArray.isEmpty()) {
			if (id != null)
			currentOrder = (!inAddMode) ? ordersArray.indexOf(new Order(id)) + 1 : ordersArray.size();  //

			if (id == null || currentOrder == 0) // indexOf could return -1.
				currentOrder = 1;
		}
		else
			currentOrder = null;

		inAddMode = (currentOrder == null);
		refreshControls();
		}


	    private boolean inputValid() {
	        return (cbCustomerName.getSelectedItem() != null &&
	        		tfCustomerID.getText().length() > 0 &&
	        		tfShipViaID.getText().length() > 0 &&
	        		tfEmployeeID.getText().length() > 0 );
	    }

	    private void msgbox(String s){
	    	   JOptionPane.showMessageDialog(this, s);
	    	}

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Set Id according combobox chosen description
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////

		 private void refreshCustomerIdOnNameChange (ActionEvent e) {
	 		JComboBox comboBox = (JComboBox)e.getSource();
	 		Object selected = comboBox.getSelectedItem();
	 		if (selected instanceof Customer){
		        Customer customer = (Customer) selected;
		        tfCustomerID.setText(customer.getCustomerID());
	        }
		}

		 private void refreshEmpIdOnNameChange (ActionEvent e) {
	 		JComboBox comboBox = (JComboBox)e.getSource();
	 		Object selected = comboBox.getSelectedItem();
	 		if (selected instanceof Employee){
		        Employee emp = (Employee) selected;
		        tfEmployeeID.setText(Long.toString(emp.getEmployeeID()));
	        }
		}

		 private void refreshtfShipViaIDOnNameChange (ActionEvent e) {
	 		JComboBox comboBox = (JComboBox)e.getSource();
	 		Object selected = comboBox.getSelectedItem();
	 		if (selected instanceof Shipper){
		        Shipper shipper = (Shipper) selected;
		        tfShipViaID.setText(Long.toString(shipper.getShipperID()));
	        }
		}









	private JPanel pnlEmpDetails = new JPanel();
    private JPanel pnlEmpContact = new JPanel();
	private JPanel contentPane;
	FrmOrderDetailsInternal panel;

	private JTextField tfOrderID;
	private JTextField tfCustomerID;
	private JTextField tfEmployeeID;
	private JTextField tfNavigation = new JTextField();

	private JLabel lblEmployeeID = new JLabel("Employee:");
	private JLabel lblCustomer = new JLabel("Customer:");
	private JLabel lblRequiredDate = new JLabel("Required Date:");
	private JLabel lblOrderID = new JLabel("Order ID:");
	private JLabel lblOrderDate = new JLabel("Order Date:");
	private JLabel lblShippedDate = new JLabel("Shipped Date:");

	private JDateChooser dtcOrderDate = new JDateChooser();
	private JDateChooser dtcRequiredDate = new JDateChooser();
	private JDateChooser dtcShippedDate = new JDateChooser();

	private JButton btnFirst = new JButton("|<");
	private JButton btnPrev = new JButton("<<");

	private JButton btnNext = new JButton(">>");
	private JButton btnLast = new JButton(">|");
	private final JButton btnSave = new JButton("Save");
	private final JButton btnAdd = new JButton("Add New");
	private final JButton btnRemove = new JButton("Delete");
	private JPanel pnlActionBtn;
	private JTextField tfSearch;
	private JTextField tfShipViaID;
	private JTextField tfShipAddress= new JTextField();
	private JTextField tfShipCity;
	private JTextField tfCountry;
	private JComboBox cbCustomerName ;
	private JComboBox cbEmployeeName = new JComboBox();
	private JComboBox cbShipViaName = new JComboBox();
	//private JTable table;
	private DefaultTableModel tableModel;
}
