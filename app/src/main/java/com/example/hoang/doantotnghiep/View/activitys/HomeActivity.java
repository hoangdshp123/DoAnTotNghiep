package com.example.hoang.doantotnghiep.View.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Adapter.HomeAdapter;
import com.example.hoang.doantotnghiep.Interface.ReloadHomeData;
import com.example.hoang.doantotnghiep.Model.ModelApi.ListClick;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelManagerRevenue.ManagerRevenue;
import com.example.hoang.doantotnghiep.Model.ModelHome.ModelClick;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.RestApi.RetrofitClient;
import com.example.hoang.doantotnghiep.View.Fragment.BorrowFragment;
import com.example.hoang.doantotnghiep.View.Fragment.BudgetFragment;
import com.example.hoang.doantotnghiep.View.Fragment.HomeFragment;
import com.example.hoang.doantotnghiep.View.Fragment.TargetFragment;
import com.example.hoang.doantotnghiep.utils.SharedPrefsUtils;
import com.example.hoang.doantotnghiep.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements View.OnCreateContextMenuListener, View.OnClickListener,ReloadHomeData{
    public static double totalRevenue = 0;
    public static double totalBudget = 0;

    public static boolean isCall = false;

    public static double budgetBase = 0, budgetMin = 0, budgetLuxury = 0,totalExpense = 0;
    public static String token = "";
    LinearLayout linearHome;
    LinearLayout linearBudget;
    LinearLayout linearAdd;
    LinearLayout linearTarget;
    LinearLayout linearVideos;
    ImageView imgHome;
    ImageView imgBudget;
    ImageView imgTarget;
    ImageView imgVideos;
    TextView txtHome;
    TextView txtBudget;
    TextView txtTarget;
    TextView txtVideos;

    TextView txtToolbarTitle;
    ViewPager viewPager;
    ManagerRevenue managerRevenue_global;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private int count = 0;

    HomeFragment homeFragment;
    BudgetFragment budgetFragment;
    TargetFragment targetFragment;
    BorrowFragment borrowFragment;
    int itemOptionMenu = R.menu.item_toolbar_calculator;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initFragment(0);
        //setup toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.icon_user_with_padding));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Infomation.class);
                startActivity(intent);
            }
        });
//        toolbar.setNavigationIcon(R.drawable.iconapp);
        //set up bottom navigation
        setupBottomNav();

        //gettoken

        token = SharedPrefsUtils.getStringPreference(this, "token");
        String currentMonth,currentYear;
        currentMonth = Utils.getCurrentMounth();
        currentYear = Utils.getCurrentYear();
        CallAPI.copyMonthData1(this,"Bearer " + token,currentMonth,currentYear);

        if (token == null || token == "") {
            Intent intent = new Intent(this, LoginRegister.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }

        RetrofitClient.MyauthHeaderContent = "Bearer " + token;
        getAPIAndCaculator(0);

//        try {
//
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String hashKey = new String(Base64.encode(md.digest(), 0));
//                Log.i("code111", "printHashKey() Hash Key: " + hashKey);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("code111", "printHashKey()", e);
//        } catch (Exception e) {
//            Log.e("code111", "printHashKey()", e);
//        }

    }

    public void getAPIAndCaculator(int selectPage) {
        CallAPI.getManager_revenue(this, Utils.getFirstDateOfMonth(new Date()),
                Utils.getLastDateOfMonth(new Date()), new CallAPI.OnCallManager_Revenue() {
                    @Override
                    public void onResult(ManagerRevenue managerRevenue) {
                        if (managerRevenue.code == 1) {
                            totalRevenue = 0;
                            budgetBase = 0;
                            budgetLuxury = 0;
                            budgetMin = 0;
                            totalBudget = 0;
                            // send data in manager with fragment
                            for (int i = 0; i < managerRevenue.data.revenue.size(); i++) {
                                totalRevenue += managerRevenue.data.revenue.get(i).getValue_revenue();
                            }
                            for (int i = 0; i < managerRevenue.data.budgets.getBudget_base().size(); i++) {
                                budgetBase += managerRevenue.data.budgets.getBudget_base().get(i).getBudget_value();

                            }
                            for (int i = 0; i < managerRevenue.data.budgets.getBudget_luxury().size(); i++) {
                                budgetLuxury += managerRevenue.data.budgets.getBudget_luxury().get(i).getBudget_value();
                            }
                            for (int i = 0; i < managerRevenue.data.budgets.getBudget_min().size(); i++) {
                                budgetMin += managerRevenue.data.budgets.getBudget_min().get(i).getBudget_value();
                            }
                            Log.d("CHECK", String.valueOf(totalRevenue));
                            totalBudget = budgetBase + budgetLuxury + budgetMin;
                            totalExpense = managerRevenue.data.getTotalExpense();
                            managerRevenue_global = managerRevenue;
                            sendDataToFragment(true);

                        } else {
                            totalRevenue = 0;
                            budgetBase = 0;
                            budgetLuxury = 0;
                            budgetMin = 0;
                            totalBudget = 0;
                            totalExpense = 0;
                            sendDataToFragment(false);
                        }
                    }
                });
    }

    public void initView() {
        viewPager = findViewById(R.id.viewpager_home);
        txtToolbarTitle = findViewById(R.id.toolbar_title);
        linearHome = findViewById(R.id.linear_home);
        linearBudget = findViewById(R.id.linear_budget);
        linearAdd = findViewById(R.id.linear_add);
        linearTarget = findViewById(R.id.linear_target);
        linearVideos = findViewById(R.id.linear_videos);
        imgHome = findViewById(R.id.img_home_nav);
        imgBudget = findViewById(R.id.img_budget_nav);
        imgTarget = findViewById(R.id.img_target_nav);
        imgVideos = findViewById(R.id.img_video_nav);
        txtHome = findViewById(R.id.txt_home_nav);
        txtBudget = findViewById(R.id.txt_budget_nav);
        txtTarget = findViewById(R.id.txt_target_nav);
        txtVideos = findViewById(R.id.txt_video_nav);

        linearHome.setOnClickListener(this);
        linearVideos.setOnClickListener(this);
        linearTarget.setOnClickListener(this);
        linearAdd.setOnClickListener(this);
        linearBudget.setOnClickListener(this);

        imgHome.setImageResource(R.drawable.icon_home_focus);
        txtHome.setTextColor(getResources().getColor(R.color.text_focus));

    }

    public void initFragment(int selectPage) {
        //create fragment
        homeFragment = HomeFragment.newInstance(managerRevenue_global, this);
        budgetFragment = BudgetFragment.newInstance(managerRevenue_global,this);
        targetFragment = TargetFragment.newInstance();
        borrowFragment = BorrowFragment.newInstance();

        //add fragments to arraylist
        ArrayList<Fragment> listFragments = new ArrayList<>();
        listFragments.add(homeFragment);
        listFragments.add(budgetFragment);
        listFragments.add(targetFragment);
        listFragments.add(borrowFragment);
//        listFragments.add(listVideoFragment);
        //add fragments to viewpager
        HomeAdapter adapter = new HomeAdapter(getSupportFragmentManager(), listFragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(selectPage);
    }

    public void sendDataToFragment(boolean isUpdate) {
        if (homeFragment != null) {
            homeFragment.updateData(isUpdate, managerRevenue_global, budgetLuxury, budgetMin, budgetBase,totalExpense);
        }
        if (budgetFragment != null) {
            budgetFragment.updateData(isUpdate, managerRevenue_global, budgetMin, budgetBase, budgetLuxury);
        }
        if (targetFragment != null) {
            targetFragment.updateData(isUpdate);
        }

//        if (borrowFragment != null) {
//            borrowFragment.updateData(isUpdate);
//        }
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
                        if (count != 0) {
                            txtToolbarTitle.setText(getResources().getString(R.string.Home));
                            onHomeNavClick();
                        }
                        count = 0;
                        break;
                    case 1:
                        if (count != 1) {
                            txtToolbarTitle.setText(getResources().getString(R.string.budget));
                            onBudgetNavClick();
                        }
                        count = 1;
                        break;
                    case 2:
                        if (count != 2) {
                            txtToolbarTitle.setText(getResources().getString(R.string.target));
                            onTargetNavClick();
                        }
                        count = 2;
                        break;
                    case 3:
                        if (count != 3) {
                            txtToolbarTitle.setText(getResources().getString(R.string.videos));
                            onVideoNavClick();
                        }
                        count = 3;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(itemOptionMenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_action_calculator) {

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.linear_home:
                if (count != 0) {
                    viewPager.setCurrentItem(0);
                    onHomeNavClick();
                }
                count = 0;
                break;
            case R.id.linear_budget:
                if (count != 1) {
                    viewPager.setCurrentItem(1);
                    onBudgetNavClick();
                }
                count = 1;
                break;
            case R.id.linear_add:
                Intent intent = new Intent(this, PaymentActivity.class);
                startActivityForResult(intent, 900);
                break;
            case R.id.linear_target:
                if (count != 2) {
                    viewPager.setCurrentItem(2);
                    onTargetNavClick();
                }
                count = 2;
                break;
            case R.id.linear_videos:
                if (count != 3) {
                    viewPager.setCurrentItem(3);
                    onVideoNavClick();
                }
                count = 3;
                break;
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1994) {
            getAPIAndCaculator(1);

        } else if (requestCode == 900 && resultCode == 1111) {
            getAPIAndCaculator(0);
            homeFragment.getLast10Expense();
        }
    }

    public void onHomeNavClick() {
        menu.getItem(0).setIcon(R.drawable.icon_calculator);
        preferences = getSharedPreferences("mData", MODE_PRIVATE);
        edit = preferences.edit();
        edit.putInt("home", (preferences.getInt("home", 0) + 1));
        edit.commit();
        imgHome.setImageResource(R.drawable.icon_home_focus);
        imgBudget.setImageResource(R.drawable.icon_wallet_unactive);
        imgTarget.setImageResource(R.drawable.icon_target_unactive);
        imgVideos.setImageResource(R.drawable.icon_video_unactive);

        txtHome.setTextColor(getResources().getColor(R.color.text_focus));
        txtBudget.setTextColor(getResources().getColor(R.color.text_default));
        txtTarget.setTextColor(getResources().getColor(R.color.text_default));
        txtVideos.setTextColor(getResources().getColor(R.color.text_default));
    }

    public void onBudgetNavClick() {
        menu.getItem(0).setIcon(R.drawable.icon_calculator);
        preferences = getSharedPreferences("mData", MODE_PRIVATE);
        edit = preferences.edit();
        edit.putInt("budget", (preferences.getInt("budget", 0) + 1));
        edit.commit();
        imgHome.setImageResource(R.drawable.icon_home_unactive);
        imgBudget.setImageResource(R.drawable.icon_wallet_focus);
        imgTarget.setImageResource(R.drawable.icon_target_unactive);
        imgVideos.setImageResource(R.drawable.icon_video_unactive);

        txtHome.setTextColor(getResources().getColor(R.color.text_default));
        txtBudget.setTextColor(getResources().getColor(R.color.text_focus));
        txtTarget.setTextColor(getResources().getColor(R.color.text_default));
        txtVideos.setTextColor(getResources().getColor(R.color.text_default));
    }

    public void onTargetNavClick() {
        menu.getItem(0).setIcon(R.drawable.icon_calculator);
        preferences = getSharedPreferences("mData", MODE_PRIVATE);
        edit = preferences.edit();
        edit.putInt("target", (preferences.getInt("target", 0) + 1));
        edit.commit();
        imgHome.setImageResource(R.drawable.icon_home_unactive);
        imgBudget.setImageResource(R.drawable.icon_wallet_unactive);
        imgTarget.setImageResource(R.drawable.icon_target_focus);
        imgVideos.setImageResource(R.drawable.icon_video_unactive);

        txtHome.setTextColor(getResources().getColor(R.color.text_default));
        txtBudget.setTextColor(getResources().getColor(R.color.text_default));
        txtTarget.setTextColor(getResources().getColor(R.color.text_focus));
        txtVideos.setTextColor(getResources().getColor(R.color.text_default));
    }

    public void onVideoNavClick() {
        menu.getItem(0).setIcon(R.drawable.icon_calculator);
        preferences = getSharedPreferences("mData", MODE_PRIVATE);
        edit = preferences.edit();
        edit.putInt("borrow", (preferences.getInt("borrow", 0) + 1));
        edit.commit();
        imgHome.setImageResource(R.drawable.icon_home_unactive);
        imgBudget.setImageResource(R.drawable.icon_wallet_unactive);
        imgTarget.setImageResource(R.drawable.icon_target_unactive);
        imgVideos.setImageResource(R.drawable.icon_video_focus);
        txtHome.setTextColor(getResources().getColor(R.color.text_default));
        txtBudget.setTextColor(getResources().getColor(R.color.text_default));
        txtTarget.setTextColor(getResources().getColor(R.color.text_default));
        txtVideos.setTextColor(getResources().getColor(R.color.text_focus));
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void setAddClick() {
        ArrayList<ModelClick> list = new ArrayList<>();
        String token = "Bearer " + SharedPrefsUtils.getStringPreference(this, "token");
        preferences = getSharedPreferences("mData", MODE_PRIVATE);
        list.add(new ModelClick("Trang chủ", preferences.getInt("home", 0)));
        list.add(new ModelClick("Ngân sách", preferences.getInt("budget", 0)));
        list.add(new ModelClick("Mục tiêu", preferences.getInt("target", 0)));
        list.add(new ModelClick("Vay tiền", preferences.getInt("borrow", 0)));
        list.add(new ModelClick("Thống kê chi tiêu", preferences.getInt("statistical_spend", 0)));
        list.add(new ModelClick("Quản lý chi tiêu", preferences.getInt("statistical_spend", 0)));
        //công cụ tài chính
        list.add(new ModelClick("Chi tiêu", preferences.getInt("tool_expense", 0)));
        list.add(new ModelClick("Tiết kiệm", preferences.getInt("tool_saving", 0)));
        list.add(new ModelClick("Mua nhà", preferences.getInt("tool_buyhouse", 0)));
        list.add(new ModelClick("Mua ô tô", preferences.getInt("tool_buycar", 0)));
        list.add(new ModelClick("Quản lý nợ", preferences.getInt("tool_debt", 0)));
        list.add(new ModelClick("Thẻ tín dụng", preferences.getInt("tool_credit", 0)));
        list.add(new ModelClick("Bảo hiểm", preferences.getInt("tool_insurrance", 0)));
        list.add(new ModelClick("Nghỉ hưu", preferences.getInt("tool_ritirement", 0)));
        list.add(new ModelClick("Công cụ khác", preferences.getInt("tool_other", 0)));

        CallAPI.addClick(HomeActivity.this,
                token, new ListClick(list), new CallAPI.OnCallAddListBudgets() {
                    @Override
                    public void onResult(Boolean success) {
                        if (success) {
                            SharedPreferences sharedPreferences = getSharedPreferences("mData", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putInt("home", 0);
                            edit.putInt("budget", 0);
                            edit.putInt("target", 0);
                            edit.putInt("borrow", 0);
                            edit.putInt("statistical_spend", 0);
                            edit.putInt("statistical_spend", 0);

                            edit.putInt("tool_expense", 0);
                            edit.putInt("tool_saving", 0);
                            edit.putInt("tool_buyhouse", 0);
                            edit.putInt("tool_buycar", 0);
                            edit.putInt("tool_debt", 0);
                            edit.putInt("tool_credit", 0);
                            edit.putInt("tool_insurrance", 0);
                            edit.putInt("tool_ritirement", 0);
                            edit.putInt("tool_other", 0);
                            edit.commit();
                        }
                    }
                });

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
                        setAddClick();
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);

                    }

                })
                .setNegativeButton("No", null)
                .show();
    }



    @Override
    public void onReload(boolean isReload, int page) {
        if(isReload)
            getAPIAndCaculator(page);
    }

}
