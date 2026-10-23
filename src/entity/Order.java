package entity;

import java.util.Date;

public class Order {
	private final long orderID;
	private String customerID;
 	private long employeeID;


    private Date orderDate;
    private Date requiredDate;
    private Date shippedDate;

    private int shipVia;
    private String shipAddress;
    private String shipCity;
    private String shipCountry;


    public static final int MAX_CUSTOMER_ID = 5;
    public static final int MAX_EMPLOYEE_ID = 10;
    public static final int MAX_SHIP_VIA = 10;

    public static final int MAX_SHIP_COUNTRY = 15;
    public static final int MAX_SHIP_ADDRESS = 60;
    public static final int MAX_SHIP_CITY = 15;


	public Order(long orderID, String customerID, long employeeID, Date orderDate, Date requiredDate, Date shippedDate,
			int shipVia, String shipAddress, String shipCity, String shipCountry) {
		super();
		this.orderID = orderID;
		this.customerID = customerID;
		this.employeeID = employeeID;
		this.orderDate = orderDate;
		this.requiredDate = requiredDate;
		this.shippedDate = shippedDate;
		this.shipVia = shipVia;
		this.shipAddress = shipAddress;
		this.shipCity = shipCity;
		this.shipCountry = shipCountry;
	}


	public Order(long orderID) {
		super();
		this.orderID = orderID;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (orderID ^ (orderID >>> 32));
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
		Order other = (Order) obj;
		if (orderID != other.orderID)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", customerID=" + customerID + ", employeeID=" + employeeID
				+ ", orderDate=" + orderDate + ", requiredDate=" + requiredDate + ", shippedDate=" + shippedDate
				+ ", shipVia=" + shipVia + ", shipAddress=" + shipAddress + ", shipCity=" + shipCity + ", shipCountry="
				+ shipCountry + "]";
	}


	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public long getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(long employeeID) {
		this.employeeID = employeeID;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getRequiredDate() {
		return requiredDate;
	}
	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}
	public Date getShippedDate() {
		return shippedDate;
	}
	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}
	public int getShipVia() {
		return shipVia;
	}
	public void setShipVia(int shipVia) {
		this.shipVia = shipVia;
	}
	public String getShipAddress() {
		return shipAddress;
	}
	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}
	public String getShipCity() {
		return shipCity;
	}
	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}
	public String getShipCountry() {
		return shipCountry;
	}
	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}
	public long getOrderID() {
		return orderID;
	}




}