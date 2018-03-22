package com.example.hoang.doantotnghiep.View.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Adapter.ExpandListAdapter;
import com.example.hoang.doantotnghiep.Model.ModelExpand;
import com.example.hoang.doantotnghiep.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandLvPayment extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandListAdapter expandListAdapter;
    private HashMap<String,List<ModelExpand>> listHashMap;
    List<String> titleHeader;
    List<ModelExpand> listExpand1;
    List<ModelExpand> listExpand2;
    List<ModelExpand> listExpand3;
    TextView title_toolsbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title_toolsbar = toolbar.findViewById(R.id.toolbar_title);
        title_toolsbar.setText("Mục chi tiêu");
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());
        addList();


        expandListAdapter = new ExpandListAdapter(getApplicationContext(),titleHeader,listHashMap);
        expandableListView.setAdapter(expandListAdapter);
        expandableListView.expandGroup(0);
        expandableListView.expandGroup(1);
        expandableListView.expandGroup(2);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent();
                if(groupPosition == 0 ){
                    intent.putExtra("namePayment",listExpand1.get(childPosition).getName());
                    intent.putExtra("imgPayment",String.valueOf(listExpand1.get(childPosition).getImage()));
                    intent.putExtra("title", titleHeader.get(groupPosition));
                }else if(groupPosition == 1 ){
                    intent.putExtra("namePayment",listExpand2.get(childPosition).getName());
                    intent.putExtra("imgPayment",String.valueOf(listExpand2.get(childPosition).getImage()));
                    intent.putExtra("title", titleHeader.get(groupPosition));
                }else if(groupPosition == 2 ){
                    intent.putExtra("namePayment",listExpand3.get(childPosition).getName());
                    intent.putExtra("imgPayment",String.valueOf(listExpand3.get(childPosition).getImage()));
                    intent.putExtra("title", titleHeader.get(groupPosition));
                }
                setResult(2020,intent);
                finish();
                return true;
            }
        });
        expandableListView.showContextMenu();
    }

    private void addList() {
        expandableListView = findViewById(R.id.expand_Listview);
        titleHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listExpand1 = new ArrayList<>();
        listExpand2 = new ArrayList<>();
        listExpand3 = new ArrayList<>();

        titleHeader.add("Chi tiêu tối thiểu");
        titleHeader.add("Chi tiêu cơ bản");
        titleHeader.add("Phong cách sống");

        listExpand1.add(new ModelExpand(R.drawable.mct1,"Thuê nhà"));
        listExpand1.add(new ModelExpand(R.drawable.mct2,"Điện"));
        listExpand1.add(new ModelExpand(R.drawable.mct3,"Nước"));
        listExpand1.add(new ModelExpand(R.drawable.mct4,"Gas"));
        listExpand1.add(new ModelExpand(R.drawable.mct5,"TV"));
        listExpand1.add(new ModelExpand(R.drawable.mct6,"Internet"));
        listExpand1.add(new ModelExpand(R.drawable.mct7,"Điện thoại"));
        listExpand1.add(new ModelExpand(R.drawable.mct8,"Thực phẩm hàng ngày"));
        listExpand1.add(new ModelExpand(R.drawable.mct9,"Xăng xe"));
        listExpand1.add(new ModelExpand(R.drawable.mct10,"Gửi xe"));
        listExpand1.add(new ModelExpand(R.drawable.mct11,"Vật nuôi"));
        listExpand1.add(new ModelExpand(R.drawable.mct12,"Khác"));


        listExpand2.add(new ModelExpand(R.drawable.mct13,"Mua sắm"));
        listExpand2.add(new ModelExpand(R.drawable.mct14,"Giáo dục"));
        listExpand2.add(new ModelExpand(R.drawable.mct15,"Thể thao"));
        listExpand2.add(new ModelExpand(R.drawable.mct16,"Làm đẹp"));
        listExpand2.add(new ModelExpand(R.drawable.mct17,"Hội nhóm"));
        listExpand2.add(new ModelExpand(R.drawable.mct18,"Chăm nuôi trẻ nhỏ"));
        listExpand2.add(new ModelExpand(R.drawable.mct19,"Giáo dục trẻ nhỏ"));
        listExpand2.add(new ModelExpand(R.drawable.mct20,"Cha mẹ"));
        listExpand2.add(new ModelExpand(R.drawable.mct21,"Người giúp việc"));
        listExpand2.add(new ModelExpand(R.drawable.mct22,"Y tế"));
        listExpand2.add(new ModelExpand(R.drawable.mct23,"Thăm hỏi"));
        listExpand2.add(new ModelExpand(R.drawable.mct24,"Biếu tặng"));
        listExpand2.add(new ModelExpand(R.drawable.mct25,"Khác"));


        listExpand3.add(new ModelExpand(R.drawable.mct26,"Cafe"));
        listExpand3.add(new ModelExpand(R.drawable.mct27,"Xem phim"));
        listExpand3.add(new ModelExpand(R.drawable.mct28,"Nhà hàng"));
        listExpand3.add(new ModelExpand(R.drawable.mct29,"Du lịch"));
        listExpand3.add(new ModelExpand(R.drawable.mct30,"Từ thiện/quà tặng"));
        listExpand3.add(new ModelExpand(R.drawable.mct31,"Sưu tập"));
        listExpand3.add(new ModelExpand(R.drawable.mct32,"Bảo hiểm"));
        listExpand3.add(new ModelExpand(R.drawable.mct33,"Trang trí"));
        listExpand3.add(new ModelExpand(R.drawable.mct34,"Khác"));

        listHashMap.put(titleHeader.get(0),listExpand1);
        listHashMap.put(titleHeader.get(1),listExpand2);
        listHashMap.put(titleHeader.get(2),listExpand3);
    }
}
