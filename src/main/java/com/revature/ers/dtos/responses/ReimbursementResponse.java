package com.revature.ers.dtos.responses;

import java.sql.Blob;
import java.sql.Timestamp;

public class ReimbursementResponse {
    String id;
    float amount;
    Timestamp submitted, resolved;
    String description;
    Blob receipt;
    String paymentId, author, resolver, status, type;

    public ReimbursementResponse() {}

    public ReimbursementResponse(String id, float amount, Timestamp submitted, Timestamp resolved, String description, Blob receipt, String paymentId, String author, String resolver, String status, String type) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.paymentId = paymentId;
        this.author = author;
        this.resolver = resolver;
        this.status = status;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ReimbursementResponse{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", paymentId='" + paymentId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String toStringDetailed(){
        return "ReimbursementResponse{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", paymentId='" + paymentId + '\'' +
                ", author='" + author + '\'' +
                ", resolver='" + resolver + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
