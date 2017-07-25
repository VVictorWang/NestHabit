package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapters.MusicListAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;

public class MusicProfileActivity extends BaseActivity {


    private View toolbar;
    private android.support.v7.widget.RecyclerView musiclist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected Activity getActivityToPush() {
        return MusicProfileActivity.this;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_profile;
    }

    @Override
    protected void initView() {
        this.musiclist = (RecyclerView) findViewById(R.id.music_list);
        this.toolbar = findViewById(R.id.toolbar);
        setToolbar();
        musiclist.setLayoutManager(new LinearLayoutManager(MusicProfileActivity.this));
        MusicListAdapter adapter = new MusicListAdapter(MusicProfileActivity.this, musiclist, true);
        musiclist.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {

    }

    private void setToolbar() {
        TextView title = (TextView) toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.music_profile));
    }
}
