package com.example.test1;

import java.util.Date;

public class Message {
    private String userName;
    private String textMessage;
    private long massageTime;

    public Message(){}

    public Message(String userName, String textMessage) {
        this.userName = userName;
        this.textMessage = textMessage;
        this.massageTime = new Date().getTime();
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public long getMassageTime() {
        return massageTime;
    }


}
