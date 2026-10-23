package control;

import model.Consts;
import model.Consts.Manipulation;
import model.Customer;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CustomerLogic {
	private static CustomerLogic instance;
	
	private CustomerLogic() {}
	
	public static CustomerLogic getInstance() {
		if (instance == null)
			instance = new CustomerLogic();
		return instance;
	}
	
	/**
	 * exports customers from db to xml.
	 */
    public void exportCustomersToXML() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    PreparedStatement stmt = conn.prepareStatement(
                            Consts.SELECT_ALL_CUSTOMERS);
                    ResultSet rs = stmt.executeQuery()) {
                
                // create document object.
                Document doc = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder().newDocument();
                
                // push root element into document object.
                Element rootElement = doc.createElement("Customers_info");
                rootElement.setAttribute("exportDate", LocalDateTime.now().toString());
                doc.appendChild(rootElement);
                
                while (rs.next()) {     // run on all customer records..
                    // create customer element.
                    Element customer = doc.createElement("Customer");
                    
                    // assign key to customer.
                    Attr attr = doc.createAttribute("ID");
                    attr.setValue(rs.getString(1));
                    customer.setAttributeNode(attr);
                    
                    // push elements to customer.
                    for (int i = 2; i <= rs.getMetaData().getColumnCount(); i++) {
                        Element element = doc.createElement(
                                rs.getMetaData().getColumnName(i)); // push element to doc.
                        rs.getObject(i); // for wasNull() check..
                        element.appendChild(doc.createTextNode(
                                rs.wasNull() ? "" : rs.getString(i)));  // set element value.
                        customer.appendChild(element);  // push element to customer.
                    }
                    
                    // push customer to document's root element.
                    rootElement.appendChild(customer);
                }
                
                // write the content into xml file
                
                DOMSource source = new DOMSource(doc);
                File file = new File("xml/customers.xml");
                file.getParentFile().mkdir(); // create xml folder if doesn't exist.
                StreamResult result = new StreamResult(file);
                TransformerFactory factory = TransformerFactory.newInstance();
                
                // IF CAUSES ISSUES, COMMENT THIS LINE.
                factory.setAttribute("indent-number", 2);
                //
                
                Transformer transformer = factory.newTransformer();
                
                // IF CAUSES ISSUES, COMMENT THESE 2 LINES.
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                //
                
                transformer.transform(source, result);
                
                System.out.println("customers data exported successfully!");
            } catch (SQLException | NullPointerException | ParserConfigurationException
                    | TransformerException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * imports customers from xml to db.
     * @param path xml filepath.
     */
    public void importCustomersFromXML(String path) {
    	try {
			Document doc = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder().parse(new File(path));
			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("Customer");
			
			int errors = 0;
			for (int i = 0; i < nl.getLength(); i++) {
				if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element) nl.item(i);
					Customer c = new Customer(el.getAttribute("ID"), 
							el.getElementsByTagName("CompanyName").item(0).getTextContent(),
							el.getElementsByTagName("ContactName").item(0).getTextContent(),
							el.getElementsByTagName("ContactTitle").item(0).getTextContent(),
							el.getElementsByTagName("Address").item(0).getTextContent(),
							el.getElementsByTagName("City").item(0).getTextContent(),
							el.getElementsByTagName("Country").item(0).getTextContent(),
							el.getElementsByTagName("Phone").item(0).getTextContent(),
							el.getElementsByTagName("Fax").item(0).getTextContent());
					if (!manipulateCustomer(c, Manipulation.INSERT) && 
							!manipulateCustomer(c, Manipulation.UPDATE))
						errors++;
				}
			}
			
			System.out.println((errors == 0) ? "customers data imported successfully!" : 
				String.format("customers data imported with %d errors!", errors));
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * exports customers from db to json.
     */
	public void exportCustomersToJSON() {
    	   try {
               Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
               try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                       PreparedStatement stmt = conn.prepareStatement(
                               Consts.SELECT_ALL_CUSTOMERS);
                       ResultSet rs = stmt.executeQuery()) {
            	   JsonArray customers = new JsonArray();
                   while (rs.next()) {
                	   JsonObject customer = new JsonObject();
                	   
                	   for (int i = 1; i < rs.getMetaData().getColumnCount(); i++)
                		   customer.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                	   
                	   customers.add(customer);
                   }
                   
            	   JsonObject doc = new JsonObject();
            	   doc.put("Customers_info", customers);
                   
                   File file = new File("json/customers.json");
                   file.getParentFile().mkdir();
                   
                   try (FileWriter writer = new FileWriter(file)) {
                	   writer.write(Jsoner.prettyPrint(doc.toJson()));
                	   writer.flush();
                       System.out.println("customers data exported successfully!");
                   } catch (IOException e) {
                	   e.printStackTrace();
                   }
               } catch (SQLException | NullPointerException e) {
                   e.printStackTrace();
               }
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }	
    }
    
	/**
	 * imports customers from json to db.
	 * @param path json filepath.
	 */
    public void importCustomersFromJSON(String path) {
    	try (FileReader reader = new FileReader(new File(path))) {
    		JsonObject doc = (JsonObject) Jsoner.deserialize(reader);
    		JsonArray customers = (JsonArray) doc.get("Customers_info");
    		Iterator<Object> iterator = customers.iterator();
    		int errors = 0;
    		while (iterator.hasNext()) {
    			JsonObject obj = (JsonObject) iterator.next();
    			Customer c = new Customer((String) obj.get("CustomerID"),
    					(String) obj.get("CompanyName"), 
    					(String) obj.get("ContactName"),
    					(String) obj.get("ContactTitle"), 
    					(String) obj.get("Address"),
    					(String) obj.get("City"), 
    					(String) obj.get("Country"),
    					(String) obj.get("Phone"), 
    					(String) obj.get("Fax"));
    			if (!manipulateCustomer(c, Manipulation.INSERT) && 
						!manipulateCustomer(c, Manipulation.UPDATE))
					errors++;
    		}
    		
			System.out.println((errors == 0) ? "customers data imported successfully!" : 
				String.format("customers data imported with %d errors!", errors));
    	} catch (IOException | DeserializationException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * performs data manipulation in db on given customer.
     * @param c customer to be manipulated.
     * @param manipulation manipulation type.
     * @return success or failure.
     */
    public boolean manipulateCustomer(Customer c, Manipulation manipulation) {
    	try {
    		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
    				CallableStatement stmt = conn.prepareCall(
    						(manipulation.equals(Manipulation.UPDATE)) ? 
    								Consts.UPDATE_CUSTOMER : 
    									(manipulation.equals(Manipulation.INSERT)) ? 
    											Consts.INSERT_CUSTOMER : 
    												Consts.DELETE_CUSTOMER)) {
    			allocateCustomerParams(stmt, c, manipulation);
    			stmt.executeUpdate();
    			return true;
    		} catch (SQLException e) {
//    			e.printStackTrace();
    		}
    	} catch (ClassNotFoundException e) {
//    		e.printStackTrace();
    	}
    	
    	return false;
    }
    
    /**
     * fills statement's placeholders with customer's field values.
     * @param stmt statement object.
     * @param c customer.
     * @param m manipulation type.
     * @throws SQLException
     */
    private void allocateCustomerParams(CallableStatement stmt, Customer c, Manipulation m) throws SQLException {
    	int i = 1;
    	
    	if (!m.equals(Manipulation.UPDATE)) {
    		stmt.setString(i++, c.getCustomerID());
    		
    		if (m.equals(Manipulation.DELETE))
    			return;
    	}
    	
    	stmt.setString(i++, c.getCompanyName());
    	
    	if (c.getContactName() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getContactName());
    	
    	if (c.getContactTitle() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getContactTitle());
    	
    	if (c.getAddress() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getAddress());
    	
    	if (c.getCity() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getCity());
    	
    	if (c.getCountry() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getCountry());
    	
    	if (c.getPhone() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getPhone());
    	
    	if (c.getFax() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getFax());
    	
    	if (m.equals(Manipulation.UPDATE))
    		stmt.setString(i, c.getCustomerID());
    }
}
