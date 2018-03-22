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

import com.example.hoang.doantotnghiep.Model.ModelHistory.ItemHistory;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.activitys.ManageRevenueActivity;
import com.example.hoang.doantotnghiep.View.dialog.DialogAddItem;
import com.example.hoang.doantotnghiep.utils.Const;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;

import java.util.ArrayList;


/**
 * Created by Admin on 1/19/2018.
 */

public class ManageRevenueAdapter extends RecyclerView.Adapter<ManageRevenueAdapter.ViewHolder> {
    ArrayList<ItemHistory> listItem;
    Context context;
    String type;

    public ManageRevenueAdapter(ArrayList<ItemHistory> listItem, Context context, String type) {
        this.listItem = listItem;
        this.context = context;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_revenue, parent, false);
        return new ViewHolder(view);
    }
    // TODO: 2/8/2018 xử lý reload dữ liệu khi back về ở manage revenue activity 

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (listItem != null && listItem.size() > 0) {
            switch (type) {
                case Const.MANAGE_REVENUE:
                    holder.txtTitle.setText(listItem.get(position).getName());
                    holder.txtValue.setText(
                            DecimalFormatUtils.getMoney(listItem.get(position).getValue()));
                  //  holder.edtNote.setText(listItem.get(position).getNote());
                    holder.imgIcon.setImageResource(listItem.get(position).getImage());
                    holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ManageRevenueActivity.isReloadWhenBack = true;
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("Xóa thu nhập")
                                    .setMessage("Bạn muốn xóa thu nhập này?")
                                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            CallAPI.deleteRevenua(context, listItem.get(position).getId(), new CallAPI.OnDelete() {
                                                @Override
                                                public void deleteSuccess(boolean onSuccess) {
                                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                                    listItem.remove(listItem.get(position));
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

                    holder.imgEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ManageRevenueActivity.isReloadWhenBack = true;

                            String id;
                            ItemHistory i = listItem.get(position);
                            Intent intent = new Intent(context, DialogAddItem.class);
                            intent.putExtra("id", listItem.get(position).getId());
                            id = intent.getStringExtra("id");
                            DialogAddItem dialogAddItem = new DialogAddItem((Activity) context, type, "EDIT",
                                    id, holder.txtTitle, holder.txtValue
                                    , new DialogAddItem.AddNewItemSuccess() {
                                @Override
                                public void onSuccess(String ID,String name, double value) {
                                    String valu = DecimalFormatUtils.getMone(value);
                                    listItem.set(position, new ItemHistory(name, Double.parseDouble(valu),
                                            listItem.get(position).getNote()
                                            ,listItem.get(position).getImage(),ID));
                                    notifyDataSetChanged();
                                }
                            });
                            dialogAddItem.setTextForEdittext(listItem.get(position).getName());
                            dialogAddItem.setEdtValue(listItem.get(position).getValue());
                            dialogAddItem.setId(listItem.get(position).getId());

                            dialogAddItem.showDiaglog();
                            notifyDataSetChanged();
                        }
                    });

                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtValue;
        private ImageView imgIcon;
        private ImageView imgDelete;
        private ImageView imgEdit;

        public ViewHolder(View view) {
            super(view);

            txtTitle = view.findViewById(R.id.tv_name_item_revenue);
            txtValue = view.findViewById(R.id.tv_value_item_revenue);
            imgIcon = view.findViewById(R.id.img_revenue);
            imgDelete = view.findViewById(R.id.delete_revenue);
            imgEdit = view.findViewById(R.id.edit_revenue);
        }
    }
}
