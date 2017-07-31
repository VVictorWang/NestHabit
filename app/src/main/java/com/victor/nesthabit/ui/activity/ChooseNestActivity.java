package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.MyNestInfo;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.ui.adapter.ChooseNestAdapter;
import com.victor.nesthabit.ui.adapter.NestListFragAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.contract.NestListContract;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.DataCloneUtil;
import com.victor.nesthabit.view.CircleImageView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ChooseNestActivity extends BaseActivity {
    private RecyclerView mRecyclerView;


    private ChooseNestAdapter mNestListFragAdapter;
    private TextView finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<MyNestInfo> mnestInfos = DataSupport.findAll(MyNestInfo.class);
        mNestListFragAdapter = new ChooseNestAdapter(getActivity(), mRecyclerView,
                DataCloneUtil.cloneMyNestToNestList(mnestInfos));
        mRecyclerView.setAdapter(mNestListFragAdapter);
        mNestListFragAdapter.notifyDataSetChanged();
    }

    private void setToolbar() {
        (findViewById(R.id.setting)).setVisibility(View.INVISIBLE);
        finish.setText(getString(R.string.choose_nest));
    }


    @Override
    protected Activity getActivity() {
        return ChooseNestActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bird_cage;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.birdcage_recyclerview);
        findViewById(R.id.add).setVisibility(View.GONE);
        finish = (TextView) findViewById(R.id.right_text);
        setToolbar();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void initEvent() {
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (mNestListFragAdapter != null && mNestListFragAdapter.getNestName()!=null) {
                    intent.putExtra("nestname", mNestListFragAdapter.getNestName());
                    setResult(123,intent);
                }else
                    setResult(122);
                ActivityManager.finishActivity(getActivity());
            }
        });
    }
}
