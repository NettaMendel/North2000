package control;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Product;

public class ProductLogic {
	private static ProductLogic instance;

	private ProductLogic() {
	}

	public static ProductLogic getInstance() {
		if (instance == null)
			instance = new ProductLogic();
		return instance;
	}

	public ArrayList<Product> getProducts() {
		ArrayList<Product> results = new ArrayList<Product>();

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_PRODUCTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Product(Long.parseLong(rs.getString(i++)), rs.getString(i++),
							Long.parseLong(rs.getString(i++)), Long.parseLong(rs.getString(i++)),
							new BigDecimal(rs.getString(i++)), Integer.parseInt(rs.getString(i++))));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return results;
	}

	public Product getProduct(long productID) {
		for (Product p : getProducts()) {
			if (p.getProductID() != null && p.getProductID() == productID) {
				return p;
			}
		}
		return null;
	}
}
