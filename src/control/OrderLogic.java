package control;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import control.*;
import entity.*;

/**
 * Customer Orders Report controller.
 */
public class OrderLogic {
    /** Singleton instance. */
    private static OrderLogic instance;

    /**
     * Private constructor for singleton purposes.
     */
    private OrderLogic() {
    }

    /**
     * Singleton getter.
     * 
     * @return instance of this.
     */
    public static OrderLogic getInstance() {
        if (instance == null)
            instance = new OrderLogic();
        return instance;
    }

    /**
     * Outputs report at runtime.
     * 
     * @return JFrame containing the report viewer.
     */
    public JFrame compileCustomerOrdersReport() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR)) {
                // Load the compiled Jasper report (.jasper file) from resources
                InputStream reportStream = getClass().getClassLoader()
                        .getResourceAsStream("boundary/RptCustomerOrders.jasper");
                if (reportStream == null) {
                    throw new IOException("Report file not found: boundary/RptCustomerOrders.jasper");
                }
                // Set report parameters
                Map<String, Object> parameters = new HashMap<>();

                // Load report correctly
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

                // Fill the report with data
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

                // Display the report in a JFrame
                JFrame frame = new JFrame("Customer Orders Report");
                frame.getContentPane().add(new JRViewer(jasperPrint));
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.pack();
                return frame;
            } catch (SQLException | JRException | IOException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////
    // READ ORDERS FROM DB
    ////////////////////////////////////////////////////////////////
    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> results = new ArrayList<Customer>();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_CUSTOMERS);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int i = 1;
                    results.add(new Customer(rs.getString(i++), rs.getString(i++)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return results;
    }

    public ArrayList<Shipper> getshippers() {
        ArrayList<Shipper> results = new ArrayList<Shipper>();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_SHIPPERS);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int i = 1;
                    results.add(new Shipper(rs.getLong(i++), rs.getString(i++)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return results;
    }

    public ArrayList<Order> getOrders() {
        ArrayList<Order> results = new ArrayList<Order>();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ORDERS);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int i = 1;
                    results.add(new Order(rs.getLong(i++), rs.getString(i++),
                            rs.getLong(i++), rs.getDate(i++), rs.getDate(i++),
                            rs.getDate(i++), rs.getInt(i++), rs.getString(i++),
                            rs.getString(i++), rs.getString(i++)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return results;
    }

    public Order getOrder(long OrderID) {
        for (Order o : getOrders()) {
            if (o.getOrderID() == OrderID) {
                return o;
            }
        }
        return null;
    }

    public boolean addOrder(String customerID, long employeeID, Date orderDate, Date requiredDate,
            Date shippedDate, int shipVia, String shipAddress, String shipCity, String shipCountry) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_ORDER)) {
                int i = 1;

                stmt.setString(i++, customerID); // can't be null
                stmt.setLong(i++, employeeID); // can't be null

                if (orderDate != null)
                    stmt.setDate(i++, new java.sql.Date(orderDate.getTime()));
                else
                    stmt.setNull(i++, java.sql.Types.DATE);
                if (requiredDate != null)
                    stmt.setDate(i++, new java.sql.Date(requiredDate.getTime()));
                else
                    stmt.setNull(i++, java.sql.Types.DATE);
                if (shippedDate != null)
                    stmt.setDate(i++, new java.sql.Date(shippedDate.getTime()));
                else
                    stmt.setNull(i++, java.sql.Types.DATE);

                stmt.setInt(i++, shipVia); // can't be null

                if (shipAddress != null)
                    stmt.setString(i++, shipAddress);
                else
                    stmt.setNull(i++, java.sql.Types.VARCHAR);
                if (shipCity != null)
                    stmt.setString(i++, shipCity);
                else
                    stmt.setNull(i++, java.sql.Types.VARCHAR);
                if (shipCountry != null)
                    stmt.setString(i++, shipCountry);
                else
                    stmt.setNull(i++, java.sql.Types.VARCHAR);

                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * outputs report in runtime.
     * 
     * @return
     */
    public JFrame compileProductOrdersByCountryReport(int productID, String productName) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR)) {
                // Load the compiled Jasper report (.jasper file) from resources
                InputStream reportStream = getClass().getClassLoader()
                        .getResourceAsStream("boundary/RptProductOrdersByCountry.jasper");
                if (reportStream == null) {
                    throw new IOException("Report file not found: boundary/RptCustomerOrders.jasper");
                }

                HashMap<String, Object> params = new HashMap<>();
                params.put("p1", productID);
                params.put("productName", productName);
                // Load report correctly
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

                // Fill the report with data
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

                JFrame frame = new JFrame("Product " + productName + "'s Orders By Country");
                frame.getContentPane().add(new JRViewer(jasperPrint));
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.pack();
                return frame;
            } catch (SQLException | JRException | NullPointerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean removeOrder(long orderID) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    CallableStatement stmt1 = conn.prepareCall(Consts.SQL_DEL_ORDER_DETAILS);

            ) {
                stmt1.setLong(1, orderID);
                stmt1.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);

                    CallableStatement stmt2 = conn.prepareCall(Consts.SQL_DEL_ORDER)) {

                stmt2.setLong(1, orderID);
                stmt2.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editOrder(long orderID, String customerID, long employeeID, Date orderDate, Date requiredDate,
            Date shippedDate, int shipVia, String shipAddress, String shipCity, String shipCountry) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_ORDER)) {
                int i = 1;

                stmt.setString(i++, customerID); // can't be null
                stmt.setLong(i++, employeeID); // can't be null

                if (orderDate != null)
                    stmt.setDate(i++, new java.sql.Date(orderDate.getTime()));
                else
                    stmt.setNull(i++, java.sql.Types.DATE);
                if (requiredDate != null)
                    stmt.setDate(i++, new java.sql.Date(requiredDate.getTime()));
                else
                    stmt.setNull(i++, java.sql.Types.DATE);
                if (shippedDate != null)
                    stmt.setDate(i++, new java.sql.Date(shippedDate.getTime()));
                else
                    stmt.setNull(i++, java.sql.Types.DATE);

                stmt.setInt(i++, shipVia); // can't be null

                if (shipAddress != null)
                    stmt.setString(i++, shipAddress);
                else
                    stmt.setNull(i++, java.sql.Types.VARCHAR);
                if (shipCity != null)
                    stmt.setString(i++, shipCity);
                else
                    stmt.setNull(i++, java.sql.Types.VARCHAR);
                if (shipCountry != null)
                    stmt.setString(i++, shipCountry);
                else
                    stmt.setNull(i++, java.sql.Types.VARCHAR);

                stmt.setLong(i++, orderID);

                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<OrderDetails> getOrderDetails(long orderID) {
        ArrayList<OrderDetails> results = new ArrayList<OrderDetails>();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ORDER_DETAILS);) {

                stmt.setLong(1, orderID);
                ResultSet rs = stmt.executeQuery();
                Order order = getOrder(orderID);
                while (rs.next()) {
                    int i = 1;
                    long oId = rs.getLong(i++);
                    Order curOrder = (order != null && order.getOrderID() == oId) ? order : getOrder(oId);
                    long pId = rs.getLong(i++);
                    Product product = ProductLogic.getInstance().getProduct(pId);
                    String prodName = rs.getString(i++);
                    int qty = rs.getInt(i++);
                    float discount = rs.getFloat(i++);
                    BigDecimal unitPrice = rs.getBigDecimal(i++);
                    BigDecimal linePrice = rs.getBigDecimal(i++);
                    if (linePrice == null && unitPrice != null) {
                        linePrice = BigDecimal.valueOf(unitPrice.doubleValue() * qty * (1.0 - discount));
                    }
                    results.add(new OrderDetails(curOrder, product, prodName, qty, discount, unitPrice, linePrice));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return results;
    }

    public boolean editOrderDetails(long orderID, long productID, int quantity, float discount) {

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            try (
                    Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_ORDER_DETAILS)) {

                int i = 1;

                stmt.setInt(i++, quantity);

                stmt.setFloat(i++, discount);// can't be null

                stmt.setLong(i++, orderID); // can't be null

                stmt.setLong(i++, productID); // can't be null

                stmt.executeUpdate();

                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addOrderDetails(long orderID, long productID, int quantity, float discount) {

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            try (
                    Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_ORDER_DETAILS)) {

                int i = 1;

                stmt.setLong(i++, orderID); // can't be null
                stmt.setLong(i++, productID); // can't be null
                stmt.setInt(i++, quantity);
                stmt.setFloat(i++, discount);// can't be null
                stmt.executeUpdate();

                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeOrderDetail(long orderID, long productID) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    CallableStatement stmt1 = conn.prepareCall(Consts.SQL_DEL_ORDER_DETAILS_PRODUCT);

            ) {
                stmt1.setLong(1, orderID);
                stmt1.setLong(2, productID);
                stmt1.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
