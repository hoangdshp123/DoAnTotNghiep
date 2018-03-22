package com.example.hoang.doantotnghiep.Model.ModelTarget;

/**
 * Created by hoang on 2/1/2018.
 */

public class ModelEditLoan {
    private String loan_name;
    private String origin_money;
    private String preferential_rate;
    private String preferential_time;
    private String rate_base;
    private String pay_per_month;
    private String total_interest_pay;
    private String total_money_pay;
    private String loan_time;

    public ModelEditLoan(String loan_name, String origin_money, String preferential_rate, String preferential_time, String rate_base, String pay_per_month, String total_interest_pay, String total_money_pay, String loan_time) {
        this.loan_name = loan_name;
        this.origin_money = origin_money;
        this.preferential_rate = preferential_rate;
        this.preferential_time = preferential_time;
        this.rate_base = rate_base;
        this.pay_per_month = pay_per_month;
        this.total_interest_pay = total_interest_pay;
        this.total_money_pay = total_money_pay;
        this.loan_time = loan_time;
    }

    public String getLoan_name() {
        return loan_name;
    }

    public void setLoan_name(String loan_name) {
        this.loan_name = loan_name;
    }

    public String getOrigin_money() {
        return origin_money;
    }

    public void setOrigin_money(String origin_money) {
        this.origin_money = origin_money;
    }

    public String getPreferential_rate() {
        return preferential_rate;
    }

    public void setPreferential_rate(String preferential_rate) {
        this.preferential_rate = preferential_rate;
    }

    public String getRate_base() {
        return rate_base;
    }

    public void setRate_base(String rate_base) {
        this.rate_base = rate_base;
    }

    public String getPay_per_month() {
        return pay_per_month;
    }

    public void setPay_per_month(String pay_per_month) {
        this.pay_per_month = pay_per_month;
    }

    public String getTotal_interest_pay() {
        return total_interest_pay;
    }

    public void setTotal_interest_pay(String total_interest_pay) {
        this.total_interest_pay = total_interest_pay;
    }

    public String getTotal_money_pay() {
        return total_money_pay;
    }

    public void setTotal_money_pay(String total_money_pay) {
        this.total_money_pay = total_money_pay;
    }

    public String getLoan_time() {
        return loan_time;
    }

    public void setLoan_time(String loan_time) {
        this.loan_time = loan_time;
    }

    public String getPreferential_time() {
        return preferential_time;
    }

    public void setPreferential_time(String preferential_time) {
        this.preferential_time = preferential_time;
    }
}
