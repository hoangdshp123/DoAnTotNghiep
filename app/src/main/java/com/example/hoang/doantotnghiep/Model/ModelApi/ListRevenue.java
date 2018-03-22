package com.example.hoang.doantotnghiep.Model.ModelApi;

import com.example.hoang.doantotnghiep.Model.RevenueRequest;

import java.util.ArrayList;

/**
 * Created by kien.lovan on 1/18/2018.
 */

public class ListRevenue {
    ArrayList<RevenueRequest> data;

    public ListRevenue(ArrayList<RevenueRequest> data) {
        this.data = data;
    }
}
