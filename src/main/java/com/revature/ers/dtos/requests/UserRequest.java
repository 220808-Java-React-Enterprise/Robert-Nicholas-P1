package com.revature.ers.dtos.requests;

public class UserRequest {
    private String username, email, password1, password2, givenName, surName, roleId;

    public UserRequest(){

    }

    public UserRequest(String username, String email, String password1, String password2, String givenName, String surName, String roleId) {
        this.username = username;
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
        this.givenName = givenName;
        this.surName = surName;
        this.roleId = roleId;
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

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
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
        return "UserRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password1='" + password1 + '\'' +
                ", password2='" + password2 + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surName='" + surName + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
