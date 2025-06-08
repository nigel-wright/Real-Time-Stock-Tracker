package com.equities.equityplatform.model;

public class User {

    private int userId;
    private String username;
    private String email;
    private String fullName;
    private String dateCreated;

    // Setters
    public void setUserName(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Getters
    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFullName() {
        return this.fullName;
    }

}
