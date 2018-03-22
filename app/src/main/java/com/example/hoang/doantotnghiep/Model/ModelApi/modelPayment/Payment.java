package com.example.hoang.doantotnghiep.Model.ModelApi.modelPayment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TWO on 12/27/2017.
 */

public class Payment {
    @SerializedName("expense_name")
    private String name;

    @SerializedName("expense_value")
    private String value;

    @SerializedName("note")
    private String note;

    public Payment(String name, String value, String note) {
        this.name = name;
        this.value = value;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getNote() {
        return note;
    }
}
