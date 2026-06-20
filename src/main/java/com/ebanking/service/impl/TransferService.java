package com.ebanking.service.impl;

import com.ebanking.dao.TransactionDAO;
import com.ebanking.model.Account;
import com.ebanking.service.BaseTransaction;

public class TransferService extends BaseTransaction {

    private String sourceAccount;

    private String destinationAccount;

    public TransferService(
            Account account,
            String sourceAccount,
            String destinationAccount,
            double amount,
            String description
    ) {

        super(account, amount, description);

        this.sourceAccount = sourceAccount;

        this.destinationAccount = destinationAccount;
    }

    @Override
    public boolean validate() {

        return amount > 0
                && sourceAccount != null
                && destinationAccount != null
                && !sourceAccount.isEmpty()
                && !destinationAccount.isEmpty();
    }

    @Override
    public void execute() {

    if (!validate()) {

        System.out.println("Transfer validation failed!");

        return;
    }

    TransactionDAO dao = new TransactionDAO();

    boolean success = dao.fundTransfer(
            user.getAccountNumber(),
            destinationAccount,
            amount,
            "101",
            user.getCifNumber(),
            "127.0.0.1"
    );

    if (success) {

        System.out.println("Transfer berhasil");

    } else {

        System.out.println("Transfer gagal");
    }
}

    // Getter Setter

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
}