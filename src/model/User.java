package model;
import java.util.Date;

public class User {
    private int userId;
    private String name;
    private Date dob;
    private String email;
    private String phoneNo;
    private String username;
    private String password;
    private String address;
    private String cardType; // "gold" or "platinum"
    private String bankName;
    private String accountNo;
    private String ifscCode;
    private double totalCredit;
    private double usedCredit;
    private boolean isActive;
    
	public User(int userId, String name, Date dob, String email, String phoneNo, String username, String password,
			String address, String cardType, String bankName, String accountNo, String ifscCode, double totalCredit,
			double usedCredit, boolean isActive) {
		super();
		this.userId = userId;
		this.name = name;
		this.dob = dob;
		this.email = email;
		this.phoneNo = phoneNo;
		this.username = username;
		this.password = password;
		this.address = address;
		this.cardType = cardType;
		this.bankName = bankName;
		this.accountNo = accountNo;
		this.ifscCode = ifscCode;
		this.totalCredit = totalCredit;
		this.usedCredit = usedCredit;
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", dob=" + dob + ", email=" + email + ", phoneNo="
				+ phoneNo + ", username=" + username + ", password=" + password + ", address=" + address + ", cardType="
				+ cardType + ", bankName=" + bankName + ", accountNo=" + accountNo + ", ifscCode=" + ifscCode
				+ ", totalCredit=" + totalCredit + ", usedCredit=" + usedCredit + ", isActive=" + isActive + "]";
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public double getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(double totalCredit) {
		this.totalCredit = totalCredit;
	}

	public double getUsedCredit() {
		return usedCredit;
	}

	public void setUsedCredit(double usedCredit) {
		this.usedCredit = usedCredit;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
