package com.example.hoang.doantotnghiep.View.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.hoang.doantotnghiep.Adapter.HomeAdapter;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelManagerRevenue.ManagerRevenue;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.View.Fragment.HistoryFragment;
import com.example.hoang.doantotnghiep.View.Fragment.StatisticalFragment;

import java.util.ArrayList;

/**
 * Created by TWO on 12/26/2017.
 */

public class SpendManagementActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnHistory, btnStatistical;
    HistoryFragment historyFragment;
    StatisticalFragment statisticalFragment;
    private FragmentTransaction ft;
    private ViewPager viewPager;
    ManagerRevenue managerRevenue_global;
    private boolean isDataChange = false;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_management);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
//        showHistory();
        initFragment();
        setupBottomNav();
    }

    private void initView() {
        findViewById(R.id.btn_manager_back).setOnClickListener(this);
        btnHistory = (Button) findViewById(R.id.btn_manager_history);
        btnStatistical = (Button) findViewById(R.id.btn_manager_statistical);
        btnHistory.setOnClickListener(this);
        btnStatistical.setOnClickListener(this);
        viewPager = findViewById(R.id.viewpager_spend);




    }

    public void initFragment() {
        //create fragment
         historyFragment = HistoryFragment.newInstance();
         statisticalFragment = StatisticalFragment.newInstance();

        //add fragments to arraylist
        ArrayList listFragments = new ArrayList<>();
        listFragments.add(historyFragment);
        listFragments.add(statisticalFragment);

        //add fragments to viewpager
        HomeAdapter adapter = new HomeAdapter(getSupportFragmentManager(), listFragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_manager_back:
                onBackPressed();
                break;
            case R.id.btn_manager_history:
                setBackgroundButton(btnHistory, btnStatistical);
                viewPager.setCurrentItem(0);
                preferences = getSharedPreferences("mData", MODE_PRIVATE);
                edit = preferences.edit();
                edit.putInt("manager_spend", (preferences.getInt("manager_spend", 0) + 1));
                edit.commit();
                break;
            case R.id.btn_manager_statistical:
                setBackgroundButton(btnStatistical, btnHistory);
                viewPager.setCurrentItem(1);
                if(historyFragment.isReloadDataBack|| historyFragment.isReloadDataCurrent){
                    statisticalFragment.reloadData();
                }

                preferences = getSharedPreferences("mData", MODE_PRIVATE);
                edit = preferences.edit();
                edit.putInt("statistical_spend", (preferences.getInt("statistical_spend", 0) + 1));
                edit.commit();
//                if (historyFragment != null) {
//                    hideFragment(historyFragment, statisticalFragment);
//                }
                break;
        }
    }

    public void setBackgroundButton(Button btn1, Button btn2) {
        btn1.setBackgroundResource(R.drawable.background_blue_conor_button);
        btn1.setTextColor(Color.parseColor("#d0e2fb"));
        btn2.setBackgroundColor(Color.TRANSPARENT);
        btn2.setTextColor(Color.parseColor("#0e7dc1"));
    }


    private void setupBottomNav() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                //set title toolbar
                switch (position) {
                    case 0:
                        setBackgroundButton(btnHistory, btnStatistical);
                        preferences = getSharedPreferences("mData", MODE_PRIVATE);
                        edit = preferences.edit();
                        edit.putInt("manager_spend", (preferences.getInt("manager_spend", 0) + 1));
                        edit.commit();
                        break;
                    case 1:
                        setBackgroundButton(btnStatistical, btnHistory);
                        if(historyFragment.isReloadDataBack|| historyFragment.isReloadDataCurrent){
                            statisticalFragment.reloadData();
                        }

                        preferences = getSharedPreferences("mData", MODE_PRIVATE);
                        edit = preferences.edit();
                        edit.putInt("statistical_spend", (preferences.getInt("statistical_spend", 0) + 1));
                        edit.commit();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 2223 && resultCode == 1111)||((requestCode == 9087) && resultCode == 2023)) {
            statisticalFragment.reloadData();
        }

    }

    @Override
    public void onBackPressed() {
        if(historyFragment != null && historyFragment.isReloadDataBack){

            setResult(1112);
            historyFragment.isReloadDataBack = false;
        }
        super.onBackPressed();
        if(isDataChange){

        }
    }
}
