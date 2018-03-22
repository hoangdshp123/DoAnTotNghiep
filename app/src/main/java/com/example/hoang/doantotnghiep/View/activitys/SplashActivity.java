package com.example.hoang.doantotnghiep.View.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.utils.SharedPrefsUtils;

/**
 * Created by hoang on 12/19/2017.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String token = SharedPrefsUtils.getStringPreference(SplashActivity.this,"token");
                if(token == null || token == ""){
                    Intent i = new Intent(SplashActivity.this,LoginRegister.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        },3000);
    }
}
