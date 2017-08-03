package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapter.MemberListAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.util.ActivityManager;

public class ChooseFriendActivity extends BaseActivity {
    private View toolbar;
    private android.support.v7.widget.RecyclerView list;
    private ImageView back;
    private TextView title;
    private TextView manage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Activity getActivity() {
        return ChooseFriendActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_friend;
    }

    @Override
    protected void initView() {
        this.list = (RecyclerView) findViewById(R.id.list);
        this.toolbar = findViewById(R.id.toolbar);
        setToolbar();
        MemberListAdapter adapter = new MemberListAdapter(getActivity(), false, true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(getActivity());
            }
        });
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private void setToolbar() {
        back = (ImageView) (toolbar.findViewById(R.id.back));
        title = (TextView) (toolbar.findViewById(R.id.title_text));
        manage = (TextView) (toolbar.findViewById(R.id.right_text));
        title.setText(getString(R.string.choose_friend));
        manage.setText(getString(R.string.finish));
    }
}
