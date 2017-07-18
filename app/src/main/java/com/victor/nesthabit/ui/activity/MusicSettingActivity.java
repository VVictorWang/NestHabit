package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapters.MusicListAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.utils.ActivityManager;
import com.victor.nesthabit.view.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicSettingActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.toogle)
    SwitchButton toogle;
    @BindView(R.id.music_profile_layout)
    CardView musicProfileLayout;
    @BindView(R.id.music_list)
    RecyclerView musicList;
    private MusicListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
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
        musicList = (RecyclerView) findViewById(R.id.music_list);
        musicList.setLayoutManager(new LinearLayoutManager(MusicSettingActivity.this));
        musicProfileLayout = (CardView) findViewById(R.id.music_profile_layout);
        adapter = new MusicListAdapter(MusicSettingActivity.this, musicList,
                false);
        musicList.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        musicProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(MusicSettingActivity.this, MusicProfileActivity
                        .class);
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
