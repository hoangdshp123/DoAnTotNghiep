package com.example.hoang.doantotnghiep.Model.ModelApi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 1/30/2018.
 */

public class MessageEvaluteProfile {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("profile")
    @Expose
    private Profile profile;

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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public class Profile {

        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("age")
        @Expose
        private Integer age;
        @SerializedName("gender")
        @Expose
        private Integer gender;
        @SerializedName("is_marriage")
        @Expose
        private Integer isMarriage;
        @SerializedName("care_child_alone")
        @Expose
        private Integer careChildAlone;
        @SerializedName("finace_healthy")
        @Expose
        private Integer finaceHealthy;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public Integer getIsMarriage() {
            return isMarriage;
        }

        public void setIsMarriage(Integer isMarriage) {
            this.isMarriage = isMarriage;
        }

        public Integer getCareChildAlone() {
            return careChildAlone;
        }

        public void setCareChildAlone(Integer careChildAlone) {
            this.careChildAlone = careChildAlone;
        }

        public Integer getFinaceHealthy() {
            return finaceHealthy;
        }

        public void setFinaceHealthy(Integer finaceHealthy) {
            this.finaceHealthy = finaceHealthy;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
