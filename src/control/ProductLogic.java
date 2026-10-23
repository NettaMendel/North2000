package control;

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
		ArrayList<Product> result = new ArrayList<>();

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SEL_PRODUCTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					result.add(new Product(rs.getInt(i++), rs.getString(i)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return result;
	}
}
