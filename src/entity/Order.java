package entity;

import java.util.Date;

public class Order {
	private final long orderID;
	
	private Customer customer;
 	private Employee employee;


    private Date orderDate;
    private Date requiredDate;
    private Date shippedDate;

    private Shipper shipVia;
    private String shipAddress;
    private String shipCity;
    private String shipCountry;


    public static final int MAX_CUSTOMER_ID = 5;
    public static final int MAX_EMPLOYEE_ID = 10;
    public static final int MAX_SHIP_VIA = 10;

    public static final int MAX_SHIP_COUNTRY = 15;
    public static final int MAX_SHIP_ADDRESS = 60;
    public static final int MAX_SHIP_CITY = 15;


	public Order(long orderID, Customer customer, Employee employee, Date orderDate, Date requiredDate, Date shippedDate,
			Shipper shipVia, String shipAddress, String shipCity, String shipCountry) {
		super();
		this.orderID = orderID;
		this.customer = customer;
		this.employee = employee;
		this.orderDate = orderDate;
		this.requiredDate = requiredDate;
		this.shippedDate = shippedDate;
		this.shipVia = shipVia;
		this.shipAddress = shipAddress;
		this.shipCity = shipCity;
		this.shipCountry = shipCountry;
	}

	public Order(long orderID, String customerID, long employeeID, Date orderDate, Date requiredDate, Date shippedDate,
			int shipVia, String shipAddress, String shipCity, String shipCountry) {
		this(orderID, customerID != null ? new Customer(customerID) : null,
				new Employee(employeeID), orderDate, requiredDate, shippedDate,
				new Shipper(shipVia), shipAddress, shipCity, shipCountry);
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
		return "Order [orderID=" + orderID + ", customer=" + customer + ", employee=" + employee
				+ ", orderDate=" + orderDate + ", requiredDate=" + requiredDate + ", shippedDate=" + shippedDate
				+ ", shipVia=" + shipVia + ", shipAddress=" + shipAddress + ", shipCity=" + shipCity + ", shipCountry="
				+ shipCountry + "]";
	}


	public Customer getcustomer() {
		return customer;
	}
	public void setcustomer(Customer customer) {
		this.customer = customer;
	}
	public String getCustomerID() {
		return customer != null ? customer.getCustomerID() : null;
	}
	public void setCustomerID(String customerID) {
		this.customer = customerID != null ? new Customer(customerID) : null;
	}
	public Employee getemployee() {
		return employee;
	}
	public void setemployee(Employee employee) {
		this.employee = employee;
	}
	public Long getEmployeeID() {
		return employee != null ? employee.getEmployeeID() : null;
	}
	public void setEmployeeID(long employeeID) {
		this.employee = new Employee(employeeID);
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
	public Shipper getShipVia() {
		return shipVia;
	}
	public void setShipVia(Shipper shipVia) {
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