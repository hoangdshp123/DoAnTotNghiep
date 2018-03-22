package com.example.hoang.doantotnghiep.Model.ModelLogin;

/**
 * Created by Admins on 9/28/2017.
 */

public class ModelLogin {
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ModelLoginResponse callback;

    public ModelLogin(ModelLoginResponse callback) {
        this.callback = callback;
    }

    public ModelLogin() {
    }

    public void Loginwithemail(String email, String password){
        if(email.equals("")||password.equals("")){
            //chưa nhập đủ
            callback.importloginmissing();
        }
        else {
            if(email.length()>=4 && email.matches(emailPattern) && password.length()>=6){
                //nhập đúng
                callback.importloginsuccess();
            }
            else {
                //nhập sai
                callback.importloginerror();
            }
        }
    }
}
