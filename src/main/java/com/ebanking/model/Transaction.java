/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 *
 * @author natan
 */

public class Transaction {

    private long idTransaction;
    private String referenceNumber;
    private String cifNumber;
    private String fromAccountNumber;
    private String customerReference;
    private BigDecimal transactionAmount;
    private BigDecimal fee;
    private String transactionStatus;
    private Timestamp transactionDate;
    private String featureCode;
    private String responseCode;
    private String ipaddress;
    private String billerName;
    private String location;
    
    
    // Constructor kosong
    public Transaction() {
    }

    // Constructor lengkap
    public Transaction(long idTransaction, String referenceNumber, String cifNumber,
                       String fromAccountNumber, String customerReference,
                       BigDecimal transactionAmount, BigDecimal fee,
                       String transactionStatus, Timestamp transactionDate,
                       String featureCode, String responseCode,
                       String ipaddress, String billerName, String location) {

        this.idTransaction = idTransaction;
        this.referenceNumber = referenceNumber;
        this.cifNumber = cifNumber;
        this.fromAccountNumber = fromAccountNumber;
        this.customerReference = customerReference;
        this.transactionAmount = transactionAmount;
        this.fee = fee;
        this.transactionStatus = transactionStatus;
        this.transactionDate = transactionDate;
        this.featureCode = featureCode;
        this.responseCode = responseCode;
        this.ipaddress = ipaddress;
        this.billerName = billerName;
        this.location = location;
    }

    public long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(long idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getCifNumber() {
        return cifNumber;
    }

    public void setCifNumber(String cifNumber) {
        this.cifNumber = cifNumber;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(String customerReference) {
        this.customerReference = customerReference;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "idTransaction=" + idTransaction +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", cifNumber='" + cifNumber + '\'' +
                ", fromAccountNumber='" + fromAccountNumber + '\'' +
                ", customerReference='" + customerReference + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", fee=" + fee +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", transactionDate=" + transactionDate +
                ", featureCode='" + featureCode + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                ", billerName='" + billerName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
