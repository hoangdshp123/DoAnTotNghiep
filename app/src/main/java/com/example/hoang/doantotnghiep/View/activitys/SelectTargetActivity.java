package com.example.hoang.doantotnghiep.View.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Adapter.Recycler_tools_adapter;
import com.example.hoang.doantotnghiep.Model.Item_Tools;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.View.CustomView.ItemOffsetDecoration;
import com.example.hoang.doantotnghiep.utils.Const;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;

import java.util.ArrayList;

public class SelectTargetActivity extends AppCompatActivity {
    ArrayList<Item_Tools> listItem = new ArrayList<>();
    Recycler_tools_adapter adapter;
    RecyclerView recyclerAddTaret;
    String targetType;
    Toolbar toolbar;
    TextView title;
    TextView txt_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_target);

        initView();
        setupView();
    }

    public void initView() {
        recyclerAddTaret = findViewById(R.id.recycle_add_target);
        toolbar = findViewById(R.id.toolbar);
        txt_bottom = findViewById(R.id.txt_bottom);
        title = toolbar.findViewById(R.id.toolbar_title);
        double conlai = HomeActivity.totalRevenue - HomeActivity.totalExpense;
        if(conlai < 0)
            conlai = 0;
        String txt = "Với tổng thu nhập là "+ DecimalFormatUtils.getMoney(HomeActivity.totalRevenue) +"  và tổng ngân sách chi tiêu là "+ DecimalFormatUtils.getMoney(HomeActivity.totalExpense)+", bạn chỉ còn "+DecimalFormatUtils.getMoney(conlai)+" tiền nhàn rỗi cho tất cả các mục tiêu tài chính.";
        txt_bottom.setText(txt);
    }

    public void setupView() {
        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());
        toolbar.setPadding(0, 0, 24, 0);
        title.setText(getResources().getString(R.string.addtarget));
        title.setGravity(Gravity.CENTER);

        //add data
        addDataToList();

        //targetType = getIntent().getStringExtra(Const.TARGET_TYPE);

//        if (targetType.equals(Const.TARGET_SAVING)) {
//            adapter = new Recycler_tools_adapter(listItem, this, Const.TARGET_SAVING);
//        } else if (targetType.equals(Const.TARGET_LOAN)) {
//            adapter = new Recycler_tools_adapter(listItem, this, Const.TARGET_LOAN);
//        }
        adapter = new Recycler_tools_adapter(listItem, this, Const.TARGET_TYPE);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerAddTaret.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.size_space_recycleview);
        recyclerAddTaret.addItemDecoration(itemDecoration);
        recyclerAddTaret.setNestedScrollingEnabled(false);
        recyclerAddTaret.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("hoas", String.valueOf(resultCode));
        if (resultCode == 3333 || resultCode == 2121) {
            setResult(2221);
            finish();
        }
    }

    public void addDataToList() {
        listItem.add(new Item_Tools(getString(R.string.setup3_index0), getResources().getDrawable(R.drawable.icon_house_bright),
                getResources().getResourceEntryName(R.drawable.icon_house_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index1), getResources().getDrawable(R.drawable.icon_car_bright),
                getResources().getResourceEntryName(R.drawable.icon_car_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index2), getResources().getDrawable(R.drawable.icon_shopping_bright),
                getResources().getResourceEntryName(R.drawable.icon_shopping_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index3), getResources().getDrawable(R.drawable.icon_travel_bright),
                getResources().getResourceEntryName(R.drawable.icon_travel_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index4), getResources().getDrawable(R.drawable.icon_repair_bright),
                getResources().getResourceEntryName(R.drawable.icon_repair_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index5), getResources().getDrawable(R.drawable.icon_help_bright),
                getResources().getResourceEntryName(R.drawable.icon_help_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index6), getResources().getDrawable(R.drawable.icon_pig_bright),
                getResources().getResourceEntryName(R.drawable.icon_pig_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index7), getResources().getDrawable(R.drawable.icon_loan_bright),
                getResources().getResourceEntryName(R.drawable.icon_loan_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup3_index8), getResources().getDrawable(R.drawable.icon_plus_bright),
                getResources().getResourceEntryName(R.drawable.icon_plus_bright)));
    }
}
