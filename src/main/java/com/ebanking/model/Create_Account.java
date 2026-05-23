package com.ebanking.model;

/**
 * @author user
 */
public class Create_Account {
    // 1. Added data types (String) for the fields
    private String name;
    private String alamat;
    private String email;
    private String password;
    private String jenis_kelamin;
    
    // 2. Added parameters to the constructor so you can pass data when creating the object
    public Create_Account(String name, String alamat, String email, String password, String jenis_kelamin) {
        this.name = name;
        this.alamat = alamat;
        this.email = email;
        this.password = password;
        this.jenis_kelamin = jenis_kelamin;
    }

    // 3. Added Getters and Setters (essential for a Model class to access private data)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }
}