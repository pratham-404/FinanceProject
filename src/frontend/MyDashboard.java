package frontend;
import model.Admin;
import model.Installment;
import model.Purchase;
import model.User;

import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import dao.DatabaseConnection;
import dao.InstallmentDAO;
import dao.PurchaseDAO;
import java.util.List;

public class MyDashboard {
	private Connection connection;
    private PurchaseDAO purchaseDAO;
    private InstallmentDAO installmentDAO;

    // Constructor to initialize DAOs
    public MyDashboard() {
    	Connection connection = DatabaseConnection.getConnection();
        this.purchaseDAO = new PurchaseDAO(connection);
        this.installmentDAO = new InstallmentDAO(connection);
    }
	
	Line l = new Line();
	Scanner sc = new Scanner(System.in);
	
	// Method to display user details
    public void display(User user, Admin isAdmin) {
        // DateTimeFormatter to format LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Print user details
        System.out.println("\t\t\t<- "+user.getName()+" -> ");
        l.line(90);
        System.out.println("Name: " + user.getName());
        System.out.println("Date of Birth: " + user.getDob().format(formatter));
        System.out.println("Email: " + user.getEmail());
        System.out.println("Phone Number: " + user.getPhoneNo());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Address: " + user.getAddress());
        System.out.println("Card Type: " + user.getCardType());
        System.out.println("Bank Name: " + user.getBankName());
        System.out.println("Account Number: " + user.getAccountNo());
        System.out.println("IFSC Code: " + user.getIfscCode());
        System.out.println("Total Credit: " + user.getTotalCredit());
        System.out.println("Used Credit: " + user.getUsedCredit());
        System.out.println("Active: " + (user.isActive() ? "Yes" : "No"));
        
        // Fetch and display purchase history
        displayPurchaseHistory(user.getUserId());
        
        // Fetch and display installment history
        displayInstallmentHistory(user.getUserId());
        
        while(true) {
        	System.out.println("Options: ");
            System.out.println("1. Go Back ");
            System.out.println("2. Exit ");
            System.out.print("Enter your choice: ");
        	int option = sc.nextInt();
            switch(option) {
            case 1:
            	HomePage hm = new HomePage();
            	hm.display(user, isAdmin);
            	break;
            case 2:
            	System.exit(0);
            	break;
            default:
            	System.out.println("Please enter a valid input :) \n");
            	break;
            }
        }
    }
 // Method to display purchase history
    private void displayPurchaseHistory(int userId) {
        System.out.println("\n\nPurchase History:");
        l.line(50);
        List<Purchase> purchases = purchaseDAO.readAllPurchasesByUserId(userId);
        if (purchases.isEmpty()) {
            System.out.println("No purchases found.");
        } else {
            for (Purchase purchase : purchases) {
                System.out.println("Purchase ID: " + purchase.getPurchaseId());
                System.out.println("Product ID: " + purchase.getProductId());
                System.out.println("EMI Period: " + purchase.getEmiPeriod());
                System.out.println("Purchase Date: " + new SimpleDateFormat("dd/MM/yyyy").format(purchase.getPurchaseDate()));
                System.out.println("Total Amount: " + purchase.getTotalAmount());
                System.out.println("Amount Paid: " + purchase.getAmountPaid());
                System.out.println("Installment Count: " + purchase.getInstallmentCount());
                System.out.println("Installment Amount: " + purchase.getInstallmentAmount());
                System.out.println("Payment Status: " + purchase.getPaymentStatus());
                System.out.println("------------------------------------------------");
            }
        }
    }

    // Method to display installment history
    private void displayInstallmentHistory(int userId) {
        System.out.println("\n\nInstallment History:");
        l.line(50);
        List<Installment> installments = installmentDAO.getInstallmentsByUserId(String.valueOf(userId));
        if (installments.isEmpty()) {
            System.out.println("No installments found.");
        } else {
            for (Installment installment : installments) {
                System.out.println("Installment ID: " + installment.getInstallmentId());
                System.out.println("Purchase ID: " + installment.getPurchaseId());
                System.out.println("Due Date: " + new SimpleDateFormat("dd/MM/yyyy").format(installment.getInstallmentDueDate()));
                System.out.println("Amount: " + installment.getAmount());
                System.out.println("Payment Status: " + installment.getPaymentStatus());
                System.out.println("Payment Date: " + (installment.getPaymentDate() != null ? new SimpleDateFormat("dd/MM/yyyy").format(installment.getPaymentDate()) : "Not Paid"));
                System.out.println("------------------------------------------------");
            }
        }
    }

}
