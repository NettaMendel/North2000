package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Order;
import entity.Shipper;

public class ShipperLogic {
	
	private static ShipperLogic instance;
	
    public static final String SQL_SEL_SHIPPERS = "SELECT TblShippers.* FROM TblShippers;";

	private ShipperLogic() {
		super();
	}

	/**
	 * @return the instance
	 */
	public static ShipperLogic getInstance() {
		if(instance==null) {
			instance = new ShipperLogic();
		}
		return instance;
	}
	
	public ArrayList<Shipper> getshippers() {
        ArrayList<Shipper> results = new ArrayList<Shipper>();

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    PreparedStatement stmt = conn.prepareStatement(SQL_SEL_SHIPPERS);
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
	
	public Shipper getShipper(long shipperID) {
        for (Shipper s : getshippers()) {
            if (s.getShipperID() == shipperID) {
                return s;
            }
        }
        return null;
    }
	

}
