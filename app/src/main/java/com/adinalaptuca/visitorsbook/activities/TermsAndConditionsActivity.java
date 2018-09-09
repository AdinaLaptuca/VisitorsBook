package com.adinalaptuca.visitorsbook.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseActivity;

public class TermsAndConditionsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView = new WebView(this);
        webView.loadUrl("file:///android_asset/terms-and-conditions.html");

        setContentView(webView);
    }
}
