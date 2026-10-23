/**
 * 
 */
package entity;

import java.util.Objects;

/**
 * 
 */
public class Category {
	final long CategoryID;
	String CategoryName, Description;
	public Category(long categoryID, String categoryName, String description) {
		super();
		CategoryID = categoryID;
		CategoryName = categoryName;
		Description = description;
	}
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return CategoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}
	/**
	 * @return the categoryID
	 */
	public long getCategoryID() {
		return CategoryID;
	}
	@Override
	public String toString() {
		return "Category [CategoryID=" + CategoryID + ", CategoryName=" + CategoryName + ", Description=" + Description
				+ "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(CategoryID, CategoryName, Description);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return CategoryID == other.CategoryID && Objects.equals(CategoryName, other.CategoryName)
				&& Objects.equals(Description, other.Description);
	}
	
	
}
