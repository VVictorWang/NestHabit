package com.victor.nesthabit.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.RecordItem;
import com.victor.nesthabit.listenners.OnNewRecordListenner;
import com.victor.nesthabit.utils.LogUtils;
import com.victor.nesthabit.utils.PrefsUtils;
import org.litepal.crud.DataSupport;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class RecordingService extends Service {
    private static final String LOG_TAG = "RecordingService";

    private String mFileName = null;
    private String mFilePath = null;

    private static OnNewRecordListenner sOnNewRecordListenner = null;
    private MediaRecorder mRecorder = null;
    private RecordItem mRecordItem;
    private OnTimerChangedListener onTimerChangedListener = null;
    private long mStartingTimeMillis = 0;
    private Timer mTimer = null;
    private TimerTask mIncrementTimerTask = null;
    private long mElapsedMillis = 0;
    private int mElapsedSeconds = 0;
    private static final SimpleDateFormat mTimerFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());

    Handler handler = new Handler();

    private static final String TAG = "RecordingService";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public interface OnTimerChangedListener {
        void onTimerChanged(int seconds);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRecordItem = new RecordItem();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mRecorder != null) {
            stopRecording();
        }
        super.onDestroy();
    }

    public void startRecording() {
        setFileNameAndPath();
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFilePath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(1);
        if (PrefsUtils.getPrefHighQuality(this)) {
            mRecorder.setAudioSamplingRate(44100);
            mRecorder.setAudioEncodingBitRate(192000);
        }
        try {
            mRecorder.prepare();
            mRecorder.start();
            mStartingTimeMillis = System.currentTimeMillis();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public void setFileNameAndPath() {
        int count = 0;
        File f;

        do {
            count++;
            mFileName = getString(R.string.default_file_name)
                    + "_" + (DataSupport.count(RecordItem.class) + count) + ".mp3";
            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFilePath += "/SoundRecorder/" + mFileName;
            f = new File(mFilePath);
        } while (f.exists() && !f.isDirectory());
    }

    public void stopRecording() {
        mRecorder.stop();
        mElapsedMillis = (System.currentTimeMillis() - mStartingTimeMillis);
        mRecorder.release();

        //remove notification
        if (mIncrementTimerTask != null) {
            mIncrementTimerTask.cancel();
            mIncrementTimerTask = null;
        }

        mRecorder = null;

        try {
            mRecordItem.setName(mFileName);
            mRecordItem.setFile_path(mFilePath);
            mRecordItem.setLength((int) mElapsedMillis);
            mRecordItem.setTime_added(System.currentTimeMillis());
            mRecordItem.save();
            if (sOnNewRecordListenner != null) {
                sOnNewRecordListenner.onNewRecordAdded(mRecordItem.getId());
            }
        } catch (Exception e) {
            LogUtils.e(LOG_TAG, "exception ", e);
        }
    }
    public static void setOnNewRecordListenner(OnNewRecordListenner listenner) {
        sOnNewRecordListenner = listenner;
    }
}
