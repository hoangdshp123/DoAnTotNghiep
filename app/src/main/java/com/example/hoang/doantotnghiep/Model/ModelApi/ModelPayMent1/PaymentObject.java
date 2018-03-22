package com.example.hoang.doantotnghiep.Model.ModelApi.ModelPayMent1;

import java.io.File;

/**
 * Created by hoang on 1/22/2018.
 */

public class PaymentObject {
    String expense_name;
    String expense_value;
    String date_time;
    String type;
    String note;
    File imgfile;
    String imgName;
    String sybType;

    public PaymentObject(String expense_name, String expense_value, String date_time, String type, String note, File imgfile, String imgName, String sybType) {
        this.expense_name = expense_name;
        this.expense_value = expense_value;
        this.date_time = date_time;
        this.type = type;
        this.note = note;
        this.imgfile = imgfile;
        this.imgName = imgName;
        this.sybType = sybType;
    }

    public PaymentObject() {
    }

    public String getExpense_name() {
        return expense_name;
    }

    public void setExpense_name(String expense_name) {
        this.expense_name = expense_name;
    }

    public String getExpense_value() {
        return expense_value;
    }

    public void setExpense_value(String expense_value) {
        this.expense_value = expense_value;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public File getImgfile() {
        return imgfile;
    }

    public void setImgfile(File imgfile) {
        this.imgfile = imgfile;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getSybType() {
        return sybType;
    }

    public void setSybType(String sybType) {
        this.sybType = sybType;
    }
}
