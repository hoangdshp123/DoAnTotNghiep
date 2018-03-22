package com.example.hoang.doantotnghiep.Presenter.Login;

import com.example.hoang.doantotnghiep.Model.ModelLogin.ModelLogin;
import com.example.hoang.doantotnghiep.Model.ModelLogin.ModelLoginResponse;

/**
 * Created by dangha.dev on 9/28/2017.
 */

public class PresenterLoginAccess implements ModelLoginResponse{

    PresenterLoginResponse callback;
    ModelLogin modelLogin;

    public PresenterLoginAccess() {
    }

    public PresenterLoginAccess(PresenterLoginResponse callback) {
        this.callback = callback;
    }


    public void received(String email, String password){
        modelLogin = new ModelLogin(this);
        modelLogin.Loginwithemail(email,password);
    }

    @Override
    public void importloginmissing() {
        callback.importloginmissing1();
    }

    @Override
    public void importloginerror() {
        callback.importloginerror1();
    }

    @Override
    public void importloginsuccess() {
        callback.importloginsuccess1();
    }
}
