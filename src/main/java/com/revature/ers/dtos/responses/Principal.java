package com.revature.ers.dtos.responses;

public class Principal {
    private String id, username, role;

    public Principal(){

    }

    public Principal(String id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String roleId) {
        this.role = roleId;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", roleId='" + role + '\'' +
                '}';
    }
}
