package com.example.newser.model;

public class message {
    String sender, message, resturant, createdon;

    public message() {
    }

    public message(String sender, String message, String resturant, String createdon) {
        this.sender = sender;
        this.message = message;
        this.createdon = createdon;
        this.resturant = resturant;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedon() {
        return createdon;
    }

    public String getResturant() {
        return resturant;
    }
}
