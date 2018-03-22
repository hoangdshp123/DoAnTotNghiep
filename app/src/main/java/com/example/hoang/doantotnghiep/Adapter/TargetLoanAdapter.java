package com.example.hoang.doantotnghiep.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Model.ModelTarget.ModelTargetLoan;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.View.activitys.AddNewTargetActivity;
import com.example.hoang.doantotnghiep.utils.Const;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hoang on 1/29/2018.
 */

public class TargetLoanAdapter extends BaseAdapter {

    private ArrayList<ModelTargetLoan> targetLoanList;
    private LayoutInflater inflater;
    private Context context;

    public TargetLoanAdapter(ArrayList<ModelTargetLoan> targetLoanList, Context context) {
        this.targetLoanList = targetLoanList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return targetLoanList.size();
    }

    @Override
    public Object getItem(int position) {
        return targetLoanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.custom_list_targetloan, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.nameTargetLoan = (TextView) view.findViewById(R.id.nameTargetLoan);
            viewHolder.originmoney = (TextView) view.findViewById(R.id.priceLoan);
            viewHolder.time = (TextView) view.findViewById(R.id.timeReLoan);
            viewHolder.ratebase = (TextView) view.findViewById(R.id.txtv_rate_base);
            viewHolder.moneyPerMounth = (TextView) view.findViewById(R.id.moneyTargetLoan);
            viewHolder.totalmoneyTargetLoan = (TextView) view.findViewById(R.id.txtv_TotalTargetLoan);
            viewHolder.imgTargetLoan = (ImageView) view.findViewById(R.id.img_target_loan);
            viewHolder.btnEditTargetLoan = (ImageView) view.findViewById(R.id.editTargetLoan);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ModelTargetLoan target = targetLoanList.get(i);

        viewHolder.nameTargetLoan.setText(target.getNameTargetLoan());
        viewHolder.originmoney.setText(DecimalFormatUtils.getMoney(Double.parseDouble(target.getOriginmoney())));
        viewHolder.originmoney.setSelected(true);
        viewHolder.time.setText(target.getTime());
        viewHolder.ratebase.setText(target.getRatebase());
        viewHolder.moneyPerMounth.setText(DecimalFormatUtils.getMoney(Double.parseDouble(target.getMoneyPerMounth())));
        viewHolder.moneyPerMounth.setSelected(true);
        viewHolder.totalmoneyTargetLoan.setText(DecimalFormatUtils.getMoney(Double.parseDouble(target.getTotalmoneyTargetLoan())));
        viewHolder.totalmoneyTargetLoan.setSelected(true);
        String static_icon = target.getStatic_icon();
        if (static_icon == null)
            static_icon = "";
        if (static_icon.length() > 0) {
            int id = context.getResources().getIdentifier(static_icon, "drawable", context.getPackageName());
            viewHolder.imgTargetLoan.setImageResource(id);
        } else
            Picasso.with(context).load(target.getImage()).into(viewHolder.imgTargetLoan);
        viewHolder.btnEditTargetLoan.setOnClickListener(new View.OnClickListener() {
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
        return view;
    }


    private class ViewHolder {
        TextView nameTargetLoan;
        TextView originmoney;
        TextView time;
        TextView ratebase;
        TextView moneyPerMounth;
        TextView totalmoneyTargetLoan;
        ImageView imgTargetLoan;
        ImageView btnEditTargetLoan;
    }
}
