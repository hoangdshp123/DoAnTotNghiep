package com.example.hoang.doantotnghiep.Model.ModelApi.modelPayment;

/**
 * Created by TWO on 12/27/2017.
 */

public class PaymentHandler {
    private PaymentRespone callback;

    public PaymentHandler(PaymentRespone callback) {
        this.callback = callback;
    }

    public void checkData(String name, String value){
        if (name.equals("")||value.equals("")){
            callback.addmissing();
        } else {

        }
    }

}
