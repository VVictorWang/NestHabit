package com.victor.nesthabit.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.contract.NestGroupDetailContract;
import com.victor.nesthabit.ui.presenter.NestGroupDetailPresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.view.CircleImageView;
import com.victor.nesthabit.view.SwitchButton;

import java.util.Calendar;

public class NestGroupDetailActivity extends BaseActivity implements NestGroupDetailContract.View {

    private View toolbar;
    private android.widget.EditText name;
    private android.widget.RelativeLayout memberlayout;
    private android.widget.EditText day;
    private android.widget.TextView starttime;
    private android.widget.RelativeLayout starttimelayout;
    private com.victor.nesthabit.view.SwitchButton limitamounttoogle;
    private android.widget.EditText amount;
    private com.victor.nesthabit.view.SwitchButton opentoogle;
    private android.widget.TextView quit;
    private android.widget.TextView loginway;
    private android.widget.RelativeLayout invitationtoplayout;
    private CardView remind;
    private com.victor.nesthabit.view.CircleImageView qq;
    private com.victor.nesthabit.view.CircleImageView qzone;
    private com.victor.nesthabit.view.CircleImageView wtchat;
    private com.victor.nesthabit.view.CircleImageView moment;
    private ImageView back;
    private TextView title;
    private NestGroupDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new NestGroupDetailPresenter(this);

    }

    @Override
    protected Activity getActivity() {
        return NestGroupDetailActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nest_group_detail;
    }

    @Override
    protected void initView() {
        this.moment = (CircleImageView) findViewById(R.id.moment);
        this.wtchat = (CircleImageView) findViewById(R.id.wtchat);
        this.qzone = (CircleImageView) findViewById(R.id.qzone);
        this.qq = (CircleImageView) findViewById(R.id.qq);
        this.invitationtoplayout = (RelativeLayout) findViewById(R.id.invitation_top_layout);
        this.loginway = (TextView) findViewById(R.id.login_way);
        this.quit = (TextView) findViewById(R.id.quit);
        this.opentoogle = (SwitchButton) findViewById(R.id.open_toogle);
        this.amount = (EditText) findViewById(R.id.amount);
        this.limitamounttoogle = (SwitchButton) findViewById(R.id.limit_amount_toogle);
        this.starttimelayout = (RelativeLayout) findViewById(R.id.start_time_layout);
        this.starttime = (TextView) findViewById(R.id.start_time);
        this.day = (EditText) findViewById(R.id.day);
        this.memberlayout = (RelativeLayout) findViewById(R.id.member_layout);
        this.name = (EditText) findViewById(R.id.name);
        this.toolbar = findViewById(R.id.toolbar);
        remind = (CardView) findViewById(R.id.layout_four);
        back = (ImageView) (toolbar.findViewById(R.id.back));
        title = (TextView) (toolbar.findViewById(R.id.title_text));
        setToolbar();
    }

    private void setToolbar() {
        title.setText("早起的鸟儿有虫吃");
        (toolbar.findViewById(R.id.right_text)).setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initEvent() {
        memberlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(NestGroupDetailActivity.this, MemberListActivity
                        .class);
            }
        });
        starttimelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout
                        .calendar_dialog, null);
                TextView finish = (TextView) view.findViewById(R.id.finish);
                MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id
                        .calendar);
                calendarView.state().edit().setMinimumDate(Calendar.getInstance().getTime()
                ).commit();
                calendarView.setSelectedDate(CalendarDay.from(DateUtils.StringToDate(getStartTime
                        ())));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
                finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setStartTime(DateUtils.DatetoString(calendarView.getSelectedDate().getDate
                                ()));
                        dialog.dismiss();
                    }
                });
            }
        });
        remind.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23 && !((checkSelfPermission(Manifest.permission
                        .WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) && checkSelfPermission(Manifest
                        .permission.RECORD_AUDIO) ==
                        PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO},
                            101);
                } else {
                    ActivityManager.startActivity(NestGroupDetailActivity.this, RemindFriendActivity
                            .class);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(NestGroupDetailActivity.this);
            }
        });
        limitamounttoogle.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {

            }
        });
        opentoogle.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {

            }
        });

    }

    @Override
    public void setPresenter(NestGroupDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    @Override
    public String getIntroduction() {
        return name.getText().toString();
    }

    @Override
    public int getChalengeDay() {
        return Integer.valueOf(day.getText().toString());
    }

    @Override
    public String getStartTime() {
        return starttime.getText().toString();
    }

    @Override
    public void setStartTime(String date) {
        starttime.setText(date);
    }

    @Override
    public void setAmount(int amount) {

    }

    @Override
    public boolean isLimited() {
        return false;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void setMaxAmount(int amount) {

    }
}
