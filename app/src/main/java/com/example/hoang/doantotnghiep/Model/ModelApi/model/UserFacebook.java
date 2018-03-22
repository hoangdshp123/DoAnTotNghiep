package com.example.hoang.doantotnghiep.Model.ModelApi.model;

/**
 * Created by hoang on 12/15/2017.
 */


public class UserFacebook {

    String facebookId;
    String name;
    String email;
    Boolean isFirstLogin;

    public UserFacebook(String facebookId, String name, String email, Boolean isFirstLogin) {
        this.facebookId = facebookId;
        this.name = name;
        this.email = email;
        this.isFirstLogin = isFirstLogin;
    }

    public UserFacebook(String facebookId, String name, String email) {
        this.facebookId = facebookId;
        this.name = name;
        this.email = email;
    }

    public UserFacebook() {
    }

    public Boolean getFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
