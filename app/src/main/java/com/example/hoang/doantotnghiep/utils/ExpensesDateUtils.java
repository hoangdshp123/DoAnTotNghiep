package com.example.hoang.doantotnghiep.utils;

import com.example.hoang.doantotnghiep.Model.ModelApi.ExpensesDate;

import java.util.ArrayList;

/**
 * Created by ADMIN on 1/18/2018.
 */

public class ExpensesDateUtils {

    private ExpensesDateUtils(){}

    public static ArrayList<ExpensesDate.Data> getExpenseDate(ArrayList<ExpensesDate.Data> inputArray){
        ArrayList<ExpensesDate.Data> temp = new ArrayList<>();
        for(int i = 0; i < inputArray.size(); i++){
            ExpensesDate.Data item = inputArray.get(i);
            boolean checkDate = false;
            for (ExpensesDate.Data itemData:temp) {
                if (itemData.getDate_time().equals(item.getDate_time())){
                    checkDate = true;
                    break;
                }
            }
            if (checkDate){
                continue;
            }
            float total = 0;
            for (int j = i ; j < inputArray.size(); j++){
                ExpensesDate.Data item2 = inputArray.get(j);
                if (item.getDate_time().equals(item2.getDate_time())){
                    total += Float.parseFloat(item2.getExpense_value());
                }
            }
            item.setExpense_value(String.valueOf(total));
            temp.add(item);

        }
        return temp;
    }

    public static ArrayList<ExpensesDate.Data> getDate(ArrayList<ExpensesDate.Data> inputArray,String date2){
        ArrayList<ExpensesDate.Data> temp = new ArrayList<>();
        for(int i = 0; i < inputArray.size(); i++){
            ExpensesDate.Data item = inputArray.get(i);
//           Log.d("kok",item.getDate_time());
           if(item.getDate_time().equals(date2))
               temp.add(item);
//           Log.d("aoa",item.getExpense_value());
        }
        return temp;
    }

    public static ArrayList<ExpensesDate.Data> getValueDate(ArrayList<ExpensesDate.Data> inputArray){
        ArrayList<ExpensesDate.Data> temp = new ArrayList<>();
        for(int i = 0; i < inputArray.size(); i++){
            ExpensesDate.Data item = inputArray.get(i);
            boolean checkDate = false;
            for (ExpensesDate.Data itemData:temp) {
                if (itemData.getDate_time().equals(item.getDate_time())){
                    checkDate = true;
                    break;
                }
            }
            if (checkDate){
                continue;
            }
            temp.add(item);

        }
        return temp;
    }

}
