package entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDetails {

	private final long orderID;
	private final long productID;

	 private String productName;

    private int quantity;
    private float discount;


    private BigDecimal unitPrice;
    private BigDecimal linePrice;


    public static final int MAX_PRODUCT_ID = 10;
    public static final int MAX_QUANTITY = 10;
    public static final int MAX_DISCOUNT = 3;


    public OrderDetails(long orderID, long productID,String productName, int quantity, float discount,
    		BigDecimal unitPrice, BigDecimal linePrice) {
		super();
		this.orderID = orderID;
		this.productID = productID;
		this.quantity = quantity;
		this.discount = discount;
		this.productName = productName;
		this.unitPrice = unitPrice.setScale(2);
		
		this.linePrice = linePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public OrderDetails(long orderID,long productID) {
		super();
		this.orderID = orderID;
		this.productID = productID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (orderID ^ (orderID >>> 32));
		result = prime * result + (int) (productID ^ (productID >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetails other = (OrderDetails) obj;
		if (orderID != other.orderID)
			return false;
		if (productID != other.productID)
			return false;
		return true;
	}

	public long getOrderID() {
		return orderID;
	}


	public long getProductID() {
		return productID;
	}


	public int getQuantity() {
		return quantity;
	}


	public float getDiscount() {
		return discount;
	}


	public String getProductName() {
		return productName;
	}


	public BigDecimal getUnitPrice() {
		return unitPrice;
	}


	public BigDecimal getLinePrice() {
		return linePrice;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public void setDiscount(float discount) {
		this.discount = discount;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}


	public void setLinePrice(BigDecimal linePrice) {
		this.linePrice = linePrice;
	}
	@Override
	public String toString() {
		return "OrderDetails [orderID=" + orderID + ", productID=" + productID + ", quantity=" + quantity
				+ ", discount=" + discount + ", productName=" + productName + ", unitPrice=" + unitPrice
				+ ", linePrice=" + linePrice + "]";
	}


}
