package com.example.hoang.doantotnghiep.View.activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ModelApi.model.UserEmail;
import com.example.hoang.doantotnghiep.Model.ModelLogin.ModelLogin;
import com.example.hoang.doantotnghiep.Presenter.Login.PresenterLoginAccess;
import com.example.hoang.doantotnghiep.Presenter.Login.PresenterLoginResponse;
import com.example.hoang.doantotnghiep.Presenter.Register.PresenterRegister;
import com.example.hoang.doantotnghiep.Presenter.Register.PresenterRegisterResponse;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.utils.SharedPrefsUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;

public class LoginRegister extends AppCompatActivity implements View.OnClickListener, PresenterRegisterResponse, PresenterLoginResponse {
    public static int RC_SIGN_IN = 111;
    CallbackManager callbackManager;
    ProgressDialog mProgressDialog;
    LinearLayout layoutlogin, layoutregister;
    TextView txtvlogin, txtvregister;
    EditText edtrgtemail, edtpassword, edtrepassword, edtloginacount, edtloginpassword;
    String rgtemail, rgtpassword, rgtrepassword, loginacount, loginpassword, useremailid, useremailname;
    CallAPI Call = new CallAPI();

    String picture = "", id = "", name = "", idgg = "", namegg = "", emailfb = "", emailgg = "";
    PresenterLoginAccess presenterLogin;
    AccessToken accessToken;
    ModelLogin modelLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginregister);
        callbackManager = CallbackManager.Factory.create();
        findViewById(R.id.login_buttonfb).setOnClickListener(this);
        findViewById(R.id.txtv_register).setOnClickListener(this);
        findViewById(R.id.txtv_login).setOnClickListener(this);
        findViewById(R.id.btn_rgtemail).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        //Visibility
        layoutlogin = findViewById(R.id.Layout_login);
        layoutlogin.setVisibility(View.VISIBLE);
        layoutregister = findViewById(R.id.Layout_register);
        layoutregister.setVisibility(View.INVISIBLE);
        //Anh xa
        txtvlogin = findViewById(R.id.txtv_login);
        txtvregister = findViewById(R.id.txtv_register);
        txtvregister.setTextColor(Color.GRAY);
        edtrgtemail = findViewById(R.id.edt_rgtemail);
        edtpassword = findViewById(R.id.edt_rgtpassword);
        edtrepassword = findViewById(R.id.edt_rergtpassword);
        edtloginacount = findViewById(R.id.edt_account);
        edtloginpassword = findViewById(R.id.edt_password);

    }

    private void addDownload() {
        String deviceID = Build.ID;
        String deviceName = Build.BRAND + " " + Build.DEVICE;
        String deviceOS = "Android";
        String deviceVersion = Build.VERSION.RELEASE;

        SharedPreferences preferences = getSharedPreferences("mData", MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        String myID = preferences.getString("id", "");
        if (myID.equals("")) {
            edit.putString("id", deviceID);
            edit.commit();
            CallAPI.addDownload(LoginRegister.this,
                    "Bearer " + SharedPrefsUtils.getStringPreference(getApplicationContext(), "token"),
                    deviceID, deviceName, deviceOS, deviceVersion);
        }

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtv_register: {
                layoutlogin.setVisibility(View.INVISIBLE);
                layoutregister.setVisibility(View.VISIBLE);
                txtvlogin.setTextColor(Color.GRAY);
                txtvregister.setTextColor(Color.WHITE);
            }
            break;

            case R.id.txtv_login: {
                layoutlogin.setVisibility(View.VISIBLE);
                layoutregister.setVisibility(View.INVISIBLE);
                txtvlogin.setTextColor(Color.WHITE);
                txtvregister.setTextColor(Color.GRAY);
            }
            break;

            case R.id.btn_rgtemail: {
                PresenterRegister presenterRegister = new PresenterRegister(this);
                rgtemail = edtrgtemail.getText().toString().trim();
                rgtpassword = edtpassword.getText().toString().trim();
                rgtrepassword = edtrepassword.getText().toString().trim();
                presenterRegister.received(rgtemail, rgtpassword, rgtrepassword);
            }
            break;
            case R.id.btn_login: {
                loginacount = edtloginacount.getText().toString().trim();
                loginpassword = edtloginpassword.getText().toString().trim();
                PresenterLoginAccess presenterLogin = new PresenterLoginAccess(this);
                presenterLogin.received(loginacount, loginpassword);
                SharedPrefsUtils.setStringPreference(this, "emailInfo", loginacount);
            }
            break;
        }
    }



    @Override
    public void importmissing1() {
        Toast.makeText(this, "Nhập thiếu thông tin !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void importerror1() {
        Toast.makeText(this, "Email hoặc password chưa đúng định dạng !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void importsuccess1() {
        UserEmail user = new UserEmail(rgtemail, rgtpassword);
        Call.insert_signup(this, user, layoutlogin, layoutregister, txtvlogin, txtvregister,
                edtrgtemail, edtloginacount, edtpassword, edtrepassword);
    }

    @Override
    public void importloginmissing1() {
        //Nhap thieu
        Toast.makeText(this, "Nhập thiếu thông tin !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void importloginerror1() {
        // Nhap loi
        Toast.makeText(this, "Nhập sai !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void importloginsuccess1() {
        //thanh cong
        UserEmail userlogin = new UserEmail(loginacount, loginpassword);
        Call.Login_Email(this, userlogin, new CallAPI.OnLoginSuccess() {
            @Override
            public void onSuccess(boolean success, Boolean isFirstLogin) {
                if (success) {
                    addDownload();
                    if (!isFirstLogin) {
                        Intent iIntro = new Intent(LoginRegister.this, IntroActivity.class);
                        iIntro.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(iIntro);
                        finish();
                    } else {
                        Intent iIntro = new Intent(LoginRegister.this, HomeActivity.class);
                        iIntro.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(iIntro);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.iconout1)
                .setTitle("THOÁT")
                .setMessage("Bạn có chắc chắn thoát khỏi ứng dụng?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory(Intent.CATEGORY_HOME);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
