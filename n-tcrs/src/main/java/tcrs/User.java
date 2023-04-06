package tcrs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class User {
    private String username;
    private String password;
    private String type;
    private String status;//default false

    public boolean isValid; 

     // SQL Server and Credentials
     private static String url = "jdbc:mysql://localhost:3306/project";
     private static String usernameServer = "root";
     private static String passwordServer = "test";



    public User(String username, String password, String type, String status) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.status = status;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters for all fields

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static User searchUser(String searchUsername) throws Exception {
        String query = "SELECT * FROM Users WHERE Username = ?";
        User user = null;

        try (Connection conn = DatabaseUtils.getConnection()) {
            PreparedStatement preparedStatement = null;
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, searchUsername);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                String type = resultSet.getString("Type");
                String status = resultSet.getString("Status");

                user = new User(username, password, type, status);
            }
        } catch (SQLException e) {
            System.out.println("Error searching user: " + e.getMessage());
        }

        return user;
    }
    //Save information in database
    public void saveUser() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, usernameServer, passwordServer)) {
            String insertUserSql = "INSERT INTO Users (Username, Password, Type, Status, ) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertUserSql);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.password);
            preparedStatement.setString(3, this.type);
            preparedStatement.setString(4, this.status);

            preparedStatement.executeUpdate();
        }
    }

    // Update User
    public void updateUser() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, usernameServer, passwordServer)) {
            String insertUserSql = "UPDATE Users SET Username = ?, Password = ?, Type = ?, Status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(insertUserSql);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.password);
            preparedStatement.setString(3, this.type);
            preparedStatement.setString(4, this.status);

            preparedStatement.executeUpdate();
        }
    }

    //List all users in database
    public static List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();

        try (Connection connection = DatabaseUtils.getConnection()) {
            String selectUserSql = "SELECT * FROM Users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectUserSql);

            while (resultSet.next()) {
                String userName = resultSet.getString("Username");
                String user_password = resultSet.getString("Password");
                String type = resultSet.getString("Type");
                String activeStatus = resultSet.getString("Status");

                User user = new User (userName, user_password, type, activeStatus);
                users.add(user);
            }
        }
        return users;
    }

    //Delete this User
    public void deleteUser() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, usernameServer, passwordServer)) {
            String deleteUserSql = "DELETE FROM Users WHERE Username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteUserSql);
            preparedStatement.setString(1, this.username);

            preparedStatement.executeUpdate();
        }
    }
}
