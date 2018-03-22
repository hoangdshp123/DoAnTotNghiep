package com.example.hoang.doantotnghiep.utils;

import android.content.Context;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by ADMIN on 1/29/2018.
 */

public class DecimalFormatUtils {

    private DecimalFormatUtils(){}

    public static String getMoney(long money){
        DecimalFormat formatter = new DecimalFormat("###,###,###,###,###");
        String s = formatter.format(money) + " vnđ";
        return s;
    }

    public static String getMoney(float money){
        DecimalFormat formatter = new DecimalFormat("###,###,###,###,###");
        String s = formatter.format(money) + " vnđ";
        return s;
    }

    public static String getMoney(double money){
        DecimalFormat formatter = new DecimalFormat("###,###,###,###,###");
        String s = formatter.format(money) + " vnđ";
        return s;
    }

    public static String getMone(double money){
        DecimalFormat formatter = new DecimalFormat("###############");
        Context context;
        String s = formatter.format(money);
        return s;
    }

    public static String getPercent(float number){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(number);
    }
    public static String getPercent(double number){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(number);
    }


}
