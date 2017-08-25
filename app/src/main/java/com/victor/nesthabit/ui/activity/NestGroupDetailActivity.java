package com.victor.nesthabit.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
import com.victor.nesthabit.ui.base.BasePresenter;
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
    private android.widget.TextView starttime, introduction;
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
    private String nestId = null;
    private boolean isOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            nestId = getIntent().getStringExtra("nestId");
            isOwner = getIntent().getBooleanExtra("isOwner", false);
        }
        super.onCreate(savedInstanceState);
        mPresenter = new NestGroupDetailPresenter(this);
    }

    @Override
    protected BasePresenter getPresnter() {
        return mPresenter;
    }

    @Override
    protected Activity getActivity() {
        return NestGroupDetailActivity.this;
    }

    @Override
    protected int getLayoutId() {
        if (isOwner)
            return R.layout.activity_nest_group_detail;
        return R.layout.activity_nest_group_detail_normal;
    }

    @Override
    protected void initView() {
        this.moment = (CircleImageView) findViewById(R.id.moment);
        this.wtchat = (CircleImageView) findViewById(R.id.wtchat);
        this.qzone = (CircleImageView) findViewById(R.id.qzone);
        this.qq = (CircleImageView) findViewById(R.id.qq);
        this.invitationtoplayout = (RelativeLayout) findViewById(R.id.invitation_top_layout);
        this.introduction = (TextView) findViewById(R.id.introduction);
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
        (toolbar.findViewById(R.id.right_text)).setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initEvent() {
        memberlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MemberListActivity.class);
                intent.putExtra("nestId", nestId);
                intent.putExtra("isOwner", isOwner);
                ActivityManager.startActivity(getActivity(), intent);
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
        if (isOwner) {
            limitamounttoogle.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
                @Override
                public void onToggle(boolean on) {
                    if (on) {
                        setAmountEnabled(true);
                    } else
                        setAmountEnabled(false);
                }
            });
        }

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
    public void setDes(String des) {
        name.setText(des);
    }

    @Override
    public boolean isOwner() {
        return isOwner;
    }

    @Override
    public String getNestid() {
        return nestId;
    }

    @Override
    public void setTitle(String titleString) {
        title.setText(titleString);
    }

    @Override
    public int getChalengeDay() {
        return Integer.valueOf(day.getText().toString());
    }

    @Override
    public void setChalengeDay(int days) {
        day.setText(days + "");
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
        introduction.setText(amount + "人");
    }

    @Override
    public void setAmountEnabled(boolean enabled) {
        amount.setEnabled(enabled);
    }

    @Override
    public boolean isLimited() {
        return limitamounttoogle.getToogle();
    }

    @Override
    public void setLimited(boolean limited) {
        if (limited) {
            limitamounttoogle.toggleOn();
        } else
            limitamounttoogle.toggleOff();
    }

    @Override
    public boolean isOpen() {
        return opentoogle.getToogle();
    }

    @Override
    public void setOpen(boolean open) {
        if (open) {
            opentoogle.toggleOn();
        } else
            opentoogle.toggleOff();
    }

    @Override
    public void setMaxAmount(int amounts) {
        amount.setText(amounts + "");
    }
}
