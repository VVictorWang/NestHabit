package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.utils.ActivityManager;
import com.victor.nesthabit.view.PickerView;
import com.victor.nesthabit.view.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class AddAlarmActivity extends BaseActivity implements View.OnClickListener{


    private RelativeLayout music_layout, title_layout;
    private PickerView pickview_hour, pickerview_minute;
    private TextView back;
    private CardView timepicker;
    private EditText title;
    private TextView sunday;
    private TextView monday;
    private TextView tuesday;
    private TextView wednesday;
    private TextView thursday;
    private TextView friday;
    private TextView saturday;
    private RelativeLayout cagelayout;
    private TextView music;
    private SeekBar progressbar;
    private RelativeLayout volumelayout;
    private TextView snap;
    private TextView snaptime;
    private SwitchButton receivevoice;
    private SwitchButton receivetext;
    private SwitchButton snaptoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);


    }

    @Override
    protected Activity getActivityToPush() {
        return AddAlarmActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_alarm;
    }

    @Override
    protected void initView() {
        this.receivetext = (SwitchButton) findViewById(R.id.receive_text);
        this.receivevoice = (SwitchButton) findViewById(R.id.receive_voice);
        this.snaptime = (TextView) findViewById(R.id.snap_time);
        this.snap = (TextView) findViewById(R.id.snap);
        this.volumelayout = (RelativeLayout) findViewById(R.id.volume_layout);
        this.progressbar = (SeekBar) findViewById(R.id.progress_bar);
        this.music = (TextView) findViewById(R.id.music);
        this.cagelayout = (RelativeLayout) findViewById(R.id.cage_layout);
        this.saturday = (TextView) findViewById(R.id.saturday);
        this.friday = (TextView) findViewById(R.id.friday);
        this.thursday = (TextView) findViewById(R.id.thursday);
        this.wednesday = (TextView) findViewById(R.id.wednesday);
        this.tuesday = (TextView) findViewById(R.id.tuesday);
        this.monday = (TextView) findViewById(R.id.monday);
        this.sunday = (TextView) findViewById(R.id.sunday);
        this.title = (EditText) findViewById(R.id.title);
        this.timepicker = (CardView) findViewById(R.id.time_picker);
        this.back = (TextView) findViewById(R.id.back);
        snaptoogle = (SwitchButton) findViewById(R.id.snap_toogle);
        pickview_hour = (PickerView) findViewById(R.id.pickview_hour);
        pickerview_minute = (PickerView) findViewById(R.id.pickerview_minute);
        music_layout = (RelativeLayout) findViewById(R.id.music_layout);
        title_layout = (RelativeLayout) findViewById(R.id.title_layout);
        List<String> hours = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hours.add("0" + i);
            } else
                hours.add("" + i);
        }
        List<String> minutes = new ArrayList<>();
        for (int i = 0; i <= 60; i++) {
            if (i < 10) {
                minutes.add("0" + i);
            } else
                minutes.add("" + i);
        }

        pickview_hour.setData(hours);
        pickerview_minute.setData(minutes);
        title.setSelection(title.getText().length());
    }

    @Override
    protected void initEvent() {
        music_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(AddAlarmActivity.this, MusicSettingActivity.class);
            }
        });
        title_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(AddAlarmActivity.this, AlarmActivity.class);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(AddAlarmActivity.this);
            }
        });
        receivetext.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {

            }
        });
        receivevoice.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {

            }
        });
        snaptoogle.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {

            }
        });
        sunday.setOnClickListener(this);
        sunday.setTag("unchoosen");
        monday.setOnClickListener(this);
        monday.setTag("unchoosen");
        tuesday.setOnClickListener(this);
        tuesday.setTag("unchoosen");
        wednesday.setOnClickListener(this);
        wednesday.setTag("unchoosen");
        thursday.setOnClickListener(this);
        thursday.setTag("unchoosen");
        friday.setOnClickListener(this);
        friday.setTag("unchoosen");
        saturday.setOnClickListener(this);
        saturday.setTag("unchoosen");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sunday:
            case R.id.monday:
            case R.id.tuesday:
            case R.id.wednesday:
            case R.id.thursday:
            case R.id.friday:
            case R.id.saturday:
                if (v.getTag().equals("unchoosen")) {
                    v.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    ((TextView) v).setTextColor(getResources().getColor(R.color.white));
                    v.setTag("choosen");
                } else if (v.getTag().equals("choosen")) {
                    v.setBackground(getResources().getDrawable(R.drawable.gray_circle_stroke));
                    ((TextView) v).setTextColor(getResources().getColor(R.color.secondary_text));
                    v.setTag("unchoosen");
                }
                break;
        }
    }
}
