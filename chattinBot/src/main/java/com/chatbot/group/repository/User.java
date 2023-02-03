package com.chatbot.group.repository;

public class User {

    String userId;
    String userName;
    double amount;

    public User(String userId, String userName, double amount) {
        this.userId = userId;
        this.userName = userName;
        this.amount = amount;
    }
    public User() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) { this.userName = userName; }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
