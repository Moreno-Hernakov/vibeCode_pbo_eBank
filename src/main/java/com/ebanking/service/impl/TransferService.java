package com.ebanking.service.impl;

import com.ebanking.model.User;
import com.ebanking.service.BaseTransaction;

public class TransferService extends BaseTransaction {
    private String destinationAccount;

    public TransferService(User user, double amount, String description, String destinationAccount) {
        super(user, amount, description);
        this.destinationAccount = destinationAccount;
    }

    @Override
    public boolean validate() {
        // Business logic validation
        return amount > 0 && destinationAccount != null && !destinationAccount.isEmpty();
    }

    @Override
    public void execute() {
        if (validate()) {
            System.out.println("Executing Transfer of " + amount + " to " + destinationAccount);
            // Logic to update database via DAO would go here
        } else {
            System.out.println("Transfer validation failed!");
        }
    }

    // Getter & Setter
    public String getDestinationAccount() { return destinationAccount; }
    public void setDestinationAccount(String destinationAccount) { this.destinationAccount = destinationAccount; }
}
