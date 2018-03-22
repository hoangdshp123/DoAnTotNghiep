package com.example.hoang.doantotnghiep.View.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Adapter.CostlyAdapter;
import com.example.hoang.doantotnghiep.Adapter.CustomMakerLayout;
import com.example.hoang.doantotnghiep.Model.Costly;
import com.example.hoang.doantotnghiep.Model.ModelApi.ExpensesDate;
import com.example.hoang.doantotnghiep.Model.ModelExpenseDate.ExpenseTop10;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.activitys.HomeActivity;
import com.example.hoang.doantotnghiep.View.dialog.SelectDateDialog;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by TWO on 12/27/2017.
 */

public class StatisticalFragment extends Fragment {
    private View view;
    private LineChart chart;
    private PieChart pieChart1, pieChart2, pieChart3;
    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5;
    private FrameLayout viewChart;
    private ArrayList<ExpensesDate.Data> listHistory;
    private ArrayList<ExpenseTop10.Data> listTop10;
    SelectDateDialog selectDateDialog;
    TextView txt_date, min_bud, base_bud, lux_bud;
    RecyclerView lvCostly;
    private float datachart1, datachart2, datachart3;
    private float total = (float) HomeActivity.totalRevenue;
    private double budgetmin = HomeActivity.budgetMin, budgetbase = HomeActivity.budgetBase, budgetlux = HomeActivity.budgetLuxury;

    TextView txtvbudgetmin, txtvbudgetbase, txtvbudgetlux;
    ArrayList<ExpensesDate.Data> result;
    ArrayList<Costly> costlies;
    CostlyAdapter costlyAdapter;

    public static double mindata = 0, basedata = 0, luxrydata = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistical_payment, container, false);
        initView();
//        initLineChart("2018/1/1", "2018/2/18");

        //get curent day utc
        String currentDate = Utils.getCurrentDate("dd/MM/yyyy");
        Date d2 = Utils.convertStringToDate(currentDate, "dd/MM/yyyy");
        String dateUTC_to = Utils.convertLocalTimeToUTC(d2);

        //get date 5day befor utc
        Date c = new Date(d2.getTime() - 4 * 24 * 3600000);
        String dateUTC_from = Utils.convertLocalTimeToUTC(c);

        //call api last 5 day
        initLineChart(dateUTC_from, dateUTC_to);

        //call api last 10 day
        Date d = new Date(d2.getTime() + (d2.getDate() - 1) * 24 * 3600000);
        String datefrom = Utils.convertLocalTimeToUTC(d);
        CoslyArray();
//        GetBudgetCostly();
        return view;
    }

    public static StatisticalFragment newInstance() {
        StatisticalFragment fragment = new StatisticalFragment();
        return fragment;
    }

    public void reloadData() {
        String currentDate = Utils.getCurrentDate("dd/MM/yyyy");
        Date d2 = Utils.convertStringToDate(currentDate, "dd/MM/yyyy");
        String dateUTC_to = Utils.convertLocalTimeToUTC(d2);

        //get date 5day befor utc
        Date c = new Date(d2.getTime() - 4 * 24 * 3600000);
        String dateUTC_from = Utils.convertLocalTimeToUTC(c);

        //call api last 5 day
        initLineChart(dateUTC_from, dateUTC_to);

        //call api last 10 day
        Date d = new Date(d2.getTime() + (d2.getDate() - 1) * 24 * 3600000);
        String datefrom = Utils.convertLocalTimeToUTC(d);
        CoslyArray();
    }

    private void GetBudgetCostly() {
        Log.d("nkn", String.valueOf(result.size()));
    }

    private void initPieChart(PieChart pieChart, float a, float b, String phanTram, String mau) {
        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(a));
        entries.add(new PieEntry(b));

        pieChart.getDescription().setEnabled(false);

        pieChart.setHoleRadius(70);

        Legend l = pieChart.getLegend();
        l.setEnabled(false);

        pieChart.setCenterText(phanTram);
        PieDataSet set = new PieDataSet(entries, null);
        set.setDrawValues(false);
        PieData data = new PieData(set);
        set.setColors(new int[]{
                Color.parseColor(mau),
                Color.parseColor("#e6e6e6"),
        });
        pieChart.setData(data);
        pieChart.invalidate();
    }

    private void initLineChart(String fromdate, String todate) {
        CallAPI.getExpensesDate(getContext(), fromdate, todate, 100, "date_time_ASC", new CallAPI.OnCallExpensesDate() {
            @Override
            public void onResult(ExpensesDate expensesDate) {

                mindata = 0;
                basedata = 0;
                luxrydata = 0;
                if (expensesDate == null)
                    return;
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

                datachart1 = (float) ((double) mindata / budgetmin * 100);
                datachart2 = (float) ((double) basedata / budgetbase * 100);
                datachart3 = (float) ((double) luxrydata / budgetlux * 100);
                if (budgetmin == 0) {
                    datachart1 = 0;
                }
                if (budgetbase == 0) {
                    datachart2 = 0;
                }
                if (budgetlux == 0) {
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


                List<Entry> lineEntries1 = new ArrayList<Entry>();
                List<String> date = new ArrayList<>();
                List<String> value = new ArrayList<>();
                List<Double> temp = new ArrayList<>();
                double temptemp = 0;
                double max = 0;

                if (expensesDate.code == 0) {
                    Toast.makeText(getContext(), "Chọn ngày !", Toast.LENGTH_SHORT).show();
                } else if (expensesDate.code == 1) {
                    if (expensesDate.data != null) {
                        listHistory = (ArrayList<ExpensesDate.Data>) expensesDate.data;
                        ArrayList<ExpensesDate.Data> result = ExpensesDateUtils.getExpenseDate(listHistory);
                        result = Utils.addZeroToEmpty(result, fromdate, todate);
                        date = Utils.addDateZero(fromdate, todate);


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

    private void CoslyArray() {

        CallAPI.getExpenseTop10(getContext(), new CallAPI.OnCallExpenseTop10() {
            @Override
            public void onResult(ExpenseTop10 expenseTop10) {
                listTop10 = (ArrayList<ExpenseTop10.Data>) expenseTop10.data;
                costlies = new ArrayList<>();
                for (int i = 0; i < listTop10.size(); i++) {
                    if (listTop10.get(i) != null) {
                        Costly costly = new Costly(listTop10.get(i).subType, String.valueOf(listTop10.get(i).expense_value));
                        costlies.add(costly);
                    }
                }

                Collections.sort(costlies, new Comparator<Costly>() {
                    public int compare(Costly obj1, Costly obj2) {
                        if (Double.parseDouble(obj1.getValue()) > Double.parseDouble(obj2.getValue()))
                            return -1;
                        else
                            return 0;
                        // To compare string values
                    }
                });

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                lvCostly.setLayoutManager(layoutManager);
                costlyAdapter = new CostlyAdapter(getContext(), costlies);
                lvCostly.setAdapter(costlyAdapter);
                costlyAdapter.notifyDataSetChanged();

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
//                return kq;
            }
        });

        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        Log.d("xxxxx", String.valueOf(date.size()));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.d("yy", value + "");
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

        lineChart.setTouchEnabled(true);
        CustomMakerLayout mv = new CustomMakerLayout(getContext(), R.layout.custommaker,value1);
        lineChart.setMarkerView(mv);
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
        Legend l = lineChart.getLegend();
        l.setEnabled(false);
        viewChart.addView(lineChart);

//                        date.clear();
    }

    private void initView() {
        pieChart1 = (PieChart) view.findViewById(R.id.piechart_statiscal_1);
        pieChart2 = (PieChart) view.findViewById(R.id.piechart_statiscal_2);
        pieChart3 = (PieChart) view.findViewById(R.id.piechart_statiscal_3);
        viewChart = (FrameLayout) view.findViewById(R.id.viewChart1);

        txtvbudgetmin = view.findViewById(R.id.txtv_budgetmin1);
        txtvbudgetlux = view.findViewById(R.id.txtv_budgetluxury1);
        txtvbudgetbase = view.findViewById(R.id.txtv_budgetbase1);
        min_bud = view.findViewById(R.id.min_bud);
        base_bud = view.findViewById(R.id.base_bud);
        lux_bud = view.findViewById(R.id.lux_bud);

        txtvbudgetlux.setText(DecimalFormatUtils.getMoney(budgetlux));
        txtvbudgetbase.setText(DecimalFormatUtils.getMoney(budgetbase));
        txtvbudgetmin.setText(DecimalFormatUtils.getMoney(budgetmin));
        txtvbudgetlux.setSelected(true);
        txtvbudgetbase.setSelected(true);
        txtvbudgetmin.setSelected(true);
        min_bud.setSelected(true);
        base_bud.setSelected(true);
        lux_bud.setSelected(true);

        txt_date = view.findViewById(R.id.txt_date_statistical);
        lvCostly = view.findViewById(R.id.lv_costly);

        selectDateDialog = new SelectDateDialog(getActivity(), new SelectDateDialog.Selected() {
            @Override
            public void onSelect(String toDate, String fromDate) {
                txt_date.setText("Từ " + Utils.revertStringDate(toDate).substring(0, 5) + " đến " + Utils.revertStringDate(fromDate));

                initLineChart(Utils.convertLocalTimeToUTC(Utils.convertStringToDate(toDate, "yyyy/MM/dd")), Utils.convertLocalTimeToUTC(Utils.convertStringToDate(fromDate, "yyyy/MM/dd")));
            }
        });

        view.findViewById(R.id.btn_selectDate1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDateDialog.showDialog();
            }
        });
    }


}
