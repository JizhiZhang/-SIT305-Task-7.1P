package com.example.noteapp.model;

public class Note {
    private int note_id;
    private String note_desc;

    public Note(int note_id, String note_desc) {
        this.note_id = note_id;
        this.note_desc = note_desc;
    }

    public Note(String note_desc) {
        this.note_desc = note_desc;
    }

    public Note() {
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getNote_desc() {
        return note_desc;
    }

    public void setNote_desc(String note_desc) {
        this.note_desc = note_desc;
    }
}
