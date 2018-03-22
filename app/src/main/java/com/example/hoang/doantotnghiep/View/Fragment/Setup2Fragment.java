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

import static com.example.hoang.doantotnghiep.utils.Const.NSCB;
import static com.example.hoang.doantotnghiep.utils.Const.NSTT;
import static com.example.hoang.doantotnghiep.utils.Const.PCS;
import static com.example.hoang.doantotnghiep.utils.Const.REQUEST_CODE_SETUP2;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_NSCB;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_NSTT;
import static com.example.hoang.doantotnghiep.utils.Const.RESULT_CODE_PCS;

/**
 * A simple {@link Fragment} subclass.
 */
public class Setup2Fragment extends Fragment implements View.OnClickListener, RecyclerSetup1Adapter.removeBudgets {

    RecyclerView recycleSetup2NSTT;
    RecyclerView recycleSetup2NSCB;
    RecyclerView recycleSetup2PCS;
    ArrayList<ItemSetup1> listItemNSTT = new ArrayList<>();
    ArrayList<ItemSetup1> listItemNSCB = new ArrayList<>();
    ArrayList<ItemSetup1> listItemPCS = new ArrayList<>();
    RecyclerSetup1Adapter adapterNSTT;
    RecyclerSetup1Adapter adapterNSCB;
    RecyclerSetup1Adapter adapterPCS;

    Button btnNSTT;
    Button btnNSCB;
    Button btnPCS;

    public Setup2Fragment() {
        // Required empty public constructor
    }

    public ArrayList<ItemSetup1> getListItemNSTT() {
        getAllTextInput(NSTT);
        return listItemNSTT;
    }

    public ArrayList<ItemSetup1> getListItemNSCB() {
        getAllTextInput(NSCB);

        return listItemNSCB;
    }

    public ArrayList<ItemSetup1> getListItemPCS() {
        getAllTextInput(PCS);
        return listItemPCS;
    }

    public static Setup2Fragment newInstance() {
        Setup2Fragment setup2Fragment = new Setup2Fragment();
        return setup2Fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup2, container, false);

        recycleSetup2NSTT = view.findViewById(R.id.recycle_setup2_nstt);
        recycleSetup2NSCB = view.findViewById(R.id.recycle_setup2_nscb);
        recycleSetup2PCS = view.findViewById(R.id.recycle_setup2_pcs);


        btnNSTT = view.findViewById(R.id.btn_ns_tt);
        btnNSCB = view.findViewById(R.id.btn_ns_cb);
        btnPCS = view.findViewById(R.id.btn_pcs);
        btnPCS.setOnClickListener(this);
        btnNSCB.setOnClickListener(this);
        btnNSTT.setOnClickListener(this);

        //setutp NSTT
        adapterNSTT = new RecyclerSetup1Adapter(listItemNSTT, getActivity(), Const.REVENUE, this);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recycleSetup2NSTT.setLayoutManager(layoutManager1);
        recycleSetup2NSTT.setNestedScrollingEnabled(false);
        recycleSetup2NSTT.setAdapter(adapterNSTT);
        //setutp NSCB
        adapterNSCB = new RecyclerSetup1Adapter(listItemNSCB, getActivity(), Const.REVENUE, this);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recycleSetup2NSCB.setLayoutManager(layoutManager2);
        recycleSetup2NSCB.setNestedScrollingEnabled(false);
        recycleSetup2NSCB.setAdapter(adapterNSCB);
        //setutp PCS
        adapterPCS = new RecyclerSetup1Adapter(listItemPCS, getActivity(), Const.REVENUE, this);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recycleSetup2PCS.setLayoutManager(layoutManager3);
        recycleSetup2PCS.setNestedScrollingEnabled(false);
        recycleSetup2PCS.setAdapter(adapterPCS);


        return view;
    }

    private void getAllTextInput(String type) {
        ArrayList<ItemSetup1> listTemp = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        if (type == NSTT) {
            listTemp = listItemNSTT;
            hashMap = adapterNSTT.getAllTextInput();
        } else if (type == NSCB) {
            listTemp = listItemNSCB;
            hashMap = adapterNSCB.getAllTextInput();
        } else if (type == PCS) {
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

        if (type == NSTT) {
            listItemNSTT = listTemp;
        } else if (type == NSCB) {
            listItemNSCB = listTemp;
        } else if (type == PCS) {
            listItemPCS = listTemp;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ns_tt:
                getAllTextInput(NSTT);
                Intent iNSTT = new Intent(getActivity(), Setup2AddActivity.class).putExtra(Const.ACTION_GUIDE, NSTT);
                startActivityForResult(iNSTT, REQUEST_CODE_SETUP2);
                break;
            case R.id.btn_ns_cb:
                getAllTextInput(NSCB);
                Intent iNSCB = new Intent(getActivity(), Setup2AddActivity.class).putExtra(Const.ACTION_GUIDE, NSCB);
                startActivityForResult(iNSCB, REQUEST_CODE_SETUP2);
                break;
            case R.id.btn_pcs:
                getAllTextInput(PCS);
                Intent iPCS = new Intent(getActivity(), Setup2AddActivity.class).putExtra(Const.ACTION_GUIDE, PCS);
                startActivityForResult(iPCS, REQUEST_CODE_SETUP2);
                break;
        }
    }


    public void addNewDataReceive(Intent data, int resultCode) {
        Bundle bundle = data.getBundleExtra("DATA");
        String name = bundle.get("title").toString();
        String v = bundle.get("value").toString();
        double value = Double.parseDouble(v);
        if (resultCode == RESULT_CODE_NSTT) {
            listItemNSTT.add(new ItemSetup1(name, value));
            adapterNSTT.notifyDataSetChanged();
        } else if (resultCode == RESULT_CODE_NSCB) {
            listItemNSCB.add(new ItemSetup1(name, value));
            adapterNSCB.notifyDataSetChanged();
        } else if (resultCode == RESULT_CODE_PCS) {
            listItemPCS.add(new ItemSetup1(name, value));
            adapterPCS.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SETUP2)
            switch (resultCode) {
                case RESULT_CODE_NSTT:
                    addNewDataReceive(data, resultCode);
                    break;
                case RESULT_CODE_NSCB:
                    addNewDataReceive(data, resultCode);
                    break;

                case RESULT_CODE_PCS:
                    addNewDataReceive(data, resultCode);
                    break;


            }
    }

    @Override
    public void onRemove(String type) {
        getAllTextInput(type);
    }
}
