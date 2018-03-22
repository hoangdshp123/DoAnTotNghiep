package com.example.hoang.doantotnghiep.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ModelTarget.ModelTargetLoan;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.activitys.AddNewTargetActivity;
import com.example.hoang.doantotnghiep.utils.Const;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Admin on 2/9/2018.
 */

public class RecyclerTargetLoanAdapter extends RecyclerView.Adapter<RecyclerTargetLoanAdapter.ViewHolder> {

    Context context;
    ArrayList<ModelTargetLoan> targetLoanList;

    public RecyclerTargetLoanAdapter(Context context, ArrayList<ModelTargetLoan> targetLoanList) {
        this.context = context;
        this.targetLoanList = targetLoanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_targetloan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (targetLoanList != null && targetLoanList.size() > 0) {
            ModelTargetLoan target = targetLoanList.get(position);

            holder.nameTargetLoan.setText(target.getNameTargetLoan());
            holder.originmoney.setText(DecimalFormatUtils.getMoney(Double.parseDouble(target.getOriginmoney())));
            holder.originmoney.setSelected(true);
            holder.time.setText(target.getTime() + " năm");
            holder.ratebase.setText(target.getRatebase() + " %");
            holder.moneyPerMounth.setText(DecimalFormatUtils.getMoney(Double.parseDouble(target.getMoneyPerMounth())));
            holder.moneyPerMounth.setSelected(true);
            holder.totalmoneyTargetLoan.setText(DecimalFormatUtils.getMoney(Double.parseDouble(target.getTotalmoneyTargetLoan())));
            holder.totalmoneyTargetLoan.setSelected(true);

            String static_icon = target.getStatic_icon();
            if (static_icon == null)
                static_icon = "";
            if (static_icon.length() > 0) {
                int id = context.getResources().getIdentifier(static_icon, "drawable", context.getPackageName());
                holder.imgTargetLoan.setImageResource(id);
            } else
                Picasso.with(context).load(target.getImage()).into(holder.imgTargetLoan);
            holder.btnEditTargetLoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iedit = new Intent(context, AddNewTargetActivity.class);
                    iedit.putExtra("type1", "editloan");
                    iedit.putExtra("type", "");
                    iedit.putExtra("name_loan", target.getNameTargetLoan());
                    iedit.putExtra("preferentrate", target.getRatePreferent());
                    iedit.putExtra("preferenttime", target.getTimePreferent());
                    iedit.putExtra("originmoney", target.getOriginmoney());
                    iedit.putExtra("time", target.getTime());
                    iedit.putExtra("ratebase", target.getRatebase());
                    iedit.putExtra("moneyPerMounth", target.getMoneyPerMounth());
                    iedit.putExtra("totalmoneyTargetLoan", target.getTotalmoneyTargetLoan());
                    iedit.putExtra("interestPay", target.getTotalInterestLoan());
                    iedit.putExtra("imgLoan", target.getImage());
                    iedit.putExtra("loanid", target.getLoanid());
                    iedit.putExtra("tag", Const.TARGET_TYPE);
                    String static_icon = target.getStatic_icon();
                    if (static_icon == null)
                        static_icon = "";
                    iedit.putExtra("static_icon", static_icon);

                    ((Activity) context).startActivityForResult(iedit, 12134);
                }
            });

            holder.deleteLoans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Xóa mục tiêu")
                            .setMessage("Bạn muốn xóa muc tiêu này?")
                            .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    CallAPI.deleteLoans(context, target.getLoanid(), new CallAPI.OnDelete() {
                                        @Override
                                        public void deleteSuccess(boolean onSuccess) {
                                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                            targetLoanList.remove(position);
                                            notifyDataSetChanged();

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create()
                            .show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return targetLoanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTargetLoan;
        TextView originmoney;
        TextView time;
        TextView ratebase;
        TextView moneyPerMounth;
        TextView totalmoneyTargetLoan;
        ImageView imgTargetLoan;
        ImageView btnEditTargetLoan;
        ImageView deleteLoans;

        public ViewHolder(View view) {
            super(view);
            nameTargetLoan = view.findViewById(R.id.nameTargetLoan);
            originmoney = view.findViewById(R.id.priceLoan);
            time = view.findViewById(R.id.timeReLoan);
            ratebase = view.findViewById(R.id.txtv_rate_base);
            moneyPerMounth = view.findViewById(R.id.moneyTargetLoan);
            totalmoneyTargetLoan = view.findViewById(R.id.txtv_TotalTargetLoan);
            imgTargetLoan = view.findViewById(R.id.img_target_loan);
            btnEditTargetLoan = view.findViewById(R.id.editTargetLoan);
            deleteLoans = view.findViewById(R.id.deleteLoans);
        }
    }
}
