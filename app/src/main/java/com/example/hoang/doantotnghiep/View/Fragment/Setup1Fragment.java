package com.example.hoang.doantotnghiep.View.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hoang.doantotnghiep.Adapter.RecyclerSetup1Adapter;
import com.example.hoang.doantotnghiep.Model.ItemSetup1;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.View.activitys.Setup2AddActivity;
import com.example.hoang.doantotnghiep.utils.Const;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.hoang.doantotnghiep.utils.Const.CODE_REVENUE;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_REVENUE;
import static com.example.hoang.doantotnghiep.utils.Const.REVENUE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Setup1Fragment extends Fragment {
    Button btnSetUpAdd;
    RecyclerView recycleSetup1;
    ArrayList<ItemSetup1> listRevenue = new ArrayList<>();
    RecyclerSetup1Adapter adapter;

    public ArrayList<ItemSetup1> getListRevenue() {
        getAllTextInput();
        return listRevenue;
    }

    public Setup1Fragment() {
        // Required empty public constructor
    }

    public static Setup1Fragment newInstance() {
        Setup1Fragment setup1Fragment = new Setup1Fragment();
        return setup1Fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setup1, container, false);
        recycleSetup1 = view.findViewById(R.id.recycle_setup1);

        adapter = new RecyclerSetup1Adapter(listRevenue, getActivity(), Const.REVENUE, new RecyclerSetup1Adapter.removeBudgets() {
            @Override
            public void onRemove(String type) {
                getAllTextInput();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recycleSetup1.setLayoutManager(layoutManager);
        recycleSetup1.setNestedScrollingEnabled(false);
        recycleSetup1.setAdapter(adapter);

        btnSetUpAdd = view.findViewById(R.id.btn_setup1_add);
        btnSetUpAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllTextInput();
                Intent iNSTT = new Intent(getActivity(), Setup2AddActivity.class).putExtra(Const.ACTION_GUIDE, REVENUE);
                startActivityForResult(iNSTT, CODE_REVENUE);

            }
        });
        return view;
    }

    private void getAllTextInput() {
        HashMap<String, String> hashMap = adapter.getAllTextInput();
        for (int i = 0; i < listRevenue.size(); i++) {
            if (hashMap.containsKey("name" + i)) {
                listRevenue.get(i).setName(hashMap.get("name" + i));
            }
            if (hashMap.containsKey("value" + i)) {
                String value = hashMap.get("value" + i).trim();
                if (value.equals("")) {
                    value = "";
                }
                listRevenue.get(i).setValue(Double.parseDouble(value));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REVENUE) {
            switch (resultCode) {
                case RESULT_CODE_REVENUE: {
                    Bundle bundle = data.getBundleExtra("DATA");
                    String name = bundle.get("title").toString();
                    double value = Double.parseDouble(bundle.get("value").toString());
                    String staticIcon = bundle.get("staticIconRevenue").toString();
                    listRevenue.add(new ItemSetup1(name, value,"", staticIcon));
//                    adapter = new RecyclerSetup1Adapter(listRevenue,getActivity(), Const.TAG_SETUP1);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }


}
