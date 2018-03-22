package com.example.hoang.doantotnghiep.View.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ModelApi.model.RevenueInfo;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.utils.Const;
import com.example.hoang.doantotnghiep.utils.DecimalFormatUtils;
import com.example.hoang.doantotnghiep.utils.SharedPrefsUtils;

import java.text.DecimalFormat;

/**
 * Created by kien.lovan on 1/17/2018.
 */

public class DialogAddItem implements View.OnClickListener {
    Activity activity;
    Dialog dialog;
    EditText edtTitle;
    EditText edtValue;
    String staticIcon = "";
    String token = "";

    AddNewItemSuccess addNewItemSuccess;
    String TAG;
    String EDIT = "";
    String ID;
    TextView tvName, tvValue;

    public DialogAddItem(Activity activity, String staticIcon, AddNewItemSuccess addNewItemSuccess, String TAG) {
        this.activity = activity;
        this.staticIcon = staticIcon;
        this.addNewItemSuccess = addNewItemSuccess;
        this.TAG = TAG;
        initDialog();
    }

    public DialogAddItem(Activity activity, String TAG, AddNewItemSuccess addNewItemSuccess) {
        this.activity = activity;
        this.addNewItemSuccess = addNewItemSuccess;
        this.TAG = TAG;
        initDialog();
    }

    public DialogAddItem(Activity activity, String TAG, String EDIT, String id,
                         TextView tvName, TextView tvValue,
                         AddNewItemSuccess addNewItemSuccess) {
        this.activity = activity;
        this.addNewItemSuccess = addNewItemSuccess;
        this.TAG = TAG;
        this.EDIT = EDIT;
        this.ID = id;
        this.tvName = tvName;
        this.tvValue = tvValue;
        initDialog();
    }

    public void showDiaglog() {
        dialog.show();
    }

    private void initDialog() {
        token = "Bearer " + SharedPrefsUtils.getStringPreference(activity, "token");
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_setup1_add);
        dialog.setCanceledOnTouchOutside(false);
        dialog.findViewById(R.id.btn_add).setOnClickListener(this);
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(this);
        edtTitle = dialog.findViewById(R.id.edt_title);
        edtValue = dialog.findViewById(R.id.edt_value);
        TextView txtTitle = dialog.findViewById(R.id.txt_title_dialog);
        TextInputLayout txtInputTitle = dialog.findViewById(R.id.txt_input_title);
        TextInputLayout txtInputValue = dialog.findViewById(R.id.txt_input_value);

        if (TAG == Const.REVENUE) {
            txtInputTitle.setHint(activity.getResources().getString(R.string.income_name));
            txtInputValue.setHint(activity.getResources().getString(R.string.income_value));
            txtTitle.setHint(activity.getResources().getString(R.string.income));
        } else if (TAG == Const.NSTT) {
            txtInputTitle.setHint(activity.getResources().getString(R.string.budget_name));
            txtInputValue.setHint(activity.getResources().getString(R.string.budget_value));
            txtTitle.setText(activity.getString(R.string.add_budget_type));
        } else if (TAG == Const.NSCB) {
            txtInputTitle.setHint(activity.getResources().getString(R.string.budget_name));
            txtInputValue.setHint(activity.getResources().getString(R.string.budget_value));
            txtTitle.setText(activity.getString(R.string.add_budget_type));
        } else if (TAG == Const.PCS) {
            txtInputTitle.setHint(activity.getResources().getString(R.string.budget_name));
            txtInputValue.setHint(activity.getResources().getString(R.string.budget_value));
            txtTitle.setText(activity.getString(R.string.add_budget_type));
        } else if (TAG == Const.MANAGE_REVENUE) {
            txtInputTitle.setHint(activity.getResources().getString(R.string.income_name));
            txtInputValue.setHint(activity.getResources().getString(R.string.income_value));
            txtTitle.setText(activity.getResources().getString(R.string.add_income));
            edtValue.requestFocus();
        } else if (TAG == Const.EDIT_BUDGET_NSTT) {
            txtInputTitle.setHint(activity.getResources().getString(R.string.budget_name));
            txtInputValue.setHint(activity.getResources().getString(R.string.budget_value));
            txtTitle.setText(activity.getResources().getString(R.string.add_budget_type));
            edtValue.requestFocus();
        } else if (TAG == Const.EDIT_BUDGET_NSCB) {
            txtInputTitle.setHint(activity.getResources().getString(R.string.budget_name));
            txtInputValue.setHint(activity.getResources().getString(R.string.budget_value));
            txtTitle.setText(activity.getString(R.string.add_budget_type));
        } else if (TAG == Const.EDIT_BUDGET_PCS) {
            txtInputTitle.setHint(activity.getResources().getString(R.string.budget_name));
            txtInputValue.setHint(activity.getResources().getString(R.string.budget_value));
            txtTitle.setText(activity.getString(R.string.add_budget_type));
        }


    }


    public void setTextForEdittext(String name) {
        edtTitle.setText(name);
        if(!name.equals("Thu nhập khác")){
            edtTitle.setFocusable(false);
            edtTitle.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            edtTitle.setClickable(false);
        }
    }

    public void setStaticIcon(String icon) {
        staticIcon = icon;
        Log.d("STATICICON",staticIcon);
    }

    public void setEdtValue(double value) {
        DecimalFormat formatter = new DecimalFormat("###############");
        edtValue.setText(formatter.format(value));
    }

    public void setId(String id){
        this.ID = id;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                String name = edtTitle.getText().toString().trim();
                String value = edtValue.getText().toString().trim();
                if (value == "" || value.equals("")) {
                    Toast.makeText(activity, "Chưa nhập số liệu !", Toast.LENGTH_SHORT).show();
                }
                else {
                    //call API
                    if (TAG == Const.MANAGE_REVENUE) {
                        if (EDIT.equals("EDIT")) {
                            String finalValue = DecimalFormatUtils.getMone(Double.parseDouble(value));
                            if (value.length() > 15) {
                                Toast.makeText(activity, "Số tiền bạn nhập quá lớn", Toast.LENGTH_SHORT).show();
                            } else {
                                String i = this.ID;
                                CallAPI.editRevenue(activity, token, ID,
                                        edtTitle.getText().toString().trim(), Double.parseDouble(value), new CallAPI.OnCallAddRevenue() {
                                            @Override
                                            public void onResult(int code) {
                                                if (code == 1) {
                                                    addNewItemSuccess.onSuccess(ID, name, Double.parseDouble(finalValue));
                                                } else {
                                                    Toast.makeText(activity, activity.getResources().getString(R.string.nettworkError),
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                dialog.dismiss();

                            }
                        } else {
                            String finalValue = DecimalFormatUtils.getMone(Double.parseDouble(value));
                            if (finalValue.length() > 15) {
                                Toast.makeText(activity, "Số tiền bạn nhập quá lớn", Toast.LENGTH_SHORT).show();
                            } else {
                                CallAPI.addRevenue(activity, token, new RevenueInfo(name, Double.parseDouble(value), staticIcon,
                                                null, null),
                                        new CallAPI.OnCallAddRevenue() {
                                            @Override
                                            public void onResult(int result) {
                                                if (result == 1) {
                                                    addNewItemSuccess.onSuccess(ID, name, Double.parseDouble(finalValue));
                                                } else {
                                                    Toast.makeText(activity, activity.getResources().getString(R.string.nettworkError),
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                dialog.dismiss();
                            }

                            Log.d("Call API: ", "Send" + name);
                            Log.d("TOKEN", token);
                            Log.d("STATIC ICON", staticIcon);
                        }
                    } else {
                        String finalValue = DecimalFormatUtils.getMone(Double.parseDouble(value));
                        if (finalValue.length() > 15) {
                            Toast.makeText(activity, "Số tiền bạn nhập quá lớn", Toast.LENGTH_SHORT).show();
                        } else {
                            addNewItemSuccess.onSuccess(ID, name, Double.parseDouble(finalValue));
                            dialog.dismiss();
                        }
                    }
                }
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
        }
    }

    public interface AddNewItemSuccess {
        void onSuccess(String id, String name, double value);
    }

}
