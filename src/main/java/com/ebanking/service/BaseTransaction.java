package com.ebanking.service;

import com.ebanking.model.Account;
import com.ebanking.model.User;

public abstract class BaseTransaction implements IValidatable {
    protected Account user;
    protected double amount;
    protected String description;

    public BaseTransaction(Account user, double amount, String description) {
        this.user = user;
        this.amount = amount;
        this.description = description;
    }

    public abstract void execute();

    // Getters & Setters
    public Account getUser() { return user; }
    public void setUser(Account user) { this.user = user; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
