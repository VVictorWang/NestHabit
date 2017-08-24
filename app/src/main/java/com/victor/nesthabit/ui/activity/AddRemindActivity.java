package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.service.RecordingService;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.AddRemindContract;
import com.victor.nesthabit.ui.presenter.AddRemindPresenter;
import com.victor.nesthabit.util.ActivityManager;

public class AddRemindActivity extends BaseActivity implements AddRemindContract.View, View
        .OnClickListener {
    private android.widget.ImageView back;
    private android.widget.TextView recordagain;
    private android.widget.ImageView record;
    private android.widget.TextView recordtext;
    private android.widget.Chronometer chronometer;
    private android.widget.Button finish;
    private EditText textinput;
    private ImageView play;
    private Handler mHandler = new Handler();
    private AddRemindContract.Presenter mPresenter;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddRemindPresenter(this);

    }

    @Override
    protected BasePresenter getPresnter() {
        return mPresenter;
    }

    @Override
    protected Activity getActivity() {
        return AddRemindActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_remind;
    }

    @Override
    protected void initView() {
        this.finish = (Button) findViewById(R.id.finish);
        this.chronometer = (Chronometer) findViewById(R.id.chronometer);
        this.recordtext = (TextView) findViewById(R.id.record_text);
        this.record = (ImageView) findViewById(R.id.record);
        this.recordagain = (TextView) findViewById(R.id.record_again);
        this.back = (ImageView) findViewById(R.id.back);
        textinput = (EditText) findViewById(R.id.text_input);
        play = (ImageView) findViewById(R.id.play);
    }

    @Override
    protected void initEvent() {
        record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPresenter.onRecord(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.onRecord(false);
                            }
                        }, 100);
                        record.setVisibility(View.GONE);
                        play.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });
        play.setOnClickListener(this);
        recordagain.setOnClickListener(this);
        back.setOnClickListener(this);
        finish.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish:
                mPresenter.finish();
                break;
            case R.id.record_again:
                mPresenter.recordAgain();
                break;
            case R.id.play:
                mPresenter.Play();
                break;
            case R.id.back:
                ActivityManager.finishActivity(getActivity());
                break;
        }

    }

    @Override
    public void setPresenter(AddRemindContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    //开始录音服务
    @Override
    public void startService() {
        intent = new Intent(AddRemindActivity.this, RecordingService.class);
        startService(intent);
    }

    //结束录音服务
    @Override
    public void stopService() {
        if (intent != null) {
            stopService(intent);
        }
    }

    @Override
    public void startChrometor() {
        //开始计时
        chronometer.setVisibility(View.VISIBLE);
        recordtext.setVisibility(View.INVISIBLE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        addWindowFlags();
    }

    @Override
    public void stopChrometor() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        clearWindowFlags();
    }

    @Override
    public void setPauseImage() {
        play.setImageResource(R.drawable.pause);
    }

    @Override
    public void setPlayImage() {
        play.setImageResource(R.drawable.play);
    }

    @Override
    public void updateTime(Runnable runnable) {
        mHandler.postDelayed(runnable, 1000);

    }

    @Override
    public void removeCallbacks(Runnable runnable) {
        mHandler.removeCallbacks(runnable);
    }

    @Override
    public void setRecordText(String text) {
        recordtext.setText(text);
    }

    @Override
    public void hidePlayButtonshowRecord() {
        play.setVisibility(View.GONE);
        record.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecordButtonshowPlay() {
        record.setVisibility(View.GONE);
        play.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideChormetorShowText() {
        chronometer.setVisibility(View.INVISIBLE);
        recordtext.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTextShowChrometor() {
        recordtext.setVisibility(View.INVISIBLE);
        chronometer.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearWindowFlags() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void addWindowFlags() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public String getRemindText() {
        return textinput.getText().toString();
    }

    @Override
    public void finishActivity() {
        ActivityManager.finishActivity(getActivity());
    }
}
