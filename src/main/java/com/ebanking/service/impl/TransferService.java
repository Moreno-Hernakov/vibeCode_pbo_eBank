package com.ebanking.service.impl;

import com.ebanking.dao.TransactionDAO;
import com.ebanking.model.Account;
import com.ebanking.service.BaseTransaction;
import javax.swing.JOptionPane;

public class TransferService extends BaseTransaction {

    private String sourceAccount;

    private String destinationAccount;
    private String featureCode;

    public TransferService(
            Account account,
            String sourceAccount,
            String destinationAccount,
            double amount,
            String description,
            String featureCode
    ) {

        super(account, amount, description);

        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.featureCode = featureCode;
    }

    @Override
    public boolean validate() {

        return amount > 0
                && sourceAccount != null
                && destinationAccount != null
                && !sourceAccount.isEmpty()
                && !destinationAccount.isEmpty()
                && !featureCode.isEmpty();
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
            this.featureCode,
            user.getCifNumber(),
            "127.0.0.1"
    );

    if (success) {
        JOptionPane.showMessageDialog(null, "Transfer Berhasil!");

    } else {
        JOptionPane.showMessageDialog(null, "Transfer Gagal");
    }
}

    // Getter Setter
    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }
    
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