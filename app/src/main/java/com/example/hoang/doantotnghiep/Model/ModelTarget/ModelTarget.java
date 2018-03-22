package com.example.hoang.doantotnghiep.Model.ModelTarget;

/**
 * Created by ADMIN on 12/26/2017.
 */

public class ModelTarget {

    private String targetName;
    private String targetValue;
    private String pay_per_month;
    private String image;
    private String day;
    private String deadLine;
    private String targetSavingId;
    private String static_icon;

    public ModelTarget() {
    }

    public ModelTarget(String targetName, String targetValue, String pay_per_month, String deadLine) {
        this.targetName = targetName;
        this.targetValue = targetValue;
        this.pay_per_month = pay_per_month;
        this.deadLine = deadLine;
    }

    public ModelTarget(String targetName, String targetValue, String pay_per_month, String image, String day, String deadLine, String targetSavingId, String static_icon) {
        this.targetName = targetName;
        this.targetValue = targetValue;
        this.pay_per_month = pay_per_month;
        this.image = image;
        this.day = day;
        this.deadLine = deadLine;
        this.targetSavingId = targetSavingId;
        this.static_icon = static_icon;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public String getPay_per_month() {
        return pay_per_month;
    }

    public void setPay_per_month(String pay_per_month) {
        this.pay_per_month = pay_per_month;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getTargetSavingId() {
        return targetSavingId;
    }

    public void setTargetSavingId(String targetSavingId) {
        this.targetSavingId = targetSavingId;
    }

    public String getStatic_icon() {
        return static_icon;
    }

    public void setStatic_icon(String static_icon) {
        this.static_icon = static_icon;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}

