package com.example.hoang.doantotnghiep.Model.ModelHome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 12/27/2017.
 */

public class ModelPersonalFinance {

    @SerializedName("total_revenue")
    @Expose
    private int totalRevenue;

    @SerializedName("budget_base")
    @Expose
    private int budgetBase;

    @SerializedName("budget_min")
    @Expose
    private int budgetMin;

    @SerializedName("budget_luxury")
    @Expose
    private int budgetLuxury;

    @SerializedName("count_total_target")
    @Expose
    private int countTotalTarget;

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getBudgetBase() {
        return budgetBase;
    }

    public void setBudgetBase(int budgetBase) {
        this.budgetBase = budgetBase;
    }

    public int getBudgetMin() {
        return budgetMin;
    }

    public void setBudgetMin(int budgetMin) {
        this.budgetMin = budgetMin;
    }

    public int getBudgetLuxury() {
        return budgetLuxury;
    }

    public void setBudgetLuxury(int budgetLuxury) {
        this.budgetLuxury = budgetLuxury;
    }

    public int getCountTotalTarget() {
        return countTotalTarget;
    }

    public void setCountTotalTarget(int countTotalTarget) {
        this.countTotalTarget = countTotalTarget;
    }
}
