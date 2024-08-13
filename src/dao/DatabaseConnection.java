package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/finance_app"; // Update with your database URL
        String user = "root"; // Update with your database username
        String password = "root"; // Update with your database password
        //String password = "root"; // Update with your database password

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
