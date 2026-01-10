package com.example.heathub_splash;

public class MessageModel {
    public String message, senderId;

    public MessageModel() {} // Firebase ke liye zaroori

    public MessageModel(String message, String senderId) {
        this.message = message;
        this.senderId = senderId;
    }
}