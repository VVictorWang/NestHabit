package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapter.MusicListAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.util.ActivityManager;

public class MusicProfileActivity extends BaseActivity {


    private View toolbar;
    private android.support.v7.widget.RecyclerView musiclist;
    private MusicListAdapter adapter;
    private TextView ok;
    private ImageView back;


    @Override
    protected BasePresenter getPresnter() {
        return null;
    }

    @Override
    protected Activity getActivity() {
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
        ok = (TextView) toolbar.findViewById(R.id.right_text);
        back = (ImageView) toolbar.findViewById(R.id.back);
        setToolbar();
        musiclist.setLayoutManager(new LinearLayoutManager(MusicProfileActivity.this));
        adapter = new MusicListAdapter(MusicProfileActivity.this, musiclist, true, null);
        musiclist.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResultData();
                ActivityManager.finishActivity(getActivity());
            }
        });

    }

    private void setResultData() {
        Intent intent = new Intent();
        if (adapter != null && adapter.getMusic() != null) {
            intent.putExtra("name", adapter.getMusic());
            intent.putExtra("musicUri", adapter.getMusicUri());
            setResult(111, intent);
        } else
            setResult(112);
    }

    private void setToolbar() {
        TextView title = (TextView) toolbar.findViewById(R.id.title_text);
        title.setText(getString(R.string.music_profile));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.stopPlaying();
        }
    }
}
