package com.example.hoang.doantotnghiep.View.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ResponseCommon;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;

public class ViewCaculateBorrow extends AppCompatActivity {

    TextView tx1,tx2,tx3,tx4;
    Intent intent;
    String id,money,time,income,office,electric,issurance,typeRecieve,address,email,phone,payMonth,tag;
    TextView title_toolsbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_caculate_borrow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title_toolsbar = toolbar.findViewById(R.id.toolbar_title);
        title_toolsbar.setText("Kết quả");
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());

        tx2 = findViewById(R.id.txt_borrow_tx2);
        tx3 = findViewById(R.id.txt_borrow_tx3);
        tx4 = findViewById(R.id.txt_borrow_tx4);

        intent = getIntent();
        tag = intent.getStringExtra("TagBorrow1");
            id = intent.getStringExtra("IdEdit1");
            money = intent.getStringExtra("MoneyBorrow1");
            time = intent.getStringExtra("TimeBorrow1");
            income = intent.getStringExtra("Income1");
            office = intent.getStringExtra("Office1");
            electric = intent.getStringExtra("Electric1");
            issurance = intent.getStringExtra("Isurrance1");
            typeRecieve = intent.getStringExtra("ReceiveMoney1");
            address = intent.getStringExtra("Adress1");
            email = intent.getStringExtra("Email1");
            phone = intent.getStringExtra("PhoneNumber1");
            payMonth = intent.getStringExtra("PayPerMonth");

            tx2.setText(money);
            tx3.setText(time+" tháng");
            tx4.setText(DecimalFormatUtils.getMoney(Double.parseDouble(payMonth)));

        findViewById(R.id.btn_accept_borrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tag.equals("2")){
                    CallAPI.updateBorrow(ViewCaculateBorrow.this,"Bearer " + HomeActivity.token,id,"SHB bank",Double.parseDouble(money),
                            Integer.parseInt(String.valueOf(time)),Double.parseDouble(income),office,typeRecieve,Double.parseDouble(electric),Double.parseDouble(issurance),address,
                            email,phone,2.4, Float.parseFloat(String.valueOf(payMonth)));
                }
                else if(tag.equals("1")) {
                    CallAPI.addNewBorrow(ViewCaculateBorrow.this, "SHB bank", Double.parseDouble(money), Integer.parseInt(time), Double.parseDouble(income),
                            office, typeRecieve, Double.parseDouble(electric), Double.parseDouble(issurance), address, email, phone, 2.4, Double.parseDouble(payMonth), new CallAPI.OnAddBorrow() {
                                @Override
                                public void onResult(ResponseCommon responseCommon) {
                                    if (responseCommon.code == 0) {
                                        Toast.makeText(getApplicationContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                                    } else if (responseCommon.code == 1) {
                                        Toast.makeText(getApplicationContext(), "Thêm khoản vay thành công", Toast.LENGTH_SHORT).show();
                                        setResult(2021);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

        findViewById(R.id.btn_back_borrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
