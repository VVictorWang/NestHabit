package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.contract.AddAlarmContract;
import com.victor.nesthabit.ui.presenter.AddAlarmPresenter;
import com.victor.nesthabit.utils.ActivityManager;
import com.victor.nesthabit.utils.AlarmManagerUtil;
import com.victor.nesthabit.view.PickerView;
import com.victor.nesthabit.view.SwitchButton;

import java.util.ArrayList;
import java.util.List;

public class AddAlarmActivity extends BaseActivity implements View.OnClickListener, AddAlarmContract
        .View {


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
    private Button finish;

    private String hour = null, minute = null;
    private AddAlarmContract.Presenter mPresenter;
    private List<Integer> weeks = new ArrayList<>();
    private boolean snapon, voice, remind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddAlarmPresenter(this);
        initWeeks();

    }

    private void initWeeks() {
        for (int i = 0; i < 7; i++) {
            weeks.add(i, 0);
        }
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
        finish = (Button) findViewById(R.id.finish);
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
                ActivityManager.startActivityForResult(getActivityToPush(), MusicSettingActivity
                        .class, 222);
            }
        });
        title_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(getActivityToPush(), AlarmActivity.class);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(getActivityToPush());
            }
        });
        receivetext.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                remind = on;
            }
        });
        receivevoice.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                voice = on;
            }
        });
        snaptoogle.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                snapon = on;
            }
        });
        pickview_hour.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                hour = text;
            }
        });
        pickerview_minute.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                minute = text;
            }
        });
        finish.setOnClickListener(this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 222:
                if (resultCode == 111) {
                    setMusic(data.getStringExtra("name"));
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sunday:
                setBackground(v, 0);
                break;
            case R.id.monday:
                setBackground(v, 1);
                break;
            case R.id.tuesday:
                setBackground(v, 2);
                break;
            case R.id.wednesday:
                setBackground(v, 3);
                break;
            case R.id.thursday:
                setBackground(v, 4);
                break;
            case R.id.friday:
                setBackground(v, 5);
                break;
            case R.id.saturday:
                setBackground(v, 6);
                break;
            case R.id.finish:
                mPresenter.finish();

                break;
        }
    }

    private void setBackground(View v, int i) {
        if (v.getTag().equals("unchoosen")) {
            v.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            ((TextView) v).setTextColor(getResources().getColor(R.color.white));
            v.setTag("choosen");
            weeks.set(i, 1);
        } else if (v.getTag().equals("choosen")) {
            v.setBackground(getResources().getDrawable(R.drawable.gray_circle_stroke));
            ((TextView) v).setTextColor(getResources().getColor(R.color.secondary_text));
            v.setTag("unchoosen");
            weeks.set(i, 0);
        }
    }

    @Override
    public void setPresenter(AddAlarmContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getSeletedHour() {
        return hour;
    }

    @Override
    public String getSeletedMinute() {
        return minute;
    }

    @Override
    public List<Integer> getSeletedWeek() {
        return weeks;
    }

    @Override
    public String getEditTitle() {
        return title.getText().toString();
    }

    @Override
    public String getMusic() {
        return music.getText().toString();
    }

    @Override
    public void setMusic(String name) {
        music.setText(name);
    }

    @Override
    public boolean getSnap() {
        return snapon;
    }

    @Override
    public boolean getVoice() {
        return voice;
    }

    @Override
    public boolean getRemindText() {
        return remind;
    }

    @Override
    public void finishActivity() {
        ActivityManager.finishActivity(getActivityToPush());
    }

    @Override
    public void setAlarm(AlarmTime alarm) {
        AlarmManagerUtil.setAlarm(getActivityToPush(), alarm);
    }


}
