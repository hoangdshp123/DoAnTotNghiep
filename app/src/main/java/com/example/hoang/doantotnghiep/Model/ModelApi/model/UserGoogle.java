package com.example.hoang.doantotnghiep.Model.ModelApi.model;

/**
 * Created by hoang on 12/15/2017.
 */

public class UserGoogle {
    String googleId;
    String name;
    String email;

    public UserGoogle(String googleId, String name, String email) {
        this.googleId = googleId;
        this.name = name;
        this.email = email;
    }

    public UserGoogle() {
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
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
