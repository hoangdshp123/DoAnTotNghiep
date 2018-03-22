package com.example.hoang.doantotnghiep.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kien.lovan on 1/18/2018.
 */

public class ResponseCommon {
    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("status")
    @Expose
    private String status;

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

}
