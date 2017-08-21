package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapter.MusicSettingAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.util.ActivityManager;

public class MusicSettingActivity extends BaseActivity {

    ImageView back;
    private MusicSettingAdapter adapter;
    private android.widget.TextView titletext;
    private android.widget.RelativeLayout toolbar;
    private RecyclerView musiclist;
    private CardView musiclayout;
    private TextView finish;
    private int profileposition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null ) {
            profileposition = getIntent().getIntExtra("profile", -1);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_setting;
    }

    @Override
    protected Activity getActivity() {
        return MusicSettingActivity.this;
    }

    @Override
    protected void initView() {
        this.musiclist = (RecyclerView) findViewById(R.id.music_list);
        this.toolbar = (RelativeLayout) findViewById(R.id.toolbar);
        this.titletext = (TextView) findViewById(R.id.title_text);
        this.back = (ImageView) findViewById(R.id.back);
        finish = (TextView) findViewById(R.id.right_text);
        musiclist.setLayoutManager(new LinearLayoutManager(MusicSettingActivity.this));
        adapter = new MusicSettingAdapter(MusicSettingActivity.this, profileposition);
        musiclist.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(getActivity());
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResultData();
                ActivityManager.finishActivity(getActivity());
            }
        });
    }

    private void setResultData() {
        Intent intent = new Intent();
        if (adapter != null && adapter.getMusicName() != null) {
            intent.putExtra("name", adapter.getMusicName());
            intent.putExtra("musicUri", adapter.getMusicUri());
            setResult(111, intent);
        } else
            setResult(112);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 222:
                if (resultCode == 111) {
                    setResult(111, data);
                    ActivityManager.finishActivity(getActivity());
                }
                break;
        }
    }

    /**
     * activity暂停时停止播放
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.stopPlaying();
        }
    }
}
