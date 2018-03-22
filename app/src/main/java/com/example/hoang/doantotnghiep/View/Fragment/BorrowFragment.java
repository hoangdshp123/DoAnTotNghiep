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
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Adapter.RecycleBorrowAdapter;
import com.example.hoang.doantotnghiep.Model.ModelApi.BorrowResponse;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.ApiUtils;
import com.example.hoang.doantotnghiep.View.activitys.AddBorrowActivity;
import com.example.hoang.doantotnghiep.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BorrowFragment extends Fragment implements View.OnClickListener {


    ArrayList<BorrowResponse.Data> listBorrow = new ArrayList<>();
    RecyclerView recyclerView;
    RecycleBorrowAdapter borrowAdapter;

    Button btn_addBorrow;

    public BorrowFragment() {
        // Required empty public constructor
    }

    public static BorrowFragment newInstance() {
        BorrowFragment fragment = new BorrowFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_borrow, container, false);
        btn_addBorrow = v.findViewById(R.id.btn_addBorrow);
        btn_addBorrow.setOnClickListener(this);
        recyclerView = v.findViewById(R.id.recycle_borrow);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        getListBorrow();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getListBorrow();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addBorrow:
                Intent i = new Intent(getActivity(), AddBorrowActivity.class);
                i.putExtra("TagBorrow", "1");
                startActivityForResult(i, 2004);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2004 && resultCode == 200) {
            //reload data
        }

    }

    public void getListBorrow() {
        Utils.showProgressDialog(getActivity(), "");
        ApiUtils.getApiService().getBorrow().enqueue(new Callback<BorrowResponse>() {
            @Override
            public void onResponse(Call<BorrowResponse> call, Response<BorrowResponse> response) {

                if (response.body().code == 0) {
                    Toast.makeText(getActivity(), "Lấy dữ liệu vay không thành công", Toast.LENGTH_SHORT).show();
                } else if (response.body().code == 1) {

                    listBorrow = (ArrayList<BorrowResponse.Data>) response.body().data;
                    borrowAdapter = new RecycleBorrowAdapter(listBorrow, getActivity());
                    recyclerView.setAdapter(borrowAdapter);
                }

            }

            @Override
            public void onFailure(Call<BorrowResponse> call, Throwable t) {
                Utils.dismissCurrentDialog();
                Toast.makeText(getActivity(), getActivity().getString(R.string.nettworkError), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
