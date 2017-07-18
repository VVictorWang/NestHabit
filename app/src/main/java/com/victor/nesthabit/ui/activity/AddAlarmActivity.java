package com.victor.nesthabit.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.utils.ActivityManager;
import com.victor.nesthabit.view.PickerView;
import com.victor.nesthabit.view.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAlarmActivity extends AppCompatActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.time_picker)
    CardView timePicker;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sunday)
    TextView sunday;
    @BindView(R.id.monday)
    TextView monday;
    @BindView(R.id.tuesday)
    TextView tuesday;
    @BindView(R.id.wednesday)
    TextView wednesday;
    @BindView(R.id.thursday)
    TextView thursday;
    @BindView(R.id.friday)
    TextView friday;
    @BindView(R.id.saturday)
    TextView saturday;
    @BindView(R.id.music)
    TextView music;
    @BindView(R.id.progress_bar)
    SeekBar progressBar;
    @BindView(R.id.volume_layout)
    RelativeLayout volumeLayout;
    @BindView(R.id.snap)
    TextView snap;
    @BindView(R.id.snap_time)
    TextView snapTime;
    @BindView(R.id.receive_voice)
    SwitchButton receiveVoice;
    @BindView(R.id.receive_text)
    SwitchButton receiveText;
    private RelativeLayout music_layout, title_layout;
    private PickerView pickview_hour, pickerview_minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        ButterKnife.bind(this);
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
        pickview_hour = (PickerView) findViewById(R.id.pickview_hour);
        pickerview_minute = (PickerView) findViewById(R.id.pickerview_minute);
        music_layout = (RelativeLayout) findViewById(R.id.music_layout);
        title_layout = (RelativeLayout) findViewById(R.id.title_layout);
        pickview_hour.setData(hours);
        pickerview_minute.setData(minutes);
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
    }


}
