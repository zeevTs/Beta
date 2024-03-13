package com.example.beta1.Objs;

public class Notification {

    private String id;
    private String notificationId;

    public Notification(String id, String notificationId) {
        this.id = id;
        this.notificationId = notificationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }
}
