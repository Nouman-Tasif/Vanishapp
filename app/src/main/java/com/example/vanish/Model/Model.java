package com.example.vanish.Model;

public class Model {
    String Username,Password,Email,PhoneNo,FullName;

    public Model() {
    }

    public Model(String username, String password, String email, String phoneNo, String fullName) {
        Username = username;
        Password = password;
        Email = email;
        PhoneNo = phoneNo;
        FullName = fullName;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }
}
