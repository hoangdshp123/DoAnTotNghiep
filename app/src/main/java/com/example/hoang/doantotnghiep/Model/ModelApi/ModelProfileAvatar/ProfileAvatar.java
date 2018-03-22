package com.example.hoang.doantotnghiep.Model.ModelApi.ModelProfileAvatar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 1/16/2018.
 */

public class ProfileAvatar {
    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
