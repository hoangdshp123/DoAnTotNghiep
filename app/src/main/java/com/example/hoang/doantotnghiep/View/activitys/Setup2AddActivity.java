package com.example.hoang.doantotnghiep.View.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Adapter.Recycler_tools_adapter;
import com.example.hoang.doantotnghiep.Model.Item_Tools;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.View.CustomView.ItemOffsetDecoration;
import com.example.hoang.doantotnghiep.utils.Const;

import java.util.ArrayList;

import static com.example.hoang.doantotnghiep.utils.Const.EDIT_BUDGET_NSCB;
import static com.example.hoang.doantotnghiep.utils.Const.EDIT_BUDGET_NSTT;
import static com.example.hoang.doantotnghiep.utils.Const.EDIT_BUDGET_PCS;
import static com.example.hoang.doantotnghiep.utils.Const.MANAGE_REVENUE;
import static com.example.hoang.doantotnghiep.utils.Const.NSCB;
import static com.example.hoang.doantotnghiep.utils.Const.NSTT;
import static com.example.hoang.doantotnghiep.utils.Const.PCS;
import static com.example.hoang.doantotnghiep.utils.Const.REVENUE;

public class Setup2AddActivity extends AppCompatActivity {

    ArrayList<Item_Tools> listItem;
    Recycler_tools_adapter adapter;
    RecyclerView recyclerSetup2Add;
    View layoutSetup2Add;
    TextView txtTitleSetup2Add;


    Intent intent;
    String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setup2_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());

        txtTitleSetup2Add = findViewById(R.id.txt_title_setup2_add);
        layoutSetup2Add = findViewById(R.id.layout_setup2_add);
        recyclerSetup2Add = findViewById(R.id.recycle_setup2_add);
        intent = getIntent();
        action = intent.getStringExtra(Const.ACTION_GUIDE);

        switch (action) {
            case REVENUE:
                txtTitleSetup2Add.setText(getResources().getText(R.string.select_revenue_type));
                title.setText(getResources().getText(R.string.title_toolbar_setup1_add));
                title.setGravity(Gravity.START);
                toolbar.setBackgroundColor(getResources().getColor(R.color.color_background_default));
                addDataRevenue();
                addToRecycleView(Const.REVENUE);

                break;
            case NSTT: {
                title.setText(getResources().getText(R.string.title_toolbar_setup1_add));
                txtTitleSetup2Add.setText(getResources().getText(R.string.select_budget_type));
                title.setGravity(Gravity.START);
                toolbar.setBackgroundColor(getResources().getColor(R.color.color_background_default));
                addDataToNSTT();
                addToRecycleView(Const.NSTT);

                break;
            }
            case NSCB: {
                title.setText(getResources().getText(R.string.title_toolbar_setup1_add));
                txtTitleSetup2Add.setText(getResources().getText(R.string.select_budget_type));
                title.setGravity(Gravity.START);
                toolbar.setBackgroundColor(getResources().getColor(R.color.color_background_default));
                addDataToNSCB();
                addToRecycleView(Const.NSCB);
                break;
            }
            case PCS: {
                title.setText(getResources().getText(R.string.title_toolbar_setup1_add));
                txtTitleSetup2Add.setText(getResources().getText(R.string.select_budget_type));
                title.setGravity(Gravity.START);
                toolbar.setBackgroundColor(getResources().getColor(R.color.color_background_default));
                addDataToPCS();
                addToRecycleView(Const.PCS);
                break;
            }

            case MANAGE_REVENUE:
                title.setText(getResources().getText(R.string.add_list_revenue));
                title.setGravity(Gravity.CENTER);
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                layoutSetup2Add.setBackgroundColor(getResources().getColor(R.color.color_white));
                txtTitleSetup2Add.setText(getResources().getString(R.string.select_revenue_type));
                txtTitleSetup2Add.setTextColor(getResources().getColor(R.color.text_default));
                addDataManageRevenue();
                addToRecycleView(Const.MANAGE_REVENUE);
                break;

            case EDIT_BUDGET_NSTT:
                title.setText(getResources().getText(R.string.add_minium_budget));
                title.setGravity(Gravity.CENTER);
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                layoutSetup2Add.setBackgroundColor(getResources().getColor(R.color.color_white));
                txtTitleSetup2Add.setText(getResources().getText(R.string.select_budget_type));
                txtTitleSetup2Add.setTextColor(getResources().getColor(R.color.text_default));
                addDataToEditBudgetNSTT();
                addToRecycleView(Const.EDIT_BUDGET_NSTT);
                break;
            case EDIT_BUDGET_NSCB:
                title.setText(getResources().getText(R.string.add_basic_budget));
                title.setGravity(Gravity.CENTER);
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                layoutSetup2Add.setBackgroundColor(getResources().getColor(R.color.color_white));
                txtTitleSetup2Add.setText(getResources().getText(R.string.select_budget_type));
                txtTitleSetup2Add.setTextColor(getResources().getColor(R.color.text_default));
                addDataToEditBudgetNSCB();
                addToRecycleView(Const.EDIT_BUDGET_NSCB);
                break;
            case EDIT_BUDGET_PCS:
                title.setText(getResources().getText(R.string.add_lifestyle));
                title.setGravity(Gravity.CENTER);
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                layoutSetup2Add.setBackgroundColor(getResources().getColor(R.color.color_white));
                txtTitleSetup2Add.setText(getResources().getText(R.string.select_budget_type));
                txtTitleSetup2Add.setTextColor(getResources().getColor(R.color.text_default));
                addDataToEditBudgetPCS();
                addToRecycleView(Const.EDIT_BUDGET_PCS);
                break;
        }
    }

    public void addToRecycleView(String tag) {
        adapter = new Recycler_tools_adapter(listItem, this, tag);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerSetup2Add.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.size_space_recycleview);
        recyclerSetup2Add.addItemDecoration(itemDecoration);
        recyclerSetup2Add.setNestedScrollingEnabled(false);
        recyclerSetup2Add.setAdapter(adapter);
    }

    private void addDataToNSTT() {
        listItem = new ArrayList<>();
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index0), getResources().getDrawable(R.drawable.icon_house_bright),
                getResources().getResourceEntryName(R.drawable.icon_house_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index1), getResources().getDrawable(R.drawable.icon_electricity_bright),
                getResources().getResourceEntryName(R.drawable.icon_electricity_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index2), getResources().getDrawable(R.drawable.icon_water_bright),
                getResources().getResourceEntryName(R.drawable.icon_water_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index3), getResources().getDrawable(R.drawable.icon_food_bright),
                getResources().getResourceEntryName(R.drawable.icon_food_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index4), getResources().getDrawable(R.drawable.icon_television_bright),
                getResources().getResourceEntryName(R.drawable.icon_television_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index5), getResources().getDrawable(R.drawable.icon_phone_bright),
                getResources().getResourceEntryName(R.drawable.icon_phone_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index6), getResources().getDrawable(R.drawable.icon_gas_bright),
                getResources().getResourceEntryName(R.drawable.icon_gas_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index7), getResources().getDrawable(R.drawable.icon_loan_bright),
                getResources().getResourceEntryName(R.drawable.icon_loan_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index8), getResources().getDrawable(R.drawable.icon_plus_bright),
                getResources().getResourceEntryName(R.drawable.icon_plus_bright)));
    }

    private void addDataToNSCB() {
        listItem = new ArrayList<>();
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index0), getResources().getDrawable(R.drawable.icon_shopping_bright),
                getResources().getResourceEntryName(R.drawable.icon_shopping_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index1), getResources().getDrawable(R.drawable.icon_edu_bright),
                getResources().getResourceEntryName(R.drawable.icon_edu_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index2), getResources().getDrawable(R.drawable.icon_sports_bright),
                getResources().getResourceEntryName(R.drawable.icon_sports_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index3), getResources().getDrawable(R.drawable.icon_makeup_bright),
                getResources().getResourceEntryName(R.drawable.icon_makeup_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index4), getResources().getDrawable(R.drawable.icon_hoinhom_bright),
                getResources().getResourceEntryName(R.drawable.icon_hoinhom_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index5), getResources().getDrawable(R.drawable.icon_baby_bright),
                getResources().getResourceEntryName(R.drawable.icon_baby)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index6), getResources().getDrawable(R.drawable.icon_family_bright),
                getResources().getResourceEntryName(R.drawable.icon_family)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index7), getResources().getDrawable(R.drawable.icon_maid_bright),
                getResources().getResourceEntryName(R.drawable.icon_maid_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index8), getResources().getDrawable(R.drawable.icon_plus_bright),
                getResources().getResourceEntryName(R.drawable.icon_plus_bright)));

    }

    private void addDataToPCS() {
        listItem = new ArrayList<>();
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index0), getResources().getDrawable(R.drawable.icon_coffee_bright),
                getResources().getResourceEntryName(R.drawable.icon_coffee_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index1), getResources().getDrawable(R.drawable.icon_film_bright),
                getResources().getResourceEntryName(R.drawable.icon_film_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index2), getResources().getDrawable(R.drawable.icon_restaurant_bright),
                getResources().getResourceEntryName(R.drawable.icon_restaurant_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index3), getResources().getDrawable(R.drawable.icon_travel_bright),
                getResources().getResourceEntryName(R.drawable.icon_travel_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index4), getResources().getDrawable(R.drawable.icon_help_bright),
                getResources().getResourceEntryName(R.drawable.icon_help_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index5), getResources().getDrawable(R.drawable.icon_collect_bright),
                getResources().getResourceEntryName(R.drawable.icon_collect_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index6), getResources().getDrawable(R.drawable.icon_insurance_bright),
                getResources().getResourceEntryName(R.drawable.icon_insurance_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index7), getResources().getDrawable(R.drawable.icon_decoration_bright),
                getResources().getResourceEntryName(R.drawable.icon_decoration_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index8), getResources().getDrawable(R.drawable.icon_plus_bright),
                getResources().getResourceEntryName(R.drawable.icon_plus_bright)));
    }

    private void addDataRevenue() {
        listItem = new ArrayList<>();
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index0), getResources().getDrawable(R.drawable.icon_wallet_bright),
                getResources().getResourceEntryName(R.drawable.icon_wallet_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index1), getResources().getDrawable(R.drawable.icon_bonus_bright),
                getResources().getResourceEntryName(R.drawable.icon_bonus_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index2), getResources().getDrawable(R.drawable.icon_sale_bright),
                getResources().getResourceEntryName(R.drawable.icon_sale_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index3), getResources().getDrawable(R.drawable.icon_present_bright),
                getResources().getResourceEntryName(R.drawable.icon_present_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index4), getResources().getDrawable(R.drawable.icon_interest_bright),
                getResources().getResourceEntryName(R.drawable.icon_interest_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index5), getResources().getDrawable(R.drawable.icon_loan_bright_revenue),
                getResources().getResourceEntryName(R.drawable.icon_loan_bright_revenue)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index6), getResources().getDrawable(R.drawable.icon_overtime_bright),
                getResources().getResourceEntryName(R.drawable.icon_overtime_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index7), getResources().getDrawable(R.drawable.icon_tips_bright),
                getResources().getResourceEntryName(R.drawable.icon_tips_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index8), getResources().getDrawable(R.drawable.icon_plus_bright),
                getResources().getResourceEntryName(R.drawable.icon_plus_bright)));
    }

    public void addDataManageRevenue() {
        listItem = new ArrayList<>();
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index0), getResources().getDrawable(R.drawable.icon_wallet_bright),
                getResources().getResourceEntryName(R.drawable.icon_wallet_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index1), getResources().getDrawable(R.drawable.icon_bonus_bright),
                getResources().getResourceEntryName(R.drawable.icon_bonus_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index2), getResources().getDrawable(R.drawable.icon_sale_bright),
                getResources().getResourceEntryName(R.drawable.icon_sale_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index3), getResources().getDrawable(R.drawable.icon_present_bright),
                getResources().getResourceEntryName(R.drawable.icon_present_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index4), getResources().getDrawable(R.drawable.icon_interest_bright),
                getResources().getResourceEntryName(R.drawable.icon_interest_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index5), getResources().getDrawable(R.drawable.icon_loan_bright_revenue),
                getResources().getResourceEntryName(R.drawable.icon_loan_bright_revenue)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index6), getResources().getDrawable(R.drawable.icon_overtime_bright),
                getResources().getResourceEntryName(R.drawable.icon_overtime_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index7), getResources().getDrawable(R.drawable.icon_tips_bright),
                getResources().getResourceEntryName(R.drawable.icon_tips_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup1_add_index8), getResources().getDrawable(R.drawable.icon_plus_bright),
                getResources().getResourceEntryName(R.drawable.icon_plus_bright)));
    }

    public void addDataToEditBudgetNSTT() {
        listItem = new ArrayList<>();
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index0), getResources().getDrawable(R.drawable.icon_house_bright),
                getResources().getResourceEntryName(R.drawable.icon_house_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index1), getResources().getDrawable(R.drawable.icon_electricity_bright),
                getResources().getResourceEntryName(R.drawable.icon_electricity_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index2), getResources().getDrawable(R.drawable.icon_water_bright),
                getResources().getResourceEntryName(R.drawable.icon_water_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index3), getResources().getDrawable(R.drawable.icon_food_bright),
                getResources().getResourceEntryName(R.drawable.icon_food_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index4), getResources().getDrawable(R.drawable.icon_television_bright),
                getResources().getResourceEntryName(R.drawable.icon_television_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index5), getResources().getDrawable(R.drawable.icon_phone_bright),
                getResources().getResourceEntryName(R.drawable.icon_phone_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index6), getResources().getDrawable(R.drawable.icon_gas_bright),
                getResources().getResourceEntryName(R.drawable.icon_gas_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index7), getResources().getDrawable(R.drawable.icon_loan_bright),
                getResources().getResourceEntryName(R.drawable.icon_loan_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nstt_index8), getResources().getDrawable(R.drawable.icon_plus_bright),
                getResources().getResourceEntryName(R.drawable.icon_plus_bright)));
    }

    public void addDataToEditBudgetNSCB() {
        listItem = new ArrayList<>();
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index0), getResources().getDrawable(R.drawable.icon_shopping_bright),
                getResources().getResourceEntryName(R.drawable.icon_shopping_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index1), getResources().getDrawable(R.drawable.icon_edu_bright),
                getResources().getResourceEntryName(R.drawable.icon_edu_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index2), getResources().getDrawable(R.drawable.icon_sports_bright),
                getResources().getResourceEntryName(R.drawable.icon_sports_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index3), getResources().getDrawable(R.drawable.icon_makeup_bright),
                getResources().getResourceEntryName(R.drawable.icon_makeup_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index4), getResources().getDrawable(R.drawable.icon_hoinhom_bright),
                getResources().getResourceEntryName(R.drawable.icon_hoinhom_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index5), getResources().getDrawable(R.drawable.icon_baby_bright),
                getResources().getResourceEntryName(R.drawable.icon_baby_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index6), getResources().getDrawable(R.drawable.icon_family_bright),
                getResources().getResourceEntryName(R.drawable.icon_family_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index7), getResources().getDrawable(R.drawable.icon_maid_bright),
                getResources().getResourceEntryName(R.drawable.icon_maid_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_nscb_index8), getResources().getDrawable(R.drawable.icon_plus_bright),
                getResources().getResourceEntryName(R.drawable.icon_plus_bright)));
    }

    public void addDataToEditBudgetPCS() {
        listItem = new ArrayList<>();
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index0), getResources().getDrawable(R.drawable.icon_coffee_bright),
                getResources().getResourceEntryName(R.drawable.icon_coffee_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index1), getResources().getDrawable(R.drawable.icon_film_bright),
                getResources().getResourceEntryName(R.drawable.icon_film_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index2), getResources().getDrawable(R.drawable.icon_restaurant_bright),
                getResources().getResourceEntryName(R.drawable.icon_restaurant_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index3), getResources().getDrawable(R.drawable.icon_travel_bright),
                getResources().getResourceEntryName(R.drawable.icon_travel_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index4), getResources().getDrawable(R.drawable.icon_care_bright),
                getResources().getResourceEntryName(R.drawable.icon_care_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index5), getResources().getDrawable(R.drawable.icon_collect_bright),
                getResources().getResourceEntryName(R.drawable.icon_collect_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index6), getResources().getDrawable(R.drawable.icon_insurance_bright),
                getResources().getResourceEntryName(R.drawable.icon_insurance_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index7), getResources().getDrawable(R.drawable.icon_decoration_bright),
                getResources().getResourceEntryName(R.drawable.icon_decoration_bright)));
        listItem.add(new Item_Tools(getString(R.string.setup2_add_pcs_index8), getResources().getDrawable(R.drawable.icon_plus_bright),
                getResources().getResourceEntryName(R.drawable.icon_plus_bright)));
    }
}
