package com.example.hoang.doantotnghiep.View.activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.doantotnghiep.R;

public class QRCodeActivity extends AppCompatActivity {

    ImageView imageView;
    TextView title_toolsbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        imageView = (ImageView) findViewById(R.id.img_qrcode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title_toolsbar = toolbar.findViewById(R.id.toolbar_title);
        title_toolsbar.setText("QRCODE");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.button_back);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());

        Bitmap bitmap = getIntent().getParcelableExtra("pic");
        imageView.setImageBitmap(bitmap);


    }


}
