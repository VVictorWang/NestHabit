package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
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
import com.victor.nesthabit.data.RecordItem;
import com.victor.nesthabit.service.RecordingService;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.utils.ActivityManager;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AddRemindActivity extends BaseActivity implements RecordingService
        .OnNewRecordListenner {

    private android.widget.ImageView back;
    private android.widget.TextView recordagain;
    private android.widget.ImageView record;
    private android.widget.TextView recordtext;
    private android.widget.Chronometer chronometer;
    private android.widget.Button finish;
    private EditText textinput;
    private ImageView play;
    private int mRecordPromptCount = 0;
    private boolean finished = false;
    private RecordItem mRecordItem = null;
    private MediaPlayer mMediaPlayer = null;
    private Handler mHandler = new Handler();
    private boolean isPlaying = false;

    //stores minutes and seconds of the length of the file.
    long minutes = 0;
    long seconds = 0;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecordingService.setOnNewRecordListenner(this);

    }

    @Override
    protected Activity getActivityToPush() {
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
                        onRecord(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        onRecord(false);
                        record.setVisibility(View.GONE);
                        play.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecordItem != null) {
                    long itemDuration = mRecordItem.getLength();
                    minutes = TimeUnit.MILLISECONDS.toMinutes(itemDuration);
                    seconds = TimeUnit.MILLISECONDS.toSeconds(itemDuration)
                            - TimeUnit.MINUTES.toSeconds(minutes);
                    onPlay(isPlaying);
                    isPlaying = !isPlaying;
                }

            }
        });
        recordagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecordItem != null) {
                    File file = new File(mRecordItem.getFile_path());
                    file.delete();
                    mRecordItem.delete();
                    mRecordItem = null;
                    play.setVisibility(View.GONE);
                    record.setVisibility(View.VISIBLE);
                    recordtext.setText(getString(R.string.long_record));
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(AddRemindActivity.this);
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    /*
  * 录音
  * @param start 开始录音还是结束录音
  * */
    private void onRecord(boolean start) {
        Intent intent = new Intent(AddRemindActivity.this, RecordingService.class);
        if (start) {
            // 开始录音
            //mPauseButton.setVisibility(View.VISIBLE);
            File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
            if (!folder.exists()) {
                //folder /SoundRecorder doesn't exist, create the folder
                folder.mkdir();
            }
            //开始计时
            chronometer.setVisibility(View.VISIBLE);
            recordtext.setVisibility(View.INVISIBLE);
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            //start RecordingService
            startService(intent);
            //keep screen on while recording
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            //stop recording
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            finished = true;
            stopService(intent);
            //allow the screen to turn off again once recording is finished
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    // Play start/stop
    private void onPlay(boolean isPlaying) {
        if (!isPlaying) {
            //currently MediaPlayer is not playing audio
            if (mMediaPlayer == null) {
                startPlaying(); //start from beginning
            } else {
                resumePlaying(); //resume the currently paused MediaPlayer
            }

        } else {
            //pause the MediaPlayer
            pausePlaying();
        }
    }

    private void startPlaying() {
        play.setImageResource(R.drawable.pause);
        updateSeekBar();
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(mRecordItem.getFile_path());
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying();
            }
        });


        //keep screen on while playing audio
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }



    private void pausePlaying() {
        play.setImageResource(R.drawable.play);
        mHandler.removeCallbacks(mRunnable);
        mMediaPlayer.pause();
    }

    private void resumePlaying() {
        play.setImageResource(R.drawable.pause);
        mHandler.removeCallbacks(mRunnable);
        mMediaPlayer.start();
        updateSeekBar();
    }

    private void stopPlaying() {
        play.setImageResource(R.drawable.play);
        mHandler.removeCallbacks(mRunnable);
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;

        isPlaying = !isPlaying;

        recordtext.setText(String.format("%02d:%02d", minutes, seconds));

        //allow the screen to turn off again once audio is finished playing
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }




    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer != null) {
                int mCurrentPosition = mMediaPlayer.getCurrentPosition();
                //实时算出分钟、秒钟
                long minutes = TimeUnit.MILLISECONDS.toMinutes(mCurrentPosition);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(mCurrentPosition)
                        - TimeUnit.MINUTES.toSeconds(minutes);
                recordtext.setText(String.format("%02d:%02d", minutes, seconds));
                //在update方法里面实现每隔一秒钟更新一次
                updateSeekBar();
            }
        }
    };

    private void updateSeekBar() {
        mHandler.postDelayed(mRunnable, 1000);
    }

    @Override
    public void onNewRecordAdded(RecordItem item) {
        mRecordItem = item;
        long itemDuration = mRecordItem.getLength();
        minutes = TimeUnit.MILLISECONDS.toMinutes(itemDuration);
        seconds = TimeUnit.MILLISECONDS.toSeconds(itemDuration)
                - TimeUnit.MINUTES.toSeconds(minutes);
        chronometer.setVisibility(View.INVISIBLE);
        recordtext.setVisibility(View.VISIBLE);
        recordtext.setText(String.format("%02d:%02d", minutes, seconds));

    }

    @Override
    public void onNewRecordAddedtoDataBase(RecordItem item) {

    }

    @Override
    public void onRecordDeleted(RecordItem item) {

    }
}
