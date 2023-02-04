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

    public void updateUser(String userId, double amount, int timestamp) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update users set amount = " + amount + " where user_Id = '" + userId + "'");
            statement.executeUpdate("update users set date = " + timestamp + " where user_Id = '" + userId + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetUser(String userId) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update users set amount = " + 0 + " where user_Id = '" + userId + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String userId, String userName, double amount, int timestamp) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into users values (" + userId + "," + "'" + userName + "'" + "," + amount + "," + 0 + ")");
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
                user.setUserName(set.getString("user_Name"));
                user.setAmount(set.getDouble("amount"));
                user.setTimestamp(set.getInt("date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public String getTop() {
        String top = "";
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select * from users order by amount desc limit 10");
            while (set.next()) {
                top += set.getString("user_Name") + " - " + set.getDouble("amount") + " літрів\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return top;
    }

    public String getPublicTop() {
        String top = "";
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select * from users order by amount desc limit 100");
            while (set.next()) {
                top += set.getString("user_Name") + " - " + set.getDouble("amount") + " літрів\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return top;
    }
}
