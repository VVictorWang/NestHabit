package com.victor.nesthabit.ui.presenter;

import android.media.MediaPlayer;
import android.os.Environment;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.RecordItem;
import com.victor.nesthabit.service.RecordingService;
import com.victor.nesthabit.ui.contract.AddRemindContract;
import com.victor.nesthabit.util.AppUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class AddRemindPresenter implements AddRemindContract.Presenter, RecordingService
        .OnNewRecordListenner {
    private static OnNewRecordChanged sOnNewRecordChanged;
    long minutes = 0;
    long seconds = 0;
    private AddRemindContract.View mView;
    private MediaPlayer mMediaPlayer = null;
    private RecordItem mRecordItem = null;
    private boolean isPlaying = false;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer != null) {
                int mCurrentPosition = mMediaPlayer.getCurrentPosition();
                //实时算出分钟、秒钟
                long minutes = TimeUnit.MILLISECONDS.toMinutes(mCurrentPosition);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(mCurrentPosition)
                        - TimeUnit.MINUTES.toSeconds(minutes);
                mView.setRecordText(String.format("%02d:%02d", minutes, seconds));
                //在update方法里面实现每隔一秒钟更新一次
                mView.updateTime(mRunnable);
            }
        }
    };

    public AddRemindPresenter(AddRemindContract.View view) {
        mView = view;
        mView.setPresenter(this);
        RecordingService.setOnNewRecordListenner(this);
    }

    public static void setOnNewRecordChanged(OnNewRecordChanged onNewRecordChanged) {
        sOnNewRecordChanged = onNewRecordChanged;
    }

    @Override
    public void start() {

    }

    @Override
    public void unscribe() {

    }

    @Override
    public void onRecord(boolean start) {
        if (start) {
            // 开始录音
            //mPauseButton.setVisibility(View.VISIBLE);
            File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
            if (!folder.exists()) {
                folder.mkdir();
            }
            mView.startService();
            mView.startChrometor();

        } else {
            //stop recording
            mView.stopChrometor();
            mView.stopService();
        }
    }

    @Override
    public void Play() {
        if (mRecordItem != null) {
            long itemDuration = mRecordItem.getLength();
            minutes = TimeUnit.MILLISECONDS.toMinutes(itemDuration);
            seconds = TimeUnit.MILLISECONDS.toSeconds(itemDuration)
                    - TimeUnit.MINUTES.toSeconds(minutes);
            onPlay(isPlaying);
            isPlaying = !isPlaying;
        }
    }

    @Override
    public void recordAgain() {
        if (mRecordItem != null) {
            File file = new File(mRecordItem.getFile_path());
            file.delete();
            mRecordItem.delete();
            mRecordItem = null;
            mView.hidePlayButtonshowRecord();
            mView.setRecordText(AppUtils.getResource().getString(R.string.long_record));
        }
    }

    @Override
    public void finish() {
        if (mView.getRemindText() != null && !mView.getRemindText().equals("")) {
            RecordItem recordItem = new RecordItem();
            recordItem.setVoice(false);
            recordItem.setContent(mView.getRemindText());
            recordItem.save();
            if (sOnNewRecordChanged != null) {
                sOnNewRecordChanged.onNewAdded(recordItem);
            }
        }
        if (mRecordItem != null && sOnNewRecordChanged != null) {
            sOnNewRecordChanged.onNewAdded(mRecordItem);
        }
        mView.finishActivity();
    }

    public void onPlay(boolean isPlaying) {
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
        mView.setPauseImage();
        mView.updateTime(mRunnable);
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
        mView.addWindowFlags();
    }

    private void pausePlaying() {
        mView.setPlayImage();
        mView.removeCallbacks(mRunnable);
        mMediaPlayer.pause();
    }

    private void resumePlaying() {
        mView.setPauseImage();
        mView.removeCallbacks(mRunnable);
        mMediaPlayer.start();
        mView.updateTime(mRunnable);
    }

    private void stopPlaying() {
        mView.setPlayImage();
        mView.removeCallbacks(mRunnable);
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;
        isPlaying = !isPlaying;
        mView.setRecordText(String.format("%02d:%02d", minutes, seconds));
        //allow the screen to turn off again once audio is finished playing
        mView.clearWindowFlags();
    }

    @Override
    public void onNewRecordAdded(RecordItem item) {
        mRecordItem = item;
        long itemDuration = mRecordItem.getLength();
        minutes = TimeUnit.MILLISECONDS.toMinutes(itemDuration);
        seconds = TimeUnit.MILLISECONDS.toSeconds(itemDuration)
                - TimeUnit.MINUTES.toSeconds(minutes);
        mView.hideChormetorShowText();
        mView.setRecordText(String.format("%02d:%02d", minutes, seconds));
    }

    public interface OnNewRecordChanged {
        void onNewAdded(RecordItem recordItem);
    }


}
