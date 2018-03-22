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

import com.example.hoang.doantotnghiep.Model.ModelApi.BorrowResponse;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.activitys.AddBorrowActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;



/**
 * Created by kien.lovan on 3/2/2018.
 */

public class RecycleBorrowAdapter extends RecyclerView.Adapter<RecycleBorrowAdapter.BorrowItem> {

    ArrayList<BorrowResponse.Data> listData = new ArrayList<>();
    Context context;

    public RecycleBorrowAdapter(ArrayList<BorrowResponse.Data> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public BorrowItem onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_borrow, parent, false);

        return new BorrowItem(view);
    }

    @Override
    public void onBindViewHolder(BorrowItem holder, final int position) {
        if(listData !=  null && listData.size() > 0){
            DecimalFormat formatter = new DecimalFormat("###,###,###,###,###");
            final DecimalFormat formatter1 = new DecimalFormat("###############");
            holder.txt_borrow_name.setText(listData.get(position).name_borrow);
            holder.txt_rate.setText(listData.get(position).rate_base+"%");
            holder.txt_money_borrow.setText(formatter.format(listData.get(position).money_borrow) + "vnđ");
            holder.txt_time_borrow.setText(listData.get(position).time_borrow+" tháng");
            holder.txt_pay_per_month.setText(formatter.format(listData.get(position).pay_per_month) + "vnđ");
            holder.editBorrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, AddBorrowActivity.class);
                    i.putExtra("TagBorrow", "2");
                    i.putExtra("MoneyBorrow",formatter1.format(listData.get(position).money_borrow));
                    i.putExtra("TimeBorrow",String.valueOf(listData.get(position).time_borrow));
                    i.putExtra("Income",formatter1.format(listData.get(position).money_icome));
                    i.putExtra("Office",listData.get(position).office);
                    i.putExtra("Electric",formatter1.format(listData.get(position).money_electric));
                    i.putExtra("Isurrance",formatter1.format(listData.get(position).money_isurrance));
                    i.putExtra("ReceiveMoney",listData.get(position).type_receive_money);
                    i.putExtra("Adress",listData.get(position).address);
                    i.putExtra("Email",listData.get(position).email);
                    i.putExtra("PhoneNumber",listData.get(position).number_phone);
                    i.putExtra("IdEdit",listData.get(position).id);
                    ((Activity) context).startActivityForResult(i, 2018);
                }
            });
            holder.deleteBorrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Xóa khoản vay")
                            .setMessage("Bạn muốn xóa muc tiêu này?")
                            .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    CallAPI.deleteBorrow(context, listData.get(position).id, new CallAPI.OnDelete() {
                                        @Override
                                        public void deleteSuccess(boolean onSuccess) {
                                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                            listData.remove(position);
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
        }


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class BorrowItem extends RecyclerView.ViewHolder {

        private TextView txt_borrow_name;
        private TextView txt_rate;
        private TextView txt_money_borrow;
        private TextView txt_time_borrow;
        private TextView txt_pay_per_month;
        private ImageView editBorrow, deleteBorrow;

        public BorrowItem(View itemView) {
            super(itemView);

            txt_borrow_name = itemView.findViewById(R.id.name_borrow);
            txt_rate = itemView.findViewById(R.id.txt_rate);
            txt_money_borrow = itemView.findViewById(R.id.txt_borrow_money);
            txt_time_borrow = itemView.findViewById(R.id.txt_time_borrow);
            txt_pay_per_month = itemView.findViewById(R.id.txt_pay_per_month);
            editBorrow = itemView.findViewById(R.id.editBorrow);
            deleteBorrow = itemView.findViewById(R.id.deleteBorrow);
        }
    }

}
