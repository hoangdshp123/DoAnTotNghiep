package com.example.hoang.doantotnghiep.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Model.ModelRevenue.Revenue;
import com.example.hoang.doantotnghiep.R;

import java.util.List;

/**
 * Created by TWO on 07/02/2018.
 */

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.ViewHolder> {
    private Context context;
    private List<Revenue> list;
    private String type;

    public RevenueAdapter(Context context, List<Revenue> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    public RevenueAdapter(Context context, List<Revenue> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_revenue, parent, false);
        return new RevenueAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvMonth.setText("THÁNG "+list.get(position).getMonth().substring(0,2));
        ManageRevenueAdapter adapter = new ManageRevenueAdapter(
                list.get(position).getList(),
                context,type);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setNestedScrollingEnabled(true);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setAdapter(adapter);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMonth;
        private RecyclerView recyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            tvMonth = (TextView) itemView.findViewById(R.id.tv_month_revenue);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.receycle_revenue);
        }
    }
}
