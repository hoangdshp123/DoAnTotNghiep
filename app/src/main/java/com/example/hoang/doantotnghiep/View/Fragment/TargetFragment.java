package com.example.hoang.doantotnghiep.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hoang.doantotnghiep.Adapter.RecyclerTargetLoanAdapter;
import com.example.hoang.doantotnghiep.Adapter.RecyclerTargetSavingAdapter;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelTargetLoans.TargetLoanMessage;
import com.example.hoang.doantotnghiep.Model.ModelApi.ModelTargetSaving.TargetSavingMessage;
import com.example.hoang.doantotnghiep.Model.ModelTarget.ModelTarget;
import com.example.hoang.doantotnghiep.Model.ModelTarget.ModelTargetLoan;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.activitys.SelectTargetActivity;
import com.example.hoang.doantotnghiep.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TargetFragment extends Fragment {

    private View view;
    private Button addTarget;
    private RecyclerView recyclerTargetLoan, recyclerTargetSaving;
    private ArrayList<ModelTarget> listSaving;
    private ArrayList<ModelTargetLoan> listLoan;
    private RecyclerTargetLoanAdapter recyclerTargetLoanAdapter;
    private RecyclerTargetSavingAdapter recyclerTargetSavingAdapter;

    public static TargetFragment newInstance() {
        TargetFragment fragment = new TargetFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_target, container, false);
        initView();
        ListViewTarget();
        return view;
    }

    private void initView() {
        addTarget = view.findViewById(R.id.btn_addTarget);
        recyclerTargetLoan = view.findViewById(R.id.recycler_target_loan);
        recyclerTargetSaving = view.findViewById(R.id.recycler_target_saving);

        view.findViewById(R.id.btn_addTarget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showOptionAddNewTarget();
                Intent intent = new Intent(getActivity(), SelectTargetActivity.class);
                startActivityForResult(intent, 0231);
            }
        });

    }

    public void updateData(Boolean isUpdate){
        if (isUpdate){
            ListViewTarget();
        }
    }

    private void ListViewTarget() {

        //TargetSaving
        CallAPI.getTarget_Saving(getContext(), new CallAPI.OnCallTarrget_Saving() {
            @Override
            public void onResult(TargetSavingMessage targetSavingMessage) {
                if (targetSavingMessage != null && targetSavingMessage.getCode() == 1) {
                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat outputFormat = new SimpleDateFormat("dd/MM/yy");

                    listSaving = new ArrayList<>();
                    String dateISO_to = Utils.getCurrentDate("dd/MM/yyyy");
                    String name, day, value, imgTarget, savingpermounth, deadLine, static_icon;

                    for (int i = 0; i < targetSavingMessage.getTargetSavings().size(); i++) {
                        name = targetSavingMessage.getTargetSavings().get(i).getTargetName();
                        value = targetSavingMessage.getTargetSavings().get(i).getTargetValue();
                        imgTarget = targetSavingMessage.getTargetSavings().get(i).getIcon();
                        static_icon = targetSavingMessage.getTargetSavings().get(i).getStatic_icon();
                        String deadline = targetSavingMessage.getTargetSavings().get(i).getDeadline();
                        String d2 = Utils.convertUTCTimeToLocalTime2(deadline);

                        day = String.valueOf(Utils.getCountOfDays(dateISO_to, d2)) + " Ngày";
                        deadLine = targetSavingMessage.getTargetSavings().get(i).getDeadline();
                        savingpermounth = targetSavingMessage.getTargetSavings().get(i).getSavingPerMonth();
                        listSaving.add(new ModelTarget(name, value, savingpermounth, imgTarget, day, deadLine, targetSavingMessage.getTargetSavings().get(i).getId(), static_icon));
                        recyclerTargetSavingAdapter = new RecyclerTargetSavingAdapter(getContext(), listSaving);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                                LinearLayoutManager.VERTICAL, false);
                        recyclerTargetSaving.setLayoutManager(layoutManager);
                        recyclerTargetSaving.setNestedScrollingEnabled(false);
                        recyclerTargetSaving.setAdapter(recyclerTargetSavingAdapter);
                    }
                }
            }
        });

        //TargetLoan

        CallAPI.getTarget_Loan(getContext(), new CallAPI.OnCallTarrget_Loan() {
            @Override
            public void onResult(TargetLoanMessage targetLoanMessage) {
                if (targetLoanMessage != null && targetLoanMessage.getCode() == 1) {
                    listLoan = new ArrayList<>();
                    String name, origin, time, rate, perMounth, total, image, timePrefer, ratePrefer, interestPay, loanid, static_icon;
                    for (int i = 0; i < targetLoanMessage.getTargetLoans().size(); i++) {
                        name = targetLoanMessage.getTargetLoans().get(i).getLoanName();
                        origin = String.valueOf(targetLoanMessage.getTargetLoans().get(i).getOriginMoney());
                        time = String.valueOf(targetLoanMessage.getTargetLoans().get(i).getLoanTime());
                        rate = String.valueOf(targetLoanMessage.getTargetLoans().get(i).getRateBase());
                        perMounth = String.valueOf(targetLoanMessage.getTargetLoans().get(i).getPayPerMonth());
                        total = String.valueOf(targetLoanMessage.getTargetLoans().get(i).getTotalMoneyPay());
                        image = targetLoanMessage.getTargetLoans().get(i).getIcon();
                        timePrefer = String.valueOf(targetLoanMessage.getTargetLoans().get(i).getPreferentialTime());
                        ratePrefer = String.valueOf(targetLoanMessage.getTargetLoans().get(i).getPreferentialRate());
                        interestPay = String.valueOf(targetLoanMessage.getTargetLoans().get(i).getTotalInterestPay());
                        loanid = targetLoanMessage.getTargetLoans().get(i).getId();

                        static_icon = targetLoanMessage.getTargetLoans().get(i).getStatic_icon();
                        listLoan.add(new ModelTargetLoan(name, origin, timePrefer, ratePrefer, time, rate, perMounth, total, interestPay, image, loanid, static_icon));
                        recyclerTargetLoanAdapter = new RecyclerTargetLoanAdapter(getContext(), listLoan);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                                LinearLayoutManager.VERTICAL, false);
                        recyclerTargetLoan.setLayoutManager(layoutManager);
                        recyclerTargetLoan.setNestedScrollingEnabled(false);
                        recyclerTargetLoan.setAdapter(recyclerTargetLoanAdapter);
                    }
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("hoas", String.valueOf(resultCode));
        if (resultCode == 0231 || resultCode == 2221) {
            ListViewTarget();
        } else if (resultCode == 1113) {
            ListViewTarget();
        } else if (resultCode == 1114) {
            ListViewTarget();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ListViewTarget();
    }



}
