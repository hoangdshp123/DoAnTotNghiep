package com.example.hoang.doantotnghiep.Model.ModelApi.ModelPayMent1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 1/22/2018.
 */

public class ModelPayment {
    @SerializedName("code")
    @Expose
    private Integer code;
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
