package boundary;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import control.EmployeeLogic;
import control.OrderLogic;
import entity.Customer;
import entity.Employee;
import entity.Order;
import entity.Shipper;
import util.JTextFieldLimiter;

public class FrmOrdersPanel extends JPanel {


	private ArrayList<entity.Order> ordersArray;
	ArrayList<Customer> customers = new ArrayList<Customer>();
	ArrayList<Employee> employeeArray = new ArrayList<Employee>();
	ArrayList<Shipper> shippersArray = new ArrayList<Shipper>();
    private Integer currentOrder;
    private boolean inAddMode;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FrmOrders frame = new FrmOrders();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public FrmOrdersPanel() {


		initComponents();
		fetchAndRefresh();
		createEvents();


	}

	private void initComponents() {
		setLayout(null);

//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 753, 521);
//		contentPane = new JPanel();
//		contentPane.setForeground(SystemColor.activeCaption);
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmEmployees.class.getResource("/boundary/images/logo.png")));
//		setTitle("Order Details");
//		contentPane.setLayout(null);
		pnlEmpDetails.setBounds(10, 11, 333, 190);
		pnlEmpDetails.setBorder(null);
		add(pnlEmpDetails);
		tfOrderID = new JTextField();
		tfOrderID.setBounds(84, 11, 179, 20);
		tfOrderID.setEditable(false);
		tfOrderID.setColumns(10);


		tfCustomerID = new JTextField();
		tfCustomerID.setColumns(20);

		tfCustomerID.setBounds(84, 37, 54, 20);

		tfEmployeeID = new JTextField();

		tfEmployeeID.setBounds(84, 63, 54, 20);
		tfEmployeeID.setColumns(10);
		pnlEmpContact.setBounds(379, 11, 344, 190);
		//contentPane.
		add(pnlEmpContact);



        tfEmployeeID.setDocument(new JTextFieldLimiter(Employee.MAX_FNAME));
 		pnlActionBtn = new JPanel();
 		pnlActionBtn.setBounds(20, 358, 571, 31);
		tfNavigation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfNavigation.setText("showing X of Y");
		tfNavigation.setColumns(6);
		//contentPane.
		add(pnlActionBtn);
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


        initSelectCusomers();
        initSelectEmployee();
        initSelectShipVia();


        initGroupLayoutleftPanel();
        initGroupLayoutRightPanel();

        panel = new FrmOrderDetailsInternal();
        panel.setForeground(SystemColor.control);
        panel.setBackground(SystemColor.activeCaptionBorder);
        panel.setBounds(10, 212, 821, 135);
        //contentPane.
        add(panel);
        
        //add(contentPane);

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
				int custemerByIdIndex = customers.indexOf(new Customer(tfCustomerID.getText().toUpperCase()));
				if(custemerByIdIndex<=customers.size() && custemerByIdIndex>-1 ){
				String companyName=
						customers.get(custemerByIdIndex).getCompanyName();
				Item object = new Item (tfCustomerID.getText(),companyName);

				cbCustomerName.getModel().setSelectedItem(object);}
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

				if(index<=shippersArray.size() && index>=0 ){
				String name=shippersArray.get(index).getCompanyName();
				Item object = new Item (tfShipViaID.getText(),name);

				cbShipViaName.getModel().setSelectedItem(object);}
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

				if(employeeByIdIndex<=employeeArray.size() && employeeByIdIndex>-1 ){
				String empName=
						employeeArray.get(employeeByIdIndex).fullNametoString();
				Item object = new Item (tfEmployeeID.getText(),empName);

				cbEmployeeName.getModel().setSelectedItem(object);}
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
			lblOrderID.setBounds(10, 14, 64, 14);
			pnlEmpDetails.add(lblOrderID);
			lblOrderDate.setBounds(10, 91, 64, 14);
			pnlEmpDetails.add(lblOrderDate);
			lblCustomer.setBounds(10, 40, 54, 14);
			pnlEmpDetails.add(lblCustomer);
			lblEmployeeID.setBounds(10, 66, 55, 14);
			pnlEmpDetails.add(lblEmployeeID);
			lblRequiredDate.setBounds(10, 117, 73, 14);
			pnlEmpDetails.add(lblRequiredDate);
			dtcRequiredDate.setBounds(84, 117, 179, 20);
			pnlEmpDetails.add(dtcRequiredDate);
			dtcOrderDate.setBounds(84, 91, 179, 20);
			pnlEmpDetails.add(dtcOrderDate);
			pnlEmpDetails.add(tfEmployeeID);
			pnlEmpDetails.add(tfCustomerID);
			pnlEmpDetails.add(tfOrderID);
			cbCustomerName.setBounds(148, 37, 179, 20);
			pnlEmpDetails.add(cbCustomerName);

			cbEmployeeName.setBounds(148, 63, 181, 20);
			pnlEmpDetails.add(cbEmployeeName);
			lblShippedDate.setBounds(10, 144, 73, 14);
			pnlEmpDetails.add(lblShippedDate);
			dtcShippedDate = new JDateChooser();
			dtcShippedDate.setBounds(84, 142, 179, 20);
			pnlEmpDetails.add(dtcShippedDate);


	}


	private void initSelectCusomers()
	{

		customers= OrderLogic.getInstance().getCustomers();
		Vector model = new Vector();

		int i=0;
		while (i<customers.size()){
			model.addElement( new Item(customers.get(i).getCustomerID(), customers.get(i).getCompanyName()) );
			;i++;}


        cbCustomerName = new JComboBox( model );
        cbCustomerName.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);

	}

	private void initSelectEmployee()
	{

		employeeArray= EmployeeLogic.getInstance().getEmployees();
		Vector model = new Vector();
		int i=0;
		while (i<employeeArray.size()){
			model.addElement( new Item(Long.toString(employeeArray.get(i).getEmployeeID()), employeeArray.get(i).fullNametoString()) );
			;i++;}
		cbEmployeeName = new JComboBox( model );
		cbEmployeeName.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);

	}

	private void initSelectShipVia()
	{

		shippersArray= OrderLogic.getInstance().getshippers();
		Vector model = new Vector();
		int i=0;
		while (i<shippersArray.size()){
			model.addElement( new Item(Long.toString(shippersArray.get(i).getShipperID()), shippersArray.get(i).getCompanyName()) );
			;i++;}
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


	        tfShipViaID.setText((order != null) ? Long.toString(order.getShipVia()) : null);
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
			if(custemerByIdIndex<=customers.size() && custemerByIdIndex>0 ){
			String companyName=	customers.get(custemerByIdIndex).getCompanyName();
			Item object = new Item (tfCustomerID.getText(),companyName);
			cbCustomerName.getModel().setSelectedItem(object);
			}}
			else{cbCustomerName.getModel().setSelectedItem("select");
			}


	   }
	    private void refreshEmpComBoxSelectedByID ()
	    {
	    	if (!inAddMode)
	    	{
	    	int empByIdIndex = employeeArray.indexOf(new Employee(Long.parseLong(tfEmployeeID.getText())));
			if(empByIdIndex<=employeeArray.size() && empByIdIndex>0 ){
			String fullName=	employeeArray.get(empByIdIndex).fullNametoString();
			Item object = new Item (tfEmployeeID.getText(),fullName);
			cbEmployeeName.getModel().setSelectedItem(object);
			}}
			else{cbEmployeeName.getModel().setSelectedItem("select");
			}


	   }
	    private void refreshShipViaComBoxSelectedByID ()
	    {
	    	if (!inAddMode)
	    	{
	    	int index = shippersArray.indexOf(new Shipper(Long.parseLong(tfShipViaID.getText())));
			if(index<=shippersArray.size() && index>0 ){
			String company=	shippersArray.get(index).getCompanyName();
			Item object = new Item (tfShipViaID.getText(),company);
			cbShipViaName.getModel().setSelectedItem(object);
			}}
			else{cbShipViaName.getModel().setSelectedItem("select");
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

	    class ItemRenderer extends BasicComboBoxRenderer
	    {
	        public Component getListCellRendererComponent(
	            JList list, Object value, int index,
	            boolean isSelected, boolean cellHasFocus)
	        {
	            super.getListCellRendererComponent(list, value, index,
	                isSelected, cellHasFocus);

	            if (value != null)
	            {
	                Item item = (Item)value;
	                setText( item.getDescription().toUpperCase() );
	            }

	            if (index == -1)
	            {
	                Item item = (Item)value;
	                setText( "" + item.getId() );
	            }


	            return this;
	        }
	    }
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Set Id according combobox chosen description
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////

		 private void refreshCustomerIdOnNameChange (ActionEvent e) {
	 		JComboBox comboBox = (JComboBox)e.getSource();
	 		if (comboBox.getSelectedItem() != "select"){
	        Item item = (Item)comboBox.getSelectedItem();
	        if (item!=null){
	        tfCustomerID.setText(item.getId());
	        }}
		}

		 private void refreshEmpIdOnNameChange (ActionEvent e) {
	 		JComboBox comboBox = (JComboBox)e.getSource();
	 		if (comboBox.getSelectedItem() != "select"){
	        Item item = (Item)comboBox.getSelectedItem();
	        if (item!=null){
	        tfEmployeeID.setText(item.getId());
	        }}
		}

		 private void refreshtfShipViaIDOnNameChange (ActionEvent e) {
		 		JComboBox comboBox = (JComboBox)e.getSource();
		 		if (comboBox.getSelectedItem() != "select"){
		        Item item = (Item)comboBox.getSelectedItem();
		        if (item!=null){
		        	tfShipViaID.setText(item.getId());
		        	}}
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
