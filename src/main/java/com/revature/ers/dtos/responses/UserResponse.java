package com.revature.ers.dtos.responses;

public class UserResponse {
    private String id, username, email, givenName, surName, role;
    private boolean isActive;

    public UserResponse(String id, String username, String email, String givenName, String surName, String role, boolean isActive) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.givenName = givenName;
        this.surName = surName;
        this.role = role;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String roleId) {
        this.role = roleId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surName='" + surName + '\'' +
                ", roleId='" + role + '\'' +
                '}';
    }

    public String toStringAdmin(){
        return "UserResponse{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surName='" + surName + '\'' +
                ", roleId='" + role + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
