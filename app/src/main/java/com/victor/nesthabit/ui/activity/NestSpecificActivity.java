package com.victor.nesthabit.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.ChatInfo;
import com.victor.nesthabit.bean.PunchInfo;
import com.victor.nesthabit.ui.adapter.MyFragPageAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.NestSpecificContract;
import com.victor.nesthabit.ui.fragment.CommunicateFragment;
import com.victor.nesthabit.ui.fragment.DaKaWallFragment;
import com.victor.nesthabit.ui.presenter.NestSpecificPresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.WidgetUtils;
import com.victor.nesthabit.view.CircleProgressBar;

public class NestSpecificActivity extends BaseActivity implements NestSpecificContract.View {

    public static final String TAG = "@victor NestSpeActivity";
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
    private boolean isOwner = false;
    private long myid = -1;
    private DaKaWallFragment mDaKaWallFragment;
    private CommunicateFragment mCommunicateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = new NestSpecificPresenter(this);
        if (getIntent() != null) {
            id = getIntent().getStringExtra("nestId");
            isOwner = getIntent().getBooleanExtra("isOwner", false);
        }
        super.onCreate(savedInstanceState);
        setUpViewPager();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (mPresenter != null) {
            mPresenter.start();
        }
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

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initEvent() {
        head.setOnTouchListener(new View.OnTouchListener() {
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
        rank.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RankActivity.class);
            intent.putExtra("nestId", id);
            ActivityManager.startActivity(getActivity(), intent);
        });
        introduction.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NestGroupDetailActivity.class);
            intent.putExtra("nestId", id);
            intent.putExtra("isOwner", isOwner);
            ActivityManager.startActivity(getActivity(), intent);
        });
        back.setOnClickListener(v -> ActivityManager.finishActivity(NestSpecificActivity.this));
        daka.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ShareActivity.class);
            intent.putExtra("nestId", id);
            ActivityManager.startActivity(getActivity(), intent);
//                mPresenter.checkin();
        });

    }

    private void setUpViewPager() {
        MyFragPageAdapter adapter = new MyFragPageAdapter(getSupportFragmentManager());
        mDaKaWallFragment = new DaKaWallFragment();
        adapter.addFragment(mDaKaWallFragment, "打卡墙");
        mCommunicateFragment = CommunicateFragment.newInstance(id);
        adapter.addFragment(mCommunicateFragment, "交流板");
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
        WidgetUtils.setUpIndicatorWidth(tab, 30, 30);
    }


    @Override
    public void setPresenter(NestSpecificContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    @Override
    public int getTotalday() {
        return Integer.valueOf(dakatotalnumber.getText().toString());
    }

    @Override
    public void setTotalday(int totalday) {
        dakatotalnumber.setText(String.valueOf(totalday));
    }

    @Override
    public void addPunchIno(PunchInfo punchInfo) {
        mDaKaWallFragment.addItem(punchInfo);
    }

    @Override
    public void clearPunchInfo() {
        if (mDaKaWallFragment != null) {
            mDaKaWallFragment.clearData();
        }
    }

    @Override
    public void addChatInfo(ChatInfo chatInfo) {
        mCommunicateFragment.addItem(chatInfo);
    }

    @Override
    public void clearChatInfo() {
        if (mCommunicateFragment != null) {
            mCommunicateFragment.clearData();
        }
    }

    @Override
    public int getConstantDay() {
        return Integer.valueOf(dakaconsnumber.getText().toString());
    }

    @Override
    public void setConstantDay(int constantDay) {
        dakaconsnumber.setText(String.valueOf(constantDay));
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
    protected BasePresenter getPresnter() {
        return mPresenter;
    }
}
