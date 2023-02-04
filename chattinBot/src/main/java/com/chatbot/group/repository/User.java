package com.chatbot.group.repository;

public class User {

    String userId;
    String userName;
    double amount;
    int timestamp;

    public User(String userId, String userName, double amount, int timestamp) {
        this.userId = userId;
        this.userName = userName;
        this.amount = amount;
        this.timestamp = timestamp;
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

    public int getTimestamp() { return timestamp; }

    public void setTimestamp(int timestamp) { this.timestamp = timestamp; }
}
