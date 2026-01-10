package com.example.heathub_splash;

public class UserModel {
    public String uid, name, email;

    public UserModel() {} // Firebase ke liye zaroori hai

    public UserModel(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }
}