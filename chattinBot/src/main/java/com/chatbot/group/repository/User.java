package com.chatbot.group.repository;

public class User {

    String userId;
    double amount;

    public User(String userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }
    public User() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
