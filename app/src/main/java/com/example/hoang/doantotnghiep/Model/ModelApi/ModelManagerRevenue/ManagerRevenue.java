package com.example.hoang.doantotnghiep.Model.ModelApi.ModelManagerRevenue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kien.lovan on 1/2/2018.
 */

public class ManagerRevenue implements Serializable {

    @SerializedName("code")
    public int code;
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public Data data;

    public static class Revenue {
        @SerializedName("owner")
        public String owner;
        @SerializedName("name_revenue")
        public String name_revenue;
        @SerializedName("value_revenue")
        public double value_revenue;
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

        public String getName_revenue() {
            return name_revenue;
        }

        public void setName_revenue(String name_revenue) {
            this.name_revenue = name_revenue;
        }

        public double getValue_revenue() {
            return value_revenue;
        }

        public void setValue_revenue(double value_revenue) {
            this.value_revenue = value_revenue;
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

    public static class Budget_base implements Serializable {
        @SerializedName("owner")
        public String owner;
        @SerializedName("budget_name")
        public String budget_name;
        @SerializedName("budget_value")
        public double budget_value;
        @SerializedName("type")
        public String type;
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

    public static class Budget_min implements Serializable {
        @SerializedName("owner")
        public String owner;
        @SerializedName("budget_name")
        public String budget_name;
        @SerializedName("budget_value")
        public double budget_value;
        @SerializedName("type")
        public String type;
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


    public static class Budget_luxury implements Serializable{
        @SerializedName("owner")
        public String owner;
        @SerializedName("budget_name")
        public String budget_name;
        @SerializedName("budget_value")
        public double budget_value;
        @SerializedName("type")
        public String type;
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

    public static class Budgets {
        @SerializedName("budget_base")
        public List<Budget_base> budget_base;
        @SerializedName("budget_min")
        public List<Budget_min> budget_min;
        @SerializedName("budget_luxury")
        public List<Budget_luxury> budget_luxury;

        public List<Budget_base> getBudget_base() {
            return budget_base;
        }

        public void setBudget_base(List<Budget_base> budget_base) {
            this.budget_base = budget_base;
        }

        public List<Budget_min> getBudget_min() {
            return budget_min;
        }

        public void setBudget_min(List<Budget_min> budget_min) {
            this.budget_min = budget_min;
        }

        public List<Budget_luxury> getBudget_luxury() {
            return budget_luxury;
        }

        public void setBudget_luxury(List<Budget_luxury> budget_luxury) {
            this.budget_luxury = budget_luxury;
        }
    }

    public static class Data {
        @SerializedName("revenue")
        public List<Revenue> revenue;
        @SerializedName("budgets")
        public Budgets budgets;
        @SerializedName("total_expense")
        @Expose
        private Double totalExpense;

        public List<Revenue> getRevenue() {
            return revenue;
        }

        public void setRevenue(List<Revenue> revenue) {
            this.revenue = revenue;
        }

        public Budgets getBudgets() {
            return budgets;
        }

        public void setBudgets(Budgets budgets) {
            this.budgets = budgets;
        }

        public Double getTotalExpense() {
            return totalExpense;
        }

        public void setTotalExpense(Double totalExpense) {
            this.totalExpense = totalExpense;
        }
    }
}
