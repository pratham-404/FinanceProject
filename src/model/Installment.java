package model;
import java.util.Date;

public class Installment {
    private int installmentId;
    private int purchaseId;
    private Date installmentDueDate;
    private double amount;
    private String paymentStatus; // "paid", "pending"
    private Date paymentDate;
        
	public Installment(int installmentId, int purchaseId, Date installmentDueDate, double amount, String paymentStatus,
			Date paymentDate) {
		super();
		this.installmentId = installmentId;
		this.purchaseId = purchaseId;
		this.installmentDueDate = installmentDueDate;
		this.amount = amount;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
	}
	
	@Override
	public String toString() {
		return "Installment [installmentId=" + installmentId + ", purchaseId=" + purchaseId + ", installmentDueDate="
				+ installmentDueDate + ", amount=" + amount + ", paymentStatus=" + paymentStatus + ", paymentDate="
				+ paymentDate + "]";
	}
	
	public int getInstallmentId() {
		return installmentId;
	}
	public void setInstallmentId(int installmentId) {
		this.installmentId = installmentId;
	}
	public int getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
	public Date getInstallmentDueDate() {
		return installmentDueDate;
	}
	public void setInstallmentDueDate(Date installmentDueDate) {
		this.installmentDueDate = installmentDueDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
}
