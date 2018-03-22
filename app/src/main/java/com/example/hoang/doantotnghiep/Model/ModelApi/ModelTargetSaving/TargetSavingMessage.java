package com.example.hoang.doantotnghiep.Model.ModelApi.ModelTargetSaving;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hoang on 1/24/2018.
 */

public class TargetSavingMessage {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("target_savings")
    @Expose
    private List<TargetSaving> targetSavings = null;

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

    public List<TargetSaving> getTargetSavings() {
        return targetSavings;
    }

    public void setTargetSavings(List<TargetSaving> targetSavings) {
        this.targetSavings = targetSavings;
    }

    public static class TargetSaving{
        @SerializedName("owner")
        @Expose
        private String owner;
        @SerializedName("target_name")
        @Expose
        private String targetName;
        @SerializedName("target_value")
        @Expose
        private String targetValue;
        @SerializedName("deadline")
        @Expose
        private String deadline;
        @SerializedName("saving_per_month")
        @Expose
        private String savingPerMonth;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("static_icon")
        @Expose
        private String static_icon;

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
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

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getSavingPerMonth() {
            return savingPerMonth;
        }

        public void setSavingPerMonth(String savingPerMonth) {
            this.savingPerMonth = savingPerMonth;
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

        public String getStatic_icon() {
            return static_icon;
        }

        public void setStatic_icon(String static_icon) {
            this.static_icon = static_icon;
        }
    }
}
