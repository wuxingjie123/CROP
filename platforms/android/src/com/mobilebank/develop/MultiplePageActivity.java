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

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MultiplePageActivity extends Activity implements View.OnClickListener
{
    private ArrayList<View> mList = new ArrayList<View>();
    private LocalActivityManager localActivityManager = null;
    private ViewPager mViewPager = null;
    private String[] mListTag = {"first", "second"};
    private Button firstBtn, secondBtn;
    private ImageView back;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        localActivityManager = new LocalActivityManager(this, true);
        localActivityManager.dispatchCreate(savedInstanceState);
        initViewPager();
        firstBtn = (Button)findViewById(R.id.firstbtn);
        secondBtn = (Button)findViewById(R.id.secondbtn);
        firstBtn.setOnClickListener(this);
        secondBtn.setOnClickListener(this);
//        back = (ImageView)findViewById(R.id.back);
//        back.setOnClickListener(this);
    }

    private void initViewPager(){
        this.mViewPager = (ViewPager)findViewById(R.id.mViewPager);
        Intent intent1 = new Intent(MultiplePageActivity.this, FirstActivity.class);
        View view1 = getView(mListTag[0], intent1);
        Intent intent2 = new Intent(MultiplePageActivity.this, SecondActivity.class);
        View view2 = getView(mListTag[1], intent2);
        mList.add(view1);
        mList.add(view2);
        mViewPager.setAdapter(new MyPageAdapter(mList));
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private View getView(String id, Intent intent){
        return localActivityManager.startActivity(id, intent).getDecorView();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.firstbtn:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.secondbtn:
                mViewPager.setCurrentItem(1);
                break;
//            case R.id.back:
//                Toast.makeText(MultiplePageActivity.this, "page go back", Toast.LENGTH_LONG).show();
//                break;
        }
    }

    private class MyPageAdapter extends PagerAdapter{

        List<View> mList = new ArrayList<View>();
        public MyPageAdapter(List<View> list){
            this.mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position), 0);
            return mList.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }

    private class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            loadCurActivity(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    private void loadCurActivity(int i){
        Activity curActivity = localActivityManager.getActivity(mListTag[i]);
        switch (i){
            case 0:
                if(curActivity != null && curActivity instanceof FirstActivity){
                    //((FirstActivity)curActivity).firstActivityDo();
                }
                break;
            case 1:
                if(curActivity != null && curActivity instanceof SecondActivity){
                    //((SecondActivity)curActivity).secondActivityDo();
                }
                break;
        }
    }

}
