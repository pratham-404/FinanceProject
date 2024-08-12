package dao;

import model.Admin;
import org.mindrot.jbcrypt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    private Connection connection;

    public AdminDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to verify username and password
    public Admin verifyUsernameAndPassword(String username, String password) {
        Admin admin = null;
        String query = "SELECT admin_id, username, password FROM admin WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");

                // Verify password (assumes you're using bcrypt)
                if (BCrypt.checkpw(password, storedPassword)) {
                    int adminId = resultSet.getInt("admin_id");
                    admin = new Admin(adminId, username, storedPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
}
