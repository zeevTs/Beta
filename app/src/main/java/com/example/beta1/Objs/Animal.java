package com.example.beta1.Objs;

public class Animal
{
    private String animalId;
    private String name;
    private int kind;
    private String url;
    private Double age;

    public Animal(String animalId, String name, int kind, String url, Double age) {
        this.animalId = animalId;
        this.name = name;
        this.kind = kind;
        this.url = url;
        this.age = age;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }
}
