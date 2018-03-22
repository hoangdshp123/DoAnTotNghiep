package com.example.hoang.doantotnghiep.Adapter;

import android.content.Context;
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
 * Created by TWO on 12/26/2017.
 */

public class BudgetLuxuryAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private List<ManagerRevenue.Budget_luxury> list;

    public BudgetLuxuryAdapter(Context context, List<ManagerRevenue.Budget_luxury> list) {
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
        if (view==null){
            view = inflater.inflate(R.layout.item_budget, viewGroup, false);
            holder = new ViewHolder();

            holder.tvName = view.findViewById(R.id.tv_name_ngan_sach);
            holder.tvValue = view.findViewById(R.id.tv_gia_tri_ngan_sach);
            holder.icon = view.findViewById(R.id.img_icon_budget);
            holder.tvValue.setSelected(true);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ManagerRevenue.Budget_luxury nganSach = list.get(i);

        holder.tvName.setText(nganSach.budget_name);
        holder.tvValue.setText(DecimalFormatUtils.getMoney(nganSach.budget_value));
        if(nganSach.budget_name.equals("Cafe"))
            holder.icon.setImageResource(R.drawable.icon_coffee_bright);
        if(nganSach.budget_name.equals("Xem phim"))
            holder.icon.setImageResource(R.drawable.icon_film_bright);
        if(nganSach.budget_name.equals("Nhà hàng"))
            holder.icon.setImageResource(R.drawable.icon_restaurant_bright);
        if(nganSach.budget_name.equals("Đi du lịch"))
            holder.icon.setImageResource(R.drawable.icon_travel_bright);
        if(nganSach.budget_name.equals("Từ thiện"))
            holder.icon.setImageResource(R.drawable.icon_care_bright);
        if(nganSach.budget_name.equals("Sưu tập"))
            holder.icon.setImageResource(R.drawable.icon_collect_bright);
        if(nganSach.budget_name.equals("Bảo hiểm"))
            holder.icon.setImageResource(R.drawable.icon_insurance_bright);
        if(nganSach.budget_name.equals("Trang trí"))
            holder.icon.setImageResource(R.drawable.icon_decoration_bright);
        if(nganSach.budget_name.equals("Ngân sách khác"))
            holder.icon.setImageResource(R.drawable.icon_plus_bright);
        return view;
    }

    private class ViewHolder{
        TextView tvName;
        TextView tvValue;
        CircleImageView icon;
    }
}
