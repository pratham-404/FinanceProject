package dao;

import model.Purchase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseDAO {
    private Connection connection;

    public PurchaseDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to read all purchases for a given user ID
    public List<Purchase> readAllPurchasesByUserId(int userId) {
        List<Purchase> purchases = new ArrayList<>();
        String query = "SELECT p.purchase_id, p.user_id, p.product_id, p.emi_period, p.purchase_date, " +
                       "p.total_amount, p.amount_paid, p.installment_count, p.installment_amount, " +
                       "p.payment_status " +
                       "FROM purchases p WHERE p.user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int purchaseId = resultSet.getInt("purchase_id");
                int productId = resultSet.getInt("product_id");
                String emiPeriod = resultSet.getString("emi_period");
                Date purchaseDate = resultSet.getTimestamp("purchase_date");
                double totalAmount = resultSet.getDouble("total_amount");
                double amountPaid = resultSet.getDouble("amount_paid");
                int installmentCount = resultSet.getInt("installment_count");
                double installmentAmount = resultSet.getDouble("installment_amount");
                String paymentStatus = resultSet.getString("payment_status");

                Purchase purchase = new Purchase(purchaseId, userId, productId, emiPeriod, purchaseDate,
                        totalAmount, amountPaid, installmentCount, installmentAmount, paymentStatus);
                purchases.add(purchase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchases;
    }
    
    public boolean createPurchase(Purchase purchase) {
        String insertPurchaseSQL = "INSERT INTO purchases (user_id, product_id, purchase_date, total_amount, amount_paid, emi_period, installment_count, installment_amount, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPurchaseSQL)) {
            preparedStatement.setInt(1, purchase.getUserId());
            preparedStatement.setInt(2, purchase.getProductId());
            preparedStatement.setTimestamp(3, new Timestamp(purchase.getPurchaseDate().getTime()));
            preparedStatement.setDouble(4, purchase.getTotalAmount());
            preparedStatement.setDouble(5, purchase.getAmountPaid());
            preparedStatement.setString(6, purchase.getEmiPeriod());
            preparedStatement.setInt(7, purchase.getInstallmentCount());
            preparedStatement.setDouble(8, purchase.getInstallmentAmount());
            preparedStatement.setString(9, purchase.getPaymentStatus());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if the purchase was created
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
