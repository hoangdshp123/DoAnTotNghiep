package com.example.hoang.doantotnghiep.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.R;

import java.util.ArrayList;

/**
 * Created by kien.lovan on 1/5/2018.
 */

public class RecycleSubMenuAdapter extends RecyclerView.Adapter<RecycleSubMenuAdapter.ItemSimple> {

    ArrayList<String> listTitle;
    Context context;
    String TAG;
    private Intent intent;

    public RecycleSubMenuAdapter(ArrayList<String> listTitle, Context context, String TAG) {
        this.listTitle = listTitle;
        this.context = context;
        this.TAG = TAG;
    }

    @Override
    public ItemSimple onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_sub_menu, parent, false);
        return new ItemSimple(v);
    }

    @Override
    public void onBindViewHolder(ItemSimple holder, int position) {
        if (listTitle != null && listTitle.size() > 0) {
            holder.txtTitle.setText(listTitle.get(position));
            holder.txt_number.setText(position + 1 + ".");
        }

    }

    @Override
    public int getItemCount() {
        return listTitle.size();
    }

    public class ItemSimple extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txt_number;

        public ItemSimple(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txt_number = itemView.findViewById(R.id.txt_number);

        }
    }
}
