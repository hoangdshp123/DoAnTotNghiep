package com.example.hoang.doantotnghiep.Model.ModelHistory;

import com.example.hoang.doantotnghiep.Model.ModelApi.ExpensesDate;

import java.util.List;

/**
 * Created by TWO on 12/27/2017.
 */

public class History{
    private String date;
    private List<ExpensesDate.Data> list;

    public History(String date, List<ExpensesDate.Data> list) {
        this.date = date;
        this.list = list;
    }

    public History() {
    }

    public History(List<ExpensesDate.Data> list) {
        this.list = list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ExpensesDate.Data> getList() {
        return list;
    }

    public void setList(List<ExpensesDate.Data> list) {
        this.list = list;
    }
}
