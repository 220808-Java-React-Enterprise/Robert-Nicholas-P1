package com.revature.ers.models;

public class ReimbursementTypes {
    private String id, type;

    public ReimbursementTypes(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ReimbursementTypes{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
