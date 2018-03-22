package com.example.hoang.doantotnghiep.Presenter.Register;

import com.example.hoang.doantotnghiep.Model.ModelRegister.ModelRegister;
import com.example.hoang.doantotnghiep.Model.ModelRegister.ModelRegisterResponse;

/**
 * Created by hoang on 12/18/2017.
 */

public class PresenterRegister implements ModelRegisterResponse{
    PresenterRegisterResponse callback;
    ModelRegister modelRegister;

    public PresenterRegister(PresenterRegisterResponse callback) {
        this.callback = callback;
    }

    public void received(String email, String password, String repassword){
        modelRegister = new ModelRegister(this);
        modelRegister.xulylogic(email,password,repassword);
    }

    @Override
    public void importmissing() {
        callback.importmissing1();
    }

    @Override
    public void importerror() {
        callback.importerror1();
    }

    @Override
    public void importsuccess() {
        callback.importsuccess1();
    }
}
