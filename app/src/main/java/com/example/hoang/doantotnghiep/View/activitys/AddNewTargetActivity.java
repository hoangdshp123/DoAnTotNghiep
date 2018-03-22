package com.example.hoang.doantotnghiep.View.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ModelApi.TargetSaving;
import com.example.hoang.doantotnghiep.Model.ModelTarget.ModelEditLoan;
import com.example.hoang.doantotnghiep.Model.ModelTarget.ModelTarget;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.RestApi.RetrofitClient;
import com.example.hoang.doantotnghiep.View.dialog.DateDialog;
import com.example.hoang.doantotnghiep.utils.Const;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;
import com.example.hoang.doantotnghiep.utils.SharedPrefsUtils;
import com.example.hoang.doantotnghiep.utils.TakeImageUtils;
import com.example.hoang.doantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

public class AddNewTargetActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {


    // view for target saving
    ImageView img_picker_date;
    EditText edt_name_target, edt_value_target, edt_age, edt_age_now;
    TextView txt_money_perMotnh, edt_date_target;
    //view for targetm loan
    EditText edt_name_target_loan, edt_origin_money, edt_preferential_rate, edt_preferential_time, edt_rate_base, edt_loan_time;
    TextView tv_pay_per_month, tv_total_interest_pay, tv_total_money_pay, tv_pay_per_year;
    //view commmon
    LinearLayout btn_get_photo, btn_select_icon, layout_parent, layout_parent1, layoutAge;
    ImageView icon_selected, img_close, viewSaving;
    TextView txt_result;
    Button btn_cancel, btn_ok;
    Dialog dialogShowPickDate;
    File img_icon_file = null;
    String static_icon = "", payperYear = "";
    String per_month = "";
    String type = "", type1 = "", tag = "";
    LinearLayout view_target_loan;
    LinearLayout view_target_saving;
    Intent intent;
    String targetName, getTargetNameSaving, originMoney, time, ratebase, moneyPerMounth, totalmoneyTargetLoan, imgLoan,
            ratePrefer, timePrefer, valueSaving, deadLine, mouneyTarget, iconsaving, targetSavingId, interestPay, b1, b2, b3, loanid;
    int position;
    Toolbar toolbar;
    TextView title;
    String token;
    double money_per_month;
    TextView txtResult1, txtChooseImage, txtImage, txtIcon;
    RelativeLayout relative_parent;
    TextView txt_name_target, txt_value_target, txt_date_target, tv_money_perMotnh;
    TextView txt_name_target_loan, txt_origin_money, txt_preferential_rate, txt_preferential_time,
            txt_rate_base, txt_loan_time, txt_pay_per_month, txt_total_interest_pay, txt_total_money_pay;
    TextView txRs1, txRs2, txRs3, txRs4;
    TextView title_retirement;
    EditText edt_year_retirement;
    public static DecimalFormat df2 = new DecimalFormat("#,###,###,###,###,###");

    int ageNow = 0;
    int age = 0;
    String date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        tag = intent.getStringExtra("tag");
        Log.d("TAG", tag);
        if (tag.equals(Const.TAG_SETUP3)) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_add_new_target);

        type = intent.getStringExtra("type");
        type1 = intent.getStringExtra("type1");

        initViewCommon(tag);


        if (type1.equals("add")) {
            if (type.equals("target_saving")) {
                targetName = intent.getStringExtra("name_saving");
                position = intent.getIntExtra("position_saving", 0);

                view_target_saving.setVisibility(View.VISIBLE);
                view_target_loan.setVisibility(View.GONE);
                layout_parent.setVisibility(View.INVISIBLE);
                img_close.setVisibility(View.VISIBLE);
                icon_selected.setVisibility(View.VISIBLE);
                initViewTargetSaving(tag);
                if (edt_name_target.getText().toString().equals("Mục tiêu khác")) {
                    edt_name_target.setFocusable(true);
                    edt_name_target.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                    edt_name_target.setClickable(true);
                } else {
                    if (edt_name_target.getText().toString().equals("Tiền nghỉ hưu")) {
                        layoutAge.setVisibility(View.VISIBLE);
                        findViewById(R.id.btn_choose_date).setVisibility(View.INVISIBLE);
                        viewSaving.setVisibility(View.INVISIBLE);
                    }
                    edt_name_target.setFocusable(false);
                    edt_name_target.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    edt_name_target.setClickable(false);
                }

            } else if (type.equals("target_loan")) {
                targetName = intent.getStringExtra("name_loan");
                position = intent.getIntExtra("position_loan", 0);

                view_target_loan.setVisibility(View.VISIBLE);
                view_target_saving.setVisibility(View.GONE);
                layout_parent.setVisibility(View.INVISIBLE);
                img_close.setVisibility(View.VISIBLE);
                icon_selected.setVisibility(View.VISIBLE);
                initViewTargetLoan(tag);
            }
        } else if (type1 != null && type1.equals("editloan")) {
            targetName = intent.getStringExtra("name_loan");
            originMoney = intent.getStringExtra("originmoney");
            ratePrefer = intent.getStringExtra("preferentrate");
            timePrefer = intent.getStringExtra("preferenttime");
            time = intent.getStringExtra("time");
            ratebase = intent.getStringExtra("ratebase");
            moneyPerMounth = intent.getStringExtra("moneyPerMounth");
            totalmoneyTargetLoan = intent.getStringExtra("totalmoneyTargetLoan");
            imgLoan = intent.getStringExtra("imgLoan");
            interestPay = intent.getStringExtra("interestPay");
            loanid = intent.getStringExtra("loanid");
            static_icon = intent.getStringExtra("static_icon");
            payperYear = intent.getStringExtra("payperYear");

            view_target_loan.setVisibility(View.VISIBLE);
            view_target_saving.setVisibility(View.GONE);
            layout_parent.setVisibility(View.INVISIBLE);
            initViewTargetLoan1(tag);
            img_close.setVisibility(View.VISIBLE);
            icon_selected.setVisibility(View.VISIBLE);
            // Toast.makeText(this, "Sửa mục tiêu vay !", Toast.LENGTH_SHORT).show();
        } else if (type1 != null && type1.equals("editsaving")) {
            type = "target_saving";
            view_target_saving.setVisibility(View.VISIBLE);
            view_target_loan.setVisibility(View.GONE);
            layout_parent.setVisibility(View.INVISIBLE);
            getTargetNameSaving = intent.getStringExtra("name_saving1");
            valueSaving = intent.getStringExtra("value");
            deadLine = intent.getStringExtra("deadline");
            mouneyTarget = intent.getStringExtra("moneyTarget");
            iconsaving = intent.getStringExtra("icon");
            targetSavingId = intent.getStringExtra("idsaving");
            static_icon = intent.getStringExtra("static_icon");
            initViewTargetSaving1(tag);
            String nameSaving = edt_name_target.getText().toString();
            if (nameSaving.equals("Mua nhà") || nameSaving.equals("Mua ô tô") || nameSaving.equals("Mua sắm") ||
                    nameSaving.equals("Đi du lịch") || nameSaving.equals("Sửa nhà") || nameSaving.equals("Quỹ khẩn cấp") ||
                    nameSaving.equals("Tiền nghỉ hưu") || nameSaving.equals("Trả nợ")) {

                edt_name_target.setFocusable(false);
                edt_name_target.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                edt_name_target.setClickable(false);
            } else {
                edt_name_target.setFocusable(true);
                edt_name_target.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                edt_name_target.setClickable(true);
            }
            img_close.setVisibility(View.VISIBLE);
            icon_selected.setVisibility(View.VISIBLE);
            // Toast.makeText(this, "Sửa mục tiêu tiết kiệm !", Toast.LENGTH_SHORT).show();
        }

//        type_target = getIntent().getStringExtra("type_target");
        RetrofitClient.MyauthHeaderContent = "Bearer " + SharedPrefsUtils.getStringPreference(this, "token");
    }

    public void initViewCommon(String tag) {
        txtResult1 = findViewById(R.id.txt_result1);
        txtChooseImage = findViewById(R.id.txt_choose_image);
        txtIcon = findViewById(R.id.txt_icon);
        txtImage = findViewById(R.id.txt_image);
        layout_parent = findViewById(R.id.layout_parent);
        layout_parent1 = findViewById(R.id.layout_parent1);
        txt_result = findViewById(R.id.txt_result);
        relative_parent = findViewById(R.id.relative_parent);

        txt_name_target = findViewById(R.id.txt_name_target);
        txt_value_target = findViewById(R.id.txt_value_target);
        txt_date_target = findViewById(R.id.txt_date_target);
        tv_money_perMotnh = findViewById(R.id.tv_money_perMotnh);
        txt_name_target_loan = findViewById(R.id.txt_name_target_loan);
        txt_origin_money = findViewById(R.id.txt_origin_money);
        txt_preferential_rate = findViewById(R.id.txt_preferential_rate);
        txt_preferential_time = findViewById(R.id.txt_preferential_time);
        txt_rate_base = findViewById(R.id.txt_rate_base);
        txt_loan_time = findViewById(R.id.txt_loan_time);
        txt_pay_per_month = findViewById(R.id.tv_pay_per_month);
        txt_total_interest_pay = findViewById(R.id.tv_total_interest_pay);
        txt_total_money_pay = findViewById(R.id.tv_total_money_pay);
        txRs1 = findViewById(R.id.txtv_loan_txrs1);
        txRs2 = findViewById(R.id.txtv_loan_txrs2);
        txRs3 = findViewById(R.id.txtv_loan_txrs3);
        txRs4 = findViewById(R.id.txtv_loan_txrs4);


        edt_year_retirement = findViewById(R.id.edt_year_retirement);
        title_retirement = findViewById(R.id.title_retirement);
        edt_year_retirement.setVisibility(View.GONE);
        title_retirement.setVisibility(View.GONE);


        btn_get_photo = findViewById(R.id.btn_get_photo);
        btn_select_icon = findViewById(R.id.btn_select_icon);
        btn_get_photo.setOnClickListener(this);
        btn_select_icon.setOnClickListener(this);
        icon_selected = findViewById(R.id.icon_selected);
        img_close = findViewById(R.id.img_close);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        img_close.setOnClickListener(this);
        view_target_loan = findViewById(R.id.view_target_loan);
        view_target_saving = findViewById(R.id.view_target_saving);
        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.toolbar_title);
        token = "Bearer " + SharedPrefsUtils.getStringPreference(this, "token");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());
        toolbar.setPadding(0, 0, 24, 0);
        title.setText(getResources().getString(R.string.addtarget));
        title.setGravity(Gravity.CENTER);


        dialogShowPickDate = new DateDialog(this, new DateDialog.DateSelected() {
            @Override
            public void onSelected(String date) {
                edt_date_target.setText(Utils.revertStringDate(date));
            }
        });

        //if tag
        if (tag.equals(Const.TAG_SETUP3)) {
            txtResult1.setTextColor(getResources().getColor(R.color.color_white));
            txtImage.setTextColor(getResources().getColor(R.color.color_white));
            txtIcon.setTextColor(getResources().getColor(R.color.color_white));
            txtChooseImage.setTextColor(getResources().getColor(R.color.color_white));
            layout_parent.setBackgroundColor(getResources().getColor(R.color.color_background_default));
            layout_parent1.setBackgroundColor(getResources().getColor(R.color.color_background_default));
            relative_parent.setBackgroundColor(getResources().getColor(R.color.color_background_default));
            txt_result.setTextColor(getResources().getColor(R.color.color_white));
            toolbar.setBackgroundColor(getResources().getColor(R.color.color_background_default));


            txt_name_target.setTextColor(getResources().getColor(R.color.color_white));
            txt_value_target.setTextColor(getResources().getColor(R.color.color_white));
            txt_date_target.setTextColor(getResources().getColor(R.color.color_white));
            tv_money_perMotnh.setTextColor(getResources().getColor(R.color.color_white));
            txt_name_target_loan.setTextColor(getResources().getColor(R.color.color_white));
            txt_origin_money.setTextColor(getResources().getColor(R.color.color_white));
            txt_preferential_rate.setTextColor(getResources().getColor(R.color.color_white));
            txt_preferential_time.setTextColor(getResources().getColor(R.color.color_white));
            txt_rate_base.setTextColor(getResources().getColor(R.color.color_white));
            txt_loan_time.setTextColor(getResources().getColor(R.color.color_white));
            txt_pay_per_month.setTextColor(getResources().getColor(R.color.color_white));
            txt_total_interest_pay.setTextColor(getResources().getColor(R.color.color_white));
            txt_total_money_pay.setTextColor(getResources().getColor(R.color.color_white));
            txRs1.setTextColor(getResources().getColor(R.color.color_white));
            txRs2.setTextColor(getResources().getColor(R.color.color_white));
            txRs3.setTextColor(getResources().getColor(R.color.color_white));
            txRs4.setTextColor(getResources().getColor(R.color.color_white));

            img_close.setImageResource(R.drawable.remove_circle);

        }

    }


    void initViewTargetSaving(String tag) {
        img_picker_date = findViewById(R.id.img_picker_date);
        img_picker_date.setOnClickListener(this);
        findViewById(R.id.btn_choose_date).setOnClickListener(this);
        edt_name_target = findViewById(R.id.edt_name_target);

        viewSaving = findViewById(R.id.view_line_saving);
        edt_value_target = findViewById(R.id.edt_value_target);
        edt_date_target = findViewById(R.id.edt_date_target);
        edt_loan_time = findViewById(R.id.edt_loan_time);
        txt_money_perMotnh = findViewById(R.id.txt_money_perMotnh);
        edt_age = findViewById(R.id.edt_age);
        edt_age_now = findViewById(R.id.edt_age_now);
        layoutAge = findViewById(R.id.layout_age);
        edt_name_target.addTextChangedListener(this);
        edt_value_target.addTextChangedListener(this);
        edt_date_target.addTextChangedListener(this);
        edt_age.addTextChangedListener(this);
        edt_age_now.addTextChangedListener(this);

        if (tag.equals(Const.TAG_SETUP3)) {
            edt_name_target.setTextColor(getResources().getColor(R.color.color_white));
            edt_value_target.setTextColor(getResources().getColor(R.color.color_white));
            edt_date_target.setTextColor(getResources().getColor(R.color.color_white));
            edt_loan_time.setTextColor(getResources().getColor(R.color.color_white));
            txt_money_perMotnh.setTextColor(getResources().getColor(R.color.color_white));

            edt_name_target.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_value_target.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_date_target.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_loan_time.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            txt_money_perMotnh.setHintTextColor(getResources().getColor(R.color.text_hint_color));
        }

        edt_name_target.setText(targetName);
        edt_value_target.requestFocus();
        icon_selected.setVisibility(View.VISIBLE);
        setImageIcon(tag);
    }

    void initViewTargetSaving1(String tag) {
        img_picker_date = findViewById(R.id.img_picker_date);
        img_picker_date.setOnClickListener(this);
        edt_name_target = findViewById(R.id.edt_name_target);
        edt_value_target = findViewById(R.id.edt_value_target);
        edt_date_target = findViewById(R.id.edt_date_target);
        txt_money_perMotnh = findViewById(R.id.txt_money_perMotnh);

        edt_name_target.addTextChangedListener(this);
        edt_value_target.addTextChangedListener(this);
        edt_date_target.addTextChangedListener(this);

        if (tag.equals(Const.TAG_SETUP3)) {
            edt_name_target.setTextColor(getResources().getColor(R.color.color_white));
            edt_value_target.setTextColor(getResources().getColor(R.color.color_white));
            edt_date_target.setTextColor(getResources().getColor(R.color.color_white));
            edt_loan_time.setTextColor(getResources().getColor(R.color.color_white));
            txt_money_perMotnh.setTextColor(getResources().getColor(R.color.color_white));

            edt_name_target.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_value_target.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_date_target.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_loan_time.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            txt_money_perMotnh.setHintTextColor(getResources().getColor(R.color.text_hint_color));
        } else {
            edt_name_target.setText(getTargetNameSaving);
            edt_value_target.setText(valueSaving);
            edt_date_target.setText(deadLine);
        }
        if (static_icon.length() > 0) {
            int id = getResources().getIdentifier(static_icon, "drawable", getPackageName());
            icon_selected.setImageResource(id);
        } else
            Picasso.with(this).load(iconsaving).into(icon_selected);
        icon_selected.setVisibility(View.VISIBLE);
//        setImageIcon(tag);

    }

    void initViewTargetLoan(String tag) {
        edt_name_target_loan = findViewById(R.id.edt_name_target_loan);
        edt_origin_money = findViewById(R.id.edt_origin_money);
        edt_preferential_rate = findViewById(R.id.edt_preferential_rate);
        edt_preferential_time = findViewById(R.id.edt_preferential_time);
        edt_rate_base = findViewById(R.id.edt_rate_base);
        tv_pay_per_month = findViewById(R.id.tv_pay_per_month);
        tv_total_interest_pay = findViewById(R.id.tv_total_interest_pay);
        tv_total_money_pay = findViewById(R.id.tv_total_money_pay);
        tv_pay_per_year = findViewById(R.id.tv_pay_per_year);
        edt_loan_time = findViewById(R.id.edt_loan_time);
        edt_name_target_loan.addTextChangedListener(this);
        edt_origin_money.addTextChangedListener(this);
        edt_preferential_rate.addTextChangedListener(this);
        edt_preferential_time.addTextChangedListener(this);
        edt_rate_base.addTextChangedListener(this);
        tv_pay_per_month.addTextChangedListener(this);
        tv_total_interest_pay.addTextChangedListener(this);
        tv_total_money_pay.addTextChangedListener(this);
        edt_loan_time.addTextChangedListener(this);
        if (tag.equals(Const.TAG_SETUP3)) {
            edt_name_target_loan.setTextColor(getResources().getColor(R.color.color_white));
            edt_origin_money.setTextColor(getResources().getColor(R.color.color_white));
            edt_preferential_rate.setTextColor(getResources().getColor(R.color.color_white));
            edt_preferential_time.setTextColor(getResources().getColor(R.color.color_white));
            edt_rate_base.setTextColor(getResources().getColor(R.color.color_white));
            tv_pay_per_month.setTextColor(getResources().getColor(R.color.color_white));
            tv_total_interest_pay.setTextColor(getResources().getColor(R.color.color_white));
            tv_pay_per_year.setTextColor(getResources().getColor(R.color.color_white));
            tv_total_money_pay.setTextColor(getResources().getColor(R.color.color_white));
            edt_loan_time.setTextColor(getResources().getColor(R.color.color_white));

            edt_name_target_loan.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_origin_money.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_preferential_rate.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_preferential_time.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_rate_base.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            tv_pay_per_month.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            tv_total_interest_pay.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            tv_pay_per_year.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            tv_total_money_pay.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_loan_time.setHintTextColor(getResources().getColor(R.color.text_hint_color));
        }

        edt_name_target_loan.setText(targetName);
        edt_origin_money.requestFocus();
        icon_selected.setVisibility(View.VISIBLE);
        setImageIcon(tag);
        if (edt_name_target_loan.getText().toString().equals("Mua sắm")) {
            setMargins(edt_preferential_rate, 0, -70, 0, 0);
            setMargins(edt_preferential_time, 0, -70, 0, 0);
            setMargins(txt_preferential_rate, 0, -70, 0, 0);
            setMargins(txt_preferential_time, 0, -70, 0, 0);

            setMargins(txRs2, 0, -70, 0, 0);
            setMargins(tv_money_perMotnh, 0, -70, 0, 0);
            txRs2.setVisibility(View.INVISIBLE);
            tv_pay_per_year.setVisibility(View.INVISIBLE);
            edt_loan_time.setHint("0 Tháng");

            edt_preferential_rate.setVisibility(View.INVISIBLE);
            edt_preferential_time.setVisibility(View.INVISIBLE);
            txt_preferential_time.setVisibility(View.INVISIBLE);
            txt_preferential_rate.setVisibility(View.INVISIBLE);
        }
    }


    void initViewTargetLoan1(String tag) {
        edt_name_target_loan = findViewById(R.id.edt_name_target_loan);
        edt_origin_money = findViewById(R.id.edt_origin_money);
        edt_preferential_rate = findViewById(R.id.edt_preferential_rate);
        edt_preferential_time = findViewById(R.id.edt_preferential_time);
        edt_rate_base = findViewById(R.id.edt_rate_base);
        tv_pay_per_month = findViewById(R.id.tv_pay_per_month);
        tv_total_interest_pay = findViewById(R.id.tv_total_interest_pay);
        tv_total_money_pay = findViewById(R.id.tv_total_money_pay);
        tv_pay_per_year = findViewById(R.id.tv_pay_per_year);
        edt_loan_time = findViewById(R.id.edt_loan_time);
        edt_name_target_loan.addTextChangedListener(this);
        edt_origin_money.addTextChangedListener(this);
        edt_preferential_rate.addTextChangedListener(this);
        edt_preferential_time.addTextChangedListener(this);
        edt_rate_base.addTextChangedListener(this);
        tv_pay_per_month.addTextChangedListener(this);
        tv_total_interest_pay.addTextChangedListener(this);
        tv_total_money_pay.addTextChangedListener(this);
        edt_loan_time.addTextChangedListener(this);

        if (tag.equals(Const.TAG_SETUP3)) {
            edt_name_target_loan.setTextColor(getResources().getColor(R.color.color_white));
            edt_origin_money.setTextColor(getResources().getColor(R.color.color_white));
            edt_preferential_rate.setTextColor(getResources().getColor(R.color.color_white));
            edt_preferential_time.setTextColor(getResources().getColor(R.color.color_white));
            edt_rate_base.setTextColor(getResources().getColor(R.color.color_white));
            tv_pay_per_month.setTextColor(getResources().getColor(R.color.color_white));
            tv_total_interest_pay.setTextColor(getResources().getColor(R.color.color_white));
            tv_total_money_pay.setTextColor(getResources().getColor(R.color.color_white));
            edt_loan_time.setTextColor(getResources().getColor(R.color.color_white));
            txRs1.setTextColor(getResources().getColor(R.color.color_white));
            txRs2.setTextColor(getResources().getColor(R.color.color_white));
            txRs3.setTextColor(getResources().getColor(R.color.color_white));
            txRs4.setTextColor(getResources().getColor(R.color.color_white));
            tv_pay_per_year.setTextColor(getResources().getColor(R.color.color_white));

            edt_name_target_loan.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_origin_money.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_preferential_rate.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_preferential_time.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_rate_base.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            tv_pay_per_month.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            tv_total_interest_pay.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            tv_total_money_pay.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            edt_loan_time.setHintTextColor(getResources().getColor(R.color.text_hint_color));
            tv_pay_per_year.setHintTextColor(getResources().getColor(R.color.text_hint_color));

        }
        if (targetName.equals("Mua sắm")) {
            edt_name_target_loan.setText(targetName);
            edt_origin_money.setText(DecimalFormatUtils.getMone(Double.parseDouble(originMoney)));
            edt_preferential_rate.setText(null);
            edt_preferential_time.setText(null);
            edt_rate_base.setText(ratebase);
            edt_loan_time.setText(time);
        } else if (ratePrefer.equals("0") || timePrefer.equals("0")) {
            edt_name_target_loan.setText(targetName);
            edt_origin_money.setText(DecimalFormatUtils.getMone(Double.parseDouble(originMoney)));
            edt_preferential_rate.setText(null);
            edt_preferential_time.setText(null);
            edt_rate_base.setText(ratebase);
            edt_loan_time.setText(time);
        } else {
            edt_name_target_loan.setText(targetName);
            edt_origin_money.setText(DecimalFormatUtils.getMone(Double.parseDouble(originMoney)));
            edt_preferential_rate.setText(ratePrefer);
            edt_preferential_time.setText(timePrefer);
            edt_rate_base.setText(ratebase);
            edt_loan_time.setText(time);
        }
        if (static_icon.length() > 0) {
            int id = getResources().getIdentifier(static_icon, "drawable", getPackageName());
            icon_selected.setImageResource(id);
        } else
            Picasso.with(this).load(imgLoan).into(icon_selected);

        icon_selected.setVisibility(View.VISIBLE);
//        setImageIcon(tag);
        if (edt_name_target_loan.getText().toString().equals("Mua sắm")) {
            setMargins(edt_preferential_rate, 0, -70, 0, 0);
            setMargins(edt_preferential_time, 0, -70, 0, 0);
            setMargins(txt_preferential_rate, 0, -70, 0, 0);
            setMargins(txt_preferential_time, 0, -70, 0, 0);

            setMargins(txRs2, 0, -70, 0, 0);
            setMargins(tv_money_perMotnh, 0, -70, 0, 0);
            txRs2.setVisibility(View.INVISIBLE);
            tv_pay_per_year.setVisibility(View.INVISIBLE);
            edt_loan_time.setHint("0 Tháng");
            edt_preferential_rate.setVisibility(View.INVISIBLE);
            edt_preferential_time.setVisibility(View.INVISIBLE);
            txt_preferential_time.setVisibility(View.INVISIBLE);
            txt_preferential_rate.setVisibility(View.INVISIBLE);
        }
    }


    public void caculateTempleTargetSaving() {
//        try {
        String name = edt_name_target.getText().toString().trim();
        String value = edt_value_target.getText().toString().trim();

        int count_month_from_day = 0;
        int day = 0;
        int yearCount;
        if (name.equals("Tiền nghỉ hưu") && type1.equals("add")) {
            if (name.equals("") || value.equals("") || edt_age.getText().toString().equals("") || edt_age_now.getText().toString().equals(""))
                return;
            age = Integer.parseInt(edt_age.getText().toString());
            ageNow = Integer.parseInt(edt_age_now.getText().toString());
            count_month_from_day = (age - ageNow) * 12;
            yearCount = age - ageNow + Integer.parseInt(Utils.getCurrentYear());
            date = "30/12/" + String.valueOf(yearCount);
        } else {
            date = edt_date_target.getText().toString().trim();
            if (name.equals("") || value.equals("") || date.equals("")) {
                return;
            }
            if (!Utils.isThisDateValid(date, "dd/MM/yyyy")) {
                return;
            }
            if (Utils.convertStringToDate(date, "dd/MM/yyyy").before(Utils.convertStringToDate(Utils.getCurrentDate("dd/MM/yyyy"), "dd/MM/yyyy"))) {
                return;
            }
            day = Utils.getCountOfDays(Utils.getCurrentDate("dd/MM/yyyy"), date);
            count_month_from_day = day / 30;
            if (count_month_from_day <= 0) {
                count_month_from_day = 1;
            }
            if (day <= 0) {
                day = 1;
            }
        }

//        double money_per_day = Double.valueOf(value) / day;
        money_per_month = Double.valueOf(value) / count_month_from_day;
        DecimalFormat df = new DecimalFormat("#,###,###,###,###");
        per_month = df.format(money_per_month) + "";
        String u1 = df.format(money_per_month) + " vnđ";
        Spannable p1 = new SpannableString(u1);
        p1.setSpan(new CustomSpan().getHighlightSpan(tag), 0, u1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_money_perMotnh.setText(p1);

        String result = "Bằng cách tiết kiệm " + df.format(money_per_month) + " vnđ mỗi tháng, bạn sẽ đạt mục tiêu " + name + " của mình vào " + date;

        String t1 = "Bằng cách tiết kiệm ";
        int s1 = result.indexOf(t1);
        String t2 = "mỗi tháng, bạn sẽ đạt mục tiêu ";
        int s2 = result.indexOf(t2);

        String t3 = " của mình vào ";
        int s3 = result.indexOf(t3);

        Spannable spanText = new SpannableString(result);
        spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s1 + t1.length(), s2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s2 + t2.length(), s3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s3 + t3.length(), result.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_result.setText(spanText);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void caculateTempleTargetLoan(boolean callAPP) {
//        try {

        String loan_name = edt_name_target_loan.getText().toString().trim();
        String origin_money = edt_origin_money.getText().toString().trim();
        String preferential_rate = edt_preferential_rate.getText().toString().trim();
        String preferential_time = edt_preferential_time.getText().toString().trim();
        String rate_base = edt_rate_base.getText().toString().trim();
        String loan_time = edt_loan_time.getText().toString().trim();
        double a = 0.0, b = 0.0, c = 0.0;
        DecimalFormat formatter = new DecimalFormat("###############");


        if (loan_name.equals("") || origin_money.equals("") || rate_base.equals("") || loan_time.equals("")) {
            if (callAPP)
                Toast.makeText(this, getString(R.string.empty_value), Toast.LENGTH_SHORT).show();
            return;
        } else if (!preferential_rate.equals("") && Double.valueOf(preferential_rate) > 50) {
            if (callAPP)
                Toast.makeText(this, getString(R.string.low_preferential), Toast.LENGTH_SHORT).show();
            return;
        } else if (!preferential_time.equals("") && Double.valueOf(preferential_time) > 10) {
            if (callAPP)
                Toast.makeText(this, getString(R.string.low_preferential_time), Toast.LENGTH_SHORT).show();
            return;
        } else if (!loan_time.equals("") && Double.valueOf(loan_time) > 20) {
            if (callAPP)
                Toast.makeText(this, getString(R.string.loan_time_), Toast.LENGTH_SHORT).show();
            return;
        } else if (!rate_base.equals("") && Double.valueOf(rate_base) > 15) {
            if (callAPP)
                Toast.makeText(this, getString(R.string.low_rate_base), Toast.LENGTH_SHORT).show();
            return;
        } else if (origin_money.length() > 20) {
            Toast.makeText(this, "Số tiền bạn nhập quá lớn", Toast.LENGTH_SHORT).show();
        } else if (!rate_base.equals("") && !preferential_rate.equals("") && Double.parseDouble(rate_base) <= Double.parseDouble(preferential_rate)) {
            if (callAPP)
                Toast.makeText(this, "Lãi suất ưu đãi phải nhỏ hơn lãi suất thông thường", Toast.LENGTH_SHORT).show();
            return;
        } else if (!loan_time.equals("") && !preferential_time.equals("") && Integer.parseInt(loan_time) <= Integer.parseInt(preferential_time)) {
            if (callAPP)
                Toast.makeText(this, "Thời gian vay ưu đãi phải nhỏ hơn thời gian vay thông thường", Toast.LENGTH_SHORT).show();
            return;
        } else {
//        DecimalFormat df2 = new DecimalFormat(".##");
            // co lai suat uu dai
            if (!preferential_rate.equals("") && !preferential_time.equals("")) {
                int years, year2 = 0;
                double totalmoney, monthlypayment, difference, money, interestrate, annualpayment, preferential, yearpreferent;
                double r, n; // x2 dataset
                money = Double.parseDouble(origin_money);
                years = Integer.parseInt(loan_time);
                interestrate = Double.parseDouble(rate_base);
                double money2, totalmoney2, monthlypayment2, r2, n2, annualpayment2 = 0, difference2;
                preferential = Double.parseDouble(preferential_rate);
                yearpreferent = Double.parseDouble(preferential_time);

                if (years < yearpreferent)
                    Toast.makeText(this, "Thời gian hưởng lãi suất ưu đãi phải nhỏ hơn thời gian vay", Toast.LENGTH_SHORT).show();
                else if (Double.parseDouble(preferential_rate) == 0 || Double.parseDouble(preferential_time) == 0 || Double.parseDouble(origin_money) == 0 || Double.parseDouble(rate_base) == 0 || Double.parseDouble(loan_time) == 0) {
                    if (callAPP)
                        Toast.makeText(this, "Các trường phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    r = preferential / 12 / 100;
                    n = years * 12;
                    // Số tiền trả mỗi tháng theo lãi suất ưu đãi
                    monthlypayment = (money / ((1 - (Math.pow(Double.parseDouble(String.valueOf(1 + r)), Double.parseDouble(String.valueOf(n * (-1)))))) / r));

                    double tienconlai = money;

                    for (int i = 0; i < yearpreferent * 12; i++) {
                        tienconlai = tienconlai + tienconlai * preferential / 12 / 100 - monthlypayment;
                    }

                    year2 = (int) (years - yearpreferent);

                    r2 = interestrate / 12 / 100;
                    n2 = year2 * 12;
                    monthlypayment2 = (tienconlai / ((1 - (Math.pow(Double.parseDouble(String.valueOf(1 + r2)), Double.parseDouble(String.valueOf(n2 * (-1)))))) / r2));
                    annualpayment2 = monthlypayment * yearpreferent * 12 + monthlypayment2 * year2 * 12;

                    difference2 = annualpayment2 - money;
                    double totalanuualpay = monthlypayment2 * 12;
                    txt_pay_per_month.setText(DecimalFormatUtils.getMoney(monthlypayment2));
                    txt_total_interest_pay.setText(DecimalFormatUtils.getMoney(difference2));
                    txt_total_money_pay.setText(DecimalFormatUtils.getMoney(totalanuualpay));

                    String u1 = df2.format(monthlypayment2) + " vnđ";
                    Spannable p1 = new SpannableString(u1);
                    p1.setSpan(new CustomSpan().getHighlightSpan(tag), 0, u1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    String u2 = df2.format(difference2) + " vnđ";
                    Spannable p2 = new SpannableString(u2);
                    b1 = String.valueOf(difference2);
                    p2.setSpan(new CustomSpan().getHighlightSpan(tag), 0, u2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    String u3 = df2.format(totalanuualpay) + " vnđ";
                    Spannable p3 = new SpannableString(u3);
                    p3.setSpan(new CustomSpan().getHighlightSpan(tag), 0, u3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    double temp = Double.parseDouble(origin_money) + difference2;
                    b3 = String.valueOf(temp);
                    String u4 = df2.format(temp) + " vnđ";
                    Spannable p4 = new SpannableString(u4);
                    p4.setSpan(new CustomSpan().getHighlightSpan(tag), 0, u4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    b2 = String.valueOf(totalanuualpay);
                    tv_pay_per_month.setText(p1);
                    tv_total_interest_pay.setText(p2);
                    tv_pay_per_year.setText(p3);
                    tv_total_money_pay.setText(p4);
                    DecimalFormat df = new DecimalFormat("#,###,###,###,###");
                    String result = "Khi vay " + loan_name + " với khoản vay là " + df2.format(Double.parseDouble(origin_money)) + "vnđ. Lãi suất " + String.valueOf(interestrate) + "% một năm, trả trong thời gian là "
                            + String.valueOf(years) + " năm, lãi suất ưu đãi " + preferential_rate + "% trong " + preferential_time + "năm đầu, " +
                            "thanh toán hàng tháng trong thời gian " + String.valueOf(years) + " năm, bạn sẽ phải trả số tiền cả gốc và lãi là " +
                            DecimalFormatUtils.getMoney(temp) + ", số tiền phải trả hàng tháng là " + DecimalFormatUtils.getMoney(monthlypayment2) +
                            ", số tiền chênh lệch so với giá gốc là " + df.format(difference2) + " vnđ.";


                    String t1 = " với khoản vay là ";
                    int s1 = result.indexOf(t1);
                    String t2 = "vnđ. Lãi suất ";
                    int s2 = result.indexOf(t2);
                    String t3 = "% một năm, trả trong thời gian là ";
                    int s3 = result.indexOf(t3);
                    String t4 = "năm, lãi suất ưu đãi ";
                    int s4 = result.indexOf(t4);
                    String t5 = "% trong ";
                    int s5 = result.indexOf(t5);
                    String t6 = "năm đầu, ";
                    int s6 = result.indexOf(t6);
                    String t7 = "thanh toán hàng tháng trong thời gian ";
                    int s7 = result.indexOf(t7);
                    String t8 = "năm, bạn sẽ phải trả số tiền cả gốc và lãi là ";
                    int s8 = result.indexOf(t8);
                    String t9 = ", số tiền phải trả hàng tháng là ";
                    int s9 = result.indexOf(t9);
                    String t10 = ", số tiền chênh lệch so với giá gốc là ";
                    int s10 = result.indexOf(t10);
                    String t11 = " vnđ.";
                    int s11 = result.indexOf(t11);

                    Spannable spanText = new SpannableString(result);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), 7, s1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s1 + t1.length(), s2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s2 + t2.length(), s3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s3 + t3.length(), s4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s4 + t4.length(), s5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s5 + t5.length(), s6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s6 + t6.length(), s7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s7 + t7.length(), s8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s8 + t8.length(), s9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s9 + t9.length(), s10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s10 + t10.length(), s11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                txt_result.setText(spanText);

                    txt_result.setText(spanText);

                    a = monthlypayment2;
                    b = difference2;
                    c = totalanuualpay;
                    b1 = String.valueOf(monthlypayment2);
                    b2 = String.valueOf(difference2);
                    b3 = String.valueOf(totalanuualpay);
                }
            } else {

                double totalmoney, monthlypayment, difference, money, interestrate, annualpayment, preferential, yearpreferent;
                double r, n; // x2 dataset
                double years = 0;
                money = Double.parseDouble(edt_origin_money.getText().toString());
                if (edt_name_target_loan.getText().toString().equals("Mua sắm")) {
                    years = Double.parseDouble(edt_loan_time.getText().toString()) / 12;
                } else
                    years = Double.parseDouble(edt_loan_time.getText().toString());
                interestrate = Double.parseDouble(edt_rate_base.getText().toString());


                r = interestrate / 12 / 100;
                n = years * 12;
                monthlypayment = (double) (money / ((1 - (Math.pow(Double.parseDouble(String.valueOf(1 + r)), Double.parseDouble(String.valueOf(n * (-1)))))) / r));
                totalmoney = monthlypayment * n;
                annualpayment = totalmoney / years;
                difference = totalmoney - money;

                String u1 = df2.format(monthlypayment) + " vnđ";
                Spannable p1 = new SpannableString(u1);
                p1.setSpan(new CustomSpan().getHighlightSpan(tag), 0, u1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                String u2 = df2.format(monthlypayment * 12) + " vnđ";
                Spannable p2 = new SpannableString(u2);
                p2.setSpan(new CustomSpan().getHighlightSpan(tag), 0, u2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                double temp = Double.parseDouble(origin_money) + difference;
                String u3 = df2.format(temp) + " vnđ";
                Spannable p3 = new SpannableString(u3);
                p3.setSpan(new CustomSpan().getHighlightSpan(tag), 0, u3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                String u4 = df2.format(difference) + " vnđ";
                Spannable p4 = new SpannableString(u4);
                p4.setSpan(new CustomSpan().getHighlightSpan(tag), 0, u4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                tv_pay_per_month.setText(p1);
                tv_total_interest_pay.setText(p4);
                tv_pay_per_year.setText(p2);
                tv_total_money_pay.setText(p3);

                if (edt_name_target_loan.getText().toString().equals("Mua sắm")) {
                    String result = "Dựa trên dữ liệu bạn nhập vào, với khoản vay " + DecimalFormatUtils.getMoney(money) + " Lãi suất " +
                            String.valueOf(interestrate) + " %, trong thời gian là " + String.valueOf(years * 12) +
                            " tháng, bạn sẽ phải trả tổng tiền gốc và lãi là " + DecimalFormatUtils.getMoney(totalmoney) + ", số tiền phải trả hàng tháng là " + DecimalFormatUtils.getMoney(monthlypayment)
                            + ", Số tiền chênh lệch với giá gốc là " + DecimalFormatUtils.getMoney(difference);

                    String t1 = " Lãi suất ";
                    int s1 = result.indexOf(t1);
                    String t2 = " %, trong thời gian là ";
                    int s2 = result.indexOf(t2);
                    String t3 = " tháng, bạn sẽ phải trả tổng tiền gốc và lãi là ";
                    int s3 = result.indexOf(t3);
                    String t4 = ", số tiền phải trả hàng tháng là ";
                    int s4 = result.indexOf(t4);
                    String t5 = ", Số tiền chênh lệch với giá gốc là ";
                    int s5 = result.indexOf(t5);

                    Spannable spanText = new SpannableString(result);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), 44, s1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s1 + t1.length(), s2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s2 + t2.length(), s3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s3 + t3.length(), s4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s4 + t4.length(), s5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s5 + t5.length(), result.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    txt_result.setText(spanText);
                } else {
                    String result = "Dựa trên dữ liệu bạn nhập vào, với khoản vay " + DecimalFormatUtils.getMoney(money) + " Lãi suất " +
                            String.valueOf(interestrate) + " %, trong thời gian là " + String.valueOf(years) +
                            " năm, bạn sẽ phải trả tổng tiền gốc và lãi là " + DecimalFormatUtils.getMoney(totalmoney) + ", số tiền phải trả hàng tháng là " + DecimalFormatUtils.getMoney(monthlypayment)
                            + ", Số tiền chênh lệch với giá gốc là " + DecimalFormatUtils.getMoney(difference);

                    String t1 = " Lãi suất ";
                    int s1 = result.indexOf(t1);
                    String t2 = " %, trong thời gian là ";
                    int s2 = result.indexOf(t2);
                    String t3 = " năm, bạn sẽ phải trả tổng tiền gốc và lãi là ";
                    int s3 = result.indexOf(t3);
                    String t4 = ", số tiền phải trả hàng tháng là ";
                    int s4 = result.indexOf(t4);
                    String t5 = ", Số tiền chênh lệch với giá gốc là ";
                    int s5 = result.indexOf(t5);

                    Spannable spanText = new SpannableString(result);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), 44, s1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s1 + t1.length(), s2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s2 + t2.length(), s3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s3 + t3.length(), s4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s4 + t4.length(), s5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanText.setSpan(new CustomSpan().getHighlightSpan(tag), s5 + t5.length(), result.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    txt_result.setText(spanText);
                }

                a = monthlypayment;
                b = difference;
                c = totalmoney;
                b1 = String.valueOf(monthlypayment);
                b2 = String.valueOf(difference);
                b3 = String.valueOf(totalmoney);
            }

        }

        if (callAPP) {

            if (layout_parent.getVisibility() == View.VISIBLE) {
                Toast.makeText(this, getString(R.string.img_icon_null), Toast.LENGTH_SHORT).show();
                return;
            }
            double t1 = preferential_rate.equals("") ? 0 : Double.parseDouble(preferential_rate);
            double t2 = preferential_time.equals("") ? 0 : Double.parseDouble(preferential_time);
            double b1 = Double.parseDouble(origin_money);
            String s = formatter.format(b1);
            double b2 = Double.parseDouble(rate_base);
            double b3 = Double.parseDouble(loan_time);

            CallAPI.addNewTargetLoanSuccess(this, img_icon_file, loan_name, s, String.valueOf(t1), String.valueOf(t2)
                    , String.valueOf(b2), String.valueOf(b3), String.valueOf(formatter.format(a)), String.valueOf(formatter.format(b)), String.valueOf(formatter.format(c)), static_icon, new CallAPI.OnCallAddTargetLoanSuccess() {
                        @Override
                        public void onResult(Boolean success) {
                            if (success) {
                                Toast.makeText(AddNewTargetActivity.this, getString(R.string.addtargetsuccess), Toast.LENGTH_SHORT).show();
                                setResult(2121);
                            } else {
                                Toast.makeText(AddNewTargetActivity.this, getString(R.string.addtargetfail), Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }
                    });
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }


    public void validateAndCallApiTargetSaving() {
//        try {
        String name = edt_name_target.getText().toString().trim();
        String value = edt_value_target.getText().toString().trim();
//        int age = Integer.parseInt(edt_age.getText().toString());
//        int ageNow = Integer.parseInt(edt_age_now.getText().toString());

        if (name.equals("") || value.equals("") || date.equals("")) {
            Toast.makeText(this, getString(R.string.empty_value), Toast.LENGTH_SHORT).show();
            return;
        } else if (!Utils.isThisDateValid(date, "dd/MM/yyyy")) {
            Toast.makeText(this, getString(R.string.date_invalid), Toast.LENGTH_SHORT).show();
            return;
        } else if (Utils.convertStringToDate(date, "dd/MM/yyyy").before(new Date())) {
            Toast.makeText(this, getString(R.string.date_befor_current), Toast.LENGTH_SHORT).show();
            return;
        } else if (layout_parent.getVisibility() == View.VISIBLE) {
            Toast.makeText(this, getString(R.string.img_icon_null), Toast.LENGTH_SHORT).show();
            return;
        } else if (value.length() > 15) {
            Toast.makeText(this, "Số tiền bạn nhập quá lớn", Toast.LENGTH_SHORT).show();
        } else if (ageNow > age) {
            Toast.makeText(this, "Tuổi hiện tại phải nhỏ hơn tuổi nghỉ hưu", Toast.LENGTH_SHORT).show();
        } else {
            String date1;
//            if (name.equals("Tiền nghỉ hưu")) {
//                int year_retirement;
//                year_retirement = age - ageNow + Integer.parseInt(Utils.getCurrentYear());
//                String lastDate = "30/12/" + String.valueOf(year_retirement);
//                Date date3 = Utils.convertStringToDate(lastDate,"dd/MM/yyyy");
//                date1 = Utils.convertLocalTimeToUTC(date3);
//            } else{
            Date date2 = Utils.convertStringToDate(date, "dd/MM/yyyy");
            date1 = Utils.convertLocalTimeToUTC(date2);
//            }
            String money = String.valueOf(money_per_month);
            CallAPI.addNewTargetSuccess(this, img_icon_file, name, value, date1, money, static_icon, new CallAPI.OnCallAddTargetSavingSuccess() {

                @Override
                public void onResult(TargetSaving reslult) {
                    if (reslult != null && reslult.code == 1) {
                        Toast.makeText(AddNewTargetActivity.this, getString(R.string.addtargetsuccess), Toast.LENGTH_SHORT).show();
                        setResult(3333);
                    } else {
                        Toast.makeText(AddNewTargetActivity.this, getString(R.string.addtargetfail), Toast.LENGTH_SHORT).show();
                    }
                    finish();

                }
            });
        }

//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_picker_date:
                dialogShowPickDate.show();
                break;
            case R.id.btn_choose_date:
                dialogShowPickDate.show();
                break;
            case R.id.btn_get_photo:
                TakeImageUtils.showPictureDialog(this);
                break;
            case R.id.btn_select_icon:

                int id = getResources().getIdentifier(static_icon, "drawable", getPackageName());
                icon_selected.setImageResource(id);
                img_icon_file = null;
                layout_parent.setVisibility(View.INVISIBLE);
                img_close.setVisibility(View.VISIBLE);
                icon_selected.setVisibility(View.VISIBLE);
                break;
            case R.id.img_close:

                img_icon_file = null;
                layout_parent.setVisibility(View.VISIBLE);
                img_close.setVisibility(View.INVISIBLE);
                icon_selected.setVisibility(View.INVISIBLE);

                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_ok: {
                if (type1.equals("add")) {
                    if (type.equals("target_saving")) {
                        validateAndCallApiTargetSaving();
                    } else if (type.equals("target_loan")) {
                        caculateTempleTargetLoan(true);
                    }
                } else if (type1.equals("editsaving")) {
                    setResult(1113);
                    editTargetSaving();
                } else if (type1.equals("editloan")) {
                    setResult(1114);
                    editTargetLoan();
                }
            }
            break;
            default:
                break;
        }
    }

    public void editTargetSaving() {
        String name, value, date, money;
        name = edt_name_target.getText().toString().trim();
        value = edt_value_target.getText().toString().trim();
        Date temp = Utils.convertStringToDate(edt_date_target.getText().toString(), "dd/MM/yyyy");
        date = Utils.convertLocalTimeToUTC(temp);
        ModelTarget modelTarget = new ModelTarget(name, value, String.valueOf(money_per_month), date);
        if (layout_parent.getVisibility() == View.VISIBLE) {
            Toast.makeText(this, getString(R.string.img_icon_null), Toast.LENGTH_SHORT).show();
            return;
        } else if (name.equals("") || value.equals("")) {
            Toast.makeText(this, "Thông tin không đầy đủ !", Toast.LENGTH_SHORT).show();
        } else if (value.length() > 15) {
            Toast.makeText(this, "Số tiền bạn nhập quá lớn", Toast.LENGTH_SHORT).show();
        } else if (img_icon_file == null) {
            CallAPI.updateTargetSaving(this, token, "false", targetSavingId, modelTarget, img_icon_file, static_icon);
        } else
            CallAPI.updateTargetSaving(this, token, "true", targetSavingId, modelTarget, img_icon_file, static_icon);
    }

    public void editTargetLoan() {
        try {
            String name, origin, preferRate, preferTime, baseRate, payPerMounth, interRestPay, moneyPay, loanTime, id;
            DecimalFormat formatter = new DecimalFormat("##################");
            name = edt_name_target_loan.getText().toString().trim();
            origin = edt_origin_money.getText().toString();
            preferRate = edt_preferential_rate.getText().toString();
            baseRate = edt_rate_base.getText().toString();
            preferTime = edt_preferential_time.getText().toString();
            payPerMounth = b1;
            interRestPay = b2;
            moneyPay = String.valueOf(Double.parseDouble(edt_origin_money.getText().toString()) + Double.parseDouble(b2));
            id = loanid;
            loanTime = edt_loan_time.getText().toString();
            ModelEditLoan modelEditLoan;
            if (!preferRate.equals("")) {
                modelEditLoan = new ModelEditLoan(name, origin, preferRate, preferTime, baseRate, payPerMounth, moneyPay, interRestPay, loanTime);
            } else
                modelEditLoan = new ModelEditLoan(name, origin, "0", "0", baseRate, payPerMounth, moneyPay, interRestPay, loanTime);
            if (layout_parent.getVisibility() == View.VISIBLE) {
                Toast.makeText(this, getString(R.string.img_icon_null), Toast.LENGTH_SHORT).show();
                return;
            }
            if (name.equals("") || origin.equals("") || loanTime.equals("") || ratebase.equals("")) {
                Toast.makeText(this, "Thiếu thông tin !", Toast.LENGTH_SHORT).show();
            } else if (!preferRate.equals("") && Double.valueOf(preferRate) > 50) {
                Toast.makeText(this, getString(R.string.low_preferential), Toast.LENGTH_SHORT).show();
            } else if (!preferTime.equals("") && Double.valueOf(preferTime) > 10) {
                Toast.makeText(this, getString(R.string.low_preferential_time), Toast.LENGTH_SHORT).show();
            } else if (!loanTime.equals("") && Double.valueOf(loanTime) > 20) {
                Toast.makeText(this, getString(R.string.loan_time_), Toast.LENGTH_SHORT).show();
            } else if (!baseRate.equals("") && Double.valueOf(baseRate) > 20) {
                Toast.makeText(this, getString(R.string.low_rate_base), Toast.LENGTH_SHORT).show();
            } else if (!baseRate.equals("") && !preferRate.equals("") && Double.parseDouble(baseRate) <= Double.parseDouble(preferRate)) {
                Toast.makeText(this, "Lãi suất ưu đãi phải nhỏ hơn lãi suất thông thường", Toast.LENGTH_SHORT).show();
            } else if (!loanTime.equals("") && !preferTime.equals("") && Integer.parseInt(loanTime) <= Integer.parseInt(preferTime)) {
                Toast.makeText(this, "Thời gian vay ưu đãi phải nhỏ hơn thời gian vay thông thường", Toast.LENGTH_SHORT).show();
            } else if (!preferRate.equals("") && (Double.parseDouble(preferRate) == 0 || Double.parseDouble(preferTime) == 0 || Double.parseDouble(origin) == 0 || Double.parseDouble(baseRate) == 0 || Double.parseDouble(loanTime) == 0))
                Toast.makeText(this, "Các trường phải lớn hơn 0", Toast.LENGTH_SHORT).show();
            else if (img_icon_file == null) {
                CallAPI.updateTargetLoan(this, token, "false", loanid, modelEditLoan, img_icon_file, static_icon);
            } else {
                CallAPI.updateTargetLoan(this, token, "true", loanid, modelEditLoan, img_icon_file, static_icon);
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == Const.GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    layout_parent.setVisibility(View.INVISIBLE);
                    icon_selected.setImageBitmap(bitmap);
                    icon_selected.setVisibility(View.VISIBLE);
                    img_close.setVisibility(View.VISIBLE);
                    img_icon_file = Utils.scaleImage(this, bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == Const.CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            layout_parent.setVisibility(View.INVISIBLE);
            icon_selected.setImageBitmap(thumbnail);
            icon_selected.setVisibility(View.VISIBLE);
            img_close.setVisibility(View.VISIBLE);
            img_icon_file = Utils.scaleImage(this, thumbnail);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (type.equals("target_saving")) {
            if (edt_name_target.getText().hashCode() == editable.hashCode()) {
                caculateTempleTargetSaving();
            } else if (edt_value_target.getText().hashCode() == editable.hashCode()) {
                caculateTempleTargetSaving();
            } else if (edt_date_target.getText().hashCode() == editable.hashCode()) {
                caculateTempleTargetSaving();
            } else if (edt_age.getText().hashCode() == editable.hashCode()) {
                caculateTempleTargetSaving();
            } else if (edt_age_now.getText().hashCode() == editable.hashCode()) {
                caculateTempleTargetSaving();
            }
        } else {

            if (edt_name_target_loan.getText().hashCode() == editable.hashCode()) {
                caculateTempleTargetLoan(false);

            } else if (edt_origin_money.getText().hashCode() == editable.hashCode()) {
                caculateTempleTargetLoan(false);
            } else if (edt_preferential_rate.getText().hashCode() == editable.hashCode()) {
                caculateTempleTargetLoan(false);
            } else if (edt_preferential_time.getText().hashCode() == editable.hashCode()) {
                caculateTempleTargetLoan(false);
            } else if (edt_rate_base.getText().hashCode() == editable.hashCode()) {
                caculateTempleTargetLoan(false);
            } else if (edt_loan_time.getText().hashCode() == editable.hashCode()) {
                caculateTempleTargetLoan(false);
            }
        }

    }

    public void setImageIcon(String tag) {
        switch (position) {
            case 0:
                if (tag.equals(Const.TAG_SETUP3)) {
                    icon_selected.setImageResource(R.drawable.icon_house_bright);
                    static_icon = getResources().getResourceEntryName(R.drawable.icon_house_bright);
                    break;
                }
                icon_selected.setImageResource(R.drawable.icon_house_bright);
                static_icon = getResources().getResourceEntryName(R.drawable.icon_house_bright);
                break;
            case 1:
                if (tag.equals(Const.TAG_SETUP3)) {
                    icon_selected.setImageResource(R.drawable.icon_car_bright);
                    static_icon = getResources().getResourceEntryName(R.drawable.icon_car_bright);
                    break;
                }
                icon_selected.setImageResource(R.drawable.icon_car_bright);
                static_icon = getResources().getResourceEntryName(R.drawable.icon_car_bright);
                break;
            case 2:
                if (tag.equals(Const.TAG_SETUP3)) {
                    icon_selected.setImageResource(R.drawable.icon_shopping_bright);
                    static_icon = getResources().getResourceEntryName(R.drawable.icon_shopping_bright);
                    break;
                }
                icon_selected.setImageResource(R.drawable.icon_shopping_bright);
                static_icon = getResources().getResourceEntryName(R.drawable.icon_shopping_bright);
                break;
            case 3:
                if (tag.equals(Const.TAG_SETUP3)) {
                    icon_selected.setImageResource(R.drawable.icon_travel_bright);
                    static_icon = getResources().getResourceEntryName(R.drawable.icon_travel_bright);
                    break;
                }
                icon_selected.setImageResource(R.drawable.icon_travel_bright);
                static_icon = getResources().getResourceEntryName(R.drawable.icon_travel_bright);
                break;
            case 4:
                if (tag.equals(Const.TAG_SETUP3)) {
                    icon_selected.setImageResource(R.drawable.icon_repair_bright);
                    static_icon = getResources().getResourceEntryName(R.drawable.icon_repair_bright);
                    break;
                }
                icon_selected.setImageResource(R.drawable.icon_repair_bright);
                static_icon = getResources().getResourceEntryName(R.drawable.icon_repair_bright);
                break;
            case 5:
                if (tag.equals(Const.TAG_SETUP3)) {
                    icon_selected.setImageResource(R.drawable.icon_help_bright);
                    static_icon = getResources().getResourceEntryName(R.drawable.icon_help_bright);
                    break;
                }
                icon_selected.setImageResource(R.drawable.icon_help_bright);
                static_icon = getResources().getResourceEntryName(R.drawable.icon_help_bright);
                break;
            case 6:
                if (tag.equals(Const.TAG_SETUP3)) {
                    icon_selected.setImageResource(R.drawable.icon_pig_bright);
                    static_icon = getResources().getResourceEntryName(R.drawable.icon_pig_bright);
                    break;
                }
                icon_selected.setImageResource(R.drawable.icon_pig_bright);
                static_icon = getResources().getResourceEntryName(R.drawable.icon_pig_bright);
                break;
            case 7:
                if (tag.equals(Const.TAG_SETUP3)) {
                    icon_selected.setImageResource(R.drawable.icon_loan_bright);
                    static_icon = getResources().getResourceEntryName(R.drawable.icon_loan_bright);
                    break;
                }
                icon_selected.setImageResource(R.drawable.icon_loan_bright);
                static_icon = getResources().getResourceEntryName(R.drawable.icon_loan_bright);
                break;
            case 8:
                if (tag.equals(Const.TAG_SETUP3)) {
                    icon_selected.setImageResource(R.drawable.icon_plus_bright);
                    static_icon = getResources().getResourceEntryName(R.drawable.icon_plus_bright);
                    break;
                }
                icon_selected.setImageResource(R.drawable.icon_plus_bright);
                static_icon = getResources().getResourceEntryName(R.drawable.icon_plus_bright);
                break;
        }
    }

    public static class CustomSpan {
        TextAppearanceSpan highlightSpan;

        public CustomSpan() {
        }

        public TextAppearanceSpan getHighlightSpan(String tag) {
            int color = 0;
            if (tag.equals(Const.TAG_SETUP3)) {
                color = Color.WHITE;
            } else if (tag.equals(Const.TARGET_TYPE)) {
                color = Color.BLACK;
            }
            return new TextAppearanceSpan(null, Typeface.BOLD, -1, ColorStateList.valueOf(color), null);
        }

        public void setHighlightSpan(TextAppearanceSpan highlightSpan) {
            this.highlightSpan = highlightSpan;
        }
    }

    public static void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}
