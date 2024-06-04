package com.example.beta1.Objs;

public class Message {
    String userId;
    String userName;
    String data;
    String time;
    String title;
    String msgId;

    public Message(String userId, String userName, String data, String time,String title,String msgId) {
        this.userId = userId;
        this.userName = userName;
        this.data = data;
        this.time = time;
        this.title =title;
        this.msgId = msgId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Message(){

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
