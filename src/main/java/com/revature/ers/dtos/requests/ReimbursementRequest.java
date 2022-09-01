package com.revature.ers.dtos.requests;

import java.sql.Blob;

public class ReimbursementRequest {
    private String description, paymentId, type;
    private float amount;
    private Blob receipt;

    public ReimbursementRequest(){}

    public ReimbursementRequest(float amount, String description, Blob receipt, String paymentId, String type) {
        this.amount = amount;
        this.description = description;
        this.receipt = receipt;
        this.paymentId = paymentId;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Blob getReceipt() {
        return receipt;
    }

    public void setReceipt(Blob receipt) {
        this.receipt = receipt;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ReimbursementRequest{" +
                "description='" + description + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", receipt=" + receipt +
                '}';
    }
}
