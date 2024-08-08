import java.util.Date;

public class Purchase {
    private int purchaseId;
    private int userId;
    private int productId;
    private String emiPeriod; // "3 months", "6 months", "9 months", "12 months"
    private Date purchaseDate;
    private double totalAmount;
    private double amountPaid;
    private int installmentCount;
    private double installmentAmount;
    private String paymentStatus; // "completed", "pending", "failed"
    
	public Purchase(int purchaseId, int userId, int productId, String emiPeriod, Date purchaseDate, double totalAmount,
			double amountPaid, int installmentCount, double installmentAmount, String paymentStatus) {
		super();
		this.purchaseId = purchaseId;
		this.userId = userId;
		this.productId = productId;
		this.emiPeriod = emiPeriod;
		this.purchaseDate = purchaseDate;
		this.totalAmount = totalAmount;
		this.amountPaid = amountPaid;
		this.installmentCount = installmentCount;
		this.installmentAmount = installmentAmount;
		this.paymentStatus = paymentStatus;
	}

	@Override
	public String toString() {
		return "Purchase [purchaseId=" + purchaseId + ", userId=" + userId + ", productId=" + productId + ", emiPeriod="
				+ emiPeriod + ", purchaseDate=" + purchaseDate + ", totalAmount=" + totalAmount + ", amountPaid="
				+ amountPaid + ", installmentCount=" + installmentCount + ", installmentAmount=" + installmentAmount
				+ ", paymentStatus=" + paymentStatus + "]";
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getEmiPeriod() {
		return emiPeriod;
	}

	public void setEmiPeriod(String emiPeriod) {
		this.emiPeriod = emiPeriod;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public int getInstallmentCount() {
		return installmentCount;
	}

	public void setInstallmentCount(int installmentCount) {
		this.installmentCount = installmentCount;
	}

	public double getInstallmentAmount() {
		return installmentAmount;
	}

	public void setInstallmentAmount(double installmentAmount) {
		this.installmentAmount = installmentAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}
