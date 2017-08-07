package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapter.MyFragPageAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.contract.NestSpecificContract;
import com.victor.nesthabit.ui.fragment.CommunicateFragment;
import com.victor.nesthabit.ui.fragment.DaKaWallFragment;
import com.victor.nesthabit.ui.presenter.NsetSpecificPresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.view.CircleProgressBar;

import java.lang.reflect.Field;

import static android.R.attr.max;

public class NestSpecificActivity extends BaseActivity implements NestSpecificContract.View {

    private android.widget.ImageView back;
    private android.widget.TextView headertext;
    private android.widget.ImageView rank;
    private android.widget.ImageView introduction;
    private android.widget.RelativeLayout toolbar;
    private android.widget.TextView dakatotalnumber;
    private com.victor.nesthabit.view.CircleProgressBar constantprogress, totalprogress;
    private android.widget.TextView dakaconsnumber;
    private android.widget.Button daka;
    private android.support.design.widget.TabLayout tab;
    private android.widget.RelativeLayout toplayout;
    private android.support.v4.view.ViewPager viewpager;
    private RelativeLayout head;
    private NestSpecificContract.Presenter mPresenter;
    private String id = null;
    private long myid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
        }
        mPresenter = new NsetSpecificPresenter(this);
        mPresenter.start();
    }

    @Override
    protected Activity getActivity() {
        return NestSpecificActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nest_specific;
    }

    @Override
    protected void initView() {
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.toplayout = (RelativeLayout) findViewById(R.id.top_layout);
        this.tab = (TabLayout) findViewById(R.id.tab);
        this.daka = (Button) findViewById(R.id.daka);
        this.dakaconsnumber = (TextView) findViewById(R.id.daka_cons_number);
        this.constantprogress = (CircleProgressBar) findViewById(R.id.constant_progress);
        this.dakatotalnumber = (TextView) findViewById(R.id.daka_total_number);
        this.toolbar = (RelativeLayout) findViewById(R.id.toolbar);
        this.introduction = (ImageView) findViewById(R.id.introduction);
        this.rank = (ImageView) findViewById(R.id.rank);
        this.headertext = (TextView) findViewById(R.id.header_text);
        this.back = (ImageView) findViewById(R.id.back);
        head = (RelativeLayout) findViewById(R.id.head);
        totalprogress = (CircleProgressBar) findViewById(R.id.total_progress);
        setUpViewPager();
    }

    @Override
    protected void initEvent() {
        head.setOnTouchListener(new View.OnTouchListener() {
            //            private int lastX;
            private int lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                int x = (int) event.getX();
                int y = (int) event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_UP:
                        int offsetY = y - lastY;
                        if (offsetY < -20) {
                            toplayout.setVisibility(View.GONE);
                        } else if (offsetY > 20) {
                            toplayout.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(NestSpecificActivity.this, RankActivity.class);
            }
        });
        introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(NestSpecificActivity.this, NestGroupDetailActivity
                        .class);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(NestSpecificActivity.this);
            }
        });
        daka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.checkin();
            }
        });

    }

    private void setUpViewPager() {
        MyFragPageAdapter adapter = new MyFragPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new DaKaWallFragment(), "打卡墙");
        adapter.addFragment(new CommunicateFragment(), "交流板");
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
        setUpIndicatorWidth(tab, 30, 30);
    }

    private void setUpIndicatorWidth(TabLayout tabLayout, int marginLeft, int marginRight) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout layout = null;
        try {
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout
                        .LayoutParams.MATCH_PARENT, 1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.setMarginStart(dpToPx(marginLeft));
                    params.setMarginEnd(dpToPx(marginRight));
                }
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将dp转换成px
     *
     * @param dp
     * @return
     */
    public int dpToPx(int dp) {
        return (int) (dp * AppUtils.getAppContext().getResources().getDisplayMetrics().density);
    }

    @Override
    public void setPresenter(NestSpecificContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setTotalday(int totalday) {
        dakatotalnumber.setText(String.valueOf(totalday));
    }

    @Override
    public int getTotalday() {
        return Integer.valueOf(dakatotalnumber.getText().toString());
    }

    @Override
    public void setConstantDay(int constantDay) {
        dakaconsnumber.setText(String.valueOf(constantDay));
    }

    @Override
    public int getConstantDay() {
        return Integer.valueOf(dakaconsnumber.getText().toString());
    }

    @Override
    public String getNestId() {
        return id;
    }

    @Override
    public void setId(long id) {
        myid = id;
    }

    @Override
    public void setToolbar(String title) {
        headertext.setText(title);
    }

    @Override
    public void setMaxProgress(float progress) {
        totalprogress.setMaxProgress((int) progress);
        constantprogress.setMaxProgress((int) progress);
    }

    @Override
    public void setTotalProgress(float progress) {
        totalprogress.setCurrentProgress(progress);
    }

    @Override
    public void setConstantProgresss(float progresss) {
        constantprogress.setCurrentProgress(progresss);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unscribe();
    }
}
