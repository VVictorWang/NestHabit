package com.victor.nesthabit.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.AddAlarmContract;
import com.victor.nesthabit.ui.presenter.AddAlarmPresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.view.PickerView;
import com.victor.nesthabit.view.SwitchButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddAlarmActivity extends BaseActivity implements View.OnClickListener, AddAlarmContract
        .View {


    public static final String TAG = "@victor AddAlarmActi";
    private RelativeLayout music_layout, title_layout;
    private PickerView pickview_hour, pickerview_minute;
    private TextView back, titletext, nestname;
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
    private SwitchButton receivevoice;
    private SwitchButton receivetext;
    private SwitchButton snaptoogle;
    private Button finish;
    private AddAlarmContract.Presenter mPresenter;
    private List<Integer> weeks = new ArrayList<>();
    private List<View> mViews = new ArrayList<>();
    private String id = null;
    private String musicUri = null, nestid = null;
    private int volume;
    private boolean isVibrate;
    private File musicFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = new AddAlarmPresenter(this);
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
        }
        initWeeks();
        mPresenter.start();
    }

    @Override
    protected BasePresenter getPresnter() {
        return mPresenter;
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
    public String getIntentId() {
        return id;
    }


    @Override
    public String getMusicUri() {
        return musicUri;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public boolean isVibrate() {
        return isVibrate;
    }


    @Override
    public String getNestid() {
        return nestid;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initEvent() {
        music_layout.setOnClickListener(v -> {
            Intent intent = new Intent(AddAlarmActivity.this, MusicSettingActivity.class);
            if (Build.VERSION.SDK_INT >= 23 && !(checkSelfPermission(Manifest.permission
                    .READ_EXTERNAL_STORAGE) ==
                    PackageManager
                            .PERMISSION_GRANTED)) {
                requestPermissions(new String[]{Manifest.permission
                        .READ_EXTERNAL_STORAGE}, 101);

            } else {
                int position = containsInProfile(getMusic());
                Log.d(TAG, "position : " + position);
                intent.putExtra("profile", position);
                intent.putExtra("musicUri", musicUri);
                intent.putExtra("music", musicFile);
                ActivityManager.startActivityForResult(getActivity(), intent, 222);
            }


        });
        title_layout.setOnClickListener(v -> ActivityManager.startActivity(getActivity(),
                AlarmActivity.class));
        back.setOnClickListener(v -> ActivityManager.finishActivity(getActivity()));
        cagelayout.setOnClickListener(v -> ActivityManager.startActivityForResult(getActivity(),
                ChooseNestActivity.class,
                123));

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

        mViews.add(sunday);
        mViews.add(monday);
        mViews.add(tuesday);
        mViews.add(wednesday);
        mViews.add(thursday);
        mViews.add(friday);
        mViews.add(saturday);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 222:
                if (resultCode == 111) {
                    setMusic("nameTest.omg");
                    musicUri = data.getStringExtra("musicUri");
                    volume = data.getIntExtra("volume", 0);
                    isVibrate = data.getBooleanExtra("isVibrate", false);
                }
                break;
            case 123:
                if (resultCode == 123) {
                    setNestname(data.getStringExtra("nestname"));
                    nestid = data.getStringExtra("nestid");
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

    private void setWeeks() {
        for (int i = 0; i < weeks.size(); i++) {
            if (weeks.get(i) != 0) {
                mViews.get(i).setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                ((TextView) mViews.get(i)).setTextColor(getResources().getColor(R.color.white));
                mViews.get(i).setTag("choosen");
            }
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
                .INTERNAL_CONTENT_URI, null, null, null, null);
        boolean c = cursor == null;
        Log.d(TAG, "cursor: " + c);
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String uri_music = cursor.getString(cursor.getColumnIndex
                        (MediaStore.Audio.Media.DATA));
                String name = new File(uri_music).getName();
                if (name.substring(0, name.lastIndexOf(".")).equals(music)) {
                    return cursor.getPosition();
                }
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
    public void setSelectedWeek(List<Integer> selectedWeek) {
        weeks.clear();
        weeks.addAll(selectedWeek);
        setWeeks();
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
        if ("".equals(name)) {
            music.setText("");
        } else {
            music.setText(name.substring(0, name.lastIndexOf(".")));
        }
    }

    @Override
    public void setMusicFile(File file) {
        musicFile = file;
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
        } else {
            snaptoogle.toggleOff();
        }
    }

    @Override
    public boolean getVoice() {
        return receivevoice.getToogle();
    }

    @Override
    public void setVoice(boolean isVoice) {
        if (isVoice) {
            receivevoice.toggleOn();
        } else {
            receivevoice.toggleOff();
        }
    }

    @Override
    public boolean getRemindText() {
        return receivetext.getToogle();
    }

    @Override
    public void setRemindText(boolean isRemindText) {
        if (isRemindText) {
            receivetext.toggleOn();
        } else {
            receivetext.toggleOff();
        }
    }

    @Override
    public void finishActivity() {
        ActivityManager.finishActivity(getActivity());
    }


    @Override
    public void showMyToast(String description) {
        showToast(description);
    }
}
