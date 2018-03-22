package com.example.hoang.doantotnghiep.Model.ModelApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kien.lovan on 1/15/2018.
 */

public class TargetSaving {

    @SerializedName("code")
    public int code;
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("target_name")
        public String target_name;
        @SerializedName("target_value")
        public String target_value;
        @SerializedName("deadline")
        public String deadline;
        @SerializedName("saving_per_month")
        public String saving_per_month;
        @SerializedName("icon")
        public String icon;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("id")
        public String id;

        public String getTarget_name() {
            return target_name;
        }

        public void setTarget_name(String target_name) {
            this.target_name = target_name;
        }

        public String getTarget_value() {
            return target_value;
        }

        public void setTarget_value(String target_value) {
            this.target_value = target_value;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getSaving_per_month() {
            return saving_per_month;
        }

        public void setSaving_per_month(String saving_per_month) {
            this.saving_per_month = saving_per_month;
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
