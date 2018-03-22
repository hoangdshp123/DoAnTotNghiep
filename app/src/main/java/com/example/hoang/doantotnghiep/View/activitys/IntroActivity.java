package com.example.hoang.doantotnghiep.View.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Adapter.IntroAdapter;
import com.example.hoang.doantotnghiep.Model.ItemSetup1;
import com.example.hoang.doantotnghiep.Model.ModelApi.ListBudget;
import com.example.hoang.doantotnghiep.Model.ModelApi.ListRevenue;
import com.example.hoang.doantotnghiep.Model.ModelBudget.BudgetRequest;
import com.example.hoang.doantotnghiep.Model.RevenueRequest;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.RestApi.RetrofitClient;
import com.example.hoang.doantotnghiep.View.Fragment.FinishIntroFragment;
import com.example.hoang.doantotnghiep.View.Fragment.Setup1Fragment;
import com.example.hoang.doantotnghiep.View.Fragment.Setup2Fragment;
import com.example.hoang.doantotnghiep.View.Fragment.Setup3Fragment;
import com.example.hoang.doantotnghiep.View.Fragment.WelcomeFragment;
import com.example.hoang.doantotnghiep.utils.SharedPrefsUtils;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity implements FinishIntroFragment.FinishGuide, View.OnClickListener {
    ViewPager viewPager;
    Button btnBack;
    Button btnContinue;
    Setup1Fragment setup1Fragment;
    Setup2Fragment setup2Fragment;
    Setup3Fragment setup3Fragment;
    String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro);
        viewPager = findViewById(R.id.viewpager);
        btnBack = findViewById(R.id.btn_back);
        btnContinue = findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnBack.setVisibility(View.GONE);
        btnContinue.setVisibility(View.GONE);

        WelcomeFragment welcomeFragment = WelcomeFragment.newInstance();
        setup1Fragment = Setup1Fragment.newInstance();
        setup2Fragment = Setup2Fragment.newInstance();
        setup3Fragment = Setup3Fragment.newInstance();
        FinishIntroFragment finishIntroFragment = FinishIntroFragment.newInstance(this);
        ArrayList<Fragment> listFragments = new ArrayList<>();
        listFragments.add(welcomeFragment);
        listFragments.add(setup1Fragment);
        listFragments.add(setup2Fragment);
        listFragments.add(setup3Fragment);
        listFragments.add(finishIntroFragment);
        token = "Bearer " + SharedPrefsUtils.getStringPreference(this, "token");

        IntroAdapter adapter = new IntroAdapter(getSupportFragmentManager(), listFragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);

        ExtensiblePageIndicator extensiblePageIndicator = findViewById(R.id.circle_indicator);
        extensiblePageIndicator.initViewPager(viewPager);

        //handle indicator viewpager
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //handle button back and continue indicator
                switch (position) {
                    case 0:
                        btnBack.setVisibility(View.GONE);
                        btnContinue.setVisibility(View.GONE);
                        extensiblePageIndicator.setVisibility(View.VISIBLE);

                        break;
                    case 1:
                        btnBack.setVisibility(View.GONE);
                        btnContinue.setVisibility(View.VISIBLE);
                        extensiblePageIndicator.setVisibility(View.VISIBLE);
                        btnContinue.setOnClickListener(IntroActivity.this);


                        break;
                    case 2:
                        btnBack.setVisibility(View.VISIBLE);
                        btnContinue.setVisibility(View.VISIBLE);
                        extensiblePageIndicator.setVisibility(View.VISIBLE);
                        btnBack.setTextColor(getResources().getColor(R.color.color_white_transparent_50));
                        btnContinue.setOnClickListener(IntroActivity.this);
                        btnBack.setOnClickListener(IntroActivity.this);
                        break;
                    case 3:
                        btnBack.setVisibility(View.VISIBLE);
                        btnContinue.setVisibility(View.VISIBLE);
                        extensiblePageIndicator.setVisibility(View.VISIBLE);
                        btnBack.setTextColor(getResources().getColor(R.color.color_white_transparent_50));
                        btnContinue.setOnClickListener(IntroActivity.this);
                        btnBack.setOnClickListener(IntroActivity.this);
                        break;
                    case 4:
                        btnBack.setVisibility(View.GONE);
                        btnContinue.setVisibility(View.GONE);
                        extensiblePageIndicator.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        int position;
        switch (view.getId()) {
            case R.id.btn_continue:
                position = viewPager.getCurrentItem();
                viewPager.setCurrentItem(position + 1);
                break;
            case R.id.btn_back:
                position = viewPager.getCurrentItem();
                viewPager.setCurrentItem(position - 1);
                break;
        }
    }


    //called from Setup1 Fragment
    public void nextFragment() {
        int position = viewPager.getCurrentItem();
        viewPager.setCurrentItem(position + 1);
    }


    public void onFinish() {
        String token = "Bearer " + SharedPrefsUtils.getStringPreference(this,"token");
        RetrofitClient.MyauthHeaderContent = token;
        ArrayList<ItemSetup1> tempRevenue = setup1Fragment.getListRevenue();
        ArrayList<RevenueRequest> listRevenue = new ArrayList<>();
        for (int i = 0; i < tempRevenue.size(); i++) {
            ItemSetup1 itemSetup1 = tempRevenue.get(i);
            if (itemSetup1.getName().equals("") || itemSetup1.getValue() == 0) {
                Toast.makeText(this, getString(R.string.empty_revenue), Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("ICON", itemSetup1.getStatic_icon());
            listRevenue.add(new RevenueRequest(itemSetup1.getName(), itemSetup1.getValue(), itemSetup1.getStatic_icon()));

        }

        ArrayList<ItemSetup1> listNSTT = setup2Fragment.getListItemNSTT();
        ArrayList<ItemSetup1> listNSCB = setup2Fragment.getListItemNSCB();
        ArrayList<ItemSetup1> listPCS = setup2Fragment.getListItemPCS();

        ArrayList<BudgetRequest> listBudgets = new ArrayList<>();
        for (int i = 0; i < listNSTT.size(); i++) {
            ItemSetup1 itemSetup1 = listNSTT.get(i);
            if (itemSetup1.getName().equals("") || itemSetup1.getValue() == 0) {
                Toast.makeText(this, getString(R.string.empty_nstt), Toast.LENGTH_SHORT).show();
                return;
            }
            listBudgets.add(new BudgetRequest(itemSetup1.getName(), itemSetup1.getValue(), "min"));
        }
        for (int i = 0; i < listNSCB.size(); i++) {
            ItemSetup1 itemSetup1 = listNSCB.get(i);
            if (itemSetup1.getName().equals("") || itemSetup1.getValue() == 0) {
                Toast.makeText(this, getString(R.string.empty_nscb), Toast.LENGTH_SHORT).show();
                return;
            }
            listBudgets.add(new BudgetRequest(itemSetup1.getName(), itemSetup1.getValue(), "base"));
        }
        for (int i = 0; i < listPCS.size(); i++) {
            ItemSetup1 itemSetup1 = listPCS.get(i);
            if (itemSetup1.getName().equals("") || itemSetup1.getValue() == 0) {
                Toast.makeText(this, getString(R.string.empty_pcs), Toast.LENGTH_SHORT).show();
                return;
            }
            listBudgets.add(new BudgetRequest(itemSetup1.getName(), itemSetup1.getValue(), "luxury"));
        }


        CallAPI.updateFirstLogin(this);

        if (listBudgets.size() == 0 && listRevenue.size() == 0) {
            Intent goHome = new Intent(IntroActivity.this, HomeActivity.class);
            goHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getApplicationContext().startActivity(goHome);
            return;
        }
        final boolean[] isCalled = {false};
        if (listBudgets.size() > 0) {
            CallAPI.addListBudgets(IntroActivity.this, token, new ListBudget(listBudgets), new CallAPI.OnCallAddListBudgets() {
                @Override
                public void onResult(Boolean success) {
                    if (success) {
                        if (!isCalled[0]) {
                            Intent goHome = new Intent(IntroActivity.this, HomeActivity.class);
                            goHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            getApplicationContext().startActivity(goHome);
                            isCalled[0] = true;
                        }
                    }
                }
            });
        }
        if (listRevenue.size() > 0) {
            CallAPI.addListRevenues(this, token, new ListRevenue(listRevenue), new CallAPI.OnCallAddListRevenues() {
                @Override
                public void onResult(Boolean success) {
                    if (success) {
                        if (!isCalled[0]) {
                            Intent goHome = new Intent(IntroActivity.this, HomeActivity.class);
                            goHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            getApplicationContext().startActivity(goHome);
                            isCalled[0] = true;
                        }
                    }
                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.iconout1)
                .setTitle("THOÁT")
                .setMessage("Bạn muốn thoát khỏi ứng dụng?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);

                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
