package com.victor.nesthabit.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.RecordItem;
import com.victor.nesthabit.service.RecordingService;

import org.litepal.crud.DataSupport;

import java.io.File;

import static android.R.attr.id;

public class RecordFragment extends DialogFragment implements RecordingService.OnNewRecordListenner {
    private FloatingActionButton recordButton;
    private Chronometer chronometer;
    private TextView mRecordingPrompt;
    private int mRecordPromptCount = 0;
    private boolean mStartRecording = true;
    private boolean finished = false;
    public RecordFragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecordingService.setOnNewRecordListenner(this);
        PlayMusicFragment.setRecordListenner(this);
    }

    private static final String TAG = "RecordFragment";

    //录音文件已缓存，弹出实时预览窗口
    @Override
    public void onNewRecordAdded(RecordItem item) {
        if (finished) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            PlayMusicFragment playMusicFragment = PlayMusicFragment.newInstance(item, PlayMusicFragment.STATUS_WAIT_FOR_SAVING);
            playMusicFragment.show(transaction, "record");
        }
    }

    //录音缓存文件实际保存，关闭录音界面
    @Override
    public void onNewRecordAddedtoDataBase(RecordItem item) {
        dismiss();
    }

    @Override
    public void onRecordDeleted(RecordItem item) {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View recordView = getActivity().getLayoutInflater().inflate(R.layout.fragment_record, null);
        recordButton = (FloatingActionButton) recordView.findViewById(R.id.record_button);
        chronometer = (Chronometer) recordView.findViewById(R.id.chronometer);
        mRecordingPrompt = (TextView) recordView.findViewById(R.id.record_status_text);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                mStartRecording = !mStartRecording;
            }
        });

        builder.setView(recordView);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return builder.create();
    }

    /*
    * 录音
    * @param start 开始录音还是结束录音
    * */
    private void onRecord(boolean start) {
        Intent intent = new Intent(getActivity(), RecordingService.class);
        if (start) {
            // 开始录音
            recordButton.setImageResource(R.drawable.ic_media_stop);
            //mPauseButton.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), R.string.toast_recording_start, Toast.LENGTH_SHORT).show();
            File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
            if (!folder.exists()) {
                //folder /SoundRecorder doesn't exist, create the folder
                folder.mkdir();
            }
            //start Chronometer
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if (mRecordPromptCount == 0) {
                        mRecordingPrompt.setText(getString(R.string.record_in_progress) + ".");
                    } else if (mRecordPromptCount == 1) {
                        mRecordingPrompt.setText(getString(R.string.record_in_progress) + "..");
                    } else if (mRecordPromptCount == 2) {
                        mRecordingPrompt.setText(getString(R.string.record_in_progress) + "...");
                        mRecordPromptCount = -1;
                    }
                    mRecordPromptCount++;
                }
            });
            //start RecordingService
            getActivity().startService(intent);
            //keep screen on while recording
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            mRecordingPrompt.setText(getString(R.string.record_in_progress) + "...");
            mRecordPromptCount++;
        } else {
            //stop recording
            recordButton.setImageResource(R.drawable.ic_mic_white_36dp);
            //mPauseButton.setVisibility(View.GONE);
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            mRecordingPrompt.setText(getString(R.string.record_prompt));
            finished = true;
            getActivity().stopService(intent);
            //allow the screen to turn off again once recording is finished
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
}
