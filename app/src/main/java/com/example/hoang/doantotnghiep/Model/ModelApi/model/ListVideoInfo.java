package com.example.hoang.doantotnghiep.Model.ModelApi.model;

/**
 * Created by hoang on 12/27/2017.
 */

public class ListVideoInfo {
    String playListID1,title;

    public ListVideoInfo(String playListID1, String title) {
        this.playListID1 = playListID1;
        this.title = title;
    }

    public ListVideoInfo() {
    }

    public String getPlayListID1() {
        return playListID1;
    }

    public void setPlayListID1(String playListID1) {
        this.playListID1 = playListID1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
