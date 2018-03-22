package com.example.hoang.doantotnghiep.View.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.R;

import java.text.DecimalFormat;

public class AddBorrowActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    EditText edt_money_borrow, edt_borrow_time, edt_money_icome, edt_office, edt_electric_bill,
            edt_money_isurrance, edt_typr_receive_money, edt_address, edt_email, edt_number_phone;
    Button btn_ok;
    int month = 0;
    String tag;
    Intent intent;
    String id;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_borrow);
        intent = getIntent();
        tag = intent.getStringExtra("TagBorrow");
        id = intent.getStringExtra("IdEdit");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        if(tag.equals("2")){
            title.setText("Sửa khoản vay");
        }
        else
        title.setText("Thêm khoản vay");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());
        initView();
    }

    public void initView() {
        edt_money_borrow = findViewById(R.id.edt_money_borrow);
        edt_borrow_time = findViewById(R.id.edt_borrow_time);
        edt_money_icome = findViewById(R.id.edt_money_icome);
        edt_office = findViewById(R.id.edt_office);
        edt_electric_bill = findViewById(R.id.edt_electric_bill);
        edt_money_isurrance = findViewById(R.id.edt_money_isurrance);
        edt_typr_receive_money = findViewById(R.id.edt_typr_receive_money);
        edt_address = findViewById(R.id.edt_address);
        edt_email = findViewById(R.id.edt_email);
        edt_number_phone = findViewById(R.id.edt_number_phone);
        btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        edt_office.setOnClickListener(this);
        edt_office.setOnFocusChangeListener(this);
        edt_typr_receive_money.setOnClickListener(this);
        edt_typr_receive_money.setOnFocusChangeListener(this);
        edt_address.setOnClickListener(this);
        edt_address.setOnFocusChangeListener(this);
        edt_borrow_time.setOnFocusChangeListener(this);
        edt_borrow_time.setOnClickListener(this);

        if (tag.equals("2")){
            edt_money_borrow.setText(intent.getStringExtra("MoneyBorrow"));
            edt_borrow_time.setText(intent.getStringExtra("TimeBorrow"));
            edt_money_icome.setText(intent.getStringExtra("Income"));
            edt_office.setText(intent.getStringExtra("Office"));
            edt_electric_bill.setText(intent.getStringExtra("Electric"));
            edt_money_isurrance.setText(intent.getStringExtra("Isurrance"));
            edt_typr_receive_money.setText(intent.getStringExtra("ReceiveMoney"));
            edt_address.setText(intent.getStringExtra("Adress"));
            edt_email.setText(intent.getStringExtra("Email"));
            edt_number_phone.setText(intent.getStringExtra("PhoneNumber"));
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_office:
                showWorkType();
                break;
            case R.id.edt_typr_receive_money:
                showReceiveMoney();
                break;
            case R.id.edt_address:
                showAddress();
                break;
            case R.id.btn_ok:
                caculate();
                break;
            case R.id.edt_borrow_time:
                showMonthBorrow();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b)
            switch (view.getId()) {
                case R.id.edt_office:
                    showWorkType();
                    break;
                case R.id.edt_typr_receive_money:
                    showReceiveMoney();
                    break;
                case R.id.edt_address:
                    showAddress();
                    break;
                case R.id.edt_borrow_time:
                    showMonthBorrow();
                    break;
                default:
                    break;
            }
    }

    public void showWorkType() {
        AlertDialog.Builder Dialog = new AlertDialog.Builder(AddBorrowActivity.this);
        Dialog.setTitle("Nơi tôi làm việc");
        String dialogItems[] = {
                "Tập đoàn lớn",
                "Công ty trung bình",
                "Công ty nhỏ",
                "Nhà nước",
                "Hộ kinh doanh"
        };
        Dialog.setItems(dialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edt_office.setText(dialogItems[which]);
                        dialog.dismiss();
                    }
                });
        Dialog.create();
        Dialog.show();
    }

    public void showMonthBorrow() {
        AlertDialog.Builder Dialog = new AlertDialog.Builder(AddBorrowActivity.this);
        Dialog.setTitle("Thời gian vay");
        String dialogItems[] = {
                "3",
                "6",
                "9",
                "12",
                "24",
                "48"
        };
        Dialog.setItems(dialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                month = 3;
                                break;
                            case 1:
                                month = 6;
                                break;
                            case 2:
                                month = 9;
                                break;
                            case 3:
                                month = 12;

                                break;
                            case 4:
                                month = 24;
                                break;
                            case 5:
                                month = 48;
                                break;
                            default:
                                break;
                        }
                        edt_borrow_time.setText(dialogItems[which]);
                        dialog.dismiss();
                    }
                });
        Dialog.create();
        Dialog.show();
    }


    public void showReceiveMoney() {
        AlertDialog.Builder Dialog = new AlertDialog.Builder(AddBorrowActivity.this);
        Dialog.setTitle("Hình thức nhận lương");
        String dialogItems[] = {
                "Chuyển khoản",
                "Tiền mặt"
        };
        Dialog.setItems(dialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edt_typr_receive_money.setText(dialogItems[which]);
                        dialog.dismiss();
                    }
                });
        Dialog.create();
        Dialog.show();
    }

    public void showAddress() {
        AlertDialog.Builder Dialog = new AlertDialog.Builder(AddBorrowActivity.this);
        Dialog.setTitle("Địa điểm");
        String dialogItems[] = {
                "Hà Nội",
                "TP.HCM",
                "Hải Phòng",
                "Đà Nẵng",
                "Cần Thơ",
                "Nha Trang",
                "Nơi khác"
        };
        Dialog.setItems(dialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edt_address.setText(dialogItems[which]);
                        dialog.dismiss();
                    }
                });
        Dialog.create();
        Dialog.show();
    }

    public void caculate() {
        String money = edt_money_borrow.getText().toString();
        String time_borrow = edt_borrow_time.getText().toString();
        String money_income = edt_money_icome.getText().toString();
        String office = edt_office.getText().toString();
        String eletric_money = edt_electric_bill.getText().toString();
        String isurrance_money = edt_money_isurrance.getText().toString();
        String type_receive_money = edt_typr_receive_money.getText().toString();
        String address = edt_address.getText().toString();
        String email = edt_email.getText().toString();
        String phone = edt_number_phone.getText().toString();
        double total_money_pay = 0.0;


        if (money.equals("") || time_borrow.equals("") || money_income.equals("") || office.equals("") || eletric_money.equals("")
                || isurrance_money.equals("") || type_receive_money.equals("") || address.equals("") || email.equals("") || phone.equals("")) {
            Toast.makeText(this, "Bạn chưa nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.length() < 10)
            Toast.makeText(this, "Không đúng định dạng số điện thoại", Toast.LENGTH_SHORT).show();
        else {
            if (email.length() > 4 && email.matches(emailPattern)) {
                double a = 0.0, b = 0.0, c = 0.0;
                DecimalFormat df2 = new DecimalFormat("#,###,###,###,###,###");

                double no_dau_thang = Double.parseDouble(money);
                int n = Integer.parseInt(time_borrow);
                double r2 = (2.4 / 12.0) / 100.0;
                double pvifa2 = (1 - Math.pow((1 + r2), -n)) / r2;
                double tien_lai_va_goc2 = no_dau_thang / pvifa2;

                Intent i = new Intent(this, ViewCaculateBorrow.class);
                i.putExtra("MoneyBorrow1", money);
                i.putExtra("TimeBorrow1", time_borrow);
                i.putExtra("Income1", money_income);
                i.putExtra("Office1", office);
                i.putExtra("Electric1", eletric_money);
                i.putExtra("Isurrance1", isurrance_money);
                i.putExtra("ReceiveMoney1", type_receive_money);
                i.putExtra("Adress1", address);
                i.putExtra("Email1", email);
                i.putExtra("PhoneNumber1", phone);
                i.putExtra("IdEdit1", id);
                i.putExtra("PayPerMonth", String.valueOf(tien_lai_va_goc2));


                if (tag.equals("2")) {
                    i.putExtra("TagBorrow1", "2");
                } else {
                    i.putExtra("TagBorrow1", "1");
                }
                startActivityForResult(i,2022);
            } else Toast.makeText(this, "Không đúng định dạng email", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2022 && resultCode == 2021) {
            finish();
        }
    }

}
