package boundary;


import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyAdapter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import javax.swing.event.*;
import control.OrderLogic;
import entity.OrderDetails;
import entity.Product;


public class FrmOrderDetailsInternal extends JPanel {


	private static final long serialVersionUID = 1L;
	private  int iteration;
	private static  JTable table;
	private  JScrollPane scrollPane;
	private static DefaultTableModel tableModel;
	private static JComboBox<Item> comboBoxProductName;
	private JButton btnSaveOrderDetails = new JButton("Save");
	private JButton btnDeleteOrderDetails;
	private JLabel lblTotalOrderPrice;
	private JTextField tfTotalOrderPrice;
	private  JTextField tfProductId= new JTextField();
	private  JTextField tfUnitPrice= new JTextField();
	private  JTextField tfQuantity= new JTextField();
	private  JTextField tfDiscount= new JTextField();
	private  JTextField tfTotal= new JTextField();
	private long orderID;
	private static ArrayList<Product> products = new ArrayList<Product>();
	private ArrayList<OrderDetails> orders = new ArrayList<OrderDetails>();

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This method is being used in order to launch the internal form.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public FrmOrderDetailsInternal() {

			iteration=0;
			initComponents();
			createEvents();
		}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This method initializing the design structure
// of form left fields
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public void initComponents(){

			//Create table according to data structure
			table = new JTable(){ public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex==2 ||colIndex==5)return false; //Disallow the editing of product price and total cells
				 return true;
			}};

			tableModel = new DefaultTableModel();
		    String header[] = new String[] { "Product ID", "Product Name", "Unit Price", "Quantity", "Discount","Total" };
		    tableModel.setColumnIdentifiers(header);
		    table.setModel(tableModel);
		    table.setRowHeight(25);


		  //Create the scroll pane and add the table to it.
		    scrollPane = new JScrollPane(table);
		    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    scrollPane.setPreferredSize(new Dimension(500, 125));
		    scrollPane.setMaximumSize(new Dimension(450, 20000));
		    add(scrollPane);

		    //Set table dimensions
		    TableColumnModel columnModel = table.getColumnModel();
		    columnModel.getColumn(0).setPreferredWidth(250);
		    columnModel.getColumn(1).setPreferredWidth(1500);
		    columnModel.getColumn(2).setPreferredWidth(300);
		    columnModel.getColumn(3).setPreferredWidth(250);
		    columnModel.getColumn(4).setPreferredWidth(250);
		    columnModel.getColumn(5).setPreferredWidth(250);

		   //add save and delete button
		    btnSaveOrderDetails.setHorizontalAlignment(SwingConstants.LEFT);
		    add(btnSaveOrderDetails);
		    btnDeleteOrderDetails = new JButton("Delete");
		    btnDeleteOrderDetails.setHorizontalAlignment(SwingConstants.LEFT);
		    add(btnDeleteOrderDetails);

		  //add total order component
		    lblTotalOrderPrice = new JLabel("Total Order Price:");
		    add(lblTotalOrderPrice);
		    tfTotalOrderPrice = new JTextField();
		    tfTotalOrderPrice.setText("0");
		    tfTotalOrderPrice.setEditable(false);
		    add(tfTotalOrderPrice);
		    tfTotalOrderPrice.setColumns(10);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This method contain all the code for creating events
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		private void createEvents() {

			//Save button action - update/add/remove products in order according to table

			btnSaveOrderDetails.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		btnSaveOnClick(e);

		    	}
		    });
			// -----------------------------------

			//Delete button action - delete selected rows
		    btnDeleteOrderDetails.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent arg0) {
		    		removeSelectedRows(table);
		    		btnDeleteOrderDetails.setEnabled(false);
		    		btnSaveOrderDetails.setEnabled(true);
		    		getOrderSubTotal();
		    	}
		    });
		    // -----------------------------------

		    //add selected row listener to enable delete button when row selected
		    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
		        public void valueChanged(ListSelectionEvent event) {
		        	if (table.getSelectedRow()>0)btnDeleteOrderDetails.setEnabled(true);
		        }
		    });

		    //-----------------------------------
		    //add delete key action to table row

		    InputMap im = table.getInputMap(JTable.WHEN_FOCUSED);
		    ActionMap am = table.getActionMap();

		    Action deleteAction = new AbstractAction() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	removeSelectedRows(table);
		        	btnDeleteOrderDetails.setEnabled(false);
		        	btnSaveOrderDetails.setEnabled(true);
		        	getOrderSubTotal();
		        }

		    };
		    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
		    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "Delete");
		    am.put("Delete", deleteAction);

		    // -----------------------------------


		}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method is been used by the primary form when user browse between orders
//This method clear table rows and selections and set data according to order selected in primary form
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public void refreshComp(long orderId){
			setOrderID(orderId);
			refreshDataButtons();
			initTableData();
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method set the order to be displayed
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method set the save/delete buttons to be disabled
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 private void refreshDataButtons() {
		 btnSaveOrderDetails.setEnabled(false);
		 btnDeleteOrderDetails.setEnabled(false);




    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method set data to be presented on table according to order id
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
private void initTableData(){


	//Following code stop cell editing in case table is been edited -its important while user try to browse between orders without saving its edited values
	if (iteration ==0) {
	 	iteration++;
		}
	    else{

	    	table.getColumnModel().getColumn(0).getCellEditor().stopCellEditing();
	    	table.getColumnModel().getColumn(1).getCellEditor().stopCellEditing();
			table.getColumnModel().getColumn(2).getCellEditor().stopCellEditing();
			table.getColumnModel().getColumn(3).getCellEditor().stopCellEditing();
			table.getColumnModel().getColumn(4).getCellEditor().stopCellEditing();
			table.getColumnModel().getColumn(5).getCellEditor().stopCellEditing();
	    }

	//Following code clear table (used while browsing between orders)
	tableModel =(DefaultTableModel) table.getModel();
	tableModel.setRowCount(0);

	//Following code gets all orders details for selected order id and updates table rows
	orders = OrderLogic.getInstance().getOrderDetails(this.orderID);
	int i=0;
 	while (i<orders.size()){
    Vector<Object> data = new Vector<Object>();
    data.add(orders.get(i).getProductID());
    data.add(orders.get(i).getProductName());
    data.add(orders.get(i).getUnitPrice());
    data.add(orders.get(i).getQuantity());
    	//Add percent format for cell
	    NumberFormat numberFormat=NumberFormat.getPercentInstance();
	    Object value = numberFormat.format(orders.get(i).getDiscount());
	    data.add(value);
    data.add(orders.get(i).getLinePrice());
    i++;
    tableModel.addRow(data);}
 	//-------------------

 	// 	Add empty Row
 	addEmptyRow();
    //-------------------

 	// 	First row is been focused and selected by default
    table.changeSelection(0, 0, false, false);
    table.requestFocus();

    //set column components
    setUpProductNameColumnComboBox(table, table.getColumnModel().getColumn(1));
 	setUpTextEditor(table, 0,tfProductId);
 	setUpTextEditor(table, 2,tfUnitPrice);
 	setUpTextEditor(table, 3,tfQuantity);
 	setUpTextEditor(table, 4,tfDiscount);
 	setUpTextEditor(table, 5,tfTotal);

 	//Notifies all listeners that all cell values in the table's rows may have changed.
 	tableModel.fireTableDataChanged();

 	//Update total order price according to rows line price sum
 	getOrderSubTotal();
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method set product name column to present combo-box component with all products list
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void setUpProductNameColumnComboBox(JTable table,TableColumn columnNum) {

		//Set combo-box item list data - get product id and name
		products = OrderLogic.getInstance().getProducts();
		Vector<Item> combomodel = new Vector<Item>();
			
			for (int i =0 ;i<products.size();i++)
			{
				Item item = new Item(Long.toString(products.get(i).getProductID()), products.get(i).getProductName());
				combomodel.addElement( item );
				;i++;
				
			}
			comboBoxProductName = new JComboBox( combomodel );

		//Set action when value change update table row according to new selected product and enable saving changes button
		comboBoxProductName.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		refreshProductIdOnNameChange(e);
	        		btnSaveOrderDetails.setEnabled(true);
	        		//if last row is been edited add one more row
	        		if (table.getSelectedRow()==table.getRowCount()-1){
						addEmptyRow();
					}

	         	}
	    });

		//Set up the editor (combo-box) for the product-name cells.
		columnNum.setCellEditor(new DefaultCellEditor(comboBoxProductName));
		comboBoxProductName.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);


		//Set up tool tips for the the product-name cells.
		DefaultTableCellRenderer renderer =
		new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		columnNum.setCellRenderer(renderer);
			}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method set columns to present text field component in order to control its data changes events
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void setUpTextEditor(JTable table,int colIndex,JTextField cell) {
	TableColumn columnNum = table.getColumnModel().getColumn(colIndex);
	columnNum.setCellEditor(new DefaultCellEditor(cell));
	cell.putClientProperty("JTextField.isTableCellEditor", Boolean.TRUE);

		if (colIndex == 0)//Following code update row data when user change row product id item and enable save button
		{

					cell.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent e) {
							btnSaveOrderDetails.setEnabled(true);
						}
						public void keyReleased(KeyEvent arg0) {
							//Get product selected for combo-box by its Id
							int index = (cell.getText().length()>0) ? products.indexOf(new Product(Long.parseLong(cell.getText()))): -1;
							if (index <= products.size() && index > -1) {
								String name = products.get(index).getProductName();
								//Retrieve object item (id+name) for combo-box selection
								Item object = new Item(cell.getText(), name);
								table.setValueAt(object, table.getSelectedRow(),1);
							//Update row according to product selection
								updateRowNewProduct(Long.parseLong(object.getId()));
								getOrderSubTotal();
							//If no empty row add new one
								if (table.getSelectedRow()==table.getRowCount()-1){
									addEmptyRow();
								}

							} else {
								//If product not found set combo to present instruction for user
								Item object = new Item(null, "please select valid product");
								table.setValueAt(object, table.getSelectedRow(),1);
							}

						}
					});
	   }

	   else //Following code update totals when one of the cells changed and enable saving its changes
		{
		cell.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {

				btnSaveOrderDetails.setEnabled(true);
			}
			public void keyReleased(KeyEvent arg0) {

				if (cell.getText()!=null && cell.getText().length() >0 )
				{
					String value =cell.getText();
					if(colIndex==4){
						if (value.charAt(value.length() - 1) != '%')
						{
							 NumberFormat numberFormat=NumberFormat.getPercentInstance();
							  value = numberFormat.format(Float.parseFloat(value)/100);
							  cell.setText(value);
							  cell.setCaretPosition(value.length()-1);
						}
					}
					else {cell.setText(value);
					  }

				table.setValueAt(value, table.getSelectedRow(),table.getSelectedColumn());
				updateRowTotal(table.getSelectedRow());
				getOrderSubTotal();
				}
			}
		}
		);


		//Set text field courser before % when updating discount
		if(colIndex==4){
			cell.addFocusListener(new FocusAdapter() {
		    	@Override
		    	public void focusGained(FocusEvent arg0) {
		    		String value =cell.getText();
		    		cell.setCaretPosition(value.length()-1);
		    	}
		    });
		}
	}

	//Set up tool tips
	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	renderer.setToolTipText("Click for Edit");
	columnNum.setCellRenderer(renderer);

}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method refresh Product-Id cell value when user change selection on combo-box
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void refreshProductIdOnNameChange (ActionEvent e) {
		JComboBox comboBox = (JComboBox)e.getSource();
		if (comboBox.getSelectedItem() != "please select valid product"){
	Item item = (Item)comboBox.getSelectedItem();
	if (item!=null){
		table.setValueAt(item.getId(), table.getSelectedRow(),0);
	}
	updateRowNewProduct(Long.parseLong(item.getId()));
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method add Empty Row in order to allow adding new items
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addEmptyRow(){

	 	Vector<Object> data = new Vector<Object>();
	    data.add("");
	    data.add("");
	    data.add("");
	    data.add("");
	    data.add("");
	    data.add("");
		tableModel =(DefaultTableModel) table.getModel();
	    tableModel.addRow(data);
	    //-------------------

	}


	public void updateRowNewProduct(long id){
		products = OrderLogic.getInstance().getProducts();
	    int productArrayIndex= products.indexOf(new Product(id));
	    BigDecimal unitPrice = products.get(productArrayIndex).getUnitPrice();
     	table.setValueAt(unitPrice, table.getSelectedRow(),2);
     	table.setValueAt(new Integer (1), table.getSelectedRow(),3);
     	table.setValueAt(NumberFormat.getPercentInstance().format(new Float(0.0)), table.getSelectedRow(),4);
     	table.setValueAt(calculateTotalPerRow(table.getSelectedRow()), table.getSelectedRow(),5);
     	getOrderSubTotal();
	 }
	 private void updateRowTotal(int selectedRow) {

		 table.setValueAt(calculateTotalPerRow(selectedRow), selectedRow,5);


		}
	 public static final int getComponentIndex(Component component) {
	    if (component != null && component.getParent() != null) {

	      Container c = component.getParent();
	      for (int i = 0; i < c.getComponentCount(); i++) {
	        if (c.getComponent(i) == component)
	          return i;
	      }
	    }

	    return -1;
	  }
	 public static  BigDecimal calculateTotalPerRow(int row){

		BigDecimal price = (BigDecimal)table.getValueAt(row,2);
		int qty=(table.getValueAt(row,3)!=null)? (Integer.parseInt(table.getValueAt(row,3).toString())):1;


		String pecentFormated = table.getValueAt(row,4).toString();

		if (pecentFormated.charAt(pecentFormated.length() - 1) == '%')
			pecentFormated = pecentFormated.substring(0, pecentFormated.length() - 1);

		if (pecentFormated.length()<=0)pecentFormated="0";
		BigDecimal discount=new BigDecimal(Float.parseFloat(pecentFormated)/100.00);


		BigDecimal total =  new BigDecimal(price.floatValue() *qty *(1-discount.floatValue()));
		return total.setScale(2, BigDecimal.ROUND_HALF_UP);

	}
	 private Boolean btnSaveOnClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveOnClick


		table.getColumnModel().getColumn(0).getCellEditor().stopCellEditing();
    	table.getColumnModel().getColumn(1).getCellEditor().stopCellEditing();
		table.getColumnModel().getColumn(2).getCellEditor().stopCellEditing();
		table.getColumnModel().getColumn(3).getCellEditor().stopCellEditing();
		table.getColumnModel().getColumn(4).getCellEditor().stopCellEditing();
		table.getColumnModel().getColumn(5).getCellEditor().stopCellEditing();


	    		int rowIndex =0 ;
	    		while (rowIndex<table.getRowCount()) {

	    			if ((rowIndex)+1==table.getRowCount()){
	    				if (table.getValueAt(rowIndex,0).toString().length() <=0) break;
	    			}

	    			if (!inputValid(rowIndex)) {
	    				btnSaveOrderDetails.setEnabled(false);
	    				return false;}
	    			rowIndex++;
	    			}

	    		Boolean success = true;
	    		try {
	    			rowIndex =0 ;
					while (rowIndex<table.getRowCount()) {
							if (table.getValueAt(rowIndex,0).toString().length() <=0) break;
							int orderDetailArrayIndex= orders.indexOf(new OrderDetails(orderID,Long.parseLong(table.getValueAt(rowIndex,0).toString())));
							if (orderDetailArrayIndex>-1){//update

								String pecentFormated = table.getValueAt(rowIndex,4).toString();
								if (pecentFormated.charAt(pecentFormated.length() - 1) == '%')
									pecentFormated = pecentFormated.substring(0, pecentFormated.length() - 1);
								if (pecentFormated.length()<=0)pecentFormated="0";
								Float newdiscount=(float) (Float.parseFloat(pecentFormated)/100.00);


								success=OrderLogic.getInstance().editOrderDetails(orderID,Long.parseLong(table.getValueAt(rowIndex,0).toString()),Integer.parseInt(table.getValueAt(rowIndex,3).toString()),formatpercentToFloat(table.getValueAt(rowIndex,4).toString()));


							}
							if (orderDetailArrayIndex==-1){//add
								success=true;
								success=OrderLogic.getInstance().addOrderDetails(orderID,Long.parseLong(table.getValueAt(rowIndex,0).toString()),Integer.parseInt(table.getValueAt(rowIndex,3).toString()),formatpercentToFloat(table.getValueAt(rowIndex,4).toString()));

							}
							rowIndex++;
							}


					int ArrayIndex = 0;
					while (ArrayIndex<orders.size()) {


							rowIndex =0 ;
							Boolean found = false;
							while (rowIndex<table.getRowCount()) {


								if (table.getValueAt(rowIndex,0).toString().length()>0){
								if(Long.parseLong(table.getValueAt(rowIndex,0).toString()) == orders.get(ArrayIndex).getProductID())
								{
									found = true;
									break;
								}}
								rowIndex++;
							}

							if (found == false){
								success=OrderLogic.getInstance().removeOrderDetail(orderID,orders.get(ArrayIndex).getProductID());

							}

							ArrayIndex++;
					}

					}
	    		catch (NumberFormatException e) {
	    			e.printStackTrace();
	    			return false;
						}
	    			refreshDataButtons();
	    			initTableData();




	    		return null;


	    }//GEN-LAST:event_btnSaveOnClick

	 private boolean inputValid(int rowIndex) {

		boolean valid = true;

		try {
		        int quantity = Integer.parseInt(table.getValueAt(rowIndex,3).toString());

		        if (quantity<=0)
		        	{
		        	msgbox("Quantity grater than zero");
		        	return false;}
	    	}
		catch(NumberFormatException e) {
			msgbox("Quantity must be integer");
			table.getValueAt(rowIndex,0);}

		try {
			Float discount = Float.parseFloat(table.getValueAt(rowIndex,3).toString());

	        if (discount<0)
	        	{
		        	msgbox("Discount must be positive number");
		        	return false;
	        	}
		    }
		catch(NumberFormatException e) {
			msgbox("Discount must be decimal number");
			table.getValueAt(rowIndex,0);}


			if (table.getValueAt(rowIndex,0).toString().length() <=0)
			{
				msgbox("Product Id must be set");
				table.getValueAt(rowIndex,0);
			}



			int i =0;
			while (i<table.getRowCount()) {
				if( i!=rowIndex)
				{

					if (table.getValueAt(rowIndex,0).toString().equals( table.getValueAt(i,0).toString()))
					{
						msgbox("Duplicated productes not not allowed!");

												return false;
					}
				}
				i++;
			}





        return valid;
    }

	 private void getOrderSubTotal(){

		 Float total = (float) 0;
	    for (int i = 0; i < table.getRowCount()-1; i++){
	    	Float amount = ((BigDecimal) table.getValueAt(i, 5)).floatValue();
	        total += amount;
	    }

	   	    tfTotalOrderPrice.setText(new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

	 }


	 public void removeSelectedRows(JTable table){
		   DefaultTableModel model = (DefaultTableModel) table.getModel();
		   int[] rows = table.getSelectedRows();
		   for(int i=0;i<rows.length;i++){
			   if(rows[i]!=table.getRowCount()-1){
		     model.removeRow(rows[i]-i);}
		   }
		}
	 private void msgbox(String s){
  	   JOptionPane.showMessageDialog(this, s);
  	}

	 private float formatpercentToFloat(String pecentFormated){


			if (pecentFormated.charAt(pecentFormated.length() - 1) == '%')
				pecentFormated = pecentFormated.substring(0, pecentFormated.length() - 1);
			if (pecentFormated.length()<=0)pecentFormated="0";
			Float newdiscount=(float) (Float.parseFloat(pecentFormated)/100.00);

			return newdiscount;

	 }


//

}


