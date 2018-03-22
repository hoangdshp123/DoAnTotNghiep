package com.example.hoang.doantotnghiep.Model;

import android.graphics.drawable.Drawable;

/**
 * Created by kien.lovan on 1/3/2018.
 */

public class Item_Tools {
    private String title;
    private Drawable icon;
    private String staticIcon;

    public Item_Tools(String title, Drawable icon, String staticIcon) {
        this.title = title;
        this.icon = icon;
        this.staticIcon = staticIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getStaticIcon() {
        return staticIcon;
    }

    public void setStaticIcon(String staticIcon) {
        this.staticIcon = staticIcon;
    }
}
