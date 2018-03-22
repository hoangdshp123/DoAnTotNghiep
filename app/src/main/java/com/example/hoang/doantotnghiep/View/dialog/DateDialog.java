package com.example.hoang.doantotnghiep.View.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;

import com.example.hoang.doantotnghiep.R;

import java.util.Calendar;

/**
 * Created by TWO on 12/28/2017.
 */

public class DateDialog extends Dialog implements View.OnClickListener {
    private DatePicker datePicker;
    private DateSelected dateSelected;
    private String key = "";

    public DateDialog(@NonNull Context context, DateSelected dateSelected) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_calendar);
        this.dateSelected = dateSelected;

        initView();
    }

    public DateDialog(@NonNull Context context, DateSelected dateSelected, String key) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_calendar);
        this.dateSelected = dateSelected;
        this.key = key;

        initView();
    }

    private void initView() {
        findViewById(R.id.btn_date_picker_exit).setOnClickListener(this);
        findViewById(R.id.btn_date_picker_ok).setOnClickListener(this);
        datePicker = (DatePicker) findViewById(R.id.date_picker);
        if (key.equals("history")){
            datePicker.setMaxDate(Calendar.getInstance().getTimeInMillis());
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_date_picker_exit:
                dismiss();
                break;
            case R.id.btn_date_picker_ok:
                String date_selectet;
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth()+1;
                int year = datePicker.getYear();
                date_selectet = year+"/"+month+"/"+day;
                dateSelected.onSelected(date_selectet);
                dismiss();
                break;
        }
    }

    public interface DateSelected {
        void onSelected(String date);
    }
}
