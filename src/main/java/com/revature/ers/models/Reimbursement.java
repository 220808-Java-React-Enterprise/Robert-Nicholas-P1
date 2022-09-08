package com.revature.ers.models;

import java.sql.Blob;
import java.sql.Timestamp;

public class Reimbursement {
    private String id, description, paymentId, authorId, resolverId, statusId, typeId;
    private float amount;
    private Timestamp submitted, resolved;
    private Blob receipt;

    public Reimbursement(String id, float amount, Timestamp submitted, String description, Blob receipt, String paymentId, String authorId, String statusId, String typeId){
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = null;
        this.description = description;
        this.receipt = receipt;
        this.paymentId = paymentId;
        this.authorId = authorId;
        this.resolverId = null;
        this.statusId = statusId;
        this.typeId = typeId;
  }


    public Reimbursement(String id, float amount, Timestamp submitted, Timestamp resolved, String description, String authorId, String statusId, String typeId) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.authorId = authorId;
        this.statusId = statusId;
        this.typeId = typeId;
    }


    public Reimbursement(String id, String description, String paymentId, String authorId, String resolverId, String statusId, String typeId, float amount, Timestamp submitted, Timestamp resolved, Blob receipt) {
        this.id = id;
        this.description = description;
        this.paymentId = paymentId;
        this.authorId = authorId;
        this.resolverId = resolverId;
        this.statusId = statusId;
        this.typeId = typeId;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.receipt = receipt;


    }

    public Reimbursement() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getResolverId() {
        return resolverId;
    }

    public void setResolverId(String resolverId) {
        this.resolverId = resolverId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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

    public Blob getReceipt() {
        return receipt;
    }

    public void setReceipt(Blob receipt) {
        this.receipt = receipt;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id='" + id + '\'' +
                ", amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", resolverId='" + resolverId + '\'' +
                ", statusId='" + statusId + '\'' +
                ", typeId='" + typeId + '\'' +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", receipt=" + receipt +
                '}';
    }

}