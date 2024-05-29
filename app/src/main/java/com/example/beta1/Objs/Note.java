package com.example.beta1.Objs;

public class Note {
    private String title;
    private String data;
    private String animalId;
    private String notId;
    public Note(){}

    public Note(String title, String data, String animalId, String notId) {
        this.title = title;
        this.data = data;
        this.animalId = animalId;
        this.notId = notId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getNotId() {
        return notId;
    }

    public void setNotId(String notId) {
        this.notId = notId;
    }
}
