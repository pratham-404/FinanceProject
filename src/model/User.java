package model;
import java.util.Date;
import java.time.LocalDate;

public class User {
    private String name;
    private LocalDate dob;
    private String email;
    private String phoneNo;
    private String username;
    private String password;
    private String address;
    private String cardType;
    private String bankName;
    private String accountNo; 	
    private String ifscCode;
    
    // not used in constructor call
    private int userId;
    private float totalCredit;
    private float usedCredit;
    private boolean isActive;
    
	public User(String name, LocalDate dob2, String email, String phoneNo, String username, String password, String address,
			String cardType, String bankName, String accountNo, String ifscCode) {
		super();
		this.name = name;
		this.dob = dob2;
		this.email = email;
		this.phoneNo = phoneNo;
		this.username = username;
		this.password = password;
		this.address = address;
		this.cardType = cardType;
		this.bankName = bankName;
		this.accountNo = accountNo;
		this.ifscCode = ifscCode;
	}

	public User(String name2, LocalDate dob2, String email2, String phoneNo2, String username2, String password2,
			String address2, String cardType2, String bankName2, String accountNo2, String ifscCode2,
			double totalCredit2, double usedCredit2, boolean isActive2) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", dob=" + dob + ", email=" + email + ", phoneNo=" + phoneNo + ", username="
				+ username + ", address=" + address + ", cardType=" + cardType
				+ ", bankName=" + bankName + ", accountNo=" + accountNo + ", ifscCode=" + ifscCode + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public  LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public float getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(float totalCredit) {
		this.totalCredit = totalCredit;
	}

	public float getUsedCredit() {
		return usedCredit;
	}

	public void setUsedCredit(float usedCredit) {
		this.usedCredit = usedCredit;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setDob(Date date) {
		// TODO Auto-generated method stub
		
	}
}
