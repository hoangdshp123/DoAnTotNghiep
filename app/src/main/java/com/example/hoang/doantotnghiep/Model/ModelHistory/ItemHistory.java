package com.example.hoang.doantotnghiep.Model.ModelHistory;

/**
 * Created by TWO on 12/27/2017.
 */

public class ItemHistory {

    private int image;
    private String name;
    private double value;
    private String note;
    private String id;
    private String time;

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {

        return time;
    }

    public ItemHistory(int image, String name, double value, String note, String id, String time) {

        this.image = image;
        this.name = name;
        this.value = value;
        this.note = note;
        this.id = id;
        this.time = time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {

        return id;
    }

    public ItemHistory(String name, double value, String note, int image, String id) {

        this.image = image;
        this.name = name;
        this.value = value;
        this.note = note;
        this.id = id;
    }

    public ItemHistory(String name, double value, String note, int image) {
        this.image = image;
        this.name = name;
        this.value = value;
        this.note = note;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }
}
