package com.example.hoang.doantotnghiep.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.Model.ModelExpand;
import com.example.hoang.doantotnghiep.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hoang on 3/9/2018.
 */

public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String,List<ModelExpand>> listHashMap;

    public ExpandListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<ModelExpand>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater .inflate(R.layout.row_group_expand_listview,null);

        }
        TextView txtHeader = (TextView) convertView.findViewById(R.id.txt_expand_header);
        txtHeader.setText(headerTitle);
        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ModelExpand modelExpand = (ModelExpand) getChild(groupPosition,childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater .inflate(R.layout.row_expand_listview,null);

        }
        TextView txtHeader = (TextView) convertView.findViewById(R.id.txt_expand_item);
        txtHeader.setText(modelExpand.getName());
        ImageView img = convertView.findViewById(R.id.img_expand);
        img.setImageResource(modelExpand.getImage());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
