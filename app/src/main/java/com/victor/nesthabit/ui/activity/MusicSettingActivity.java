package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.base.BaseActivity;
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

    }

    @Override
    protected void initEvent() {

    }
}
