public class Product {
    private int productId;
    private String productName;
    private String productDetails;
    private double cost;
    
	public Product(int productId, String productName, String productDetails, double cost) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDetails = productDetails;
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productDetails=" + productDetails
				+ ", cost=" + cost + "]";
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
}
