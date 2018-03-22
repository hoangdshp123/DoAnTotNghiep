package com.example.hoang.doantotnghiep.Model.ModelBuyCar;

/**
 * Created by hoang on 12/25/2017.
 */

public class ModelBuyCarHandler {
    ModelBuyCarResponse callback;

    public ModelBuyCarHandler(ModelBuyCarResponse callback) {
        this.callback = callback;
    }

    public void xulylogic(String money, String year, String interestrate, String preferential_interestrate,String year_preferential){
        if(money.equals("")|| year.equals("")||interestrate.equals("")){
            //nhapthieu
            callback.importmissing();
        }
        else {
            if(Integer.parseInt(interestrate)>20 || Integer.parseInt(year)>20){
                //nhapsai
                callback.importerror();
            }
            else {
                //thanh cong
                if(preferential_interestrate.equals("")||year_preferential.equals(""))
                {
                    callback.importsuccess();

                }
                else
                    callback.importsuccess2();
            }
        }

    }
}
