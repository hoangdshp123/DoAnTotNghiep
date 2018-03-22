package com.example.hoang.doantotnghiep.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Adapter.HistoryAdapter;
import com.example.hoang.doantotnghiep.Adapter.ItemHistoryAdapter;
import com.example.hoang.doantotnghiep.Model.ModelApi.ExpensesDate;
import com.example.hoang.doantotnghiep.Model.ModelHistory.History;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.activitys.PaymentActivity;
import com.example.hoang.doantotnghiep.View.dialog.SelectDateDialog;
import com.example.hoang.doantotnghiep.utils.Const;
import com.example.hoang.doantotnghiep.utils.ExpensesDateUtils;
import com.example.hoang.doantotnghiep.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TWO on 12/27/2017.
 */

public class HistoryFragment extends Fragment implements View.OnClickListener {
    private View view;
    private SearchView search;
    private ListView listView;
    private RecyclerView recyclerView;
    private ArrayList<ExpensesDate.Data> listHistory = new ArrayList<>();
    private HistoryAdapter adapter;
    private ItemHistoryAdapter itemHistoryAdapter;
    TextView txt_date, txt_day, txt_month;
    SelectDateDialog selectDateDialog;
    ArrayList<History> listArrayList;
    public static  boolean isReloadDataBack = false;
    public static  boolean isReloadDataCurrent = false;

    Date fDate,tDate;
    String dateISO_from;
    String dateUTC;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        dateISO_from = Const.DEFAULT_FROM_DATE1;
        String dateISO_to = Utils.getCurrentDate("yyyy/MM/dd");
        tDate = Utils.convertStringToDate(dateISO_to, "yyyy/MM/dd");
        dateUTC = Utils.convertLocalTimeToUTC(tDate);
//        initHistory(dateISO_from, dateUTC);
        String currentYear = Utils.getCurrentYear();
        initHistory(currentYear+"-01-01T17:00:00.000Z", dateUTC);
        initView();
        return view;
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }


    private void initHistory(String fromDate, String toDate) {

        CallAPI.getExpensesDate(getContext(), fromDate, toDate, 100, "date_time_DESC", new CallAPI.OnCallExpensesDate() {
            @Override
            public void onResult(ExpensesDate expensesDate) {
                if (expensesDate.code == 0) {

                } else if (expensesDate.code == 1) {
                    if (expensesDate.data != null) {
                        listHistory = (ArrayList<ExpensesDate.Data>) expensesDate.data;
                        ArrayList<ExpensesDate.Data> result1 = ExpensesDateUtils.getValueDate(listHistory);
                        listArrayList = new ArrayList<>();
                        History history;
                        for (int i = 0; i < result1.size(); i++) {
                            Log.d("joj", result1.get(i).getDate_time());

                            ArrayList<ExpensesDate.Data> result = ExpensesDateUtils.getDate(listHistory, result1.get(i).getDate_time());
                            history = new History(result1.get(i).getDate_time(), result);
                            listArrayList.add(history);
                        }
                        itemHistoryAdapter = new ItemHistoryAdapter(getContext(), listHistory);
                        adapter = new HistoryAdapter(getActivity(), listArrayList);
                        listView.setAdapter(adapter);
                    }
                }
            }
        });
    }

    private void initView() {

        view.findViewById(R.id.btn_add).setOnClickListener(this);
        view.findViewById(R.id.btn_selectDate).setOnClickListener(this);
        txt_date = view.findViewById(R.id.txt_date);
        txt_day = view.findViewById(R.id.day);
        txt_month = view.findViewById(R.id.month);
        search = view.findViewById(R.id.search_view);
        listView = (ListView) view.findViewById(R.id.lv_history);
        selectDateDialog = new SelectDateDialog(getActivity(), new SelectDateDialog.Selected() {
            @Override
            public void onSelect(String fromDate, String toDate) {

                txt_date.setText("Từ " + Utils.revertStringDate(fromDate) + " đến " + Utils.revertStringDate(toDate));

                String fDateString = Utils.revertStringDate(fromDate);
                fDate = Utils.convertStringToDate(fDateString, "dd/MM/yyyy");

                String tDateString = Utils.revertStringDate(toDate);
                tDate = Utils.convertStringToDate(tDateString, "dd/MM/yyyy");

                Log.d("TODATE", Utils.convertLocalTimeToUTC(tDate));
                Log.d("FROMDATE", Utils.convertLocalTimeToUTC(fDate));
                //initHistory(tDateString, fDateString);

                initHistory(Utils.convertLocalTimeToUTC(fDate), Utils.convertLocalTimeToUTC(tDate));
            }
        }, "date");

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (itemHistoryAdapter != null && listArrayList != null && listArrayList.size() > 0) {

                    String search = s.trim();
                    String search2 = search.toLowerCase();
//                    String search3 = search.toUpperCase();

                    ArrayList<History> tempHistory = new ArrayList<>();
                    for (int i = 0; i < listArrayList.size(); i++) {
                        History history = listArrayList.get(i);
                        ArrayList<ExpensesDate.Data> arrTemp = new ArrayList<>();
                        ArrayList<ExpensesDate.Data> arrChild = (ArrayList<ExpensesDate.Data>) history.getList();
                        for (int j = 0; j < arrChild.size(); j++) {
                            if ((arrChild.get(j).getExpense_name().toLowerCase().indexOf(search2)) > -1) {
                                arrTemp.add(arrChild.get(j));
                            }
                        }
                        if (arrTemp.size() > 0) {
                            tempHistory.add(new History(history.getDate(), arrTemp));
                        }
                    }
                    adapter = new HistoryAdapter(getActivity(), tempHistory);
                    listView.setAdapter(adapter);
                }
                return true;
            }
        });
    }

    @Override
    public void onResume() {
       //
        if(isReloadDataCurrent){
//            initHistory(dateISO_from, dateUTC);
            if(fDate == null)
            {
                String currentYear = Utils.getCurrentYear();
                initHistory(currentYear+"-01-01T17:00:00.000Z", Utils.convertLocalTimeToUTC(tDate));
            }
            else
            initHistory(Utils.convertLocalTimeToUTC(fDate), Utils.convertLocalTimeToUTC(tDate));
            isReloadDataCurrent = false;
        }
        super.onResume();


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                Intent intent = new Intent(getContext(), PaymentActivity.class);
                startActivityForResult(intent, 2223);
                break;
            case R.id.btn_selectDate:
                selectDateDialog.showDialog();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 2223 && resultCode == 1111)||((requestCode == 9087) && resultCode == 2023)) {
            isReloadDataBack = true;
            String currentYear = Utils.getCurrentYear();
            initHistory(currentYear+"-01-01T17:00:00.000Z", Utils.convertLocalTimeToUTC(tDate));
        }

    }
}