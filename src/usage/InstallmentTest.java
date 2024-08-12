package usage;

import dao.InstallmentDAO;
import model.Installment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class InstallmentTest {
    private static Connection connection;
    private static InstallmentDAO installmentDao;

    public static void main(String[] args) {
        try {
            // Set up the database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_app", "root", "root");
            installmentDao = new InstallmentDAO(connection);


            // Test retrieving installments by user ID
            String userId = "3"; // Use a valid user ID from your database
            List<Installment> installments = installmentDao.getInstallmentsByUserId(userId);
            System.out.println("Installments for user ID " + userId + ":");
            for (Installment installment : installments) {
                System.out.println(installment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection if it's not null
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
