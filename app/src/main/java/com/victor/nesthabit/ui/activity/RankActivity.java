package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapter.MyFragPageAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.fragment.RankTotalFragment;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.WidgetUtils;

public class RankActivity extends BaseActivity {

    private android.widget.ImageView back;
    private android.support.design.widget.TabLayout tab;
    private android.support.v4.view.ViewPager viewpager;
    private String nestid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            nestid = getIntent().getStringExtra("nestId");
        }
        setUpViewPager();
    }

    @Override
    protected BasePresenter getPresnter() {
        return null;
    }

    @Override
    protected Activity getActivity() {
        return RankActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rank;
    }

    @Override
    protected void initView() {
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.tab = (TabLayout) findViewById(R.id.tab);
        this.back = (ImageView) findViewById(R.id.back);

    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(getActivity());
            }
        });

    }

    private void setUpViewPager() {
        MyFragPageAdapter adapter = new MyFragPageAdapter(getSupportFragmentManager());
        adapter.addFragment(RankTotalFragment.newInstance(nestid, RankTotalFragment.TOTAL_TYPE),
                "总打卡");
        adapter.addFragment(RankTotalFragment.newInstance(nestid, RankTotalFragment
                .CONSTANT_TYPE), "连续打卡");
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
        WidgetUtils.setUpIndicatorWidth(tab, 30, 30);
    }


}
