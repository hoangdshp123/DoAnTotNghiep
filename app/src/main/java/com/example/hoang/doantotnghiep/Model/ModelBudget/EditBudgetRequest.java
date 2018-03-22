package com.example.hoang.doantotnghiep.Model.ModelBudget;

import java.util.ArrayList;

/**
 * Created by kien.lovan on 1/25/2018.
 */

public class EditBudgetRequest {

    ArrayList<EditItem> arrItem;

    public EditBudgetRequest(ArrayList<EditItem> arrItem) {
        this.arrItem = arrItem;
    }

    public static class EditItem {
        private String id;
        private String budget_name;
        private double budget_value;

        public EditItem(String id, String budget_name, double budget_value) {
            this.id = id;
            this.budget_name = budget_name;
            this.budget_value = budget_value;
        }
    }
}
