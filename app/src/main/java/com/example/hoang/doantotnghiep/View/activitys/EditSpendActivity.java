package com.example.hoang.doantotnghiep.View.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ModelExpenseDate.ModelExpenseDate;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.Fragment.HistoryFragment;
import com.example.hoang.doantotnghiep.View.dialog.DateDialog;
import com.example.hoang.doantotnghiep.utils.SharedPrefsUtils;
import com.example.hoang.doantotnghiep.utils.Utils;

import java.io.File;
import java.util.Date;

/**
 * Created by ADMIN on 2/1/2018.
 */

public class EditSpendActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView, btnRemove, imgIconEdit;
    private Spinner spinner;
    private DateDialog dateDialog;
    private TextView txtvdatepayment, title,txtSubTypeEdit;
    private EditText edt_expense_name,edt_expense_value,edt_note;
    private File imgfile = null;
    private String date1;
    private Intent intent;
    private String change_icon;
    private String spendName, spendValue, spendDateTime, spendNote, spendImage, token, spendId;
    private boolean check = false;
    String subTypeEdit,temp="",iconName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        intent = getIntent();
        spendName = intent.getStringExtra("name");
        spendValue = intent.getStringExtra("value");
        spendDateTime = intent.getStringExtra("date");
        spendNote = intent.getStringExtra("note");
        spendImage = intent.getStringExtra("image");
        spendId = intent.getStringExtra("id");
        iconName = intent.getStringExtra("iconEdit");
        subTypeEdit = intent.getStringExtra("subTypeEdit");
        initView();

    }

    private void initView() {
//        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.image);
        btnRemove = (ImageView) findViewById(R.id.btn_remove);
        txtvdatepayment = findViewById(R.id.txtv_date_payment);
//        btnRemove.setOnClickListener(this);
        edt_expense_name = findViewById(R.id.edt_expense_name);
        edt_expense_value = findViewById(R.id.edt_expense_value);
        edt_note = findViewById(R.id.edt_note);
        title = findViewById(R.id.title_spend);
        txtSubTypeEdit = findViewById(R.id.txtv_name_payment);
        imgIconEdit = findViewById(R.id.img_payment);


        title.setText("Chỉnh sửa chi tiêu");
        edt_expense_name.setText(spendName);
        edt_expense_value.setText(spendValue);
        edt_note.setText(spendNote);
        txtSubTypeEdit.setText(subTypeEdit);
        imgIconEdit.setImageResource(this.getResources().getIdentifier(iconName, "drawable", this.getPackageName()));
        txtvdatepayment.setText(Utils.convertUTCTimeToLocalTime2(spendDateTime));
//        Picasso.with(this).load(spendImage).into(imageView);
//        imageView.getLayoutParams().width = 400;
//        imageView.getLayoutParams().height = 500;
//        if(spendImage != null){
//            btnRemove.setVisibility(View.VISIBLE);
//        }else {
//            btnRemove.setVisibility(View.GONE);
//        }
        token = "Bearer " + SharedPrefsUtils.getStringPreference(this,"token");

        findViewById(R.id.btn_spend_back).setOnClickListener(this);
        findViewById(R.id.btn_choose_date).setOnClickListener(this);
        findViewById(R.id.btn_accept).setOnClickListener(this);
        findViewById(R.id.btn_choose_payment).setOnClickListener(this);
        dateDialog = new DateDialog(EditSpendActivity.this, new DateDialog.DateSelected() {
            @Override
            public void onSelected(String date) {
                String dateTemp = Utils.revertStringDate(date);
                Date d = Utils.convertStringToDate(dateTemp, "dd/MM/yyyy");
                date1 = Utils.convertLocalTimeToUTC(d);
                txtvdatepayment.setText(dateTemp);
            }
        });
//        initSpinner();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2019 && resultCode == 2020) {
            txtSubTypeEdit.setText(data.getStringExtra("namePayment"));
            imgIconEdit.setImageResource(Integer.parseInt(data.getStringExtra("imgPayment")));
            subTypeEdit = data.getStringExtra("namePayment");
            iconName = data.getStringExtra("imgPayment");
            temp = data.getStringExtra("title");
        }
    }

//    private void initSpinner() {
//        spinner = (Spinner) findViewById(R.id.spinner_payment);
//        String arr[] = {
//                "Chi tiêu tối thiểu",
//                "Chi tiêu cơ bản",
//                "Chi tiêu xa xỉ"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditSpendActivity.this,
//                android.R.layout.simple_spinner_item, arr);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spinner.setAdapter(adapter);
//        if(spendType.equals("min")) {
//            spinner.setSelection(0);
//        }else if(spendType.equals("base")){
//            spinner.setSelection(1);
//        }else if(spendType.equals("luxury")){
//            spinner.setSelection(2);
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btn_camera:
//                TakeImageUtils.showPictureDialog(this);
//                break;
//            case R.id.btn_remove:
//                btnRemove.setVisibility(View.GONE);
//                imageView.setImageBitmap(null);
//                imageView.getLayoutParams().width = 0;
//                imageView.getLayoutParams().height = 0;
//                check = true;
//                break;
            case R.id.btn_spend_back:
                onBackPressed();
                break;
            case R.id.btn_choose_payment:
            {
                Intent intent = new Intent(getApplicationContext(),ExpandLvPayment.class);
                startActivityForResult(intent,2019);
            }
            break;
            case R.id.btn_choose_date:
                dateDialog.show();
                break;
            case R.id.btn_cancel:
                onBackPressed();
                break;
            case R.id.btn_accept:{
                String expense_name,expense_value,note,type = null;
                expense_name = edt_expense_name.getText().toString().trim();
                expense_value = edt_expense_value.getText().toString();
                Date date = Utils.convertStringToDate(txtvdatepayment.getText().toString(), "dd/MM/yyyy");
                date1 = Utils.convertLocalTimeToUTC(date);
                note = edt_note.getText().toString();
                if (temp.equals("")){
                    type = intent.getStringExtra("type");
                }
                else if (temp.equals("Chi tiêu tối thiểu"))
                    type = "min";
                else if (temp.equals("Chi tiêu cơ bản"))
                    type = "base";
                else if (temp.equals("Phong cách sống"))
                    type = "luxury";

                if(imgfile == null){
                    if(check == true){
                        change_icon = "true";
                    }else {
                        change_icon = "false";
                    }
                } else {
                    change_icon = "true";
                }

                if(expense_name.equals("") || expense_value.equals("")){
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                }else if(temp.equals("Chọn chủ đề")){
                    Toast.makeText(this, "Vui lòng chọn chủ đề", Toast.LENGTH_SHORT).show();
                }else if(expense_value.length() > 15){
                    Toast.makeText(this, "Số tiền bạn nhập quá lớn", Toast.LENGTH_SHORT).show();
                } else {
                        ModelExpenseDate modelExpenseDate = new ModelExpenseDate(expense_name, expense_value, date1, type, note, imgfile,iconName,subTypeEdit);
                        CallAPI.updateExpensesDate(this, token, spendId, change_icon, modelExpenseDate, imgfile, new CallAPI.onBackReload() {
                            @Override
                            public void onReload(boolean isReload) {
                                if(isReload) {
                                    HistoryFragment.isReloadDataBack = true;
                                    HistoryFragment.isReloadDataCurrent = true;
                                    finish();
                                }else {
                                    Toast.makeText(EditSpendActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }

            default:
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == this.RESULT_CANCELED) {
//            return;
//        }
//        if (requestCode == Const.GALLERY) {
//            if (data != null) {
//                Uri contentURI = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                    imageView.setImageBitmap(bitmap);
//                    imageView.getLayoutParams().width = 400;
//                    imageView.getLayoutParams().height = 500;
//                    btnRemove.setVisibility(View.VISIBLE);
//                    imgfile = Utils.scaleImage(this, bitmap);
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(EditSpendActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } else if (requestCode == Const.CAMERA) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(thumbnail);
//            imageView.getLayoutParams().width = 400;
//            imageView.getLayoutParams().height = 500;
//            TakeImageUtils.saveImage(thumbnail,this);
//            btnRemove.setVisibility(View.VISIBLE);
//            String path = TakeImageUtils.saveImage(thumbnail, this);
//            imgfile = Utils.scaleImage(this, thumbnail);
//        }
//    }
}
