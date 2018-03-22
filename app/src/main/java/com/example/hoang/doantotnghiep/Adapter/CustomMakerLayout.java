package com.example.hoang.doantotnghiep.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

public class CustomMakerLayout extends MarkerView {

    private TextView tvContent;
    private final String xLabels[];

    public CustomMakerLayout(Context context, int layoutResource, List<String> xLabels) {
        super(context, layoutResource);
        // this markerview only displays a textview
        tvContent = (TextView) findViewById(R.id.tvContent);

        this.xLabels = xLabels.toArray(new String[0]);

    }
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int xIndex = (int) e.getX();

        // use x value to lookup for price & vol value from arrays passed in constructor
        String xVal = xLabels[xIndex];

        tvContent.setText(DecimalFormatUtils.getMoney(Double.parseDouble(xVal)));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2),-(getHeight() / 2));
    }

}

