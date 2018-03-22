package com.example.hoang.doantotnghiep.Model.ModelExpenseDate;

import java.io.File;

/**
 * Created by ADMIN on 2/1/2018.
 */

public class ModelExpenseDate {

    private String expense_name;
    private String expense_value;
    private String date_time;
    private String type;
    private String note;
    private File imgfile;
    private String spendId;
    private String icon;
    private String subType;

    public ModelExpenseDate(){

    }

    public ModelExpenseDate(String expense_name, String expense_value, String date_time, String type, String note, File imgfile, String icon, String subType) {
        this.expense_name = expense_name;
        this.expense_value = expense_value;
        this.date_time = date_time;
        this.type = type;
        this.note = note;
        this.imgfile = imgfile;
        this.icon = icon;
        this.subType = subType;
    }

    public ModelExpenseDate(String expense_name, String expense_value, String date_time, String type, String note, File imgfile) {
        this.expense_name = expense_name;
        this.expense_value = expense_value;
        this.date_time = date_time;
        this.type = type;
        this.note = note;
        this.imgfile = imgfile;
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

    public String getSpendId() {
        return spendId;
    }

    public void setSpendId(String spendId) {
        this.spendId = spendId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }
}
