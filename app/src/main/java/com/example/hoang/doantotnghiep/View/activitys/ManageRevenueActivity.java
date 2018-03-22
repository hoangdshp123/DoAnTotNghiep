package com.example.hoang.doantotnghiep.View.activitys;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Adapter.ManageRevenueAdapter;
import com.example.hoang.doantotnghiep.Adapter.RevenueAdapter;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelManagerRevenue.MessageRevenueGet;
import com.example.hoang.doantotnghiep.Model.ModelHistory.ItemHistory;
import com.example.hoang.doantotnghiep.Model.ModelRevenue.Revenue;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.dialog.DateDialog;
import com.example.hoang.doantotnghiep.utils.Const;
import com.example.hoang.doantotnghiep.utils.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.hoang.doantotnghiep.utils.Const.CODE_REVENUE;
import static com.example.hoang.doantotnghiep.utils.Const.MANAGE_REVENUE;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_MANAGE_REVENUE;

public class ManageRevenueActivity extends AppCompatActivity implements View.OnClickListener {
    SearchView searchView;
    TextView txtAllIncome;
    TextView txtTime;
    TextView txtDate;
    View btnSelectDate;
    View btnAdd;
    String from = "", to = "";
    ManageRevenueAdapter adapter;
    List<Revenue> list = new ArrayList<>();
    RevenueAdapter revenueAdapter;
    RecyclerView recyclerHistory;
    DateDialog dateDialog;
    String staticIcon = "";

    public static boolean isReloadWhenBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_revenue);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initView();
//        initListView(Utils.getFirstDateOfMonth(new Date()), Utils.getLastDateOfMonth(new Date()));
        initListView("2001-02-02T17:00:00.000Z", Utils.getLastDateOfMonth(new Date()));
        setupView();
    }

    public void initListView(String dateUTC_from, String dateUTC_to) {


        CallAPI.get_Revenue(this, dateUTC_from, dateUTC_to, new CallAPI.OnCallRevenue() {
            @Override
            public void onResult(MessageRevenueGet messageRevenueGet) {
                list.clear();
                if (messageRevenueGet.getCode() == 1) {

                    List<MessageRevenueGet.Revenue> revenueList = messageRevenueGet.getRevenues();

                    for (int i = 0; i < revenueList.size(); i++) {
                        String date = Utils.convertUTCMonthToLocalTime(revenueList.get(i).getCreatedAt());

                        String staticIcon = messageRevenueGet.getRevenues().get(i).getStaticIcon();
                        ItemHistory itemHistory = null;
                        if (staticIcon != null && staticIcon.length() > 0) {
                            int image = getResources().getIdentifier(staticIcon, "drawable", getPackageName());
                            Log.d("ICON", String.valueOf(image));

                            itemHistory = new ItemHistory(revenueList.get(i).getNameRevenue(),
                                    revenueList.get(i).getValueRevenue(),
                                    "", image, revenueList.get(i).getId());
                        } else {
                            itemHistory = new ItemHistory(revenueList.get(i).getNameRevenue(),
                                    revenueList.get(i).getValueRevenue(),
                                    "", R.drawable.icon_plus,
                                    revenueList.get(i).getId());
                        }

                        boolean hasItem = false;
                        for (int k = 0; k < list.size(); k++) {
                            if (list.get(k).getMonth().equals(date)) {
                                list.get(k).getList().add(itemHistory);
                                hasItem = true;
                            }

                        }
                        if (!hasItem) {
                            ArrayList<ItemHistory> newList = new ArrayList<>();
                            newList.add(itemHistory);
                            list.add(new Revenue(date, newList));
                        }
                    }

                    revenueAdapter = new RevenueAdapter(getApplicationContext(), list, MANAGE_REVENUE);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false);
                    recyclerHistory.setLayoutManager(layoutManager);
                    recyclerHistory.setNestedScrollingEnabled(true);
                    recyclerHistory.setHasFixedSize(true);
                    recyclerHistory.setAdapter(revenueAdapter);
                }
            }
        });
    }

    public void initView() {
        searchView = findViewById(R.id.search_view);
        txtAllIncome = findViewById(R.id.txt_all_payment);
        txtTime = findViewById(R.id.txt_time);
        btnSelectDate = findViewById(R.id.btn_selectDate);
        txtDate = findViewById(R.id.txt_date);
        txtDate.setText("Chọn tháng");
        btnAdd = findViewById(R.id.btn_add);
        recyclerHistory = findViewById(R.id.recycler_history);

    }

    public void setupView() {
        searchView.setQueryHint(getResources().getString(R.string.search_income));
        txtAllIncome.setText(getResources().getString(R.string.all_income));
        txtTime.setText(getResources().getString(R.string.select_time));
        btnSelectDate.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(getResources().getText(R.string.manage_revenue));
        title.setGravity(Gravity.START);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());

        //adapter
        //adapter = new ManageRevenueAdapter(listItem, this, MANAGE_REVENUE);
        /*revenueAdapter = new RevenueAdapter(this, list, MANAGE_REVENUE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerHistory.setLayoutManager(layoutManager);
        recyclerHistory.setNestedScrollingEnabled(false);
        recyclerHistory.setHasFixedSize(true);
        recyclerHistory.setAdapter(revenueAdapter);*/

        dateDialog = new DateDialog(this, new DateDialog.DateSelected() {
            @Override
            public void onSelected(String date) {

                Date dateTemp = Utils.convertStringToDate(date, "dd/MM/yyyy");
                String d = Utils.getMonthFromFullDate(dateTemp);
                txtDate.setText("Tháng " + d);
            }
        });

        //handle searchview
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (revenueAdapter != null && list.size() > 0) {
                    String search = s.trim();
                    String search2 = search.toLowerCase();


                    ArrayList<Revenue> listTemp = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        Revenue itemRevenue = list.get(i);

                        ArrayList<ItemHistory> listItem = new ArrayList<>();
                        for (int j = 0; j < itemRevenue.getList().size(); j++) {

                            ItemHistory itemHistory = itemRevenue.getList().get(j);
                            if (itemHistory.getName().toLowerCase().indexOf(search2) > -1) {
                                listItem.add(itemHistory);
                            }
                        }
                        if (listItem.size() > 0) {
                            Revenue newRevenue = new Revenue(itemRevenue.getMonth(), listItem);
                            listTemp.add(newRevenue);
                        }


                    }
                    revenueAdapter = new RevenueAdapter(getApplicationContext(), listTemp, MANAGE_REVENUE);
                    recyclerHistory.setAdapter(revenueAdapter);

                }
                return true;
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_selectDate:
                //selectDateDialog.showDialog();
                //dateDialog.show();
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                if (Build.VERSION.SDK_INT == 24) {    // Android 7.0 Nougat, API 24
                    final Context contextThemeWrapper = new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light_Dialog);
                    FixedHoloDatePickerDialog mDatePickerDialog = new FixedHoloDatePickerDialog(contextThemeWrapper, new DatePickerDialog.OnDateSetListener() {
                        boolean fired = false;

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if (fired) {
                                return;
                            } else {
                                txtDate.setText("Tháng " + (monthOfYear + 1) + "/" + year);
                                String tempdate = year + "/" + (monthOfYear + 1) + "/15";

                                String tempUTC = Utils.convertLocalTimeToUTC(Utils.convertStringToDate(tempdate, "yyyy/MM/dd"));
                                Date tempDate1 = Utils.convertUTCTimeToLocalTime(tempUTC);

                                from = Utils.getFirstDateOfMonth(tempDate1);
                                to = Utils.getLastDateOfMonth(tempDate1);
                                initListView(from, to);
                                fired = true;
                            }
                        }
                    }, mYear, mMonth, mDay);
                    ((ViewGroup) mDatePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day",
                            "id", "android")).setVisibility(View.GONE);
                    mDatePickerDialog.show();
                } else {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                            new DatePickerDialog.OnDateSetListener() {
                                boolean fired = false;

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    if (fired) {
                                        return;
                                    } else {
                                        txtDate.setText("Tháng " + (monthOfYear + 1) + "/" + year);
                                        String tempdate = year + "/" + (monthOfYear + 1) + "/15";

                                        String tempUTC = Utils.convertLocalTimeToUTC(Utils.convertStringToDate(tempdate, "yyyy/MM/dd"));
                                        Date tempDate1 = Utils.convertUTCTimeToLocalTime(tempUTC);

                                        from = Utils.getFirstDateOfMonth(tempDate1);
                                        to = Utils.getLastDateOfMonth(tempDate1);
                                        initListView(from, to);
                                        fired = true;
                                    }
                                }
                            }, mYear, mMonth, mDay);
                    ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day",
                            "id", "android")).setVisibility(View.GONE);
                    datePickerDialog.show();
                }
                break;
            case R.id.btn_add:
                isReloadWhenBack = true;
                Intent intent = new Intent(this, Setup2AddActivity.class).putExtra(Const.ACTION_GUIDE, MANAGE_REVENUE);
                startActivityForResult(intent, CODE_REVENUE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_REVENUE) {
            switch (resultCode) {
                case RESULT_CODE_MANAGE_REVENUE:
                    Bundle bundle = data.getBundleExtra("DATA");
                    String name = bundle.get("title").toString();
                    double value = (double) bundle.get("value");

                    /*if (name != null) {
                        if (name.equals(getResources().getString(R.string.setup1_add_index0))) {
                            listItem.add(new ItemHistory(name, value, "", R.drawable.icon_wallet));

                        } else if (name.equals(getResources().getString(R.string.setup1_add_index1))) {
                            listItem.add(new ItemHistory(name, value, "", R.drawable.icon_bonus));

                        } else if (name.equals(getResources().getString(R.string.setup1_add_index2))) {
                            listItem.add(new ItemHistory(name, value, "", R.drawable.icon_sale));

                        } else if (name.equals(getResources().getString(R.string.setup1_add_index3))) {
                            listItem.add(new ItemHistory(name, value, "", R.drawable.icon_present));

                        } else if (name.equals(getResources().getString(R.string.setup1_add_index4))) {
                            listItem.add(new ItemHistory(name, value, "", R.drawable.icon_interest));

                        } else if (name.equals(getResources().getString(R.string.setup1_add_index5))) {
                            listItem.add(new ItemHistory(name, value, "", R.drawable.icon_loan));

                        } else if (name.equals(getResources().getString(R.string.setup1_add_index6))) {
                            listItem.add(new ItemHistory(name, value, "", R.drawable.icon_overtime));

                        } else if (name.equals(getResources().getString(R.string.setup1_add_index7))) {
                            listItem.add(new ItemHistory(name, value, "", R.drawable.icon_tips));

                        } else if (name.equals(getResources().getString(R.string.setup1_add_index8))) {
                            listItem.add(new ItemHistory(name, value, "", R.drawable.icon_plus));
                        }
                    }

                    //adapter.notifyDataSetChanged();
                    revenueAdapter.notifyDataSetChanged();*/

                    initListView(Utils.getFirstDateOfMonth(new Date()), Utils.getLastDateOfMonth(new Date()));
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent();
        i.putExtra("isReload", isReloadWhenBack);
        setResult(200, i);
        isReloadWhenBack = false;

        LinearLayout mainLayout;
        // Get your layout set up, this is just an example
        mainLayout = (LinearLayout)findViewById(R.id.layoutmain_manager_revenue);

// Then just use the following:
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
        super.onBackPressed();
    }


    private static final class FixedHoloDatePickerDialog extends DatePickerDialog {
        private FixedHoloDatePickerDialog(Context context, OnDateSetListener callBack,
                                          int year, int monthOfYear, int dayOfMonth) {
            super(context, callBack, year, monthOfYear, dayOfMonth);

            // Force spinners on Android 7.0 only (SDK 24).
            // Note: I'm using a naked SDK value of 24 here, because I'm
            // targeting SDK 23, and Build.VERSION_CODES.N is not available yet.
            // But if you target SDK >= 24, you should have it.
            if (Build.VERSION.SDK_INT == 24) {
                try {
                    final Field field = this.findField(
                            DatePickerDialog.class,
                            DatePicker.class,
                            "mDatePicker"
                    );

                    final DatePicker datePicker = (DatePicker) field.get(this);
                    final Class<?> delegateClass = Class.forName(
                            "android.widget.DatePicker$DatePickerDelegate"
                    );
                    final Field delegateField = this.findField(
                            DatePicker.class,
                            delegateClass,
                            "mDelegate"
                    );

                    final Object delegate = delegateField.get(datePicker);
                    final Class<?> spinnerDelegateClass = Class.forName(
                            "android.widget.DatePickerSpinnerDelegate"
                    );

                    if (delegate.getClass() != spinnerDelegateClass) {
                        delegateField.set(datePicker, null);
                        datePicker.removeAllViews();

                        final Constructor spinnerDelegateConstructor =
                                spinnerDelegateClass.getDeclaredConstructor(
                                        DatePicker.class,
                                        Context.class,
                                        AttributeSet.class,
                                        int.class,
                                        int.class
                                );
                        spinnerDelegateConstructor.setAccessible(true);

                        final Object spinnerDelegate = spinnerDelegateConstructor.newInstance(
                                datePicker,
                                context,
                                null,
                                android.R.attr.datePickerStyle,
                                0
                        );
                        delegateField.set(datePicker, spinnerDelegate);

                        datePicker.init(year, monthOfYear, dayOfMonth, this);
                        datePicker.setCalendarViewShown(false);
                        datePicker.setSpinnersShown(true);
                    }
                } catch (Exception e) { /* Do nothing */ }
            }
        }

        /**
         * Find Field with expectedName in objectClass. If not found, find first occurrence of
         * target fieldClass in objectClass.
         */
        private Field findField(Class objectClass, Class fieldClass, String expectedName) {
            try {
                final Field field = objectClass.getDeclaredField(expectedName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) { /* Ignore */ }

            // Search for it if it wasn't found under the expectedName.
            for (final Field field : objectClass.getDeclaredFields()) {
                if (field.getType() == fieldClass) {
                    field.setAccessible(true);
                    return field;
                }
            }

            return null;
        }
    }
}
