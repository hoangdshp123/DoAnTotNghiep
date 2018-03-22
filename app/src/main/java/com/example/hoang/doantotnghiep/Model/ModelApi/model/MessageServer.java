package com.example.hoang.doantotnghiep.Model.ModelApi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vancu on 07/12/2017.
 */

public class MessageServer {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("token")
    @Expose
    private String token;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class Profile{
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email_social")
        @Expose
        private String emailSocial;
        @SerializedName("owner")
        @Expose
        private String owner;
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
    public static class User{
        @SerializedName("profile")
        @Expose
        private List<Profile> profile = null;
        @SerializedName("facebook_id")
        @Expose
        private String facebookId;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("isFirstLogin")
        @Expose
        private Boolean isFirstLogin;
        @SerializedName("id")
        @Expose
        private String id;

        public List<Profile> getProfile() {
            return profile;
        }

        public void setProfile(List<Profile> profile) {
            this.profile = profile;
        }

        public String getFacebookId() {
            return facebookId;
        }

        public void setFacebookId(String facebookId) {
            this.facebookId = facebookId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Boolean getIsFirstLogin() {
            return isFirstLogin;
        }

        public void setIsFirstLogin(Boolean isFirstLogin) {
            this.isFirstLogin = isFirstLogin;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
