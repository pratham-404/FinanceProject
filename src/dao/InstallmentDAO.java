package dao;

import model.Installment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstallmentDao {
    private Connection connection;

    public InstallmentDao(Connection connection) {
        this.connection = connection;
    }

    // Method to create a new installment
    public boolean createInstallment(Installment installment) {
        String query = "INSERT INTO installments (purchase_id, installment_due_date, amount, payment_status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, installment.getPurchaseId());
            pstmt.setTimestamp(2, new Timestamp(installment.getInstallmentDueDate().getTime()));
            pstmt.setDouble(3, installment.getAmount());
            pstmt.setString(4, installment.getPaymentStatus());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve all installments by user_id, sorted by due_date
    public List<Installment> getInstallmentsByUserId(String userId) {
        List<Installment> installments = new ArrayList<>();
        String query = "SELECT i.* FROM installments i " +
                       "JOIN purchases p ON i.purchase_id = p.purchase_id " +
                       "WHERE p.user_id = ? " +
                       "ORDER BY i.installment_due_date ASC";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Installment installment = new Installment(rs.getInt("installment_id"), rs.getInt("purchase_id"), rs.getTimestamp("installment_due_date"), rs.getDouble("amount"),rs.getString("payment_status"), rs.getDate("payment_date"));
                installment.setInstallmentId(rs.getInt("installment_id"));
                installment.setPaymentDate(rs.getTimestamp("payment_date"));

                installments.add(installment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return installments;
    }
}
