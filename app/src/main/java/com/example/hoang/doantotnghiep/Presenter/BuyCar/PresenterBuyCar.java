package com.example.hoang.doantotnghiep.Presenter.BuyCar;

import com.example.hoang.doantotnghiep.Model.ModelBuyCar.ModelBuyCarHandler;
import com.example.hoang.doantotnghiep.Model.ModelBuyCar.ModelBuyCarResponse;

/**
 * Created by hoang on 12/25/2017.
 */

public class PresenterBuyCar implements ModelBuyCarResponse {
    ModelBuyCarHandler modelBuyCarHandler;
    PresenterBuyCarResponse callback;

    public PresenterBuyCar(PresenterBuyCarResponse callback) {
        this.callback = callback;
    }




    public void received(String money, String year, String interestrate,String preferential_interestrate,String year_preferential){
        modelBuyCarHandler = new ModelBuyCarHandler(this);
        modelBuyCarHandler.xulylogic(money,year,interestrate,preferential_interestrate,year_preferential);
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

    @Override
    public void importsuccess2() {
        callback.importsuccess12();
    }
}
