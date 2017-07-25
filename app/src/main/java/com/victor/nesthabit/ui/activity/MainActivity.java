package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ProgressBar;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.ui.adapters.MyFragPageAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.contract.MainContract;
import com.victor.nesthabit.ui.fragments.BirdCageFragment;
import com.victor.nesthabit.ui.fragments.ClockFragment;
import com.victor.nesthabit.ui.presenter.MainPresenter;
import com.victor.nesthabit.utils.MusicManger;
import com.victor.nesthabit.utils.PrefsUtils;

import static com.victor.nesthabit.utils.MusicManger.TAG;
import static com.victor.nesthabit.utils.MusicManger.getMusic;

public class MainActivity extends BaseActivity implements MainContract.View {

    private android.support.v4.view.ViewPager birdcageviewpager;
    private android.support.design.widget.TabLayout maintable;
    private TabLayout.Tab nest, clock;
    public static final String TAG = "@victor MainActivity";
    private MainContract.Presenter mPresenter;
//    private ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);
    private long userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter(this);
        mPresenter.start();
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
    public void setUpViewPager() {
        MyFragPageAdapter adapter = new MyFragPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ClockFragment(), "闹钟");
        adapter.addFragment(new BirdCageFragment(), "鸟窝");
        birdcageviewpager.setAdapter(adapter);
        maintable.setupWithViewPager(birdcageviewpager);
    }

    @Override
    public void setTab() {
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
        PrefsUtils.putLongValue(MainActivity.this, GlobalData.USER_ID, userid);
        userId = userid;
    }


}
