package com.example.hoang.doantotnghiep.Model.ModelApi.ModelProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 1/17/2018.
 */

public class MessageProfile {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("staus")
    @Expose
    private String staus;
    @SerializedName("profile")
    @Expose
    private Profile profile;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public static class Profile {
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email_social")
        @Expose
        private String emailSocial;
        @SerializedName("owner")
        @Expose
        private String owner;
        @SerializedName("finace_healthy")
        @Expose
        private Integer finaceHealthy;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmailSocial() {
            return emailSocial;
        }

        public void setEmailSocial(String emailSocial) {
            this.emailSocial = emailSocial;
        }

        public Integer getFinaceHealthy() {
            return finaceHealthy;
        }

        public void setFinaceHealthy(Integer finaceHealthy) {
            this.finaceHealthy = finaceHealthy;
        }

        @SerializedName("createdAt")
        @Expose

        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("id")
        @Expose
        private String id;

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
