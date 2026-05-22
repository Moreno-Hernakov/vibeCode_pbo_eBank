package com.ebanking.model;

public class User {
    private Long id;
    private String username;
    private String password;
    private String cifNumber;
    private String status;

    public User() {}

    public User(Long id, String username, String password, String cifNumber, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cifNumber = cifNumber;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCifNumber() { return cifNumber; }
    public void setCifNumber(String cifNumber) { this.cifNumber = cifNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
