package boundary;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

import control.OrderLogic;
import entity.Customer;
 
public class ComboBoxItem extends JFrame implements ActionListener
{
    public ComboBoxItem()
    {
    	ArrayList<Customer> customers = new ArrayList<Customer>();
		customers= OrderLogic.getInstance().getCustomers();
		Vector model = new Vector();
		int i=1;
		 
		while (i<customers.size()){				
			model.addElement( new Item(customers.get(i).getCustomerID(), customers.get(i).getCompanyName()) );
			;i++;}
  
        JComboBox comboBox;
        comboBox = new JComboBox( model );
        comboBox.addActionListener( this );
        comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        getContentPane().add(comboBox, BorderLayout.NORTH );

    }
 
    public void actionPerformed(ActionEvent e)
    {
        JComboBox comboBox = (JComboBox)e.getSource();
        Item item = (Item)comboBox.getSelectedItem();
        System.out.println( item.getId() + " : " + item.getDescription() );
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
 
    
 
    public static void main(String[] args)
    {
        JFrame frame = new ComboBoxItem();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible( true );
     }
 
}
  	
