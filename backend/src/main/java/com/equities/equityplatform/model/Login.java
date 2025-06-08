package com.equities.equityplatform.model;

public class Login {

    private String loginId;
    private String username;
    private String email;
    private String password_hash;

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public void setHashPassword(String password) {
    }

    // Getters
    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

}
