package entity;

public class Customer {

	private String customerID;
	private String CompanyName;


	public Customer(String customerID) {
		super();
		this.customerID = customerID;
	}
	public String getCustomerID() {
		return customerID;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", CompanyName=" + CompanyName + "]";
	}
	public Customer(String customerID, String companyName) {
		super();
		this.customerID = customerID;
		this.CompanyName = companyName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerID == null) ? 0 : customerID.hashCode());
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
		Customer other = (Customer) obj;
		if (customerID == null) {
			if (other.customerID != null)
				return false;
		} else if (!customerID.equals(other.customerID))
			return false;
		return true;
	}
}
