package com.chatbot.group.repository;

import java.sql.*;

public class DrinkRepository {
    private Connection connection;

    public DrinkRepository() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/drink_app",
                    "root", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(String userId, double amount) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update users set amount = " + amount + " where user_Id = '" + userId + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String userId, double amount) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into users values (" + userId + "," + amount + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findUser(String userId) {
        User user = new User();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select * from users where user_Id = " + userId);
            if (set.next()) {
                user.setUserId(set.getString("user_Id"));
                user.setAmount(set.getDouble("amount"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}