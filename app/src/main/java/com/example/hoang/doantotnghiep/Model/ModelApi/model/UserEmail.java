package com.example.hoang.doantotnghiep.Model.ModelApi.model;

/**
 * Created by hoang on 12/18/2017.
 */

public class UserEmail {
    String email;
    String password;
    String type;
    String id;

    public UserEmail(String email, String type, String id) {
        this.email = email;
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserEmail(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserEmail() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
