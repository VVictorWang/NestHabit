package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapters.RecordListAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.contracts.RecordContract;
import com.victor.nesthabit.data.RecordItem;
import com.victor.nesthabit.ui.fragments.RecordFragment;
import com.victor.nesthabit.ui.presenter.RecordActivityPresenter;

import java.util.List;

public class RecordActivity extends BaseActivity implements RecordContract.View{

    private View toolbarRecord;
    private RecyclerView record_list;
    private RecordListAdapter mRecordListAdapter;
    private List<RecordItem> mRecordItems;
    private FloatingActionButton mFloatingActionButton;
    private RecordContract.Presenter mRecordActivityPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecordActivityPresenter = new RecordActivityPresenter(this);
        mRecordActivityPresenter.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected Activity getActivityToPush() {
        return RecordActivity.this;
    }

    @Override
    protected void initView() {
        toolbarRecord = (View) findViewById(R.id.toolbar_record);
        record_list = (RecyclerView) findViewById(R.id.record_list);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_new_record);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecordActivity.this);
        record_list.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {

    }
    @Override
    protected  void initEvent() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                RecordFragment recordFragment = new RecordFragment();
                recordFragment.show(transaction, "record");
            }
        });
    }

    @Override
    public void setPresenter(RecordContract.Presenter presenter) {
        mRecordActivityPresenter = presenter;
    }

    @Override
    public void showRecyclerview(List<RecordItem> mRecordItems) {
        mRecordListAdapter = new RecordListAdapter(RecordActivity.this, mRecordItems);
        record_list.setAdapter(mRecordListAdapter);
        mRecordListAdapter.notifyDataSetChanged();
    }
}
