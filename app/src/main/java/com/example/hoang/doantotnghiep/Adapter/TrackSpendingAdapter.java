package com.example.hoang.doantotnghiep.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Model.ModelApi.ExpensesDate;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;

import java.util.ArrayList;

/**
 * Created by ADMIN on 12/26/2017.
 */

public class TrackSpendingAdapter extends BaseAdapter {

    private ArrayList<ExpensesDate.Data> homeList;
    private LayoutInflater inflater;
    private Context context;

    public TrackSpendingAdapter(ArrayList<ExpensesDate.Data> homeList, Context context) {
        this.homeList = homeList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return homeList.size();
    }

    @Override
    public Object getItem(int i) {
        return homeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if(view == null){
            view = inflater.inflate(R.layout.custom_lv, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.theoDoi = (TextView) view.findViewById(R.id.theoDoi);
            viewHolder.money = (TextView) view.findViewById(R.id.money);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        ExpensesDate.Data home = homeList.get(i);

        viewHolder.theoDoi.setText(home.expense_name);
        viewHolder.money.setText(DecimalFormatUtils.getMoney(Long.parseLong(home.expense_value)));
        viewHolder.money.setSelected(true);

        return view;
    }

    private class ViewHolder{
        TextView theoDoi;
        TextView money;
    }
}
