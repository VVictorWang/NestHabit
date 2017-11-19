package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.AlarmTime;
import com.victor.nesthabit.bean.Constants;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.ui.adapter.MyFragPageAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.MainContract;
import com.victor.nesthabit.ui.fragment.ClockListFragment;
import com.victor.nesthabit.ui.fragment.NestListFragment;
import com.victor.nesthabit.ui.presenter.MainPresenter;
import com.victor.nesthabit.util.PrefsUtils;

import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View {

    public static final String TAG = "@victor MainActivity";
    private android.support.v4.view.ViewPager birdcageviewpager;
    private android.support.design.widget.TabLayout maintable;
    private TabLayout.Tab nest, clock;
    private MainContract.Presenter mPresenter;
    private ClockListFragment mClockListFragment;
    private NestListFragment mNestListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = new MainPresenter(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Activity getActivity() {
        return MainActivity.this;
    }

    @Override
    protected void initView() {
        this.maintable = findViewById(R.id.main_table);
        this.birdcageviewpager = findViewById(R.id.birdcage_view_pager);
        setUpViewPager();
        setTab();
    }


    @Override
    protected void initEvent() {
        maintable.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == maintable.getTabAt(0)) {
                    clock.setIcon(getResources().getDrawable(R.drawable.clock_clicked));
                    birdcageviewpager.setCurrentItem(0);
                } else if (tab == maintable.getTabAt(1)) {
                    nest.setIcon(getResources().getDrawable(R.drawable.nest_click));
                    birdcageviewpager.setCurrentItem(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == maintable.getTabAt(0)) {
                    clock.setIcon(getResources().getDrawable(R.drawable.clock_unclick));
                } else if (tab == maintable.getTabAt(1)) {
                    nest.setIcon(getResources().getDrawable(R.drawable.nest_normal));
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    private void setUpViewPager() {
        MyFragPageAdapter adapter = new MyFragPageAdapter(getSupportFragmentManager());
        mClockListFragment = new ClockListFragment();
        mNestListFragment = new NestListFragment();
        adapter.addFragment(mClockListFragment, "闹钟");
        adapter.addFragment(mNestListFragment, "鸟窝");
        birdcageviewpager.setAdapter(adapter);
        maintable.setupWithViewPager(birdcageviewpager);
    }

    private void setTab() {
        clock = maintable.getTabAt(0);
        nest = maintable.getTabAt(1);
        clock.setIcon(getResources().getDrawable(R.drawable.clock_clicked));
        nest.setIcon(getResources().getDrawable(R.drawable.nest_normal));
        birdcageviewpager.setCurrentItem(0);
    }

    @Override
    public void showProgress() {
//        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
//        mProgressDialog.dismiss();

    }

    @Override
    public void saveUserId(long userid) {
        PrefsUtils.putLongValue(MainActivity.this, Constants.USER_ID, userid);
    }

    @Override
    public void addAlarmTime(AlarmTime alarmTime) {
        mClockListFragment.addData(alarmTime);
    }

    @Override
    public void addNestInfo(NestInfo nestInfo) {
        mNestListFragment.addData(nestInfo);
    }


    @Override
    protected BasePresenter getPresnter() {
        return mPresenter;
    }
}
