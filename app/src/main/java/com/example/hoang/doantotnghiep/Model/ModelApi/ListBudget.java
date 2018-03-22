package com.example.hoang.doantotnghiep.Model.ModelApi;

import com.example.hoang.doantotnghiep.Model.ModelBudget.BudgetRequest;

import java.util.ArrayList;

/**
 * Created by kien.lovan on 1/18/2018.
 */

public class ListBudget {
    ArrayList<BudgetRequest> data;

    public ListBudget(ArrayList<BudgetRequest> data) {
        this.data = data;
    }
}
