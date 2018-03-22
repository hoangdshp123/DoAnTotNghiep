package com.example.hoang.doantotnghiep.View.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Adapter.BudgetBaseAdapter;
import com.example.hoang.doantotnghiep.Adapter.BudgetLuxuryAdapter;
import com.example.hoang.doantotnghiep.Adapter.BudgetMinAdapter;
import com.example.hoang.doantotnghiep.Adapter.CustomMakerLayout;
import com.example.hoang.doantotnghiep.Interface.ReloadHomeData;
import com.example.hoang.doantotnghiep.Model.ModelApi.ExpensesDate;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelManagerRevenue.ManagerRevenue;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.activitys.EditBudgetActivity;
import com.example.hoang.doantotnghiep.View.activitys.HomeActivity;
import com.example.hoang.doantotnghiep.View.activitys.SpendManagementActivity;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;
import com.example.hoang.doantotnghiep.utils.ExpensesDateUtils;
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

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TWO on 12/26/2017.
 */

@SuppressLint("ValidFragment")
public class BudgetFragment extends Fragment implements View.OnClickListener {
    private View view;
    private LineChart chart;
    private PieChart pieChart1, pieChart2, pieChart3;
    private Button btnEdit;
    private float datachart1= 0, datachart2 = 0, datachart3 = 0;
    private double total = (double) HomeActivity.totalRevenue;
    private double budgetmin = (double) HomeActivity.budgetMin, budgetbase = (double) HomeActivity.budgetBase, budgetlux = (double) HomeActivity.budgetLuxury;
    private ListView listViewNganSachToithieu, listViewNganSachCoBan, listViewPhongCachSong;
    private List<ManagerRevenue.Budget_base> listNganSachCoBan;
    private List<ManagerRevenue.Budget_min> listNganSachToiThieu;
    private List<ManagerRevenue.Budget_luxury> listBudgetLuxury;
    private BudgetLuxuryAdapter adapterBudgetLuxury;
    private BudgetMinAdapter adapterNganSachToiThieu;
    private BudgetBaseAdapter adapterNganSachCoBan;
    private ManagerRevenue managerRevenue;
    private TextView txtvbudgetmin, txtvbudgetbase, txtvbudgetluxury, min_budget, base_budget, lux_budget;
    public static double mindata = 0, basedata = 0, luxrydata = 0;

    private ArrayList<ExpensesDate.Data> listHistory;
    private ReloadHomeData reloadHomeData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_budget, container, false);
        initData();
        return view;
    }

    public void initData(){
        initView();
        initLineChart();
        initDataPieChart();
        //initNganSachToiThieu();
        if (managerRevenue != null) {
            initBudgetLuxury();
            initNganSachCoBan();
            initNganSachToiThieu();
        }
    }

    public void updateData(Boolean isUpdate, ManagerRevenue managerRevenue,
                           double budgetmin, double budgetbase, double budgetlux) {
        if (isUpdate){
            this.managerRevenue = managerRevenue;
            this.budgetmin = budgetmin;
            this.budgetbase = budgetbase;
            this.budgetlux = budgetlux;
            initData();
        }
    }

    @SuppressLint("ValidFragment")
    public BudgetFragment(ManagerRevenue managerRevenue, ReloadHomeData reloadHomeData) {
        this.managerRevenue = managerRevenue;
        this.reloadHomeData = reloadHomeData;
    }

    public static BudgetFragment newInstance(ManagerRevenue managerRevenue, ReloadHomeData reloadHomeData) {
        BudgetFragment fragment = new BudgetFragment(managerRevenue,reloadHomeData);
        return fragment;
    }

    private void initBudgetLuxury() {
        listBudgetLuxury = new ArrayList<>();
        listBudgetLuxury = managerRevenue.data.budgets.budget_luxury;
        adapterBudgetLuxury = new BudgetLuxuryAdapter(getContext(), listBudgetLuxury);
        listViewPhongCachSong.setAdapter(adapterBudgetLuxury);
        setListViewHeightBasedOnChildren(listViewPhongCachSong);
    }

    private void getManagerRevenua(){
        CallAPI.getManager_revenue(getContext(), Utils.getFirstDateOfMonth(new Date()),
                Utils.getLastDateOfMonth(new Date()), new CallAPI.OnCallManager_Revenue() {
                    @Override
                    public void onResult(ManagerRevenue managerRevenue) {
                        if (managerRevenue.code == 1) {
                            BudgetFragment.this.managerRevenue = managerRevenue;
                        }
                    }
                });
    }

    private void initNganSachCoBan() {
        listNganSachCoBan = new ArrayList<>();
        listNganSachCoBan = managerRevenue.data.budgets.budget_base;
        adapterNganSachCoBan = new BudgetBaseAdapter(getContext(), listNganSachCoBan);
        listViewNganSachCoBan.setAdapter(adapterNganSachCoBan);
        setListViewHeightBasedOnChildren(listViewNganSachCoBan);
    }

    public void initNganSachToiThieu() {
        listNganSachToiThieu = new ArrayList<>();
        listNganSachToiThieu = managerRevenue.data.budgets.budget_min;
        adapterNganSachToiThieu = new BudgetMinAdapter(getContext(), listNganSachToiThieu);
        listViewNganSachToithieu.setAdapter(adapterNganSachToiThieu);
        setListViewHeightBasedOnChildren(listViewNganSachToithieu);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += (listItem.getMeasuredHeight());
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void initPieChart(PieChart pieChart, float a, float b, String phanTram, String mau) {
        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(a));
        entries.add(new PieEntry(b));


        Legend l = pieChart.getLegend();

        PieDataSet set = new PieDataSet(entries, null);
        set.setDrawValues(false);
        PieData data = new PieData(set);
        set.setColors(new int[]{
                Color.parseColor(mau),
                Color.parseColor("#e6e6e6"),
        });
        pieChart.setCenterText(phanTram);
        pieChart.setCenterTextSize(14);
        pieChart.setCenterTextColor(Color.parseColor("#637181"));
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleRadius(70);
        pieChart.setData(data);
        pieChart.invalidate();
        l.setEnabled(false);
    }

    private void initDataPieChart() {
        Date current = new Date();
        String dateISO_from = Utils.getCurrentMounth() + "/" + 1;
        Log.d("poiu", dateISO_from);
        String dateISO_to = Utils.convertDateToISO(current);
        CallAPI.getExpensesDate(getContext(), dateISO_from, dateISO_to, 100, "date_time_ASC", new CallAPI.OnCallExpensesDate() {
            @Override
            public void onResult(ExpensesDate expensesDate) {
                if (managerRevenue != null && managerRevenue.code == 1) {

                    datachart1 = (float) ((double) BudgetFragment.mindata / budgetmin * 100);
                    datachart2 = (float) ((double) BudgetFragment.basedata / budgetbase * 100);
                    datachart3 = (float) ((double) BudgetFragment.luxrydata / budgetlux * 100);
                    if(budgetmin==0)
                    {
                        datachart1 = 0;
                    }
                    if(budgetbase == 0)
                    {
                        datachart2 = 0;
                    }
                    if(budgetlux==0)
                    {
                        datachart3 = 0;
                    }

                    initPieChart(pieChart1, datachart1, 100 - datachart1, Math.round(datachart1 * 10) / 10 + "%", "#BA68C8");
                    initPieChart(pieChart2, datachart2, 100 - datachart2, Math.round(datachart2 * 10) / 10 + "%", "#f8e71c");
                    initPieChart(pieChart3, datachart3, 100 - datachart3, Math.round(datachart3 * 10) / 10 + "%", "#4a90e2");
                    if (datachart1 > 100) {
                        initPieChart(pieChart1, 100, 0, "> 100%", "#F44336");
                    }
                    if (datachart2 > 100) {
                        initPieChart(pieChart2, 100, 0, "> 100%", "#F44336");
                    }
                    if (datachart3 > 100) {
                        initPieChart(pieChart3, 100, 0, "> 100%", "#F44336");
                    }
                }
            }
        });
    }

    private void initLineChart() {
        Date current = new Date();

        String currentDate = Utils.getCurrentDate("dd/MM/yyyy");
        Date d2 = Utils.convertStringToDate(currentDate, "dd/MM/yyyy");
        String dateISO_to = Utils.convertLocalTimeToUTC(d2);
        String dateISO_to1 = Utils.convertLocalTimeToUTC1(d2);

        Date c = new Date(d2.getTime() - 4 * 24 * 3600000);
        String dateISO_from = Utils.convertLocalTimeToUTC(c);
        String dateISO_from1 = Utils.convertLocalTimeToUTC1(c);

        CallAPI.getExpensesDate(getContext(), dateISO_from, dateISO_to, 100, "date_time_ASC", new CallAPI.OnCallExpensesDate() {
            @Override
            public void onResult(ExpensesDate expensesDate) {

                mindata = 0;
                basedata = 0;
                luxrydata = 0;
                for (int i = 0; i < expensesDate.data.size(); i++) {
                    if (expensesDate.data.get(i).getType().equals("min")) {
                        mindata += Double.parseDouble(expensesDate.data.get(i).getExpense_value());
                    }
                    if (expensesDate.data.get(i).getType().equals("base")) {
                        basedata += Double.parseDouble(expensesDate.data.get(i).getExpense_value());
                    }
                    if (expensesDate.data.get(i).getType().equals("luxury")) {
                        luxrydata += Double.parseDouble(expensesDate.data.get(i).getExpense_value());
                    }
                }

                DateFormat outputFormat = new SimpleDateFormat("dd/MM");
                List<Entry> lineEntries = new ArrayList<Entry>();
                List<String> date = new ArrayList<>();
                List<String> datekey = new ArrayList<>();
                List<Double> temp = new ArrayList<>();
                List<String> value = new ArrayList<>();
                double max = 0;
                if (expensesDate.code == 0) {
                    Toast.makeText(getContext(), "Chọn ngày !", Toast.LENGTH_SHORT).show();
                } else if (expensesDate.code == 1) {
                    if (expensesDate.data != null && expensesDate.data.size() > 0) {
                        listHistory = (ArrayList<ExpensesDate.Data>) expensesDate.data;
                        ArrayList<ExpensesDate.Data> result = ExpensesDateUtils.getExpenseDate(listHistory);
                        result = Utils.addZeroToEmpty(result,dateISO_from,dateISO_to);
                        date = Utils.addDateZero(dateISO_from1,dateISO_to1);
                        for (int i = 0; i < result.size(); i++) {
//                            lineEntries.add(new Entry(i, Float.parseFloat(result.get(i).expense_value)));
                            temp.add(Double.parseDouble(result.get(i).getExpense_value()));
                            //String inpuText = result.get(i).getDate_time();
                            //date.add(Utils.convertUTCTimeToLocalTime2(inpuText));
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
                            lineEntries.add(new Entry(i, Float.parseFloat(String.valueOf(temp.get(i)))));
                        }

                        LineDataSet dataSet = new LineDataSet(lineEntries, "Label");
                        dataSet.setColors(Color.parseColor("#74b1d8"));
                        dataSet.setLineWidth(2);
                        dataSet.setCircleRadius(3);
                        dataSet.setCircleSize(3);
                        dataSet.setDrawValues(false);
                        dataSet.setCircleColorHole(Color.parseColor("#74b1d8"));
                        dataSet.setCircleColor(Color.parseColor("#4a90e2"));

                        YAxis leftAxis = chart.getAxisLeft();
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
                                    kq = String.valueOf(lol * value / 100);
                                } else kq = String.valueOf(0);
                                return String.valueOf(value);
//                                return kq;
                            }
                        });

                        YAxis rightYAxis = chart.getAxisRight();
                        rightYAxis.setEnabled(false);


                        XAxis xAxis = chart.getXAxis();
                        List<String> finalDate = date;
                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                if (finalDate.size() > 1) {
                                    DecimalFormat mFormat = new DecimalFormat("###,###,##0");
                                    return finalDate.get((int) value);
//                                return mFormat.format(value);
                                } else {
                                    DecimalFormat mFormat = new DecimalFormat("###,###,##0");
                                    return finalDate.get(0);
                                }
                            }
                        });

                        xAxis.setGranularity(1f);

                        xAxis.setAxisMaximum(dataSet.getXMax());
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawAxisLine(true);
                        xAxis.setDrawGridLines(false);

                        LineData lineData = new LineData(dataSet);
                        chart.getDescription().setEnabled(false);
//                        lineData.notifyDataChanged();
                        chart.setData(lineData);
                        chart.setTouchEnabled(true);
                        CustomMakerLayout mv = new CustomMakerLayout(getContext(), R.layout.custommaker,value);
                        chart.setMarkerView(mv);
                        chart.notifyDataSetChanged();
                        chart.invalidate();
                        Legend l = chart.getLegend();
                        l.setEnabled(false);

                        initDataPieChart();
                    }
                }
            }
        });
    }

    private void initView() {
        chart = (LineChart) view.findViewById(R.id.chart);
        pieChart1 = (PieChart) view.findViewById(R.id.piechart1);
        pieChart2 = (PieChart) view.findViewById(R.id.piechart2);
        pieChart3 = (PieChart) view.findViewById(R.id.piechart3);

        listViewNganSachCoBan = (ListView) view.findViewById(R.id.lv_ngan_sach_co_ban1);
        listViewNganSachToithieu = (ListView) view.findViewById(R.id.lv_ngan_sach_toi_thieu);
        listViewPhongCachSong = (ListView) view.findViewById(R.id.lv_phong_cach_song);
        view.findViewById(R.id.btn_manager).setOnClickListener(this);
        btnEdit = view.findViewById(R.id.btn_edit_ns);
        btnEdit.setOnClickListener(this);

        txtvbudgetbase = view.findViewById(R.id.txtv_budget_base);
        txtvbudgetluxury = view.findViewById(R.id.txtv_budgetluxury);
        txtvbudgetmin = view.findViewById(R.id.txtv_budgetmin);
        min_budget = view.findViewById(R.id.min_budget);
        base_budget = view.findViewById(R.id.base_budget);
        lux_budget = view.findViewById(R.id.lux_budget);

        txtvbudgetmin.setText(String.valueOf(DecimalFormatUtils.getMoney(Math.round(budgetmin))));
        txtvbudgetluxury.setText(String.valueOf(DecimalFormatUtils.getMoney(Math.round(budgetlux))));
        txtvbudgetbase.setText(String.valueOf(DecimalFormatUtils.getMoney(Math.round(budgetbase))));
        min_budget.setSelected(true);
        base_budget.setSelected(true);
        lux_budget.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_manager:
                Intent intent = new Intent(getActivity(), SpendManagementActivity.class);
                startActivityForResult(intent, 9009);
                break;

            case R.id.btn_edit_ns:
                Intent iEdit = new Intent(getActivity(), EditBudgetActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("nstt", (Serializable) listNganSachToiThieu);
                bundle.putSerializable("nscb", (Serializable) listNganSachCoBan);
                bundle.putSerializable("pcs", (Serializable) listBudgetLuxury);
                iEdit.putExtra("data", bundle);

                startActivityForResult(iEdit, 4991);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //getManagerRevenua();
        //initData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 9009 && resultCode == 1112) || (requestCode == 9009 && resultCode == 2023) || (requestCode == 9009 && resultCode == 2024)) {
            int a = 0;
            reloadHomeData.onReload(true,1);
            initLineChart();
            initDataPieChart();
        }
    }


}
