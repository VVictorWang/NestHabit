package com.victor.nesthabit.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
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
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.AlarmManagerUtil;
import com.victor.nesthabit.view.PickerView;
import com.victor.nesthabit.view.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import static com.victor.nesthabit.R.color.cursor;

public class AddAlarmActivity extends BaseActivity implements View.OnClickListener, AddAlarmContract
        .View {


    private RelativeLayout music_layout, title_layout;
    private PickerView pickview_hour, pickerview_minute;
    private TextView back, titletext, nestname;
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

    private AddAlarmContract.Presenter mPresenter;
    private List<Integer> weeks = new ArrayList<>();
    private long id = -1;
    public static final String TAG = "@victor AddAlarmActi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddAlarmPresenter(this);
        if (getIntent() != null)
            id = getIntent().getLongExtra("id", -1);
        initWeeks();
        mPresenter.start();
    }

    private void initWeeks() {
        for (int i = 0; i < 7; i++) {
            weeks.add(i, 0);
        }
    }

    @Override
    protected Activity getActivity() {
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
        titletext = (TextView) findViewById(R.id.title_text);
        nestname = (TextView) findViewById(R.id.nest_name);
        finish = (Button) findViewById(R.id.finish);
        snaptoogle = (SwitchButton) findViewById(R.id.snap_toogle);
        pickview_hour = (PickerView) findViewById(R.id.pickview_hour);
        pickerview_minute = (PickerView) findViewById(R.id.pickerview_minute);
        music_layout = (RelativeLayout) findViewById(R.id.music_layout);
        title_layout = (RelativeLayout) findViewById(R.id.title_layout);
        initData();
    }

    private void initData() {
        List<String> hours = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hours.add(String.format("%02d", i));
        }
        List<String> minutes = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            minutes.add(String.format("%02d", i));
        }
        pickview_hour.setData(hours);
        pickerview_minute.setData(minutes);
    }


    @Override
    public void setEditToobar() {
        titletext.setText(getString(R.string.edit_alarm));
    }

    @Override
    public void clearText() {
        setEditTitle("");
        title.setHint(getString(R.string.edit_your_title));
        setNestname("");
        nestname.setHint(getString(R.string.please_choose));
        setMusic("");
        music.setHint(getString(R.string.please_choose));
    }

    @Override
    public long getIntentId() {
        return id;
    }

    @Override
    protected void initEvent() {
        music_layout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAlarmActivity.this, MusicSettingActivity.class);
                if (!(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager
                                .PERMISSION_GRANTED)) {
                    requestPermissions(new String[]{Manifest.permission
                            .READ_EXTERNAL_STORAGE}, 101);
                } else {
                    int position = containsInProfile(getMusic());
                    Log.d(TAG, "position : " + position);
                    if (position != -1) {
                        intent.putExtra("profile", position);
                    }
                    ActivityManager.startActivityForResult(getActivity(), intent, 222);
                }

            }
        });
        title_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(getActivity(), AlarmActivity.class);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(getActivity());
            }
        });
        cagelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(getActivity(), ChooseNestActivity.class);
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    private int containsInProfile(String music) {
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media
                .EXTERNAL_CONTENT_URI, null, null, null, null);
        boolean c = cursor == null;
        Log.d(TAG, "cursor: " + c);
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex
                        (MediaStore.Audio.Media
                                .TITLE));
                if (name.equals(music))
                    return cursor.getPosition();

            }
        }

        return -1;
    }

    @Override
    public void setPresenter(AddAlarmContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getSeletedHour() {
        return pickview_hour.getSeletedString();
    }

    @Override
    public void setSeletedHour(String hour) {
        pickview_hour.setSelected(hour);
    }

    @Override
    public String getSeletedMinute() {
        return pickerview_minute.getSeletedString();
    }

    @Override
    public void setSeletedMinute(String minute) {
        pickerview_minute.setSelected(minute);
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
    public void setEditTitle(String titletext) {
        title.setText(titletext);
        if (titletext != null) {
            title.setSelection(title.getText().length());
        }
    }

    @Override
    public void setEditTitleError() {
        title.setError(getString(R.string.edit_your_title));
    }

    @Override
    public String getNestName() {
        return nestname.getText().toString();
    }

    @Override
    public void setNestname(String Nestname) {
        nestname.setText(Nestname);
    }

    @Override
    public void setNestError() {
        nestname.setError(getString(R.string.please_choose));
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
    public void setMusicError() {
        music.setError(getString(R.string.please_choose));
    }

    @Override
    public boolean getSnap() {
        return snaptoogle.getToogle();
    }

    @Override
    public void setSnap(boolean isSnap) {
        if (isSnap) {
            snaptoogle.toggleOn();
        } else
            snaptoogle.toggleOff();
    }

    @Override
    public boolean getVoice() {
        return receivevoice.getToogle();
    }

    @Override
    public void setVoice(boolean isVoice) {
        if (isVoice) {
            receivevoice.toggleOn();
        } else
            receivevoice.toggleOff();
    }

    @Override
    public boolean getRemindText() {
        return receivetext.getToogle();
    }

    @Override
    public void setRemindText(boolean isRemindText) {
        if (isRemindText) {
            receivetext.toggleOn();
        } else
            receivetext.toggleOff();
    }

    @Override
    public void finishActivity() {
        ActivityManager.finishActivity(getActivity());
    }

    @Override
    public void setAlarm(AlarmTime alarm) {
        AlarmManagerUtil.setAlarm(getActivity(), alarm);
    }


}
