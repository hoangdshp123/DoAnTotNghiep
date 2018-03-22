package com.example.hoang.doantotnghiep.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Model.ModelBudget.BudgetRequest;
import com.example.hoang.doantotnghiep.R;

import java.util.List;

/**
 * Created by TWO on 1/1/2018.
 */

public class EditBudgetAdapter extends  RecyclerView.Adapter<EditBudgetAdapter.ViewHolder>{
    private Context context;
    private List<BudgetRequest> list;
    private LayoutInflater inflater;

    public EditBudgetAdapter(Context context, List<BudgetRequest> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_edit_budget, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        BudgetRequest budgetRequest = list.get(position);

        holder.tvName.setText(budgetRequest.getBudget_name());
        holder.tvValue.setText(String.valueOf(budgetRequest.getBudget_value()));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvValue;

        public ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.btn_delete_edit_budget);
            tvName = v.findViewById(R.id.tv_name_edit_budget);
            tvValue = v.findViewById(R.id.tv_value_edit_budget);
        }
    }
}
