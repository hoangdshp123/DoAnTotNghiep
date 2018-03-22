package com.example.hoang.doantotnghiep.Model.ModelExpenseDate;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kien.lovan on 3/1/2018.
 */

public class ExpenseTop10 {

    @SerializedName("code")
    public int code;
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @SerializedName("expense_name")
        public String expense_name;
        @SerializedName("expense_value")
        public double expense_value;
        @SerializedName("subType")
        public String subType;
    }
}
