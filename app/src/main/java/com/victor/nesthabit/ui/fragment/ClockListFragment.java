package com.victor.nesthabit.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.AlarmTime;
import com.victor.nesthabit.ui.activity.AddAlarmActivity;
import com.victor.nesthabit.ui.adapter.ClockListAdapter;
import com.victor.nesthabit.ui.base.BaseFragment;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.ClockListContract;
import com.victor.nesthabit.ui.presenter.ClockListPresenter;
import com.victor.nesthabit.util.ActivityManager;

import java.util.ArrayList;
import java.util.List;


public class ClockListFragment extends BaseFragment implements ClockListContract.View {

    private RecyclerView mRecyclerView;
    private ClockListAdapter mAdapter;
    private FloatingActionButton add;
    private ClockListContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ClockListPresenter(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unscribe();
    }

    public void addData(AlarmTime alarmTime) {
        mAdapter.AlarmAdded(alarmTime);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(ClockListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_clock;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.clock_list);
        add = (FloatingActionButton) rootView.findViewById(R.id.add);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ClockListAdapter(getContext(), new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(getActivity(), AddAlarmActivity.class);
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    @Override
    public void showRecyclerView(List<AlarmTime> time) {
        mAdapter = new ClockListAdapter(getContext(), time);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}
