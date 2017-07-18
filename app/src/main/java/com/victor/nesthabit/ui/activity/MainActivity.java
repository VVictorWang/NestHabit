package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapters.MyFragPageAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.fragments.BirdCageFragment;
import com.victor.nesthabit.ui.fragments.ClockFragment;
import com.victor.nesthabit.utils.MusicManger;

import static com.victor.nesthabit.utils.MusicManger.TAG;
import static com.victor.nesthabit.utils.MusicManger.getMusic;

public class MainActivity extends BaseActivity {

    private android.support.v4.view.ViewPager birdcageviewpager;
    private android.support.design.widget.TabLayout maintable;
    public static final String TAG = "@victor MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpViewPager(birdcageviewpager);
        maintable.setupWithViewPager(birdcageviewpager);
        String[] names = MusicManger.getMusic(MainActivity.this);
        for (int i = 0; i < names.length; i++) {
            Log.e(TAG, names[i] + "   ");
        }
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
        this.maintable = (TabLayout) findViewById(R.id.main_table);
        this.birdcageviewpager = (ViewPager) findViewById(R.id.birdcage_view_pager);
    }


    @Override
    protected void initEvent() {

    }

    private void setUpViewPager(ViewPager viewPager) {
        MyFragPageAdapter adapter = new MyFragPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new BirdCageFragment(), "鸟窝");
        adapter.addFragment(new ClockFragment(), "闹钟");
        viewPager.setAdapter(adapter);
    }
}
