package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.RecordItem;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.ui.adapter.MemberListAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.ChooseContract;
import com.victor.nesthabit.ui.presenter.ChoosePresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.DateUtils;

import org.litepal.crud.DataSupport;

import java.util.Calendar;

public class ChooseActivity extends BaseActivity implements ChooseContract.View {

    private android.widget.ImageView back;
    private android.widget.RelativeLayout toolbar;
    private android.widget.RelativeLayout choosefriend;
    private android.support.v7.widget.CardView layoutone;
    private android.widget.TextView choosetime;
    private MaterialCalendarView calendar;
    private android.support.v7.widget.CardView layouttwo;
    private android.widget.Button nextstep;
    private RecyclerView list;
    private long id;
    private RecordItem mRecordItem;
    private CalendarDay mCalendarDay;
    private String nestId = null;
    private MemberListAdapter mMemberListAdapter;

    private ChooseContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = new ChoosePresenter(this);
        if (getIntent() != null) {
            nestId = getIntent().getStringExtra("nestId");
        }
        super.onCreate(savedInstanceState);
        id = getIntent().getLongExtra("id", 1);
        mRecordItem = DataSupport.find(RecordItem.class, id);
    }

    @Override
    protected BasePresenter getPresnter() {
        return null;
    }

    @Override
    protected Activity getActivity() {
        return ChooseActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose;
    }

    @Override
    protected void initView() {
        this.nextstep = (Button) findViewById(R.id.next_step);
        this.layouttwo = (CardView) findViewById(R.id.layout_two);
        this.calendar = (MaterialCalendarView) findViewById(R.id.calendar);
        this.choosetime = (TextView) findViewById(R.id.choose_time);
        this.layoutone = (CardView) findViewById(R.id.layout_one);
        this.choosefriend = (RelativeLayout) findViewById(R.id.choose_friend);
        this.toolbar = (RelativeLayout) findViewById(R.id.toolbar);
        this.back = (ImageView) findViewById(R.id.back);
        list = (RecyclerView) findViewById(R.id.list);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(6,
                StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);
        mMemberListAdapter = new MemberListAdapter(ChooseActivity.this, true, false, true,
                nestId);
        list.setAdapter(mMemberListAdapter);
        calendar.state().edit().setMinimumDate(Calendar.getInstance().getTime()
        ).commit();
        calendar.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(ChooseActivity.this);
            }
        });
        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay
                    date, boolean selected) {
                mCalendarDay = date;
            }
        });
        choosefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivityForResult(getActivity(), ChooseFriendActivity.class,
                        123);
            }
        });
        nextstep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void setPresenter(ChooseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    @Override
    public String getChooseDate() {
        return DateUtils.DatetoString(mCalendarDay.getDate());
    }

    @Override
    public String getNestId() {
        return nestId;
    }

    @Override
    public void addItem(UserInfo userInfo) {
        mMemberListAdapter.addItem(userInfo);
    }

    @Override
    public void finishActivity() {
        ActivityManager.finishActivity(getActivity());
    }
}
