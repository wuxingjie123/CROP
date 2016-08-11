package com.mobilebank.develop;

import android.app.Activity;
import android.os.Bundle;

import asp.citic.ptframework.plugin.browser.PTWindowActivity;

/**
 * Created by mj on 3/30/16.
 */
public class FirstActivity extends PTWindowActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchUrl = "app/index.html";
        loadUrl(launchUrl);
    }
}
