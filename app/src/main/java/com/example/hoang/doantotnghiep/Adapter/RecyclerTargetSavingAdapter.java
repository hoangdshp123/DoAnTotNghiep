package com.example.hoang.doantotnghiep.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ModelTarget.ModelTarget;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.activitys.AddNewTargetActivity;
import com.example.hoang.doantotnghiep.utils.Const;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;
import com.example.hoang.doantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Admin on 2/9/2018.
 */

public class RecyclerTargetSavingAdapter extends RecyclerView.Adapter<RecyclerTargetSavingAdapter.ViewHolder> {
    Context context;

    ArrayList<ModelTarget> targetSavingList;


    public RecyclerTargetSavingAdapter(Context context, ArrayList<ModelTarget> targetSavingList) {
        this.context = context;
        this.targetSavingList = targetSavingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_target, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (targetSavingList != null && targetSavingList.size() > 0) {
            ModelTarget target = targetSavingList.get(position);

            String static_icon = target.getStatic_icon();
            if (static_icon == null)
                static_icon = "";
            if (static_icon.length() > 0) {
                int id = context.getResources().getIdentifier(static_icon, "drawable", context.getPackageName());
                holder.imgTarget.setImageResource(id);
            } else
                Picasso.with(context).load(target.getImage()).into(holder.imgTarget);

            String current = Utils.getCurrentDate("dd/MM/yyyy");
            Date d2 = Utils.convertStringToDate(current, "dd/MM/yyyy");

            String deadline = target.getDeadLine();
            Date date_from = Utils.convertUTCTimeToLocalTime(deadline);
            Date d3 = new Date(date_from.getTime() - 1 * 24 * 3600000);

            if (d2.getTime() >= date_from.getTime()){
                holder.name1.setTextColor(Color.parseColor("#ff0000"));
                holder.name2.setTextColor(Color.parseColor("#ff0000"));
                holder.name3.setTextColor(Color.parseColor("#ff0000"));
                holder.nameTarget.setText(target.getTargetName());
                holder.nameTarget.setTextColor(Color.parseColor("#ff0000"));
                holder.price.setText(DecimalFormatUtils.getMoney(Double.parseDouble(target.getTargetValue())));
                holder.price.setTextColor(Color.parseColor("#ff0000"));
                holder.price.setSelected(true);
                holder.time.setText("Hết hạn");
                holder.time.setTextColor(Color.parseColor("#ff0000"));
                holder.moneyTarget.setText(DecimalFormatUtils.getMoney(Double.parseDouble(target.getPay_per_month())));
                holder.moneyTarget.setTextColor(Color.parseColor("#ff0000"));
                holder.moneyTarget.setSelected(true);
            }
            else {
                holder.nameTarget.setText(target.getTargetName());
                holder.price.setText(DecimalFormatUtils.getMoney(Double.parseDouble(target.getTargetValue())));
                holder.price.setSelected(true);
                holder.time.setText(target.getDay());
                holder.moneyTarget.setText(DecimalFormatUtils.getMoney(Double.parseDouble(target.getPay_per_month())));
                holder.moneyTarget.setSelected(true);
            }
            holder.btneditSaving.setOnClickListener(new View.OnClickListener() {
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

            holder.deleteSaving.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Xóa thu nhập")
                            .setMessage("Bạn muốn xóa thu nhập này?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    CallAPI.deleteSaving(context, target.getTargetSavingId(), new CallAPI.OnDelete() {
                                        @Override
                                        public void deleteSuccess(boolean onSuccess) {
                                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                            targetSavingList.remove(position);
                                            notifyDataSetChanged();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
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
        return targetSavingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTarget, btneditSaving, deleteSaving;
        TextView price;
        TextView nameTarget;
        TextView time;
        TextView moneyTarget;
        TextView name1, name2, name3;

        public ViewHolder(View view) {
            super(view);
            price = view.findViewById(R.id.price);
            moneyTarget = view.findViewById(R.id.moneyTarget);
            nameTarget = view.findViewById(R.id.nameTarget);
            time = view.findViewById(R.id.timeRe);
            imgTarget = view.findViewById(R.id.img_target);
            btneditSaving = view.findViewById(R.id.editTargetSaving);
            deleteSaving = view.findViewById(R.id.deleteSaving);
            name1 = view.findViewById(R.id.name1);
            name2 = view.findViewById(R.id.name2);
            name3 = view.findViewById(R.id.name3);
        }
    }
}
