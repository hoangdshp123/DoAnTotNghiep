package com.example.hoang.doantotnghiep.View.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hoang.doantotnghiep.R;

public class WebCaculateActivity extends AppCompatActivity {
    WebView wvCaculate;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_caculate);

        Bundle extras = getIntent().getExtras();
        Intent bundle= getIntent();
        if(bundle!=null) {
            url = extras.getString("url");
        }
        wvCaculate = (WebView) findViewById(R.id.wv_caculate);
        wvCaculate.setWebViewClient(new WebViewClient());

        wvCaculate.getSettings().setLoadsImagesAutomatically(true);
        wvCaculate.getSettings().setJavaScriptEnabled(true);
        wvCaculate.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvCaculate.getSettings().setDomStorageEnabled(true);
        wvCaculate.getSettings().setAppCacheEnabled(true);

        wvCaculate.getSettings().setPluginState(WebSettings.PluginState.ON);
        wvCaculate.getSettings().setJavaScriptEnabled(true);
        wvCaculate.getSettings().setUseWideViewPort(true);
        wvCaculate.getSettings().setLoadWithOverviewMode(true);
        wvCaculate.getSettings().setPluginState(WebSettings.PluginState.ON);
        wvCaculate.getSettings().setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/cache");
        wvCaculate.getSettings().setDatabaseEnabled(true);
        wvCaculate.getSettings().setJavaScriptEnabled(true);
        wvCaculate.getSettings().setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/databases");
        wvCaculate.loadUrl(url);
    }
}
