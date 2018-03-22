package com.example.hoang.doantotnghiep.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hoang.doantotnghiep.Model.ItemSetup1;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.utils.Const;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Admin on 1/8/2018.
 */

public class RecyclerSetup1Adapter extends RecyclerView.Adapter<RecyclerSetup1Adapter.ViewHolder> {

    ArrayList<ItemSetup1> listSetup1;
    Context context;
    String type;
    View layoutNguonThuNhap, underLine;
    EditText edtName, edtValue;
    ImageView imgRemove,imgBudget;
    HashMap<String, String> allTextInput = new HashMap<>();
    removeBudgets removeBudgets;


    public RecyclerSetup1Adapter(ArrayList<ItemSetup1> listSetup1, Context context, String type, removeBudgets removeBudgets) {
        this.listSetup1 = listSetup1;
        this.context = context;
        this.type = type;
        this.removeBudgets = removeBudgets;
    }

    public HashMap<String, String> getAllTextInput() {
        return allTextInput;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_nguon_thu_nhap, parent, false);
        layoutNguonThuNhap = view.findViewById(R.id.layout_nguon_thu_nhap);
        edtName = view.findViewById(R.id.edt_name);
        edtValue = view.findViewById(R.id.edt_value);
        underLine = view.findViewById(R.id.under_line);
        imgRemove = view.findViewById(R.id.img_remove);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DecimalFormat formatter = new DecimalFormat("######################");

        if (listSetup1 != null && listSetup1.size() > 0) {
            switch (type) {
                case Const.REVENUE:
                case Const.NSTT:
                case Const.NSCB:
                case Const.PCS:
                    String icon = listSetup1.get(position).getStatic_icon();
                    String name = listSetup1.get(position).getName();
                    String value = String.valueOf(listSetup1.get(position).getValue());

                    holder.ref = position;
                    holder.edtName.setText(name);
                    if(listSetup1.get(position).getValue() == 0)
                    {
                        holder.edtValue.setText("");
                    }
                    else
                    holder.edtValue.setText(formatter.format(listSetup1.get(position).getValue()));
                    holder.edtName.setTag("name" + position);
                    holder.edtValue.setTag("value" + position);
                    dataChangeAlltext("name" + position, name);
                    dataChangeAlltext("value" + position, value);
                    holder.edtValue.requestFocus();
                    holder.edtName.addTextChangedListener(new GenericTextWatcher(holder, holder.edtName, "name"));
                    holder.edtValue.addTextChangedListener(new GenericTextWatcher(holder, holder.edtValue, "value"));

                    holder.imgRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            removeBudgets.onRemove(type);
                            listSetup1.remove(position);
//                            notifyItemRemoved(position);
//                            notifyItemRangeRemoved(position, listSetup1.size());
                            notifyDataSetChanged();
                            allTextInput.remove("name" + position);
                            allTextInput.remove("value" + position);
                            refreshAlltext(position);
                        }
                    });
                    break;
                case Const.EDIT_BUDGET_NSTT:
                case Const.EDIT_BUDGET_NSCB:
                case Const.EDIT_BUDGET_PCS:
                    layoutNguonThuNhap.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                    edtName.setTextColor(context.getResources().getColor(R.color.text_default));
                    edtName.setHintTextColor(context.getResources().getColor(R.color.text_hint_bright));
                    edtValue.setHintTextColor(context.getResources().getColor(R.color.text_hint_bright));
                    edtValue.setTextColor(context.getResources().getColor(R.color.text_default));
                    underLine.setBackgroundColor(context.getResources().getColor(R.color.text_default));
                    imgRemove.setImageResource(R.drawable.icon_remove_bright);
                    String name1 = listSetup1.get(position).getName();
                    String vl = String.valueOf(formatter.format(listSetup1.get(position).getValue()));
                    String value1 = String.valueOf(formatter.format(listSetup1.get(position).getValue()));
                    if(vl.equals("0.0")|| vl.equals("0")){
                        vl = "";
                    }


                    holder.ref = position;
                    holder.edtName.setText(name1);
                    holder.edtValue.setText(vl);
                    holder.edtName.setTag("name" + position);
                    holder.edtValue.setTag("value" + position);
                    dataChangeAlltext("name" + position, name1);
                    dataChangeAlltext("value" + position, value1);
                    holder.edtValue.requestFocus();
                    holder.edtName.addTextChangedListener(new GenericTextWatcher(holder, holder.edtName, "name"));
                    holder.edtValue.addTextChangedListener(new GenericTextWatcher(holder, holder.edtValue, "value"));

                    holder.imgRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ItemSetup1 itemSetup1 = listSetup1.get(position);
                            if (itemSetup1.getId() != null && !itemSetup1.getId().equals("")) {
                                CallAPI.deleteBudget(context, itemSetup1.getId(), new CallAPI.OnDelete() {
                                    @Override
                                    public void deleteSuccess(boolean onSuccess) {
                                        if (onSuccess) {
                                            removeBudgets.onRemove(type);
                                            listSetup1.remove(position);
                                            notifyDataSetChanged();
                                            allTextInput.remove("name" + position);
                                            allTextInput.remove("value" + position);
                                            refreshAlltext(position);
                                        }
                                    }
                                });
                            } else {
                                removeBudgets.onRemove(type);
                                listSetup1.remove(position);
                                notifyDataSetChanged();
                                allTextInput.remove("name" + position);
                                allTextInput.remove("value" + position);
                                refreshAlltext(position);
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }


    public void dataChangeAlltext(String key, String value) {
        if (allTextInput.containsKey(key)) {
            return;
        } else {
            allTextInput.put(key, value);
        }

    }

    public void refreshAlltext(int position) {
        HashMap<String, String> temp = new HashMap<>();
        for (String key : allTextInput.keySet()) {
            String value = allTextInput.get(key);
            String lastIndex = key.substring(key.length() - 1, key.length());
            String originName = key.substring(0, key.length() - 1);
            if (Integer.parseInt(lastIndex) > position) {
                String newKey = originName + (Integer.parseInt(lastIndex) - 1);
                temp.put(newKey, value);
                continue;
            }
            temp.put(key, value);

        }
        allTextInput = temp;
    }


    @Override
    public int getItemCount() {
        return listSetup1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText edtName;
        private EditText edtValue;
        private ImageView imgRemove;
        int ref;

        public ViewHolder(View view) {
            super(view);
            edtName = view.findViewById(R.id.edt_name);
            edtValue = view.findViewById(R.id.edt_value);
            imgRemove = view.findViewById(R.id.img_remove);

        }
    }

    private class GenericTextWatcher implements TextWatcher {

        private View view;
        ViewHolder parrent;
        String type;

        public GenericTextWatcher(ViewHolder parrent, View view, String type) {
            this.view = view;
            this.parrent = parrent;
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString().trim();
            allTextInput.put(type + parrent.ref, text);

        }
    }

    public interface removeBudgets {
        void onRemove(String type);
    }

}
