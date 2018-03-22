package com.example.hoang.doantotnghiep.Model.ModelRegister;

/**
 * Created by hoang on 12/18/2017.
 */

public class ModelRegister {
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ModelRegisterResponse callback;

    public ModelRegister(ModelRegisterResponse callback) {
        this.callback = callback;
    }

    public void xulylogic(String email, String password, String repassword){
        if(email.equals("")||password.equals("")||repassword.equals("")){
            //chưa nhập đủ
            callback.importmissing();
        }
        else {
            if(email.length()>=4 && email.matches(emailPattern) && password.length()>=6 && password.equals(repassword) == true){
                //nhập đúng
                callback.importsuccess();
            }
            else{
                //nhập sai
                callback.importerror();
            }
        }
    }
}
