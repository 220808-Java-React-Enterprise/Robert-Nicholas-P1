package com.revature.ers.models;

import java.util.Date;

public class Reimbursement {
    private String id;
    private float amount;
    private Date submitted, resolved;
    private String description;
    private String author;
    private String statusId;
    private String typeId;

    public Reimbursement(String id, float amount, Date submitted, Date resolved, String description, String author, String statusId, String typeId) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.author = author;
        this.statusId = statusId;
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", statusId='" + statusId + '\'' +
                ", typeId='" + typeId + '\'' +
                "}<br>";
    }
}