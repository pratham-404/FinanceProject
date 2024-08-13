package dao;

import model.User;
import org.mindrot.jbcrypt.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    // Create a new user
    public boolean createUser(User user) {
        String insertUserSQL = "INSERT INTO users (name, dob, email, phone_no, username, password, address, card_type, bank_name, account_no, ifsc_code, total_credit, used_credit, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(user.getDob()));
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhoneNo());
            preparedStatement.setString(5, user.getUsername());
            preparedStatement.setString(6, hashPassword(user.getPassword())); // Hash the password
            preparedStatement.setString(7, user.getAddress());
            preparedStatement.setString(8, user.getCardType());
            preparedStatement.setString(9, user.getBankName());
            preparedStatement.setString(10, user.getAccountNo());
            preparedStatement.setString(11, user.getIfscCode());
            preparedStatement.setFloat(12, user.getTotalCredit()); // Store total credit
            preparedStatement.setFloat(13, user.getUsedCredit()); // Store used credit
            preparedStatement.setBoolean(14, user.isActive()); // Store activation status

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if the user was created
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Verify user name and password
    public User verifyUsernameAndPassword(String username, String password) {
        String queryUserSQL = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryUserSQL)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    // Create a User object and return it if the password matches
                    return mapResultSetToUser(resultSet);
                }
                else {
                	return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if login fails
    }

    // Verify user name and phone number
    public boolean verifyUsernameAndPhone(String username, String phoneNo) {
        String queryUserSQL = "SELECT * FROM users WHERE username = ? AND phone_no = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryUserSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, phoneNo);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // Returns true if a user is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update user password
    public boolean updatePassword(String username, String newPassword) {
        String updatePasswordSQL = "UPDATE users SET password = ? WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updatePasswordSQL)) {
            preparedStatement.setString(1, hashPassword(newPassword)); // Hash the new password
            preparedStatement.setString(2, username);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if the password was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read user card details
    public User readCardDetails(int userId) {
        String queryUserSQL = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryUserSQL)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if user not found
    }

    // 2
    // Read all inactive users 
    public List<User> readAllInactiveUsers() {
        List<User> inactiveUsers = new ArrayList<>();
        String querySQL = "SELECT * FROM users WHERE is_active = FALSE";
        try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                inactiveUsers.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inactiveUsers; // Return the list of inactive users
    }

    // 3
    // Update user activation
    public  boolean updateActivation(int userId, boolean isActive) {
        String updateActivationSQL = "UPDATE users SET is_active = ? WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateActivationSQL)) {
            preparedStatement.setBoolean(1, isActive);
            preparedStatement.setInt(2, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if the activation status was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 1
    // Read all users
    public List<User> readAllUsers() {
        List<User> users = new ArrayList<>();
        String querySQL = "SELECT * FROM users";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users; // Return the list of all users
    }

    // Deactivate user account
    public boolean deactivateUser(int userId) {
        return updateActivation(userId, false); // Set isActive to false
    }

    // Helper method to hash passwords
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Helper method to map ResultSet to User object
    private static User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User(
                resultSet.getString("name"),
                resultSet.getDate("dob").toLocalDate(),
                resultSet.getString("email"),
                resultSet.getString("phone_no"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("address"),
                resultSet.getString("card_type"),
                resultSet.getString("bank_name"),
                resultSet.getString("account_no"),
                resultSet.getString("ifsc_code")
        );
        user.setUserId(resultSet.getInt("user_id"));
        user.setTotalCredit(resultSet.getFloat("total_credit"));
        user.setUsedCredit(resultSet.getFloat("used_credit"));
        user.setActive(resultSet.getBoolean("is_active"));
        return user;
    }
    
    public boolean updateUserCredit(int userId, float newUsedCredit) {
        String updateCreditSQL = "UPDATE users SET used_credit = ? WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateCreditSQL)) {
            preparedStatement.setFloat(1, newUsedCredit);
            preparedStatement.setInt(2, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if the credit was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
