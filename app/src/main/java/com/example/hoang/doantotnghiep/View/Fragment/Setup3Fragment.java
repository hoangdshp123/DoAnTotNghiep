package com.example.hoang.doantotnghiep.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hoang.doantotnghiep.Adapter.Recycler_tools_adapter;
import com.example.hoang.doantotnghiep.Model.Item_Tools;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.View.CustomView.ItemOffsetDecoration;
import com.example.hoang.doantotnghiep.utils.Const;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Setup3Fragment extends Fragment {
    ArrayList<Item_Tools> listItem;
    Recycler_tools_adapter adapter;
    RecyclerView recyclerSetup3;

    public Setup3Fragment() {
        // Required empty public constructor
    }

    public static Setup3Fragment newInstance() {
        Setup3Fragment fragment = new Setup3Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup3, container, false);

        recyclerSetup3 = view.findViewById(R.id.recycle_setup3);
        addDataToList();
        adapter = new Recycler_tools_adapter(listItem, getActivity(), Const.TAG_SETUP3);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerSetup3.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.size_space_recycleview);
        recyclerSetup3.addItemDecoration(itemDecoration);
        recyclerSetup3.setNestedScrollingEnabled(false);
        recyclerSetup3.setAdapter(adapter);
        return view;
    }

    private void addDataToList() {
        listItem = new ArrayList<>();
        listItem.add(new Item_Tools(getString(R.string.setup3_index0), getResources().getDrawable(R.drawable.icon_house_bright),
                getResources().getResourceEntryName(R.drawable.icon_house_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index1), getResources().getDrawable(R.drawable.icon_car_bright),
                getResources().getResourceEntryName(R.drawable.icon_car_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index2), getResources().getDrawable(R.drawable.icon_shopping_bright),
                getResources().getResourceEntryName(R.drawable.icon_shopping_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index3), getResources().getDrawable(R.drawable.icon_travel_bright),
                getResources().getResourceEntryName(R.drawable.icon_travel_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index4), getResources().getDrawable(R.drawable.icon_repair_bright),
                getResources().getResourceEntryName(R.drawable.icon_repair_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index5), getResources().getDrawable(R.drawable.icon_help_bright),
                getResources().getResourceEntryName(R.drawable.icon_help_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index6), getResources().getDrawable(R.drawable.icon_pig_bright),
                getResources().getResourceEntryName(R.drawable.icon_pig_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index7), getResources().getDrawable(R.drawable.icon_loan_bright),
                getResources().getResourceEntryName(R.drawable.icon_loan_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index8), getResources().getDrawable(R.drawable.icon_plus_bright),
                getResources().getResourceEntryName(R.drawable.icon_plus_bright)));

    }
}
