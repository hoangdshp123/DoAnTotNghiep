package com.example.hoang.doantotnghiep.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Model.Costly;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoang on 1/23/2018.
 */

public class CostlyAdapter extends RecyclerView.Adapter<CostlyAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Costly> list;
    private ArrayList<String> listColor = new ArrayList<>();
    private ArrayList<Integer> listColor1 = new ArrayList<>();


    public CostlyAdapter(Context context, List<Costly> list) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        listColor.add("#ff0000");
        listColor.add("#c30821");
        listColor.add("#d92a27");
        listColor.add("#630a38");
        listColor.add("#ac1691");
        listColor.add("#e6910d");
        listColor.add("#fff90a");
        listColor.add("#1b75bc");
        listColor.add("#3b4b14");
        listColor.add("#efa6fe");

        listColor1.add(R.drawable.color3);
        listColor1.add(R.drawable.color1);
        listColor1.add(R.drawable.color2);
        listColor1.add(R.drawable.color4);
        listColor1.add(R.drawable.color5);
        listColor1.add(R.drawable.color6);
        listColor1.add(R.drawable.color7);
        listColor1.add(R.drawable.color8);
        listColor1.add(R.drawable.color9);
        listColor1.add(R.drawable.color10);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_lv_costly, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Costly costly = list.get(position);
//
        holder.tvName.setText(costly.getName());
        holder.tvValue.setText(DecimalFormatUtils.getMoney(Double.parseDouble(costly.getValue())));

        int percent = (int) (Float.parseFloat(list.get(position).getValue())/Float.parseFloat(list.get(0).getValue())*100);
        holder.progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#80DAEB"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        if(Build.VERSION.SDK_INT <= 21){
            holder.progressBar.setProgressDrawable(  context.getResources().getDrawable(listColor1.get(position)));
        }
        else
        holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor(listColor.get(position))));
        holder.progressBar.setMax(100);
        holder.progressBar.setProgress(percent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvValue;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txtv_expense_name);
            tvValue = itemView.findViewById(R.id.txtv_expense_value);
            progressBar = itemView.findViewById(R.id.process_bar_1);
        }
    }
}
