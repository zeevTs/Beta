package com.example.beta1.Objs;

public class Note {
    private String noteId;
    private String animalId;

    public Note(String noteId, String animalId) {
        this.noteId = noteId;
        this.animalId = animalId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }


}
