package com.example.hoang.doantotnghiep.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ModelApi.ExpensesDate;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.Fragment.HistoryFragment;
import com.example.hoang.doantotnghiep.View.activitys.EditSpendActivity;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



/**
 * Created by TWO on 12/27/2017.
 */

public class ItemHistoryAdapter extends BaseAdapter {
    private Context context;
    private List<ExpensesDate.Data> list;
    private LayoutInflater inflater;
    private String token;

    public ItemHistoryAdapter(Context context, List<ExpensesDate.Data> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_item_history, viewGroup, false);
            holder = new ViewHolder();

            holder.tvName = (TextView) view.findViewById(R.id.tv_name_item_history);
            holder.tvValue = (TextView) view.findViewById(R.id.tv_value_item_history);
            holder.tvNote = (TextView) view.findViewById(R.id.tv_note_item_history);
            holder.imgHistory = (CircleImageView) view.findViewById(R.id.img_history);
            holder.edit = view.findViewById(R.id.editHistory);
            holder.delete = view.findViewById(R.id.deleteHistory);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ExpensesDate.Data itemHistory = list.get(i);

        holder.tvName.setText(itemHistory.expense_name);
        holder.tvValue.setText(DecimalFormatUtils.getMoney(Double.parseDouble(itemHistory.expense_value)));
        holder.tvNote.setText(itemHistory.note);
//        Picasso.with(context).load(itemHistory.getImage()).into(holder.imgHistory);
        holder.imgHistory.setImageResource(context.getResources().getIdentifier(itemHistory.getIcon(), "drawable", context.getPackageName()));
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditSpendActivity.class);
                intent.putExtra("name", itemHistory.expense_name);
                intent.putExtra("type", itemHistory.type);
                intent.putExtra("value", itemHistory.expense_value);
                intent.putExtra("date", itemHistory.date_time);
                intent.putExtra("note", itemHistory.note);
                intent.putExtra("image", itemHistory.getImage());
                intent.putExtra("id", itemHistory.id);
                intent.putExtra("iconEdit",itemHistory.getIcon());
                intent.putExtra("subTypeEdit",itemHistory.getSubType());
                ((Activity)context).startActivityForResult(intent,9087);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Xóa chi tiêu")
                        .setMessage("Bạn muốn xóa chi tiêu này?")
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                            token = "Bearer " + SharedPrefsUtils.getStringPreference(context,"token");
                                CallAPI.deleteExpenses(context, itemHistory.id, new CallAPI.OnDelete() {
                                    @Override
                                    public void deleteSuccess(boolean onSuccess) {
                                        HistoryFragment.isReloadDataBack = true;
                                        HistoryFragment.isReloadDataCurrent = true;
                                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                        list.remove(itemHistory);
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
        return view;
    }


    private class ViewHolder {
        TextView tvName;
        TextView tvValue;
        TextView tvNote;
        CircleImageView imgHistory;
        ImageView edit, delete;
    }
}
