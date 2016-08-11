package com.mobilebank.develop;

import android.os.Bundle;
import android.util.Log;

import asp.citic.ptframework.common.exception.PTException;
import asp.citic.ptframework.plugin.browser.PTBrowserContainer;
import asp.citic.ptframework.templatemanager.PTTemplateManager;

/**
 * Created by mj on 3/30/16.
 */
public class SinglePageActivity extends PTBrowserContainer {
    protected String TAG = "SinglePageActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //String launchUrl = "file:///android_asset/www/app/index.html";
        //loadUrl(launchUrl);
        String url = "app/index.html";

        try {
            final String html = PTTemplateManager.getTemplateByUrl(url, new PTTemplateManager.TemplateLoadedListener() {

                @Override
                public void onFinish(String url, String html) {
                    // TODO Auto-generated method stub
                    //Log.d(TAG, "html====>" + html);
                    loadData(url, html);
                }
            });
        } catch (PTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
