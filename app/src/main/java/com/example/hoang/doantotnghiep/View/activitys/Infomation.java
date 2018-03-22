package com.example.hoang.doantotnghiep.View.activitys;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hoang.doantotnghiep.Model.ModelApi.ModelProfile.MessageProfile;
import com.example.hoang.doantotnghiep.Model.ModelApi.model.MessageServer;
import com.example.hoang.doantotnghiep.Model.ModelApi.model.ProfileEvalute;
import com.example.hoang.doantotnghiep.Model.ModelLogin.ModelLogin;
import com.example.hoang.doantotnghiep.Presenter.Login.PresenterLoginAccess;
import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.RestApi.CallAPI;
import com.example.hoang.doantotnghiep.View.Fragment.HomeFragment;
import com.example.hoang.doantotnghiep.utils.BarcodeEncoder;
import com.example.hoang.doantotnghiep.utils.Const;
import com.example.hoang.doantotnghiep.utils.SharedPrefsUtils;
import com.example.hoang.doantotnghiep.utils.TakeImageUtils;
import com.example.hoang.doantotnghiep.utils.Utils;
import com.facebook.AccessToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;


public class Infomation extends AppCompatActivity implements View.OnClickListener {

    String picture = "", id = "", name = "", idgg = "", namegg = "", emailfb = "", emailgg = "", imgprofile = "";
    CircleImageView imguser1;
    TextView txtvuserid, txtvusername1;
    PresenterLoginAccess presenterLogin;
    AccessToken accessToken;
    ModelLogin modelLogin;
    Response<MessageServer> response;
    CallAPI Call = new CallAPI();
    Button btnfb, btngg, btnemail;
    LinearLayout verify, notifycation, language, version, qrcode;
    TextView txtTimeNotifi;
    int hourNotifi;
    int minuteNotifi;
    View btnTimeNotifi;
    long financeHealthy;
    ImageView rating1, rating2, rating3, rating4, rating5;
    String userID = "";
    View imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        imguser1 = findViewById(R.id.img_user1);
        txtvuserid = findViewById(R.id.txtv_usermail1);
        btnfb = findViewById(R.id.btn_logout);
        txtTimeNotifi = findViewById(R.id.txt_time_notifi);
        btnTimeNotifi = findViewById(R.id.btn_time_notifi);
        imgBack = findViewById(R.id.img_back);
        rating1 = findViewById(R.id.rating1);
        rating2 = findViewById(R.id.rating2);
        rating3 = findViewById(R.id.rating3);
        rating4 = findViewById(R.id.rating4);
        rating5 = findViewById(R.id.rating5);
        qrcode = findViewById(R.id.qr_code);
        qrcode.setOnClickListener(this);
        btnTimeNotifi.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        Picasso.with(getApplicationContext()).load(R.drawable.icon_avatar).into(imguser1);

        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        presenterLogin = new PresenterLoginAccess();
        modelLogin = new ModelLogin();

        GetInfo();

        findViewById(R.id.btn_logout).setOnClickListener(this);
        findViewById(R.id.verify_phonenumber).setOnClickListener(this);
        findViewById(R.id.language).setOnClickListener(this);
        findViewById(R.id.notification).setOnClickListener(this);
        findViewById(R.id.version).setOnClickListener(this);
        findViewById(R.id.uploadimg).setOnClickListener(this);
        findViewById(R.id.btn_evaluate).setOnClickListener(this);
        txtTimeNotifi.setText("Lúc: " + hourNotifi + " giờ " + minuteNotifi + " phút");
        int h = SharedPrefsUtils.getIntegerPreference(Infomation.this, "hours_noti", -1);
        int m = SharedPrefsUtils.getIntegerPreference(Infomation.this, "minutes_noti", -1);
        if (h != -1 && m != -1) {
            hourNotifi = h;
            minuteNotifi = m;
            txtTimeNotifi.setText("Lúc: " + hourNotifi + " giờ " + minuteNotifi + " phút");

        }

    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    MessageServer messageServer = new MessageServer();

    private void GetInfo() {
        CallAPI.getProfile(this, new CallAPI.OnCallMessageProfile() {
            @Override
            public void onResult(MessageProfile messageProfile) {
                if (messageProfile != null && messageProfile.getProfile() != null) {
                    String email = SharedPrefsUtils.getStringPreference(getApplicationContext(), "emailInfo");
                    txtvuserid.setText(email);

                    if (messageProfile.getProfile().getFinaceHealthy() != null) {
//                        financeHealthy = messageProfile.getProfile().getFinaceHealthy();
                        handleRating(HomeFragment.percent);
                    }
                    if (messageProfile != null && messageProfile.getProfile() != null)
                        imgprofile = messageProfile.getProfile().getAvatar();
                    if (imgprofile != null && imgprofile.length() > 0)
                        Picasso.with(getApplicationContext()).load(imgprofile).into(imguser1);
                    userID = messageProfile.getProfile().getId();
                    Log.d("FINANCE", String.valueOf(financeHealthy));
                    Log.d("hinh anh", picture);
                }
            }
        });
    }

    public void handleRating(long value) {
        if (value > 20) {
            rating1.setImageResource(R.drawable.star_bright);
            rating2.setImageResource(R.drawable.star_bright);
            rating3.setImageResource(R.drawable.star_bright);
            rating4.setImageResource(R.drawable.star_bright);
            rating5.setImageResource(R.drawable.star_bright);
        } else if (value > 12 && value <= 20) {
            rating1.setImageResource(R.drawable.star_bright);
            rating2.setImageResource(R.drawable.star_bright);
            rating3.setImageResource(R.drawable.star_bright);
            rating4.setImageResource(R.drawable.star_bright);
            rating5.setImageResource(R.drawable.star_transparent);
        } else if (value > 5 && value <= 12) {
            rating1.setImageResource(R.drawable.star_bright);
            rating2.setImageResource(R.drawable.star_bright);
            rating3.setImageResource(R.drawable.star_bright);
            rating4.setImageResource(R.drawable.star_transparent);
            rating5.setImageResource(R.drawable.star_transparent);
        } else if (value > 0 && value <= 5) {
            rating1.setImageResource(R.drawable.star_bright);
            rating2.setImageResource(R.drawable.star_bright);
            rating3.setImageResource(R.drawable.star_transparent);
            rating4.setImageResource(R.drawable.star_transparent);
            rating5.setImageResource(R.drawable.star_transparent);
        } else {
            rating1.setImageResource(R.drawable.star_bright);
            rating2.setImageResource(R.drawable.star_transparent);
            rating3.setImageResource(R.drawable.star_transparent);
            rating4.setImageResource(R.drawable.star_transparent);
            rating5.setImageResource(R.drawable.star_transparent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout: {
                SharedPrefsUtils.deleteKey(getApplicationContext(), "userid");
                SharedPrefsUtils.deleteKey(getApplicationContext(), "useremail");
                SharedPrefsUtils.deleteKey(getApplicationContext(), "emailInfo");
                SharedPrefsUtils.deleteKey(getApplicationContext(), "token");
                Intent logoutemail = new Intent(getApplicationContext(), LoginRegister.class);
                startActivity(logoutemail);
                ResetData();
                SharedPrefsUtils.setStringPreference(Infomation.this, "token", "");
                Toast.makeText(this, "Đăng xuất thành công !", Toast.LENGTH_SHORT).show();
                finish();
            }


            break;
            case R.id.verify_phonenumber: {
                Toast.makeText(this, "Xác thực số điện thoại", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.notification: {
                Toast.makeText(this, "Thông báo", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.language: {
                Toast.makeText(this, "Chọn ngôn ngữ ", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.version: {
                Toast.makeText(this, "Phiên bản đang dùng", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.uploadimg: {
                TakeImageUtils.showPictureDialog(this);
            }
            break;
            case R.id.btn_evaluate: {
                financeHealthy =  HomeFragment.percent;

                CallAPI.updateEvaluteProfile(this, "Bearer " + SharedPrefsUtils.getStringPreference(this, "token"),
                        new ProfileEvalute( (float )financeHealthy));
                if (financeHealthy >= 0) {
                    if (financeHealthy > 20) {
                        showAlertDialog(getResources().getString(R.string.notification),
                                getResources().getString(R.string.rating_1));
                    } else if (financeHealthy > 12 && financeHealthy <= 20) {
                        showAlertDialog(getResources().getString(R.string.notification),
                                getResources().getString(R.string.rating_2));
                    } else if (financeHealthy > 5 && financeHealthy <= 12) {
                        showAlertDialog(getResources().getString(R.string.notification),
                                getResources().getString(R.string.rating_3));
                    } else if (financeHealthy >= 0 && financeHealthy <= 5) {
                        showAlertDialog(getResources().getString(R.string.notification),
                                getResources().getString(R.string.rating_4));
                    } else {
                        showAlertDialog(getResources().getString(R.string.notification),
                                getResources().getString(R.string.rating_5));
                    }
                } else {
                    Intent iEvalute = new Intent(this, EvaluateActivity.class);
                    startActivityForResult(iEvalute, 1000);
                }

            }
            break;

            case R.id.btn_time_notifi:
                showDialog(Const.DIALOG_ID);
                break;
            case R.id.qr_code:
                Log.d("QR_CODE_ID", userID);

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(userID, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    Intent intent = new Intent(this, QRCodeActivity.class);
                    intent.putExtra("pic", bitmap);
                    startActivity(intent);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.img_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }


    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == Const.DIALOG_ID) {
            return new TimePickerDialog(Infomation.this, timePickerListener, hourNotifi, minuteNotifi, false);
        }
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            hourNotifi = hourOfDay;
            minuteNotifi = minute;
            Toast.makeText(Infomation.this, hourNotifi + " : " + minuteNotifi, Toast.LENGTH_SHORT).show();
            txtTimeNotifi.setText("Lúc: " + hourNotifi + " giờ " + minuteNotifi + " phút");
            SharedPrefsUtils.setIntegerPreference(Infomation.this, "hours_noti", hourOfDay);
            SharedPrefsUtils.setIntegerPreference(Infomation.this, "minutes_noti", minute);
            // SharedPrefsUtils.setStringPreference(Infomation.this,"time_noti","Lúc: " + hourNotifi + " giờ " + minuteNotifi + " phút");

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hourNotifi);
            calendar.set(Calendar.MINUTE, minuteNotifi);
            calendar.set(Calendar.SECOND, 0);

            Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                    100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }

        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == Const.GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imguser1.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File imgfile = new File(Utils.getRealPathFromURI(contentURI, this));

                CallAPI.uploadAvatar(this, imgfile);
            }

        } else if (requestCode == Const.CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imguser1.setImageBitmap(thumbnail);
            TakeImageUtils.saveImage(thumbnail, this);
            imguser1.setVisibility(View.VISIBLE);
            String path = TakeImageUtils.saveImage(thumbnail, this);
            File imgfile1 = new File(path);
            CallAPI.uploadAvatar(this, imgfile1);
        }

        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                GetInfo();
            }
        }
    }

    public static void ResetData() {
        HomeActivity.totalRevenue = 0;
        HomeActivity.totalBudget = 0;
        HomeActivity.budgetMin = 0;
        HomeActivity.budgetBase = 0;
        HomeActivity.budgetLuxury = 0;

    }
}
