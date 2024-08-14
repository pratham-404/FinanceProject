package frontend;

import model.Admin;
import model.Installment;
import model.Product;
import model.Purchase;
import model.User;
import dao.DatabaseConnection;
import dao.InstallmentDAO;
import dao.ProductDAO;
import dao.PurchaseDAO;
import dao.UserDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


import java.util.Date;


public class ViewProducts {
	Line l = new Line();
	Scanner sc = new Scanner(System.in);
	
	public void display(User isLoggedIn, Admin isAdmin) {
		
		System.out.println("\t\t\t<- Available Products ->");
		l.line(90);
		
		Connection connection = DatabaseConnection.getConnection();
		
		if (connection != null) {
	        // Create an instance of ProductDao
	        UserDAO userDao = new UserDAO(connection);
            ProductDAO productDao = new ProductDAO(connection);
            PurchaseDAO purchaseDao = new PurchaseDAO(connection);
            InstallmentDAO installmentDao = new InstallmentDAO(connection);

	        // Retrieve all products
	        List<Product> products = productDao.readAllProducts();

	        // Print out the products in a formatted card style
	        for (Product product : products) {
	            System.out.println(ProductFormatter.formatProductAsCard(product));
	        }
	        
	        // Prompt user to select a product for more details
	        System.out.println("\nOptions:");
	        System.out.println("1. View Detailed Product.");
	        System.out.println("2. Go Back.");
	        System.out.println("3. Exit");
	        System.out.print("Enter your choice: ");
	        int o = sc.nextInt();
	        switch(o) {
	        case 1:
	        	System.out.print("Enter the ID of the product you wish to see in more detail: ");
	            int productId = sc.nextInt();
	            sc.nextLine(); // Consume newline

	            // Display product details and EMI options
	            Product selectedProduct = getProductById(productId, productDao);
	            if (selectedProduct != null) {
	            	System.out.println();
	            	System.out.println("\t\t\t<- Product Details ->");
	            	l.line(90);
	                displayProductDetails(selectedProduct);

	                // Display EMI options
	                displayEmiOptions(selectedProduct);

	                if (isLoggedIn != null) {
	                    while(true) {
	                    	System.out.println("1. Buy Product");
		                    System.out.println("2. Go back");
		                    System.out.print("Enter your choice: ");
		                    int action = sc.nextInt();
		                    sc.nextLine(); // Consume newline

		                    if (action == 1) {
		                        // Handle product purchase
		                        purchaseProduct(selectedProduct, isLoggedIn);
		                    } else if (action == 2) {
		                        display(isLoggedIn, isAdmin);
		                    }
		                    else {
		                    	System.out.println("Please select a valid option. :) \n");
		                    }
	                    }
	                }
	                else {
	                	while(true) {
	                		System.out.println("1. Go back");
		                    System.out.println("2. Exit");
		                    System.out.print("Enter your choice: ");
		                 
		                    int action = sc.nextInt();
		                    sc.nextLine(); // Consume newline

		                    if (action == 1) {
		                        // Handle product purchase
		                    	display(isLoggedIn, isAdmin);
		                    } else if (action == 2) {
		                        System.exit(0);
		                    }
		                    else {
		                    	System.out.println("Please select a valid option. :)\n");
		                    }
	                	}
	                }
	            } else {
	                System.out.println("Product with ID " + productId + " not found.");
	                System.out.println("Going back....\n");
	                display(isLoggedIn, isAdmin);
	            }


		        // Close the connection
		        try {
		            connection.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        break;
	        case 2:
	        	HomePage hp = new HomePage();
	        	hp.display(isLoggedIn, isAdmin);
	        	break;
	        case 3:
	        	System.exit(0);
	        	break;
	        case 4:
	        	System.out.println("Please enter a valid option. :) \n");
	        	break;
	        }
	    } else {
	        System.out.println("Failed to connect to the database.");
	    }
	}
	
	private User loginUser(UserDAO userDao, Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        // Attempt to log in
        return userDao.verifyUsernameAndPassword(username, password);
    }

    private Product getProductById(int productId, ProductDAO productDao) {
        List<Product> products = productDao.readAllProducts();
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null; // Product not found
    }

    private void displayProductDetails(Product product) {
        System.out.println("Product Details:");
        System.out.println("ID: " + product.getProductId());
        System.out.println("Name: " + product.getProductName());
        System.out.println("Details: " + product.getProductDetails());
        System.out.println("Cost: " + product.getCost());
    }

    private void displayEmiOptions(Product product) {
        double cost = product.getCost();
        System.out.println("EMI Options:");
        System.out.println("\t1. 3 Months: " + (cost / 3));
        System.out.println("\t2. 6 Months: " + (cost / 6));
        System.out.println("\t3. 12 Months: " + (cost / 12));
        System.out.println();
    }

    private void purchaseProduct(Product product, User user ) {
    	Connection connection = DatabaseConnection.getConnection();
    	UserDAO userDao = new UserDAO(connection);
    	PurchaseDAO purchaseDao = new PurchaseDAO(connection);
    	InstallmentDAO installmentDao = new InstallmentDAO(connection);
        if (user.getTotalCredit() - user.getUsedCredit() >= product.getCost()) {
            // Simulate purchase processing
            System.out.println("\nProcessing purchase for: " + product.getProductName()+"\n");
            float newUsedCredit = (float)user.getUsedCredit() + (float)product.getCost();
            user.setUsedCredit(newUsedCredit);
            
            System.out.print("Please select an EMI option: ");
            int emiOption = sc.nextInt();

            // Update user in the database
            boolean updateSuccessful = userDao.updateUserCredit(user.getUserId(), newUsedCredit);
            
            if (updateSuccessful) {
                System.out.println("Purchase successful!");
                
                String [] emiTerms = {"3 months", "6 months", "12 months"};
                int [] emiCounts = {3,6,12};

                // Create a purchase record
                Purchase purchase = new Purchase(
                	    0, // purchaseId will be auto-generated by the database
                	    user.getUserId(),
                	    product.getProductId(),
                	    emiTerms[emiOption-1], // Example EMI period; this should be dynamic based on user selection
                	    new Date(), // Current date as purchase date
                	    product.getCost(),
                	    0, // Amount paid initially; can be updated later
                	    emiCounts[emiOption-1], // Number of installments (e.g., 3 months)
                	    product.getCost() / emiCounts[emiOption-1], // Installment amount
                	    "Pending" // Payment status
                	);
                boolean purchaseCreated = purchaseDao.createPurchase(purchase);

                if (!purchaseCreated) {
                	
                	System.out.println("Failed to create purchase record.");
                    // Create installments
                    //createInstallmentsForPurchase(purchase, installmentDao, emiOption);
                } 
            } else {
                System.out.println("Purchase failed. Please try again.");
            }
        } else {
            System.out.println("Insufficient credit to make the purchase.");
        }
    }

    

}
