package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapters.MusicSettingAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.utils.ActivityManager;

public class MusicSettingActivity extends BaseActivity {

    ImageView back;
    private MusicSettingAdapter adapter;
    private android.widget.TextView titletext;
    private android.widget.RelativeLayout toolbar;
    private RecyclerView musiclist;
    private CardView musiclayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_setting;
    }

    @Override
    protected Activity getActivityToPush() {
        return MusicSettingActivity.this;
    }

    @Override
    protected void initView() {
        this.musiclist = (RecyclerView) findViewById(R.id.music_list);
        this.toolbar = (RelativeLayout) findViewById(R.id.toolbar);
        this.titletext = (TextView) findViewById(R.id.title_text);
        this.back = (ImageView) findViewById(R.id.back);
        musiclist.setLayoutManager(new LinearLayoutManager(MusicSettingActivity.this));
        adapter = new MusicSettingAdapter(MusicSettingActivity.this);
        musiclist.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(getActivityToPush());
            }
        });
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
