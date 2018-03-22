package com.example.hoang.doantotnghiep.View.CustomView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.hoang.doantotnghiep.R;

/**
 * Created by kien.lovan on 12/26/2017.
 */

public class ProgressView extends FrameLayout {
    Context context;

    public ProgressView(Context context) {
        super(context);
        this.context = context;
        initView();
    }


    private void initView() {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_progess_dialog, null);
        addView(rootView);
    }

}
