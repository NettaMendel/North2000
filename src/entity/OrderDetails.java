package entity;

import java.math.BigDecimal;
import java.util.Date;

import control.OrderLogic;

public class OrderDetails {

	//private final long orderID;
	//private final long productID;
	
	private final Order order;
	private final Product product;

    private int quantity;
    private float discount;


    private BigDecimal unitPrice;
    private BigDecimal linePrice;


    public static final int MAX_PRODUCT_ID = 10;
    public static final int MAX_QUANTITY = 10;
    public static final int MAX_DISCOUNT = 3;


    public OrderDetails(Order order, Product product,String productName, int quantity, float discount,
    		BigDecimal unitPrice, BigDecimal linePrice) {
		super();
		//this.orderID = orderID;
		//this.productID = productID;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.discount = discount;
		this.unitPrice = unitPrice.setScale(2);
		
		this.linePrice = linePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public OrderDetails(Order order,Product product) {
		super();
		this.order = order;
		this.product = product;
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
		if (order.equals(other.order))
			return false;
		if (product.equals(other.product))
			return false;
		return true;
	}

	public Order getOrder() {
		return order;
	}


	public Product getProduct() {
		return product;
	}


	public int getQuantity() {
		return quantity;
	}


	public float getDiscount() {
		return discount;
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


	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}


	public void setLinePrice(BigDecimal linePrice) {
		this.linePrice = linePrice;
	}
	@Override
	public String toString() {
		return "OrderDetails [orderID=" + order.toString() + ", productID=" + product.toString() + ", quantity=" + quantity
				+ ", discount=" + discount + ", unitPrice=" + unitPrice
				+ ", linePrice=" + linePrice + "]";
	}


}
