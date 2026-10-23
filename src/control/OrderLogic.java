package control;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import entity.Consts;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Customer Orders Report controller.
 */
public class OrderLogic {
    /** Singleton instance. */
    private static OrderLogic instance;
    
    /**
     * Private constructor for singleton purposes.
     */
    private OrderLogic() {}

    /**
     * Singleton getter.
     * @return instance of this.
     */
    public static OrderLogic getInstance() {
        if (instance == null)
            instance = new OrderLogic();
        return instance;
    }
    
    /**
     * Outputs report at runtime.
     * @return JFrame containing the report viewer.
     */
    public JFrame compileCustomerOrdersReport() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR)) {
                // Load the compiled Jasper report (.jasper file) from resources
            	InputStream reportStream = getClass().getClassLoader().getResourceAsStream("boundary/RptCustomerOrders.jasper");
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

	
    /**
     * outputs report in runtime.
     * @return
     */
	public JFrame compileProductOrdersByCountryReport(int productID, String productName) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR)) {
                // Load the compiled Jasper report (.jasper file) from resources
            	InputStream reportStream = getClass().getClassLoader().getResourceAsStream("boundary/RptProductOrdersByCountry.jasper");
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
}
