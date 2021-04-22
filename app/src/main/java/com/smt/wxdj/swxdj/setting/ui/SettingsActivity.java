package com.smt.wxdj.swxdj.setting.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.setting.ui.fragment.SetDockFragment;
import com.smt.wxdj.swxdj.setting.ui.fragment.SetInfoFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * 设置界面
 */
public class SettingsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private FragmentPagerAdapter mAdapter;
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private List<Fragment> mTabContents;
    public static final String SETTINGTYPE = "SETTINGTYPE";
    public static final String TYPE_ADMINISTRATORS = "TYPE_ADMINISTRATORS";
    public static final String TYPE_USER = "TYPE_USER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        if (null == intent) return;
        String type = intent.getStringExtra(SETTINGTYPE);
        if (TextUtils.isEmpty(type)) return;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.radio1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.radio2:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });

        mTabContents = new ArrayList<>();
        if (TYPE_ADMINISTRATORS.equals(type)) {
            mTabContents.add(SetDockFragment.newInstance());
        } else {
            radioGroup.setVisibility(View.GONE);
            toolbar.setTitle(getString(R.string.menu_setting));
        }
        mTabContents.add(SetInfoFragment.newInstance());

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }
        };
        viewPager.setAdapter(mAdapter);
        viewPager.setOnPageChangeListener(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.radio1);
                break;
            case 1:
                radioGroup.check(R.id.radio2);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
