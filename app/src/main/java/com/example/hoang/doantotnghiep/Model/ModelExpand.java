package com.example.hoang.doantotnghiep.Model;

/**
 * Created by hoang on 3/9/2018.
 */

public class ModelExpand {
    int image;
    String name;

    public ModelExpand() {
    }

    public ModelExpand(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
