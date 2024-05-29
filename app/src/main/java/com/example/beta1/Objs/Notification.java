package com.example.beta1.Objs;

public class Notification extends Note {
    private String repetitive;
    private String timeStamp;

    public Notification(){}

    public Notification(String title, String data, String animalId, String notId, String repetitive, String timeStamp) {
        super(title, data, animalId, notId);
        this.repetitive = repetitive;
        this.timeStamp = timeStamp;
    }

    public String getRepetitive() {
        return repetitive;
    }

    public void setRepetitive(String repetitive) {
        this.repetitive = repetitive;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
