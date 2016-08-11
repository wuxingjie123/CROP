package com.mobilebank.develop;

import android.os.Bundle;

import asp.citic.ptframework.plugin.browser.PTWindowActivity;

/**
 * Created by mj on 3/30/16.
 */
public class SecondActivity extends PTWindowActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchUrl = "examples/demo/test2.html";
        loadUrl(launchUrl);
    }
}
