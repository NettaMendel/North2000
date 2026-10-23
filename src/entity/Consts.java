package entity;

import java.net.URLDecoder;

/**
 * http://www.javapractices.com/topic/TopicAction.do?Id=2
 */
public final class Consts {
    private Consts() {
        throw new AssertionError();
    }

	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";
	
	
    
    public static final String SEL_PRODUCTS = "SELECT * FROM TblProducts";
    
	/**
	 * find the correct path of the DB file
     * @return the path of the DB file (from eclipse or with runnable file)
	 */
	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			// System.out.println(decoded) - Can help to check the returned path
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				return decoded + "/database/Tirgul05_north2000.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				System.out.println(decoded);
				return decoded + "src/entity/Tirgul05_north2000.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
   

    
    protected static final String DB_FILEPATH = getDBPath();

    public static final String CONN_STR = "jdbc:ucanaccess://"  + DB_FILEPATH + ";COLUMNORDER=DISPLAY";





    public static final String SQL_SEL_EMPLOYEES = "SELECT * FROM TblEmployees";
    public static final String SQL_DEL_EMPLOYEE = "{ call qryDelEmployee(?) }";
    public static final String SQL_INS_EMPLOYEE =
            "{ call qryInsEmployee(?,?,?,?,?,?,?,?,?,?,?) }";
    public static final String SQL_UPD_EMPLOYEE =
            "{ call qryUpdEmployee(?,?,?,?,?,?,?,?,?,?,?,?) }";

    public static final String SQL_SEL_ORDERS = "SELECT * FROM TblOrders";
    public static final String SQL_UPD_ORDER =
            "{ call qryUpdOrder(?,?,?,?,?,?,?,?,?,?) }";
    public static final String SQL_ADD_ORDER =
            "{ call qryInsOrder(?,?,?,?,?,?,?,?,?) }";
    public static final String SQL_DEL_ORDER =
            "{ call qryDelOrder(?) }";


    public static final String SQL_DEL_ORDER_DETAILS =
            "{ call qryDelOrderDetails(?) }";
    public static final String SQL_DEL_ORDER_DETAILS_PRODUCT =
            "{ call qryDelOrderDetailProduct(?,?) }";

    public static final String SQL_SEL_ORDER_DETAILS = "SELECT TblOrderDetails.orderID,TblOrderDetails.ProductID, TblProducts.ProductName, TblOrderDetails.Quantity, TblOrderDetails.Discount, TblProducts.UnitPrice, [TblProducts].[UnitPrice]*[TblOrderDetails].[Quantity]*(1-[TblOrderDetails].[Discount]) AS LinePrice FROM TblProducts INNER JOIN TblOrderDetails ON TblProducts.ProductID = TblOrderDetails.ProductID WHERE (((TblOrderDetails.OrderID)=?));";
    public static final String SQL_UPD_ORDER_DETAILS ="{ call qryUpdOrderDetails(?,?,?,?) }";
    public static final String SQL_INS_ORDER_DETAILS ="{ call qryInsOrderDetails(?,?,?,?) }";
    	//	"{ call qryInsOrderDetails(?,?,?,?) }";



    public static final String  SQL_SEL_CUSTOMERS= "SELECT TblCustomers.CustomerID, TblCustomers.CompanyName FROM TblCustomers;";
    public static final String  SQL_SEL_SHIPPERS= "SELECT TblShippers.* FROM TblShippers;";
    public static final String  SQL_SEL_PRODUCTS= "SELECT TblProducts.* FROM TblProducts;";

	public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM TblCustomers";
    public static final String INSERT_CUSTOMER = "{ call qryInsertCustomer(?,?,?,?,?,?,?,?,?); }";
    public static final String UPDATE_CUSTOMER = "{ call qryUpdateCustomer(?,?,?,?,?,?,?,?,?); }";
	public static final String DELETE_CUSTOMER = "{ call qryDeleteCustomer(?); }";
    
    private static String getDBPath() {
    	try {
    		String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    		String decoded = URLDecoder.decode(path, "UTF-8");
    		if (decoded.contains(".jar"))
    		{
    			decoded = decoded.substring(0, decoded.lastIndexOf('/'));
    			System.out.println(decoded);
    	
    			return decoded + "/database/Tirgul01_north2000.accdb";}
    		else {
    			decoded = decoded.substring(0, decoded.lastIndexOf('/'));
    			//System.out.println(decoded);
    	
    			return decoded + "/entity/Tirgul01_north2000.accdb";}
    			
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}

		public enum Manipulation {
    		UPDATE, INSERT, DELETE;
    	}
    }
}
