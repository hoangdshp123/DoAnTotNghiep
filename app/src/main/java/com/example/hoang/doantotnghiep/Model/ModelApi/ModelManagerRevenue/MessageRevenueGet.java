package com.example.hoang.doantotnghiep.Model.ModelApi.ModelManagerRevenue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hoang on 1/30/2018.
 */

public class MessageRevenueGet {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("revenues")
    @Expose
    private List<Revenue> revenues = null;

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

    public List<Revenue> getRevenues() {
        return revenues;
    }

    public void setRevenues(List<Revenue> revenues) {
        this.revenues = revenues;
    }

    public static class Revenue{
        @SerializedName("owner")
        @Expose
        private String owner;
        @SerializedName("name_revenue")
        @Expose
        private String nameRevenue;
        @SerializedName("value_revenue")
        @Expose
        private Double valueRevenue;
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

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getNameRevenue() {
            return nameRevenue;
        }

        public void setNameRevenue(String nameRevenue) {
            this.nameRevenue = nameRevenue;
        }

        public Double getValueRevenue() {
            return valueRevenue;
        }

        public void setValueRevenue(Double valueRevenue) {
            this.valueRevenue = valueRevenue;
        }

        public String getStatic_icon() {
            return static_icon;
        }

        public void setStatic_icon(String static_icon) {
            this.static_icon = static_icon;
        }

        public String getStaticIcon() {
            return static_icon;
        }

        public void setStaticIcon(String static_icon) {
            this.static_icon = static_icon;
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
