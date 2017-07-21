package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapters.MemberListAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.utils.ActivityManager;

public class ChooseActivity extends BaseActivity {

    private android.widget.ImageView back;
    private android.widget.RelativeLayout toolbar;
    private android.widget.RelativeLayout choosefriend;
    private android.support.v7.widget.CardView layoutone;
    private android.widget.TextView choosetime;
    private MaterialCalendarView calendar;
    private android.support.v7.widget.CardView layouttwo;
    private android.widget.Button nextstep;
    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected Activity getActivityToPush() {
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
        MemberListAdapter adapter = new MemberListAdapter(ChooseActivity.this,true);
        list.setAdapter(adapter);

    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(ChooseActivity.this);
            }
        });

    }
}
