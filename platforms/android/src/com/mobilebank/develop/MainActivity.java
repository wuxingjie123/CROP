/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.mobilebank.develop;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import org.apache.cordova.*;
import asp.citic.ptframework.PTFramework;

public class MainActivity extends CordovaActivity implements View.OnClickListener
{
    private Button singleBtn, multipleBtn;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        PTFramework.initialization(getApplication());
        // Set by <content src="index.html" /> in config.xml
        //loadUrl(launchUrl);
        setContentView(R.layout.welcome);
        singleBtn = (Button)findViewById(R.id.single);
        singleBtn.setOnClickListener(this);
        multipleBtn = (Button)findViewById(R.id.multiple);
        multipleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.single:
                intent = new Intent(MainActivity.this, SinglePageActivity.class);
                startActivity(intent);
                break;
            case R.id.multiple:
                intent = new Intent(MainActivity.this, MultiplePageActivity.class);
                startActivity(intent);
                break;
        }
    }
}
