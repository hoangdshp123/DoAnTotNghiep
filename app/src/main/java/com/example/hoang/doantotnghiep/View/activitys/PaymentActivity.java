package com.example.hoang.doantotnghiep.View.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ModelApi.ModelPayMent1.PaymentObject;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.dialog.DateDialog;
import com.example.hoang.doantotnghiep.utils.Utils;

import java.io.File;
import java.util.Date;

/**
 * Created by TWO on 12/26/2017.
 */

public class PaymentActivity extends Activity implements View.OnClickListener {


    private ImageView imageView, btnRemove,imgPayment;
    private Spinner spinner;
    private DateDialog dateDialog;
    private TextView txtvdatepayment,txtNamePayment;
    private EditText edt_expense_name, edt_expense_value, edt_note;
    private File imgfile;
    private String date1;
    private Intent i;
    private String temp="",imgName,subType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
    }

    private void initView() {
//        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
//        imageView = (ImageView) findViewById(R.id.image);
//        btnRemove = (ImageView) findViewById(R.id.btn_remove);
//        btnRemove.setVisibility(View.INVISIBLE);
        txtvdatepayment = findViewById(R.id.txtv_date_payment);
//        btnRemove.setOnClickListener(this);
        edt_expense_name = findViewById(R.id.edt_expense_name);
        edt_expense_value = findViewById(R.id.edt_expense_value);
        edt_note = findViewById(R.id.edt_note);
        txtNamePayment = findViewById(R.id.txtv_name_payment);
        imgPayment = findViewById(R.id.img_payment);


        findViewById(R.id.btn_spend_back).setOnClickListener(this);
        findViewById(R.id.btn_choose_date).setOnClickListener(this);
        findViewById(R.id.btn_accept).setOnClickListener(this);
        findViewById(R.id.btn_choose_payment).setOnClickListener(this);
        dateDialog = new DateDialog(PaymentActivity.this, new DateDialog.DateSelected() {
            @Override
            public void onSelected(String date) {
                Log.d("DATE SELECTED", date);
                String dateTemp = Utils.revertStringDate(date);
                Date d = Utils.convertStringToDate(dateTemp, "dd/MM/yyyy");
                date1 = Utils.convertLocalTimeToUTC(d);
                txtvdatepayment.setText(dateTemp);
                Log.d("DATE CONVERTED", dateTemp);

            }
        }, "history");
//        initSpinner();
    }

//    private void initSpinner() {
//        spinner = (Spinner) findViewById(R.id.spinner_payment);
//        String arr[] = {
//                "Chi tiêu tối thiểu",
//                "Chi tiêu cơ bản",
//                "Chi tiêu xa xỉ"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PaymentActivity.this,
//                android.R.layout.simple_spinner_item, arr);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spinner.setAdapter(adapter);
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
//                break;
            case R.id.btn_spend_back:
                onBackPressed();
                break;
            case R.id.btn_choose_date:
                dateDialog.show();
                break;
            case R.id.btn_cancel:
                onBackPressed();
                break;
            case R.id.btn_accept: {
                String expense_name, expense_value, note, type = null, datetime;
                expense_name = edt_expense_name.getText().toString().trim();
                expense_value = edt_expense_value.getText().toString();
                Date date = Utils.convertStringToDate(txtvdatepayment.getText().toString(), "dd/MM/yyyy");
                note = edt_note.getText().toString();
//                temp = spinner.getSelectedItem().toString();\
                if (expense_name.equals("") || expense_value.equals("")|| txtvdatepayment.getText().toString().equals("")) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                }else if(expense_value.length() > 15){
                    Toast.makeText(this, "Số tiền bạn nhập quá lớn", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        if(temp.equals("")){
                            Toast.makeText(this, "Vui lòng chọn chi tiêu", Toast.LENGTH_SHORT).show();
                        }
                        else if (temp.equals("Chi tiêu tối thiểu"))
                            type = "min";
                        else if (temp.equals("Chi tiêu cơ bản"))
                            type = "base";
                        else if (temp.equals("Phong cách sống"))
                            type = "luxury";

                        datetime = Utils.convertLocalTimeToUTC(date);
                        Log.d("DATE SEND",date1);
                        PaymentObject paymentObject = new PaymentObject(expense_name, expense_value, date1, type, note, imgfile,imgName,subType);
                        CallAPI.insert_Payment(PaymentActivity.this, paymentObject);
                    } catch (Exception e) {

                    }
                }
            }
            break;
            case R.id.btn_choose_payment:
            {
                Intent intent = new Intent(getApplicationContext(),ExpandLvPayment.class);
                startActivityForResult(intent,2019);
            }
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2019 && resultCode == 2020) {
            txtNamePayment.setText(data.getStringExtra("namePayment"));
            imgPayment.setImageResource(Integer.parseInt(data.getStringExtra("imgPayment")));
            imgName = getResources().getResourceEntryName(Integer.parseInt(data.getStringExtra("imgPayment")));
            subType = data.getStringExtra("namePayment");
            temp = data.getStringExtra("title");
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
//
//                    //imgfile = new File(Utils.getRealPathFromURI(contentURI, this));
//                    imgfile = Utils.scaleImage(PaymentActivity.this, bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(PaymentActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } else if (requestCode == Const.CAMERA) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(thumbnail);
//            imageView.getLayoutParams().width = 400;
//            imageView.getLayoutParams().height = 500;
//            TakeImageUtils.saveImage(thumbnail, this);
//            btnRemove.setVisibility(View.VISIBLE);
//            String path = TakeImageUtils.saveImage(thumbnail, this);
//            imgfile = Utils.scaleImage(PaymentActivity.this, thumbnail);
//        }
//    }
}
