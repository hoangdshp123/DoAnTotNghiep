package com.example.hoang.doantotnghiep.View.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Adapter.RecyclerSetup1Adapter;
import com.example.hoang.doantotnghiep.Model.ItemSetup1;
import com.example.hoang.doantotnghiep.Model.ModelApi.ListBudget;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelManagerRevenue.ManagerRevenue;
import com.example.hoang.doantotnghiep.Model.ModelBudget.BudgetRequest;
import com.example.hoang.doantotnghiep.Model.ModelBudget.EditBudgetRequest;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.Fragment.BudgetFragment;
import com.example.hoang.doantotnghiep.utils.Const;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;
import com.example.hoang.doantotnghiep.utils.SharedPrefsUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.hoang.doantotnghiep.utils.Const.EDIT_BUDGET_NSCB;
import static com.example.hoang.doantotnghiep.utils.Const.EDIT_BUDGET_NSTT;
import static com.example.hoang.doantotnghiep.utils.Const.EDIT_BUDGET_PCS;
import static com.example.hoang.doantotnghiep.utils.Const.REQUEST_CODE_EDIT_BUDGET;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_EDIT_BUDGET_NSCB;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_EDIT_BUDGET_NSTT;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_EDIT_BUDGET_PCS;

/**
 * Created by TWO on 03/01/2018.
 */
public class EditBudgetActivity extends AppCompatActivity implements View.OnClickListener, RecyclerSetup1Adapter.removeBudgets {

    RecyclerView recycleSetup2NSTT;
    RecyclerView recycleSetup2NSCB;
    RecyclerView recycleSetup2PCS;
    ArrayList<ItemSetup1> listItemNSTT = new ArrayList<>();
    ArrayList<ItemSetup1> listItemNSCB = new ArrayList<>();
    ArrayList<ItemSetup1> listItemPCS = new ArrayList<>();
    RecyclerSetup1Adapter adapterNSTT;
    RecyclerSetup1Adapter adapterNSCB;
    RecyclerSetup1Adapter adapterPCS;
    boolean callApiSuccess = false;
    boolean isCall = false;

    private PieChart pieChart1, pieChart2, pieChart3;
    private float datachart1, datachart2, datachart3;
    private float total = (float) HomeActivity.totalRevenue;
    private double budgetmin = HomeActivity.budgetMin, budgetbase = HomeActivity.budgetBase, budgetlux = HomeActivity.budgetLuxury;
    private TextView txtvbudgetmin, txtvbudgetbase, txtvbudgetluxury, min_budget, base_budget, lux_budget, txtdescrip;

    ArrayList<ManagerRevenue.Budget_min> listNSTT;
    ArrayList<ManagerRevenue.Budget_base> listNSCB;
    ArrayList<ManagerRevenue.Budget_luxury> listPCS;

    Button btnNSTT;
    Button btnNSCB;
    Button btnPCS;

    Toolbar toolbar;
    TextView title;
    String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget);
        initData();
        initView();
        setupView();

    }

    public void initData() {
        Bundle bundle = getIntent().getBundleExtra("data");
        listNSTT = (ArrayList<ManagerRevenue.Budget_min>) bundle.getSerializable("nstt");
        listNSCB = (ArrayList<ManagerRevenue.Budget_base>) bundle.getSerializable("nscb");
        listPCS = (ArrayList<ManagerRevenue.Budget_luxury>) bundle.getSerializable("pcs");

        if (listNSTT != null) {
            for (int i = 0; i < listNSTT.size(); i++) {
                listItemNSTT.add(new ItemSetup1(listNSTT.get(i).getBudget_name(), listNSTT.get(i).getBudget_value(), listNSTT.get(i).getId()));
            }
        }

        if (listNSCB != null) {
            for (int i = 0; i < listNSCB.size(); i++) {
                listItemNSCB.add(new ItemSetup1(listNSCB.get(i).getBudget_name(), listNSCB.get(i).getBudget_value(), listNSCB.get(i).getId()));
            }
        }
        if (listPCS != null) {
            for (int i = 0; i < listPCS.size(); i++) {
                listItemPCS.add(new ItemSetup1(listPCS.get(i).getBudget_name(), listPCS.get(i).getBudget_value(), listPCS.get(i).getId()));
            }
        }

    }


    public void initView() {
        recycleSetup2NSTT = findViewById(R.id.recycle_setup2_nstt);
        recycleSetup2NSCB = findViewById(R.id.recycle_setup2_nscb);
        recycleSetup2PCS = findViewById(R.id.recycle_setup2_pcs);

        pieChart1 = (PieChart) findViewById(R.id.piechart1);
        pieChart2 = (PieChart) findViewById(R.id.piechart2);
        pieChart3 = (PieChart) findViewById(R.id.piechart3);

        btnNSTT = findViewById(R.id.btn_ns_tt);
        btnNSCB = findViewById(R.id.btn_ns_cb);
        btnPCS = findViewById(R.id.btn_pcs);
        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.toolbar_title);
        txtdescrip = findViewById(R.id.txt_descrip);
        txtdescrip.setText(getString(R.string.budget_tips));

        txtvbudgetbase = findViewById(R.id.txtv_budget_base);
        txtvbudgetluxury = findViewById(R.id.txtv_budgetluxury);
        txtvbudgetmin = findViewById(R.id.txtv_budgetmin);
        min_budget = findViewById(R.id.min_budget);
        base_budget = findViewById(R.id.base_budget);
        lux_budget = findViewById(R.id.lux_budget);

        txtvbudgetmin.setText(String.valueOf(DecimalFormatUtils.getMoney(budgetmin)));
        txtvbudgetluxury.setText(String.valueOf(DecimalFormatUtils.getMoney(budgetlux)));
        txtvbudgetbase.setText(String.valueOf(DecimalFormatUtils.getMoney(budgetbase)));
        txtvbudgetbase.setSelected(true);
        txtvbudgetluxury.setSelected(true);
        txtvbudgetmin.setSelected(true);
        min_budget.setSelected(true);
        base_budget.setSelected(true);
        lux_budget.setSelected(true);
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

    private void initPieChart(PieChart pieChart, float a, float b, String phanTram, String mau) {
        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(a));
        entries.add(new PieEntry(b));

        pieChart.getDescription().setEnabled(false);

        pieChart.setHoleRadius(70);

        Legend l = pieChart.getLegend();
        l.setEnabled(false);

        pieChart.setCenterText(phanTram);
        pieChart.setCenterTextSize(15);
        pieChart.setCenterTextColor(Color.parseColor("#637181"));
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

    public void setupView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());
        title.setText(getResources().getString(R.string.edit_budget));
        title.setGravity(Gravity.CENTER);

        btnPCS.setOnClickListener(this);
        btnNSCB.setOnClickListener(this);
        btnNSTT.setOnClickListener(this);

        //setutp NSTT
        adapterNSTT = new RecyclerSetup1Adapter(listItemNSTT, this, Const.EDIT_BUDGET_NSTT, this);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recycleSetup2NSTT.setLayoutManager(layoutManager1);
        recycleSetup2NSTT.setNestedScrollingEnabled(false);
        recycleSetup2NSTT.setAdapter(adapterNSTT);

        //setutp NSCB
        adapterNSCB = new RecyclerSetup1Adapter(listItemNSCB, this, Const.EDIT_BUDGET_NSCB, this);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recycleSetup2NSCB.setLayoutManager(layoutManager2);
        recycleSetup2NSCB.setNestedScrollingEnabled(false);
        recycleSetup2NSCB.setAdapter(adapterNSCB);
        //setutp PCS
        adapterPCS = new RecyclerSetup1Adapter(listItemPCS, this, Const.EDIT_BUDGET_PCS, this);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recycleSetup2PCS.setLayoutManager(layoutManager3);
        recycleSetup2PCS.setNestedScrollingEnabled(false);
        recycleSetup2PCS.setAdapter(adapterPCS);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ns_tt:
                Intent iNSTT = new Intent(this, Setup2AddActivity.class).putExtra(Const.ACTION_GUIDE, EDIT_BUDGET_NSTT);
                startActivityForResult(iNSTT, REQUEST_CODE_EDIT_BUDGET);
                getAllTextInput(EDIT_BUDGET_NSTT);

                break;
            case R.id.btn_ns_cb:
                Intent iNSCB = new Intent(this, Setup2AddActivity.class).putExtra(Const.ACTION_GUIDE, EDIT_BUDGET_NSCB);
                startActivityForResult(iNSCB, REQUEST_CODE_EDIT_BUDGET);
                getAllTextInput(EDIT_BUDGET_NSCB);

                break;
            case R.id.btn_pcs:
                Intent iPCS = new Intent(this, Setup2AddActivity.class).putExtra(Const.ACTION_GUIDE, EDIT_BUDGET_PCS);
                startActivityForResult(iPCS, REQUEST_CODE_EDIT_BUDGET);
                getAllTextInput(EDIT_BUDGET_PCS);
                break;
        }
    }


    private void getAllTextInput(String type) {
       // try {
            ArrayList<ItemSetup1> listTemp = new ArrayList<>();
            HashMap<String, String> hashMap = new HashMap<>();
            if (type == EDIT_BUDGET_NSTT) {
                listTemp = listItemNSTT;
                hashMap = adapterNSTT.getAllTextInput();
            } else if (type == EDIT_BUDGET_NSCB) {
                listTemp = listItemNSCB;
                hashMap = adapterNSCB.getAllTextInput();
            } else if (type == EDIT_BUDGET_PCS) {
                listTemp = listItemPCS;
                hashMap = adapterPCS.getAllTextInput();
            }

            for (int i = 0; i < listTemp.size(); i++) {
                if (hashMap.containsKey("name" + i)) {
                    listTemp.get(i).setName(hashMap.get("name" + i));
                }
                if (hashMap.containsKey("value" + i)) {
                    String value = hashMap.get("value" + i).trim();
                    if (value.equals(""))
                        value = "0";
                    listTemp.get(i).setValue(Double.parseDouble(value));
                }
            }

            if (type == EDIT_BUDGET_NSTT) {
                listItemNSTT = listTemp;
            } else if (type == EDIT_BUDGET_NSCB) {
                listItemNSCB = listTemp;
            } else if (type == EDIT_BUDGET_NSTT) {
                listItemPCS = listTemp;
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void addNewDataReceive(Intent data, int resultCode) {
        Bundle bundle = data.getBundleExtra("DATA");
        String name = bundle.get("title").toString();
        String v = bundle.get("value").toString();
        double value = Double.parseDouble(v);
        if (resultCode == RESULT_CODE_EDIT_BUDGET_NSTT) {
            listItemNSTT.add(new ItemSetup1(name, value));
            adapterNSTT.notifyDataSetChanged();
        } else if (resultCode == RESULT_CODE_EDIT_BUDGET_NSCB) {
            listItemNSCB.add(new ItemSetup1(name, value));
            adapterNSCB.notifyDataSetChanged();
        } else if (resultCode == RESULT_CODE_EDIT_BUDGET_PCS) {
            listItemPCS.add(new ItemSetup1(name, value));
            adapterPCS.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT_BUDGET)
            switch (resultCode) {
                case RESULT_CODE_EDIT_BUDGET_NSTT:
                    addNewDataReceive(data, resultCode);
                    break;
                case RESULT_CODE_EDIT_BUDGET_NSCB:
                    addNewDataReceive(data, resultCode);
                    break;

                case RESULT_CODE_EDIT_BUDGET_PCS:
                    addNewDataReceive(data, resultCode);
                    break;
            }
    }


    void getAllAddNew() {


        isCall = true;
        callApiSuccess = false;
        getAllTextInput(EDIT_BUDGET_NSTT);
        getAllTextInput(EDIT_BUDGET_NSCB);
        getAllTextInput(EDIT_BUDGET_PCS);
        final boolean[] editFinish = {false};
        final boolean[] addFinish = {false};

        final boolean[] editSuccess = {false};
        final boolean[] addSuccess = {false};

        ArrayList<BudgetRequest> newAdd = new ArrayList<>();
        ArrayList<EditBudgetRequest.EditItem> newEdit = new ArrayList<>();

        for (int i = 0; i < listItemNSTT.size(); i++) {
            ItemSetup1 itemSetup1 = listItemNSTT.get(i);
            if (itemSetup1.getId() == null)
                newAdd.add(new BudgetRequest(itemSetup1.getName(), itemSetup1.getValue(), "min"));
            else
                newEdit.add(new EditBudgetRequest.EditItem(itemSetup1.getId(), itemSetup1.getName(), itemSetup1.getValue()));
        }

        for (int i = 0; i < listItemNSCB.size(); i++) {
            ItemSetup1 itemSetup1 = listItemNSCB.get(i);
            if (itemSetup1.getId() == null)
                newAdd.add(new BudgetRequest(itemSetup1.getName(), itemSetup1.getValue(), "base"));
            else
                newEdit.add(new EditBudgetRequest.EditItem(itemSetup1.getId(), itemSetup1.getName(), itemSetup1.getValue()));
        }
        for (int i = 0; i < listItemPCS.size(); i++) {
            ItemSetup1 itemSetup1 = listItemPCS.get(i);
            if (itemSetup1.getId() == null)
                newAdd.add(new BudgetRequest(itemSetup1.getName(), itemSetup1.getValue(), "luxury"));
            else
                newEdit.add(new EditBudgetRequest.EditItem(itemSetup1.getId(), itemSetup1.getName(), itemSetup1.getValue()));
        }

        if (newEdit.size() > 0) {
            CallAPI.editListBudget(EditBudgetActivity.this,
                    "Bearer " + SharedPrefsUtils.getStringPreference(getApplicationContext(), "token"),
                    newEdit, new CallAPI.OnEdit() {
                        @Override
                        public void editSuccess(boolean onSuccess) {
                            editFinish[0] = true;
                            if (onSuccess)
                                editSuccess[0] = true;
                            if (addFinish[0] == true && editFinish[0] == true) {
                                callApiSuccess = true;
                                if (editSuccess[0] || addSuccess[0])
                                    setResult(1994);
                                else
                                    setResult(1996);
                                onBackPressed();
                                isCall = false;
                            }
                        }
                    });
        } else {
            editFinish[0] = true;
        }
        if (newAdd.size() > 0) {
            CallAPI.addListBudgets(EditBudgetActivity.this,
                    "Bearer " + SharedPrefsUtils.getStringPreference(getApplicationContext(), "token"),
                    new ListBudget(newAdd), new CallAPI.OnCallAddListBudgets() {
                        @Override
                        public void onResult(Boolean success) {
                            addFinish[0] = true;
                            if (success) {
                                addSuccess[0] = true;
                            }
                            if (addFinish[0] == true && editFinish[0] == true) {
                                callApiSuccess = true;
                                if (editSuccess[0] || addSuccess[0])
                                    setResult(1994);
                                else
                                    setResult(1996);
                                onBackPressed();
                                isCall = false;
                            }
                        }
                    });
        } else {
            addFinish[0] = true;
        }
        if (addFinish[0] == true && editFinish[0] == true) {
            callApiSuccess = true;
            setResult(1994);
            onBackPressed();
            isCall = false;
        }


    }

    @Override
    public void onBackPressed() {
        if (callApiSuccess) {
            super.onBackPressed();
        }
        if (!isCall) {
            getAllAddNew();
        }

    }

    @Override
    public void onRemove(String type) {
        getAllTextInput(type);
    }
}
