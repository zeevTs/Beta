package com.example.beta1.Objs;

import java.util.ArrayList;
import java.util.Collection;

public class User {
    private String uId;
    private String name;
    private String city;
    private ArrayList<Animal> animals;
    private ArrayList<Notification> notifications;
    private ArrayList<Note> notes;

    public User(String uId, String name, String city) {
        this.uId = uId;
        this.name = name;
        this.city = city;
        this.animals = new ArrayList<>();
        animals.clear();
        this.notifications = new ArrayList<>();
        notifications.clear();
        this.notes = new ArrayList<>();
        notes.clear();
    }
    public User() {}
    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
