package com.example.hoang.doantotnghiep.Model;

/**
 * Created by kien.lovan on 1/18/2018.
 */

public class RevenueRequest {
    public String name_revenue;
    public double value_revenue;
    public String static_icon;

    public RevenueRequest(String name_revenue, double value_revenue, String static_icon) {
        this.name_revenue = name_revenue;
        this.value_revenue = value_revenue;
        this.static_icon = static_icon;
    }
}
