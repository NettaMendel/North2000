package entity;

public class Customer {
	final String CUSTOMER_ID, companyName;
	String contactName, contactTitle, 
				address, city, country, phone, fax;
	
	public Customer(String customerID, String companyName, 
			String contactName, String contactTitle, 
			String address, String city, String country, 
			String phone, String fax) {
		if (customerID == null || customerID.isEmpty())
			throw new IllegalArgumentException("customerID is required!");
		this.CUSTOMER_ID = customerID;
		if (companyName == null || companyName.isEmpty())
			throw new IllegalArgumentException("companyName is required!");
		this.companyName = companyName;
		this.contactName = contactName;
		this.contactTitle = contactTitle;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.fax = fax;
	}
	
	public Customer(String customerID, String companyName) {
		if (customerID == null || customerID.isEmpty())
			throw new IllegalArgumentException("customerID is required!");
		this.CUSTOMER_ID = customerID;
		if (companyName == null || companyName.isEmpty())
			throw new IllegalArgumentException("companyName is required!");
		this.companyName = companyName;
		
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the contactTitle
	 */
	public String getContactTitle() {
		return contactTitle;
	}

	/**
	 * @param contactTitle the contactTitle to set
	 */
	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the customerID
	 */
	public String getCustomerID() {
		return CUSTOMER_ID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CUSTOMER_ID == null) ? 0 : CUSTOMER_ID.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (CUSTOMER_ID == null) {
			if (other.CUSTOMER_ID != null)
				return false;
		} else if (!CUSTOMER_ID.equals(other.CUSTOMER_ID))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
				"Customer [CUSTOMER_ID=%s, companyName=%s, contactName=%s, contactTitle=%s, address=%s, city=%s, country=%s, phone=%s, fax=%s]",
				CUSTOMER_ID, companyName, contactName, contactTitle, address, city, country, phone, fax);
	}
}
