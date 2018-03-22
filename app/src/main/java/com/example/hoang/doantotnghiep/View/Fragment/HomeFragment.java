package com.example.hoang.doantotnghiep.View.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Adapter.CustomMakerLayout;
import com.example.hoang.doantotnghiep.Adapter.TrackSpendingAdapter;
import com.example.hoang.doantotnghiep.Interface.OnDataGetSuccess;
import com.example.hoang.doantotnghiep.Interface.ReloadHomeData;
import com.example.hoang.doantotnghiep.Model.ModelApi.ExpensesDate;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelManagerRevenue.ManagerRevenue;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.activitys.HomeActivity;
import com.example.hoang.doantotnghiep.View.activitys.ManageRevenueActivity;
import com.example.hoang.doantotnghiep.View.activitys.PaymentActivity;
import com.example.hoang.doantotnghiep.View.activitys.SpendManagementActivity;
import com.example.hoang.doantotnghiep.View.dialog.SelectDateDialog;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;
import com.example.hoang.doantotnghiep.utils.ExpensesDateUtils;
import com.example.hoang.doantotnghiep.utils.SharedPrefsUtils;
import com.example.hoang.doantotnghiep.utils.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 12/25/2017.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements View.OnClickListener, OnDataGetSuccess {

    private View view;
    private PieChart chart;
    //    private LineChart lineChart;
    FrameLayout viewChart;
    private PieChart pieChart;
    private TextView text1, text2, text3, text4, sotien;
    private Button btnQLCT;
    private Button btnQLTN;
    private ListView lv;
    private TrackSpendingAdapter adapter;
    private ArrayList<ExpensesDate.Data> list10day = new ArrayList<>();
    String token;
    double total1;
    RelativeLayout btn_selectDate;
    SelectDateDialog selectDateDialog;
    TextView txt_date;
    ReloadHomeData reloadHomeData;


    public static long percent;
    private ManagerRevenue managerRevenue;
    private double budgetLuxury = HomeActivity.budgetLuxury;
    private double budgetMin = HomeActivity.budgetMin;
    private double budgetBase = HomeActivity.budgetBase;
    private double totalExpense = HomeActivity.budgetBase;
    private ArrayList<ExpensesDate.Data> listHistory;

    @SuppressLint("ValidFragment")
    public HomeFragment(ManagerRevenue managerRevenue, ReloadHomeData re) {
        this.managerRevenue = managerRevenue;
        this.reloadHomeData = re;

    }

    public static HomeFragment newInstance(ManagerRevenue managerRevenue,ReloadHomeData re) {
        HomeFragment fragment = new HomeFragment(managerRevenue,re);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        token = "Bearer " + SharedPrefsUtils.getStringPreference(getContext(), "token");

        //setup view cho bieu do và du lieu quan ly thu nhap
        Chartpersonal();
        personalFinanse();


        //get curent day utc
        String currentDate = Utils.getCurrentDate("dd/MM/yyyy");
        Date d2 = Utils.convertStringToDate(currentDate, "dd/MM/yyyy");
        String dateUTC_to = Utils.convertLocalTimeToUTC(d2);

        //get date 5day befor utc
        Date c = new Date(d2.getTime() - 4 * 24 * 3600000);
        String dateUTC_from = Utils.convertLocalTimeToUTC(c);

        //call api last 5 day
        Chartspend(dateUTC_from, dateUTC_to);
        txt_date.setText("Từ " + Utils.convertDateToString(c) + " đến " + currentDate);
//        txt_date.setText("Từ " + Utils.revertStringDate(currentDate).substring(0,5) + " đến " + Utils.revertStringDate(Utils.convertUTCMonthToLocalTime(dateUTC_from)));
        if (managerRevenue != null)
            Chartrest();
        getLast10Expense();


        return view;
    }

    public void getLast10Expense() {

//        String d = Utils.getCurrentMounth() + "/" + 1;

        Date c = Utils.convertStringToDate("1990/01/01", "yyyy/MM/dd");
        String dateISO_from = Utils.convertLocalTimeToUTC(c);
        String currentDate = Utils.getCurrentDate("dd/MM/yyyy");
        Date d2 = Utils.convertStringToDate(currentDate, "dd/MM/yyyy");
        String dateISO_to = Utils.convertLocalTimeToUTC(d2);
//        Date last7day = Utils.getPreveousDay(-6);
        //type = top_date_time: sắp xếp giảm dần theo date_time,type = top_expense_value: sắp xếp giảm dần theo giá trị
        CallAPI.getExpensesDate(getContext(), dateISO_from, dateISO_to, 10, "date_time_DESC", new CallAPI.OnCallExpensesDate() {
            @Override
            public void onResult(ExpensesDate expensesDate) {
                if (expensesDate.code == 0) {

                } else if (expensesDate.code == 1) {
                    if (expensesDate.data != null ) {
                        list10day = (ArrayList<ExpensesDate.Data>) expensesDate.data;
                        Log.d("mmmmm", String.valueOf(list10day.size()));
                        adapter = new TrackSpendingAdapter(list10day, getContext());
                        lv.setAdapter(adapter);
                    } else if (expensesDate.data != null ) {
                        if (!HomeActivity.isCall)
                            startAddExpense();
                    }
                }
            }
        });


    }

    public void updateData(Boolean isUpdate,ManagerRevenue managerRevenue_global,double budgetLuxury,double budgetMin,double budgetBase,double total_expense) {
        if(isUpdate){
            this.managerRevenue = managerRevenue_global;
            this.budgetLuxury = budgetLuxury;
            this.budgetMin = budgetMin;
            this.budgetBase = budgetBase;
            //setup view cho bieu do và du lieu quan ly thu nhap
            Chartpersonal();
            personalFinanse();
            Chartrest();

            String currentDate = Utils.getCurrentDate("dd/MM/yyyy");
            Date d2 = Utils.convertStringToDate(currentDate, "dd/MM/yyyy");
            String dateUTC_to = Utils.convertLocalTimeToUTC(d2);

            //get date 5day befor utc
            Date c = new Date(d2.getTime() - 4 * 24 * 3600000);
            String dateUTC_from = Utils.convertLocalTimeToUTC(c);

            //call api last 5 day
            Chartspend(dateUTC_from, dateUTC_to);
        }

    }

    public void startAddExpense() {
        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setMessage("Bạn chưa có chi tiêu nào trong tháng này. Bạn có muốn nhập chi tiêu");
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HomeActivity.isCall = true;
                        Intent intent = new Intent(getContext(), PaymentActivity.class);
                        startActivityForResult(intent, 900);
                    }
                });
                alertDialog.create().show();


            }
        }.start();
    }

    private void initView() {
        chart = (PieChart) view.findViewById(R.id.chart);
//        lineChart = (LineChart) view.findViewById(R.id.chart2);
        viewChart = (FrameLayout) view.findViewById(R.id.viewChart);
        pieChart = (PieChart) view.findViewById(R.id.piechart);
        text1 = (TextView) view.findViewById(R.id.text1);
        text2 = (TextView) view.findViewById(R.id.text2);
        text3 = (TextView) view.findViewById(R.id.text3);
        text4 = (TextView) view.findViewById(R.id.text4);
        sotien = (TextView) view.findViewById(R.id.sotien);
        lv = (ListView) view.findViewById(R.id.lv);
        btnQLCT = (Button) view.findViewById(R.id.btn_qlct);
        btnQLTN = (Button) view.findViewById(R.id.btn_manage_income);
        btn_selectDate = view.findViewById(R.id.btn_selectDate);
        btn_selectDate.setOnClickListener(this);
        btnQLTN.setOnClickListener(this);
        btnQLCT.setOnClickListener(this);

        txt_date = view.findViewById(R.id.txt_date);

        selectDateDialog = new SelectDateDialog(getActivity(), new SelectDateDialog.Selected() {
            @Override
            public void onSelect(String toDate, String fromDate) {

                txt_date.setText("Từ " + Utils.revertStringDate(toDate).substring(0,5) + " đến " + Utils.revertStringDate(fromDate));

                Chartspend(Utils.convertLocalTimeToUTC(Utils.convertStringToDate(toDate,"yyyy/MM/dd")), Utils.convertLocalTimeToUTC(Utils.convertStringToDate(fromDate,"yyyy/MM/dd")));
            }
        });

        Log.d("LAST DAY", Utils.getLastDateOfMonth(new Date()));

        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void Chartpersonal() {
        double rest;
        rest = HomeActivity.totalRevenue - HomeActivity.totalBudget;

        List<PieEntry> entries = new ArrayList<>();

        if(rest <= 0){
            rest = 0;
        }

        entries.add(new PieEntry((float)budgetLuxury));
        entries.add(new PieEntry((float) rest));
        entries.add(new PieEntry((float) budgetMin));
        entries.add(new PieEntry((float) budgetBase));

        if (budgetLuxury <= 0 && rest <= 0 && budgetMin <= 0 && budgetBase <= 0) {
            entries.add(new PieEntry(100));
            chart.setCenterText(0 + "%");
            chart.setCenterTextSize(20);
            chart.setCenterTextColor(Color.parseColor("#637181"));
        }else {
            chart.setCenterText("");
        }

        PieDataSet set = new PieDataSet(entries, null);
        set.setDrawValues(false);
        chart.getDescription().setEnabled(false);
        PieData data = new PieData(set);
        set.setColors(new int[]{
                Color.parseColor("#4a90e2"), //xanh nc biển
                Color.parseColor("#82c935"),
                Color.parseColor("#f0374e"),
                Color.parseColor("#f8e71c"),
                Color.parseColor("#c9cde5"),
        });
        chart.setCenterTextColor(Color.parseColor("#000000"));
        chart.setData(data);
        chart.setHoleRadius(63);
        chart.invalidate();

        Legend l = chart.getLegend();
        l.setEnabled(false);
    }


    private void Chartspend(String fromdate, String todate) {
        CallAPI.getExpensesDate(getContext(), fromdate, todate, 100, "date_time_ASC", new CallAPI.OnCallExpensesDate() {
            @Override
            public void onResult(ExpensesDate expensesDate) {
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                DateFormat outputFormat = new SimpleDateFormat("dd/MM");
                List<Entry> lineEntries = new ArrayList<Entry>();
                List<Entry> lineEntries1 = new ArrayList<Entry>();
                List<String> date = new ArrayList<>();
                List<String> value = new ArrayList<>();
                List<Double> temp = new ArrayList<>();
                double temptemp = 0;
                double max = 0;

                if (expensesDate.code == 0) {
                    Toast.makeText(getContext(), "Chọn ngày !", Toast.LENGTH_SHORT).show();
                } else if (expensesDate.code == 1) {
                    if (expensesDate.data != null ) {
                        listHistory = (ArrayList<ExpensesDate.Data>) expensesDate.data;
                        ArrayList<ExpensesDate.Data> result = ExpensesDateUtils.getExpenseDate(listHistory);
                        result = Utils.addZeroToEmpty(result,fromdate,todate);
                        date = Utils.addDateZero(fromdate,todate);


                        for (int i = 0; i < result.size(); i++) {
//                            lineEntries.add(new Entry(i, Float.parseFloat(result.get(i).expense_value)));
                            temp.add(Double.parseDouble(result.get(i).getExpense_value()));
                            String inpuText = result.get(i).getDate_time();
//                            date.add(Utils.convertUTCTimeToLocalTime2(inpuText));
                            value.add(result.get(i).expense_value);
                        }
                        max = temp.get(0);
                        if (temp.size() > 0) {
                            for (int i = 0; i < temp.size() - 1; i++) {
                                if (temp.get(i + 1) > max) {
                                    max = temp.get(i + 1);
                                }
                            }
                        }
                        double lol = max;
                        for (int i = 0; i < temp.size(); i++) {
                            lineEntries1.add(new Entry(i, Float.parseFloat(String.valueOf(temp.get(i)))));
                        }

                        DrawChart(lineEntries1, date, value, max);
                    }
                }
            }
        });
    }


    public void DrawChart(List<Entry> lineEntries1, List<String> date, List<String> value1, double max) {
        viewChart.removeAllViews();
        LineChart lineChart = new LineChart(getActivity());
        lineChart.setMinimumHeight(420);

        LineDataSet dataSet = new LineDataSet(lineEntries1, "Label");
        dataSet.setColors(Color.parseColor("#74b1d8"));
        dataSet.setLineWidth(2);
        dataSet.setCircleRadius(3);
        dataSet.setCircleSize(3);
        dataSet.setDrawValues(false);
        dataSet.setCircleColorHole(Color.parseColor("#74b1d8"));
        dataSet.setCircleColor(Color.parseColor("#4a90e2"));
//        dataSet.setValues(lineEntries1);

        LineData lineData = new LineData(dataSet);
        lineChart.getDescription().setEnabled(false);
//                        lineData.notifyDataChanged();
        lineChart.setData(lineData);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMaximum(dataSet.getYMax());
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGranularity(10f);
//        leftAxis.setLabelCount(value1.size());
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(true);

        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                double i = value / 5;
                String kq = null;
                if (value != 0) {
                    kq = String.valueOf(max * value / 100);
                } else kq = String.valueOf(0);
                return String.valueOf(value);
            }
        });

        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        Log.d("xxxxx", String.valueOf(date.size()));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

//                return date.get((int) value);

                if (date.size() > 1) {
                    DecimalFormat mFormat = new DecimalFormat("###,###,##0");
                    return date.get((int) value);

                } else {
                    DecimalFormat mFormat = new DecimalFormat("###,###,##0");
                    return date.get(0);
                }
            }
        });
//        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setAxisMaximum(dataSet.getXMax());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);


        lineChart.notifyDataSetChanged();
        lineChart.invalidate();

        lineChart.setTouchEnabled(true);
        CustomMakerLayout mv = new CustomMakerLayout (getContext(), R.layout.custommaker,value1);
        lineChart.setMarkerView(mv);

        Legend l = lineChart.getLegend();
        l.setEnabled(false);
        viewChart.addView(lineChart);

//                        date.clear();
    }


    void Chartrest() {
        double rest;
        double lt;
        if (managerRevenue.data.getTotalExpense() == null) {
            lt = 0;
        } else lt = managerRevenue.data.getTotalExpense();
        rest = HomeActivity.totalRevenue - lt;
        if (HomeActivity.totalRevenue <= 0 || rest < 0) {
            percent = 0;
        } else {
            percent = (long) (Math.abs(rest) * 100 / HomeActivity.totalRevenue);
        }

        if (rest < 0) {
            sotien.setText(0 + " vnđ");
        } else {
            sotien.setText(DecimalFormatUtils.getMoney(rest));
        }

        double aa = HomeActivity.totalRevenue - managerRevenue.data.getTotalExpense();


        if (aa < 0) {
            List<PieEntry> chartEntries = new ArrayList<>();
            chartEntries.add(new PieEntry(0));
            chartEntries.add(new PieEntry(100));
            PieDataSet set1 = new PieDataSet(chartEntries, null);
            set1.setDrawValues(false);
            pieChart.getDescription().setEnabled(false);
            PieData data = new PieData(set1);
            set1.setColors(new int[]{
                    Color.parseColor("#F44336"),
                    Color.parseColor("#e6e6e6"),
            });
            pieChart.setCenterText("0%");
            pieChart.setCenterTextSize(34);
            pieChart.setCenterTextColor(Color.parseColor("#637181"));
            pieChart.setHoleRadius(85);
            pieChart.setData(data);
            pieChart.invalidate();

            Legend l = pieChart.getLegend();
            l.setEnabled(false);
        } else {
            float bb = (float) ((aa/HomeActivity.totalRevenue)*100);
            List<PieEntry> chartEntries = new ArrayList<>();
            chartEntries.add(new PieEntry((float) bb));
            chartEntries.add(new PieEntry((float) (100 - bb)));
            PieDataSet set1 = new PieDataSet(chartEntries, null);
            set1.setDrawValues(false);
            pieChart.getDescription().setEnabled(false);
            PieData data = new PieData(set1);
            set1.setColors(new int[]{
                    Color.parseColor("#8086de"),
                    Color.parseColor("#c9cde5"),
            });
            double temp = percent;
            if(percent < 0){
                temp = 0;
            }

            pieChart.setCenterText(String.valueOf(temp) + "%");
            pieChart.setCenterTextSize(34);
            pieChart.setCenterTextColor(Color.parseColor("#8086de"));
            pieChart.setHoleRadius(85);
            pieChart.setData(data);
            pieChart.invalidate();

            Legend l = pieChart.getLegend();
            l.setEnabled(false);
        }
    }

    private void personalFinanse() {
        /*a = HomeActivity.totalRevenue;
        b = HomeActivity.totalBudget;*/
        text1.setText(DecimalFormatUtils.getMoney(HomeActivity.totalRevenue));
        text1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        text1.setSelected(true);
        text2.setText(DecimalFormatUtils.getMoney(HomeActivity.totalBudget));
        text2.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        text2.setSelected(true);
        text3.setText(DecimalFormatUtils.getMoney(HomeActivity.totalExpense));
        double total = HomeActivity.totalRevenue - HomeActivity.totalBudget;

        if (total < 0) {
            text4.setText("Ngân sách vượt quá thu nhập");
            text4.setTextColor(Color.parseColor("#ff0000"));
            text4.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            text4.setSelected(true);
        } else {
            text4.setText(DecimalFormatUtils.getMoney(total));
            text4.setTextColor(Color.parseColor("#637181"));
            text4.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            text4.setSelected(true);
        }
    }



    @Override
    public void onResume() {
        
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_selectDate:
                selectDateDialog.showDialog();
                break;

            case R.id.btn_manage_income:
                Intent iManageIncome = new Intent(getActivity(), ManageRevenueActivity.class);
                startActivityForResult(iManageIncome, 1011);
                break;
            case R.id.btn_qlct: {
                Intent intent = new Intent(getActivity(), SpendManagementActivity.class);
                startActivityForResult(intent, 1010);
            }
            break;
            default:
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1010 && resultCode == 1112)|| requestCode == 900) {
            //get curent day utc
            String currentDate = Utils.getCurrentDate("dd/MM/yyyy");
            Date d2 = Utils.convertStringToDate(currentDate, "dd/MM/yyyy");
            String dateUTC_to = Utils.convertLocalTimeToUTC(d2);

            //get date 5day befor utc
            Date c = new Date(d2.getTime() - 4 * 24 * 3600000);
            String dateUTC_from = Utils.convertLocalTimeToUTC(c);

            //call api last 5 day
            Chartspend(dateUTC_from, dateUTC_to);
            getLast10Expense();
            reloadHomeData.onReload(true,0);
        }
        if(requestCode == 1011 && resultCode == 200){
            boolean isReload = data.getBooleanExtra("isReload",false);
            if(isReload){
                reloadHomeData.onReload(true,0);

            }
        }

    }


    @Override
    public void onSuccess() {

    }
}
