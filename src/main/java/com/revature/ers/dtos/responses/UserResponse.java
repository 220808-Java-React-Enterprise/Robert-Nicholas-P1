package com.revature.ers.dtos.responses;

public class UserResponse {
    private String id, username, email, givenName, surName, roleId;

    public UserResponse(String id, String username, String email, String givenName, String surName, String roleId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.givenName = givenName;
        this.surName = surName;
        this.roleId = roleId;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surName='" + surName + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
