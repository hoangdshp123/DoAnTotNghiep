package com.example.hoang.doantotnghiep.Model.ModelBuyCar;

/**
 * Created by hoang on 12/25/2017.
 */

public class ModelBuyCar {
    int year;
    double moneypayment;

    public ModelBuyCar(int year, double moneypayment) {
        this.year = year;
        this.moneypayment = moneypayment;
    }

    public ModelBuyCar() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMoneypayment() {
        return moneypayment;
    }

    public void setMoneypayment(double moneypayment) {
        this.moneypayment = moneypayment;
    }
}
