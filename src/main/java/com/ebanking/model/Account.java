package com.ebanking.model;

public class Account {
    private String accountNumber;
    private String cifNumber;
    private int productTypeId;
    private double balance;
    private String status;

    public Account() {}

    public Account(String accountNumber, String cifNumber, int productTypeId, double balance, String status) {
        this.accountNumber = accountNumber;
        this.cifNumber = cifNumber;
        this.productTypeId = productTypeId;
        this.balance = balance;
        this.status = status;
    }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getCifNumber() { return cifNumber; }
    public void setCifNumber(String cifNumber) { this.cifNumber = cifNumber; }

    public int getProductTypeId() { return productTypeId; }
    public void setProductTypeId(int productTypeId) { this.productTypeId = productTypeId; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
