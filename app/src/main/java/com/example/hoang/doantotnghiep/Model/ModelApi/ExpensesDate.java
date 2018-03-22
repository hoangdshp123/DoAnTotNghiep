package com.example.hoang.doantotnghiep.Model.ModelApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kien.lovan on 1/11/2018.
 */

public class ExpensesDate {


    @SerializedName("code")
    public int code;
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @SerializedName("owner")
        public String owner;
        @SerializedName("expense_name")
        public String expense_name;
        @SerializedName("expense_value")
        public String expense_value;
        @SerializedName("date_time")
        public String date_time;
        @SerializedName("type")
        public String type;
        @SerializedName("note")
        public String note;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("id")
        public String id;
        @SerializedName("image")
        private String image;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("subType")
        @Expose
        private String subType;

        public Data(String owner, String expense_name, String expense_value, String date_time, String type, String note, String createdAt, String updatedAt, String id, String image) {
            this.owner = owner;
            this.expense_name = expense_name;
            this.expense_value = expense_value;
            this.date_time = date_time;
            this.type = type;
            this.note = note;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.id = id;
            this.image = image;
        }

        public Data(String owner, String expense_name, String expense_value, String date_time, String type, String note, String createdAt, String updatedAt, String id, String image, String icon, String subType) {
            this.owner = owner;
            this.expense_name = expense_name;
            this.expense_value = expense_value;
            this.date_time = date_time;
            this.type = type;
            this.note = note;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.id = id;
            this.image = image;
            this.icon = icon;
            this.subType = subType;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
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
}
