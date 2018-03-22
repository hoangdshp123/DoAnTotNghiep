package com.example.hoang.doantotnghiep.Model.ModelBudget;

/**
 * Created by TWO on 12/26/2017.
 */

public class BudgetRequest {
    private String budget_name;
    private double budget_value;
    private String type;

    public BudgetRequest(String budget_name, double budget_value, String type) {
        this.budget_name = budget_name;
        this.budget_value = budget_value;
        this.type = type;
    }

    public String getBudget_name() {
        return budget_name;
    }

    public void setBudget_name(String budget_name) {
        this.budget_name = budget_name;
    }

    public double getBudget_value() {
        return budget_value;
    }

    public void setBudget_value(double budget_value) {
        this.budget_value = budget_value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
