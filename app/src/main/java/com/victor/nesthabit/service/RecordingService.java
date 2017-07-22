package com.victor.nesthabit.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.RecordItem;
import com.victor.nesthabit.utils.LogUtils;
import com.victor.nesthabit.utils.PrefsUtils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

/*
* 录音Service
* Created by victor on 7/5/17.
* email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
* */
public class RecordingService extends Service {
    private static final String LOG_TAG = "RecordingService";
    private String mFileName = null;
    private String mFilePath = null;
    private static OnNewRecordListenner sOnNewRecordListenner = null;
    private MediaRecorder mRecorder = null;
    private RecordItem mRecordItem;
    private long mStartingTimeMillis = 0;
    private TimerTask mIncrementTimerTask = null;
    private long mElapsedMillis = 0;
    private static final String TAG = "RecordingService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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

    //开始录音
    public void startRecording() {
        setFileNameAndPath();
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置输出格式为mp3
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFilePath); //设置输出文件路径
        //采取AAC Low Complexity格式的编码
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //输出频道选为1
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

    //设置录音文件保存路径
    public void setFileNameAndPath() {
        int count = 0;
        File f;
        do {
            count++;
            //设置文件名，并保存成mp3格式
            mFileName = getString(R.string.default_file_name)
                    + "_" + (DataSupport.count(RecordItem.class) + count) + ".mp3";
            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFilePath += "/SoundRecorder/" + mFileName;
            f = new File(mFilePath);
        } while (f.exists() && !f.isDirectory());
    }

    //停止录音
    public void stopRecording() {
        if (mRecorder != null) {
            mRecorder.stop();
            mElapsedMillis = (System.currentTimeMillis() - mStartingTimeMillis);
            mRecorder.release();

            mRecorder = null;
            try {
                mRecordItem = new RecordItem();
                mRecordItem.setName(mFileName);
                mRecordItem.setFile_path(mFilePath);
                mRecordItem.setLength((int) mElapsedMillis);
                mRecordItem.setTime_added(System.currentTimeMillis());
                mRecordItem.save();
                if (sOnNewRecordListenner != null) {
                    //通知录音文件已缓存，以便弹出实时预览的窗口
                    sOnNewRecordListenner.onNewRecordAdded(mRecordItem);
                }
            } catch (Exception e) {
                LogUtils.e(LOG_TAG, "exception ", e);
            }
        }
    }

    //设置监听器
    public static void setOnNewRecordListenner(OnNewRecordListenner listenner) {
        sOnNewRecordListenner = listenner;
    }

    public interface OnNewRecordListenner {
        void onNewRecordAdded(RecordItem item); //录音文件已缓存

        void onNewRecordAddedtoDataBase(RecordItem item); //录音文件实际保存

        void onRecordDeleted(RecordItem item); //录音文件删除
    }
}
