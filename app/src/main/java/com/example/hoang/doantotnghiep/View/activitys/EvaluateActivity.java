package com.example.hoang.doantotnghiep.View.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ModelApi.model.ProfileEvalute;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.Fragment.HomeFragment;
import com.example.hoang.doantotnghiep.utils.SharedPrefsUtils;

public class EvaluateActivity extends AppCompatActivity implements View.OnClickListener {
    //finance healthy
    double x = HomeFragment.percent;
    Button btnCancel;
    TextView edtZone;
    EditText edtAge;
    Spinner spGender;
    Spinner spIsMarrige;
    Spinner spIsHaveChildren;
    String token = "";
    int gender = 0;
    int isMarrige = 0;
    int isHaveChildren = 0;
    String zone;
    int age = 0;
    float financeHealthy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        // setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title_toolsbar = toolbar.findViewById(R.id.toolbar_title);
        title_toolsbar.setText(getResources().getString(R.string.add_evalute));
        title_toolsbar.setGravity(Gravity.CENTER);

        token = "Bearer " + SharedPrefsUtils.getStringPreference(this, "token");
        Log.d("TOKEN", token);

        edtZone = findViewById(R.id.edt_zone);
        edtAge = findViewById(R.id.edt_age);
        spGender = findViewById(R.id.spinner_gender);
        spIsMarrige = findViewById(R.id.spinner_is_marrige);
        spIsHaveChildren = findViewById(R.id.spinner_is_have_children);

        edtZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder Dialog = new AlertDialog.Builder(EvaluateActivity.this);
                Dialog.setTitle("Chọn thành phố");
                String dialogItems[] = {
                        "An Giang",
                        "Bà Rịa - Vũng Tàu",
                        "Bắc Giang",
                        "Bắc Kạn",
                        "Bạc Liêu",
                        "Bắc Ninh",
                        "Bến Tre",
                        "Bình Định",
                        "Bình Dương",
                        "Bình Phước",
                        "Bình Thuận",
                        "Cà Mau",
                        "Cao Bằng",
                        "Đắk Lắc",
                        "Đắk Nông",
                        "Điện Biên",
                        "Đồng Nai",
                        "Đồng Tháp",
                        "Gia Lai",
                        "Hà Giang",
                        "Hà Nam",
                        "Hà Tĩnh",
                        "Hải Dương",
                        "Hậu Giang",
                        "Hòa Bình",
                        "Hưng Yên",
                        "Khánh Hòa",
                        "Kiên Giang",
                        "Kon Tum",
                        "Lai Châu",
                        "Lâm Đồng",
                        "Lạng Sơn",
                        "Lào Cai",
                        "Long An",
                        "Nam Định",
                        "Nghệ An",
                        "Ninh Bình",
                        "Ninh Thuận",
                        "Phú Thọ",
                        "Quảng Bình",
                        "Quảng Nam",
                        "Quảng Ngãi",
                        "Quảng Ninh",
                        "Quảng Trị",
                        "Sóc Trăng",
                        "Sơn La",
                        "Tây Ninh",
                        "Thái Bình",
                        "Thái Nguyên",
                        "Thanh Hóa",
                        "Thừa Thiên Huế",
                        "Tiền Giang",
                        "Trà Vinh",
                        "Tuyên Quang",
                        "Vĩnh Long",
                        "Vĩnh Phúc",
                        "Yên Bái",
                        "Phú Yên",
                        "Cần Thơ",
                        "Đà Nẵng",
                        "Hải Phòng",
                        "Hà Nội",
                        "TP HCM"
                };
                Dialog.setItems(dialogItems,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                edtZone.setText(dialogItems[which]);
                                dialog.dismiss();
                            }
                        });
                Dialog.create();
                Dialog.show();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());
        initSpinner();
        findViewById(R.id.btn_evaluatenow).setOnClickListener(this);
        btnCancel = findViewById(R.id.btn_evaluatecancel);
        btnCancel.setOnClickListener(this);
    }

    private void initSpinner() {
        String arr[] = {
                "Nam",
                "Nữ",
                "Khác"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EvaluateActivity.this,
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spGender.setAdapter(adapter);

        String arr1[] = {
                "Độc thân",
                "Đã ly hôn",
                "Đang kết hôn"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(EvaluateActivity.this,
                android.R.layout.simple_spinner_item, arr1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spIsMarrige.setAdapter(adapter1);

        String arr2[] = {
                "Có",
                "Không"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EvaluateActivity.this,
                android.R.layout.simple_spinner_item, arr2);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spIsHaveChildren.setAdapter(adapter2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_evaluatenow: {
                if (!edtZone.getText().toString().trim().equals("") && !edtAge.getText().toString().trim().equals("")) {
                    zone = edtZone.getText().toString().trim();
                    age = Integer.parseInt(edtAge.getText().toString().trim());
                    financeHealthy = (float) x;

                    if (spGender.getSelectedItem().toString().equals("Nam")) {
                        gender = 1;
                    } else if (spGender.getSelectedItem().toString().equals("Nữ")) {
                        gender = 0;
                    }

                    if (spIsMarrige.getSelectedItem().toString().equals("Độc thân")) {
                        isMarrige = 0;
                    } else if (spIsMarrige.getSelectedItem().toString().equals("Đang kết hôn")) {
                        isMarrige = 1;
                    } else if (spIsMarrige.getSelectedItem().toString().equals("Độc ly hôn")) {
                        isMarrige = 2;
                    }

                    if (spIsHaveChildren.getSelectedItem().toString().equals("Có")) {
                        isHaveChildren = 1;
                    } else if (spIsHaveChildren.getSelectedItem().toString().equals("Không")) {
                        isHaveChildren = 0;
                    }


                    //call api
                    CallAPI.updateEvaluteProfile(this, token,
                            new ProfileEvalute(zone, age, gender, isMarrige, isHaveChildren, financeHealthy));

                    if (x > 20) {
                        Toast.makeText(this, "Chúc mừng: Tình hình tài chính của bạn rất tốt, chỉ 5% dân số có thể làm được như bạn. ", Toast.LENGTH_LONG).show();

                    } else if (x > 12 && x <= 20) {
                        Toast.makeText(this, "Chúc mừng: Tình hình tài chính của bạn rất tốt. ", Toast.LENGTH_LONG).show();
                    } else if (x > 5 && x <= 12) {
                        Toast.makeText(this, "Chúc mừng: Tình hình tài chính của bạn rất tốt. Sẽ tốt hơn nếu bạn cải thiện số tiền tiết kiệm được trong các tháng sắp tới  ", Toast.LENGTH_LONG).show();
                    } else if (x > 0 && x <= 5) {
                        Toast.makeText(this, "Số tiền tiết kiệm của bạn quá thấp, bạn có thể muốn cấu trúc lại các khoản chi tiêu của bản thân.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Tình hình nghiêm trọng. Tổng chi tiêu và nợ phải trả của bạn đang lớn hơn tổng thu nhập. Hoặc bạn tìm cách giảm chi tiêu, hoặc tìm cách tăng thu nhập cho mình.  ", Toast.LENGTH_LONG).show();
                    }

                    Intent data = new Intent();
                    data.putExtra("RELOAD","RELOAD");
                    setResult(Activity.RESULT_OK, data);

                    finish();

                } else {
                    Toast.makeText(this, "Bạn hãy vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.btn_evaluatecancel:
                onBackPressed();
                break;
        }
    }
}
