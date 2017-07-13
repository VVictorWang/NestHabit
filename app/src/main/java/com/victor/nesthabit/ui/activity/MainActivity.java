package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.RelativeLayout;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapters.MyFragPageAdapter;
import com.victor.nesthabit.base.BaseActivity;
import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.ui.fragments.BirdCageFragment;
import com.victor.nesthabit.utils.AlarmManagerUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private android.support.v4.view.ViewPager birdcageviewpager;
    private android.support.design.widget.TabLayout maintable;
    private android.widget.RelativeLayout drawer;
    private DrawerLayout mDrawerLayout;
    public static final String TAG = "@victor MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpViewPager(birdcageviewpager);
        maintable.setupWithViewPager(birdcageviewpager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Activity getActivityToPush() {
        return MainActivity.this;
    }

    @Override
    protected void initView() {
        this.drawer = (RelativeLayout) findViewById(R.id.drawer);
        this.maintable = (TabLayout) findViewById(R.id.main_table);
        this.birdcageviewpager = (ViewPager) findViewById(R.id.birdcage_view_pager);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
    }

    @Override
    protected void initData() {

    }
    @Override
    protected void initEvent() {
        findViewById(R.id.header_image_pop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_image_pop:
                mDrawerLayout.openDrawer(drawer);
                AlarmTime alarmTime = new AlarmTime();
                alarmTime.setTimeInmillis(System.currentTimeMillis());
                alarmTime.setFrequency(1);
                alarmTime.setAlert_music("dswd");
                alarmTime.save();
                AlarmManagerUtil.setAlarm(MainActivity.this, alarmTime);
                break;

        }
    }

    private void setUpViewPager(ViewPager viewPager) {
        MyFragPageAdapter adapter = new MyFragPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new BirdCageFragment(), "鸟窝");
        viewPager.setAdapter(adapter);
    }

}
