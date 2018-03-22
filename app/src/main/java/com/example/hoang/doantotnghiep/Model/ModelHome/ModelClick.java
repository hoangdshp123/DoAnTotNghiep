package com.example.hoang.doantotnghiep.Model.ModelHome;

/**
 * Created by TWO on 02/02/2018.
 */

public class ModelClick {
    public String screen_name;
    public int total_click;

    public ModelClick(String screen_name, int total_click) {
        this.screen_name = screen_name;
        this.total_click = total_click;
    }

    public void setTotal_click(int total_click) {
        this.total_click = total_click;
    }

    public void setScreen_name(String screen_name) {

        this.screen_name = screen_name;
    }

    public String getScreen_name() {

        return screen_name;
    }

    public int getTotal_click() {
        return total_click;
    }
}
