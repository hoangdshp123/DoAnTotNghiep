package com.example.hoang.doantotnghiep.Model.ModelApi.ModelTargetLoans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hoang on 1/29/2018.
 */

public class TargetLoanMessage {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("target_loans")
    @Expose
    private List<TargetLoan> targetLoans = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TargetLoan> getTargetLoans() {
        return targetLoans;
    }

    public void setTargetLoans(List<TargetLoan> targetLoans) {
        this.targetLoans = targetLoans;
    }

    public static class TargetLoan {
        @SerializedName("owner")
        @Expose
        private String owner;
        @SerializedName("loan_name")
        @Expose
        private String loanName;
        @SerializedName("origin_money")
        @Expose
        private Double originMoney;
        @SerializedName("preferential_rate")
        @Expose
        private Double preferentialRate;
        @SerializedName("preferential_time")
        @Expose
        private Integer preferentialTime;
        @SerializedName("rate_base")
        @Expose
        private Double rateBase;
        @SerializedName("loan_time")
        @Expose
        private Integer loanTime;
        @SerializedName("pay_per_month")
        @Expose
        private Double payPerMonth;
        @SerializedName("total_interest_pay")
        @Expose
        private Double totalInterestPay;
        @SerializedName("total_money_pay")
        @Expose
        private Double totalMoneyPay;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("static_icon")
        @Expose
        private String static_icon;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("id")
        @Expose
        private String id;


        public String getStatic_icon() {
            return static_icon;
        }

        public void setStatic_icon(String static_icon) {
            this.static_icon = static_icon;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getLoanName() {
            return loanName;
        }

        public void setLoanName(String loanName) {
            this.loanName = loanName;
        }

        public Double getOriginMoney() {
            return originMoney;
        }

        public void setOriginMoney(Double originMoney) {
            this.originMoney = originMoney;
        }

        public Double getPreferentialRate() {
            return preferentialRate;
        }

        public void setPreferentialRate(Double preferentialRate) {
            this.preferentialRate = preferentialRate;
        }

        public Integer getPreferentialTime() {
            return preferentialTime;
        }

        public void setPreferentialTime(Integer preferentialTime) {
            this.preferentialTime = preferentialTime;
        }

        public Double getRateBase() {
            return rateBase;
        }

        public void setRateBase(Double rateBase) {
            this.rateBase = rateBase;
        }

        public Integer getLoanTime() {
            return loanTime;
        }

        public void setLoanTime(Integer loanTime) {
            this.loanTime = loanTime;
        }

        public Double getPayPerMonth() {
            return payPerMonth;
        }

        public void setPayPerMonth(Double payPerMonth) {
            this.payPerMonth = payPerMonth;
        }

        public Double getTotalInterestPay() {
            return totalInterestPay;
        }

        public void setTotalInterestPay(Double totalInterestPay) {
            this.totalInterestPay = totalInterestPay;
        }

        public Double getTotalMoneyPay() {
            return totalMoneyPay;
        }

        public void setTotalMoneyPay(Double totalMoneyPay) {
            this.totalMoneyPay = totalMoneyPay;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
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
}
