package com.example.hoang.doantotnghiep.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Model.ModelHistory.History;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.utils.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by TWO on 12/27/2017.
 */

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<History> list;
    private ItemHistoryAdapter itemHistoryAdapter;
    private LayoutInflater inflater;

    public HistoryAdapter(Context context, ArrayList<History> list) {
        this.context = context;
        this.list = list;
        this.inflater = inflater.from(context);
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

    public class ViewHolder{
        public TextView day, month, tvDay;
        public ListView listView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        HistoryAdapter.ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new HistoryAdapter.ViewHolder();
            view = inflater.inflate(R.layout.item_list_history, viewGroup, false);
            viewHolder.day = (TextView) view.findViewById(R.id.day);
            viewHolder.month = (TextView) view.findViewById(R.id.month);
            viewHolder.listView = (ListView) view.findViewById(R.id.lv_item_history);
            viewHolder.tvDay = (TextView) view.findViewById(R.id.txtv_day_history);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (HistoryAdapter.ViewHolder) view.getTag();
        }


        History history = list.get(i);


        DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat outputFormat = new SimpleDateFormat("dd");
        DateFormat outputFormat2 = new SimpleDateFormat("MM yyyy");

        try {
            String f = Utils.convertUTCTimeToLocalTime2(history.getDate());
            Log.d("GETDATE", history.getDate());
            Date date1 = inputFormat.parse(f);
            Log.d("DATE1", String.valueOf(date1));
            viewHolder.day.setText(outputFormat.format(date1));
            Log.d("DAY", outputFormat.format(date1));
            viewHolder.month.setText("tháng " + outputFormat2.format(date1));
            Log.d("MONTH", outputFormat2.format(date1));


            //get current day
            Date currentDay = new Date();
            Log.d("CUREENTDAY", String.valueOf(currentDay));

            Log.d("GET DAY", String.valueOf(currentDay.getDay()));

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            int year1 = cal1.get(Calendar.YEAR);
            int month1 = cal1.get(Calendar.MONTH);
            int day1 = cal1.get(Calendar.DAY_OF_MONTH);

            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(currentDay);
            int year2 = cal2.get(Calendar.YEAR);
            int month2 = cal2.get(Calendar.MONTH);
            int day2 = cal2.get(Calendar.DAY_OF_MONTH);

            Log.d("DAY1", String.valueOf(day1));
            Log.d("DAY2", String.valueOf(day2));

            if (date1.before(currentDay)) {
                int count = Utils.countDay(date1, currentDay);
                if (count == 0) {
                    viewHolder.tvDay.setText("Hôm nay");
                } else if (count == 1) {
                    viewHolder.tvDay.setText("Hôm qua");
                } else if (count == 2) {
                    viewHolder.tvDay.setText("Hôm kia");
                }
                Log.d("COUNT", String.valueOf(count));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        ArrayList<ExpensesDate.Data> result = ExpensesDateUtils.getValueDate(list);
        itemHistoryAdapter = new ItemHistoryAdapter(context, list.get(i).getList());
        viewHolder.listView.setAdapter(itemHistoryAdapter);

        setListViewHeightBasedOnChildren(viewHolder.listView);
        notifyDataSetChanged();

        return view;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += (listItem.getMeasuredHeight() + 16);
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
