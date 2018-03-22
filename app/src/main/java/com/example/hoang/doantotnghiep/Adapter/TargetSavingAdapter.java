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

import com.example.hoang.doantotnghiep.Model.ModelTarget.ModelTarget;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.View.activitys.AddNewTargetActivity;
import com.example.hoang.doantotnghiep.utils.Const;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;
import com.example.hoang.doantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



/**
 * Created by ADMIN on 12/26/2017.
 */

public class TargetSavingAdapter extends BaseAdapter {

    private ArrayList<ModelTarget> targetList;
    private LayoutInflater inflater;
    private Context context;

    public TargetSavingAdapter(ArrayList<ModelTarget> targetList, Context context) {
        this.targetList = targetList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return targetList.size();
    }

    @Override
    public Object getItem(int i) {
        return targetList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.custom_list_target, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.price = (TextView) view.findViewById(R.id.price);
            viewHolder.moneyTarget = (TextView) view.findViewById(R.id.moneyTarget);
            viewHolder.nameTarget = (TextView) view.findViewById(R.id.nameTarget);
            viewHolder.time = (TextView) view.findViewById(R.id.timeRe);
            viewHolder.imgTarget = (ImageView) view.findViewById(R.id.img_target);
            viewHolder.btneditSaving = view.findViewById(R.id.editTargetSaving);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ModelTarget target = targetList.get(i);
        String static_icon = target.getStatic_icon();
        if (static_icon == null)
            static_icon = "";
        if (static_icon.length() > 0) {
            int id = context.getResources().getIdentifier(static_icon, "drawable", context.getPackageName());
            viewHolder.imgTarget.setImageResource(id);
        } else
            Picasso.with(context).load(target.getImage()).into(viewHolder.imgTarget);
        viewHolder.nameTarget.setText(target.getTargetName());
        viewHolder.price.setText(DecimalFormatUtils.getMoney(Double.parseDouble(target.getTargetValue())));
        viewHolder.price.setSelected(true);
        viewHolder.time.setText(target.getDay());
        viewHolder.moneyTarget.setText(DecimalFormatUtils.getMoney(Double.parseDouble(target.getPay_per_month())));
        viewHolder.moneyTarget.setSelected(true);
        viewHolder.btneditSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iedit = new Intent(context, AddNewTargetActivity.class);
                iedit.putExtra("type1", "editsaving");
                iedit.putExtra("type", "");
                iedit.putExtra("name_saving1", target.getTargetName());
                iedit.putExtra("value", target.getTargetValue());
                iedit.putExtra("deadline", Utils.convertUTCTimeToLocalTime2(target.getDeadLine()));
                iedit.putExtra("icon", target.getImage());
                iedit.putExtra("idsaving", target.getTargetSavingId());
                String static_icon = target.getStatic_icon();
                iedit.putExtra("tag", Const.TARGET_TYPE);
                if (static_icon == null)
                    static_icon = "";
                iedit.putExtra("static_icon", static_icon);
                ((Activity) context).startActivityForResult(iedit, 12133);
            }
        });

        return view;
    }

    private class ViewHolder {
        ImageView imgTarget, btneditSaving;
        TextView price;
        TextView nameTarget;
        TextView time;
        TextView moneyTarget;
    }
}
