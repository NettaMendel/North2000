/**
 * 
 */
package entity;

import java.util.Objects;

/**
 * 
 */
public class Supplier {
	final long SupplierID;
	final String companyName;
	String contactName, contactTitle, 
				address, city, country, phone, fax, HomePage;
	public Supplier(long supplierID, String companyName, String contactName, String contactTitle, String address,
			String city, String country, String phone, String fax, String homePage) {
		super();
		SupplierID = supplierID;
		this.companyName = companyName;
		this.contactName = contactName;
		this.contactTitle = contactTitle;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.fax = fax;
		HomePage = homePage;
	}

	public Supplier(long supplierID) {
		this(supplierID, "", null, null, null, null, null, null, null, null);
	}

	public Supplier(long supplierID, String companyName) {
		this(supplierID, companyName, null, null, null, null, null, null, null, null);
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
	 * @return the homePage
	 */
	public String getHomePage() {
		return HomePage;
	}
	/**
	 * @param homePage the homePage to set
	 */
	public void setHomePage(String homePage) {
		HomePage = homePage;
	}
	/**
	 * @return the supplierID
	 */
	public long getSupplierID() {
		return SupplierID;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(HomePage, SupplierID, address, city, companyName, contactName, contactTitle, country, fax,
				phone);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supplier other = (Supplier) obj;
		return Objects.equals(HomePage, other.HomePage) && SupplierID == other.SupplierID
				&& Objects.equals(address, other.address) && Objects.equals(city, other.city)
				&& Objects.equals(companyName, other.companyName) && Objects.equals(contactName, other.contactName)
				&& Objects.equals(contactTitle, other.contactTitle) && Objects.equals(country, other.country)
				&& Objects.equals(fax, other.fax) && Objects.equals(phone, other.phone);
	}
	@Override
	public String toString() {
		return "Supplier [SupplierID=" + SupplierID + ", companyName=" + companyName + ", contactName=" + contactName
				+ ", contactTitle=" + contactTitle + ", address=" + address + ", city=" + city + ", country=" + country
				+ ", phone=" + phone + ", fax=" + fax + ", HomePage=" + HomePage + "]";
	}
	

}
