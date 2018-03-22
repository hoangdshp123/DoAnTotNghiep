package com.example.hoang.doantotnghiep.Model.ModelRevenue;

import com.example.hoang.doantotnghiep.Model.ModelHistory.ItemHistory;

import java.util.ArrayList;

/**
 * Created by TWO on 07/02/2018.
 */

public class Revenue {
    private String month;
    private ArrayList<ItemHistory> list;

    public Revenue(String month, ArrayList<ItemHistory> list) {
        this.month = month;
        this.list = list;
    }
    public Revenue(ArrayList<ItemHistory> list) {
        this.list = list;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public ArrayList<ItemHistory> getList() {
        return list;
    }

    public void setList(ArrayList<ItemHistory> list) {
        this.list = list;
    }
}
