package com.example.hoang.doantotnghiep.Model.ModelApi.model;

/**
 * Created by Admin on 1/9/2018.
 */

public class RevenueInfo {
    private String nameRevenue;
    private double valueRevenue;
    private String fromDate;
    private String toDate;
    private String staticIcon;

    public RevenueInfo(String nameRevenue, double valueRevenue,String staticIcon, String fromDate, String toDate) {
        this.nameRevenue = nameRevenue;
        this.valueRevenue = valueRevenue;
        this.staticIcon = staticIcon;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getNameRevenue() {
        return nameRevenue;
    }

    public void setNameRevenue(String nameRevenue) {
        this.nameRevenue = nameRevenue;
    }

    public double getValueRevenue() {
        return valueRevenue;
    }

    public void setValueRevenue(double valueRevenue) {
        this.valueRevenue = valueRevenue;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getStaticIcon() {
        return staticIcon;
    }

    public void setStaticIcon(String staticIcon) {
        this.staticIcon = staticIcon;
    }
}
