package com.example.hoang.doantotnghiep.Model.ModelApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kien.lovan on 3/2/2018.
 */

public class BorrowResponse {


    @SerializedName("code")
    public int code;
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @SerializedName("owner")
        public String owner;
        @SerializedName("name_borrow")
        public String name_borrow;
        @SerializedName("money_borrow")
        public double money_borrow;
        @SerializedName("time_borrow")
        public int time_borrow;
        @SerializedName("money_icome")
        public double money_icome;
        @SerializedName("office")
        public String office;
        @SerializedName("type_receive_money")
        public String type_receive_money;
        @SerializedName("money_electric")
        public double money_electric;
        @SerializedName("money_isurrance")
        public double money_isurrance;
        @SerializedName("address")
        public String address;
        @SerializedName("email")
        public String email;
        @SerializedName("number_phone")
        public String number_phone;
        @SerializedName("rate_base")
        public double rate_base;
        @SerializedName("pay_per_month")
        public double pay_per_month;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("id")
        public String id;

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getName_borrow() {
            return name_borrow;
        }

        public void setName_borrow(String name_borrow) {
            this.name_borrow = name_borrow;
        }

        public double getMoney_borrow() {
            return money_borrow;
        }

        public void setMoney_borrow(double money_borrow) {
            this.money_borrow = money_borrow;
        }

        public int getTime_borrow() {
            return time_borrow;
        }

        public void setTime_borrow(int time_borrow) {
            this.time_borrow = time_borrow;
        }

        public double getMoney_icome() {
            return money_icome;
        }

        public void setMoney_icome(double money_icome) {
            this.money_icome = money_icome;
        }

        public String getOffice() {
            return office;
        }

        public void setOffice(String office) {
            this.office = office;
        }

        public String getType_receive_money() {
            return type_receive_money;
        }

        public void setType_receive_money(String type_receive_money) {
            this.type_receive_money = type_receive_money;
        }

        public double getMoney_electric() {
            return money_electric;
        }

        public void setMoney_electric(double money_electric) {
            this.money_electric = money_electric;
        }

        public double getMoney_isurrance() {
            return money_isurrance;
        }

        public void setMoney_isurrance(double money_isurrance) {
            this.money_isurrance = money_isurrance;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNumber_phone() {
            return number_phone;
        }

        public void setNumber_phone(String number_phone) {
            this.number_phone = number_phone;
        }

        public double getRate_base() {
            return rate_base;
        }

        public void setRate_base(double rate_base) {
            this.rate_base = rate_base;
        }

        public double getPay_per_month() {
            return pay_per_month;
        }

        public void setPay_per_month(double pay_per_month) {
            this.pay_per_month = pay_per_month;
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
    }
}
