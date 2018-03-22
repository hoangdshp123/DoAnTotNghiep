package com.example.hoang.doantotnghiep.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Model.ModelApi.ModelManagerRevenue.ManagerRevenue;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by hoang on 1/10/2018.
 */

public class BudgetMinAdapter  extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ManagerRevenue.Budget_min> list;


    public BudgetMinAdapter(Context context, List<ManagerRevenue.Budget_min> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public List<ManagerRevenue.Budget_min> getList() {
        return list;
    }

    public void setList(List<ManagerRevenue.Budget_min> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BudgetMinAdapter.ViewHolder holder;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_budget, parent, false);
            holder = new BudgetMinAdapter.ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tv_name_ngan_sach);
            holder.tvValue = convertView.findViewById(R.id.tv_gia_tri_ngan_sach);
            holder.icon = convertView.findViewById(R.id.img_icon_budget);
            Log.d("nju", String.valueOf(list.size()));
            convertView.setTag(holder);
        } else {
            holder = (BudgetMinAdapter.ViewHolder) convertView.getTag();
        }

        ManagerRevenue.Budget_min nganSach = list.get(position);

        holder.tvName.setText(nganSach.budget_name);
        holder.tvValue.setText(DecimalFormatUtils.getMoney(nganSach.budget_value));
        holder.tvValue.setSelected(true);
        if(nganSach.budget_name.equals("Thuê nhà"))
            holder.icon.setImageResource(R.drawable.icon_house_bright);
        if(nganSach.budget_name.equals("Điện"))
            holder.icon.setImageResource(R.drawable.icon_electricity_bright);
        if(nganSach.budget_name.equals("Nước"))
            holder.icon.setImageResource(R.drawable.icon_water_bright);
        if(nganSach.budget_name.equals("Thực phẩm"))
            holder.icon.setImageResource(R.drawable.icon_food_bright);
        if(nganSach.budget_name.equals("TV, Internet"))
            holder.icon.setImageResource(R.drawable.icon_television_bright);
        if(nganSach.budget_name.equals("Điện thoại"))
            holder.icon.setImageResource(R.drawable.icon_phone_bright);
        if(nganSach.budget_name.equals("Xăng xe"))
            holder.icon.setImageResource(R.drawable.icon_gas_bright);
        if(nganSach.budget_name.equals("Trả nợ"))
            holder.icon.setImageResource(R.drawable.icon_loan_bright);
        if(nganSach.budget_name.equals("Ngân sách khác"))
            holder.icon.setImageResource(R.drawable.icon_plus_bright);
        return convertView;
    }


    private class ViewHolder{
        TextView tvName;
        TextView tvValue;
        CircleImageView icon;
    }
}
