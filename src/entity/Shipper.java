package entity;

public class Shipper {
    private final long shipperID;
    private String companyName;
	public Shipper(long shipperID, String companyName) {
		super();
		this.shipperID = shipperID;
		this.companyName = companyName;
	}

	public Shipper(long shipperID) {
		super();
		this.shipperID = shipperID;
	}

	public long getShipperID() {
		return shipperID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (shipperID ^ (shipperID >>> 32));
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
		Shipper other = (Shipper) obj;
		if (shipperID != other.shipperID)
			return false;
		return true;
	}

}
