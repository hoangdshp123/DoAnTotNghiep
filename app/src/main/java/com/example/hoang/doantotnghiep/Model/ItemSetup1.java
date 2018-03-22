package com.example.hoang.doantotnghiep.Model;

import java.io.Serializable;

/**
 * Created by Admin on 1/8/2018.
 */

public class ItemSetup1 implements Serializable{
    private String name;
    private double value;
    private String id;
    private String static_icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemSetup1(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public ItemSetup1(String name, double value, String id) {
        this.name = name;
        this.value = value;
        this.id = id;
    }

    public ItemSetup1(String name, double value, String id, String static_icon) {
        this.name = name;
        this.value = value;
        this.id = id;
        this.static_icon = static_icon;
    }

    public String getStatic_icon() {
        return static_icon;
    }

    public void setStatic_icon(String static_icon) {
        this.static_icon = static_icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
