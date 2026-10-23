package entity;

import java.math.BigDecimal;

public class Product {
    private  long productID;
    private  String productName;
    
    private Supplier supplier;
    private entity.Category category;

    private  BigDecimal unitPrice;
    private  Integer unitsInStock;

    public Product(long productID, String productName, Supplier supplier,
    		entity.Category category,  BigDecimal unitPrice,
            Integer unitsInStock) {
        this.productID = productID;
        this.productName = productName;
        this.supplier = supplier;
        this.category = category;
        this.unitPrice = unitPrice.setScale(2);
        this.unitsInStock = unitsInStock;

    }

    public Product(long productID, String productName, Long supplierID,
            Long categoryID, BigDecimal unitPrice,
            Integer unitsInStock) {
        this(productID, productName,
                supplierID != null ? new Supplier(supplierID) : null,
                categoryID != null ? new entity.Category(categoryID) : null,
                unitPrice, unitsInStock);
    }
    public Product(long productID) {
        this.productID = productID;


    }

    //--------------------------------------------------------------------------
    //--------------------------- GETTERS --------------------------------------
    //--------------------------------------------------------------------------


    /**
     * get product's ID.
     * @return product's ID.
     */
    public Long getProductID() {
        return productID;
    }

    public Product(long productID, String productName) {
		super();
		this.productID = productID;
		this.productName = productName;
	}



	public void setProductID(long productID) {
		this.productID = productID;
	}

	/**
     * get product's name.
     * @return product's name.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * get product's supplier ID.
     * @return product's supplier ID.
     */
    public Supplier getSupplier() {
        return supplier;
    }

    public Long getSupplierID() {
        return supplier != null ? supplier.getSupplierID() : null;
    }

    /**
     * get product's category ID.
     * @return product's category ID.
     */
    public entity.Category getCategory() {
        return category;
    }

    public Long getCategoryID() {
        return category != null ? category.getCategoryID() : null;
    }

    /**
     * get product's unit price.
     * @return product's unit price.
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * get product's units in stock.
     * @return product's units in stock.
     */
    public Integer getUnitsInStock() {
        return unitsInStock;
    }



    //--------------------------------------------------------------------------
    //--------------------------- PROPERTIES -----------------------------------
    //--------------------------------------------------------------------------

    public void setProductName(String productName) {
		this.productName = productName;
	}


    //--------------------------------------------------------------------------
    //--------------------------- OBJECT OVERRIDES -----------------------------
    //--------------------------------------------------------------------------

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (int) (this.productID ^ (this.productID >>> 32));
        return hash;
    }

    // override for finding and selecting in various controls using the key.
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return (this.productID == ((Product) obj).productID);
    }

    @Override
    public String toString() {
        return productName != null ? productName : String.valueOf(productID);
    }
}
