package com.example.hoang.doantotnghiep.View.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.utils.Utils;

import java.util.Calendar;
import java.util.Date;



/**
 * Created by kien.lovan on 1/17/2018.
 */

public class SelectDateDialog implements View.OnClickListener {

    Selected selected;
    Activity activity;
    Dialog dialogDate;
    DatePicker datePicker1;
    DatePicker datePicker2;
    Button btn_from_date;
    Button btn_to_date;
    String key = "";


    public SelectDateDialog(Activity activity, Selected selected) {
        this.selected = selected;
        this.activity = activity;
        intiDialog(activity);
    }

    public SelectDateDialog(Activity activity, Selected selected, String key) {
        this.selected = selected;
        this.activity = activity;
        this.key = key;
        intiDialog(activity);
    }

    private void intiDialog(Activity activity) {
        dialogDate = new Dialog(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.select_date, null);
        btn_from_date = view.findViewById(R.id.btn_from_date);
        btn_to_date = view.findViewById(R.id.btn_to_date);
        datePicker1 = view.findViewById(R.id.date_picker1);
        datePicker2 = view.findViewById(R.id.date_picker2);
        if (key.equals("date")) {
            datePicker1.setMaxDate(Calendar.getInstance().getTimeInMillis());
            datePicker2.setMaxDate(Calendar.getInstance().getTimeInMillis());
        }
        Button submit = view.findViewById(R.id.submit);
        Button cancel = view.findViewById(R.id.cancel);


        btn_from_date.setOnClickListener(this);
        btn_to_date.setOnClickListener(this);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);

        dialogDate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDate.setContentView(view);


    }

    public void showDialog() {
        dialogDate.show();
    }


    private void getDatePicker() {

        int day = datePicker1.getDayOfMonth();
        int month = datePicker1.getMonth() + 1;
        int year = datePicker1.getYear();

        int day2 = datePicker2.getDayOfMonth();
        int month2 = datePicker2.getMonth() + 1;
        int year2 = datePicker2.getYear();

        Date sDate = getDateFromDatePicker(datePicker1);
        Date fDate = getDateFromDatePicker(datePicker2);
        if (Utils.convertDateToString(sDate).equals(Utils.convertDateToString(fDate)) || sDate.getTime() > fDate.getTime()) {
            Toast.makeText(activity, "Thời gian kết thúc phải lớn hơn thời gian bắt đầu!", Toast.LENGTH_SHORT).show();
        } else {
            if (year == year2) {
                selected.onSelect(Utils.convertDateToStringFormat(sDate, "yyyy/MM/dd"), Utils.convertDateToStringFormat(fDate, "yyyy/MM/dd"));
                dialogDate.dismiss();
            } else
                Toast.makeText(activity, "Vui lòng chọn ngày trong cùng một năm !", Toast.LENGTH_SHORT).show();
        }
    }

    public Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                getDatePicker();
                break;
            case R.id.cancel:
                dialogDate.dismiss();
            case R.id.btn_from_date:
                datePicker1.setVisibility(View.VISIBLE);
                datePicker2.setVisibility(View.GONE);
                btn_from_date.setBackgroundResource(R.drawable.background_blue_conor_button);
                btn_to_date.setBackgroundColor(activity.getResources().getColor(R.color.transparent));
                btn_from_date.setTextColor(activity.getResources().getColor(R.color.color_white));
                btn_to_date.setTextColor(activity.getResources().getColor(R.color.color_button_blue));
                break;
            case R.id.btn_to_date:
                datePicker1.setVisibility(View.GONE);
                datePicker2.setVisibility(View.VISIBLE);
                btn_to_date.setBackgroundResource(R.drawable.background_blue_conor_button);
                btn_from_date.setBackgroundColor(activity.getResources().getColor(R.color.transparent));
                btn_from_date.setTextColor(activity.getResources().getColor(R.color.color_button_blue));
                btn_to_date.setTextColor(activity.getResources().getColor(R.color.color_white));

                break;
            default:
                break;
        }

    }


    public interface Selected {
        void onSelect(String fromDate, String toDate);
    }
}

