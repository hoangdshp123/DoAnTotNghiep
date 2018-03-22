package com.example.hoang.doantotnghiep.Model.ModelTarget;

/**
 * Created by hoang on 1/29/2018.
 */

public class ModelTargetLoan {
    private String nameTargetLoan;
    private String originmoney;
    private String timePreferent;
    private String ratePreferent;
    private String time;
    private String ratebase;
    private String moneyPerMounth;
    private String totalmoneyTargetLoan;
    private String totalInterestLoan;
    private String image;
    private String loanid;
    private String static_icon;

    public ModelTargetLoan(String nameTargetLoan, String originmoney, String timePreferent, String ratePreferent, String time, String ratebase, String moneyPerMounth, String totalmoneyTargetLoan, String totalInterestLoan, String image) {
        this.nameTargetLoan = nameTargetLoan;
        this.originmoney = originmoney;
        this.timePreferent = timePreferent;
        this.ratePreferent = ratePreferent;
        this.time = time;
        this.ratebase = ratebase;
        this.moneyPerMounth = moneyPerMounth;
        this.totalmoneyTargetLoan = totalmoneyTargetLoan;
        this.totalInterestLoan = totalInterestLoan;
        this.image = image;
    }

    public ModelTargetLoan(String nameTargetLoan, String originmoney, String timePreferent, String ratePreferent, String time, String ratebase, String moneyPerMounth, String totalmoneyTargetLoan, String totalInterestLoan, String image, String loanid,String static_icon) {
        this.nameTargetLoan = nameTargetLoan;
        this.originmoney = originmoney;
        this.timePreferent = timePreferent;
        this.ratePreferent = ratePreferent;
        this.time = time;
        this.ratebase = ratebase;
        this.moneyPerMounth = moneyPerMounth;
        this.totalmoneyTargetLoan = totalmoneyTargetLoan;
        this.totalInterestLoan = totalInterestLoan;
        this.image = image;
        this.loanid = loanid;
        this.static_icon = static_icon;
    }

    public ModelTargetLoan() {
    }

    public String getStatic_icon() {
        return static_icon;
    }

    public void setStatic_icon(String static_icon) {
        this.static_icon = static_icon;
    }

    public String getNameTargetLoan() {
        return nameTargetLoan;
    }

    public void setNameTargetLoan(String nameTargetLoan) {
        this.nameTargetLoan = nameTargetLoan;
    }

    public String getOriginmoney() {
        return originmoney;
    }

    public void setOriginmoney(String originmoney) {
        this.originmoney = originmoney;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRatebase() {
        return ratebase;
    }

    public void setRatebase(String ratebase) {
        this.ratebase = ratebase;
    }

    public String getMoneyPerMounth() {
        return moneyPerMounth;
    }

    public void setMoneyPerMounth(String moneyPerMounth) {
        this.moneyPerMounth = moneyPerMounth;
    }

    public String getTotalmoneyTargetLoan() {
        return totalmoneyTargetLoan;
    }

    public void setTotalmoneyTargetLoan(String totalmoneyTargetLoan) {
        this.totalmoneyTargetLoan = totalmoneyTargetLoan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimePreferent() {
        return timePreferent;
    }

    public void setTimePreferent(String timePreferent) {
        this.timePreferent = timePreferent;
    }

    public String getRatePreferent() {
        return ratePreferent;
    }

    public void setRatePreferent(String ratePreferent) {
        this.ratePreferent = ratePreferent;
    }

    public String getTotalInterestLoan() {
        return totalInterestLoan;
    }

    public void setTotalInterestLoan(String totalInterestLoan) {
        this.totalInterestLoan = totalInterestLoan;
    }

    public String getLoanid() {
        return loanid;
    }

    public void setLoanid(String loanid) {
        this.loanid = loanid;
    }
}
