package com.victor.nesthabit.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.ui.activity.AddAlarmActivity;
import com.victor.nesthabit.ui.adapter.ClockListAdapter;
import com.victor.nesthabit.ui.contract.ClockListContract;
import com.victor.nesthabit.ui.presenter.ClockListPresenter;
import com.victor.nesthabit.util.ActivityManager;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


public class ClockListFragment extends Fragment implements ClockListContract.View {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private ClockListAdapter mAdapter;
    private View rootView;
    private FloatingActionButton add;
    private ClockListContract.Presenter mPresenter;


    public ClockListFragment() {
        // Required empty public constructor
    }

    public static ClockListFragment newInstance(String param1, String param2) {
        ClockListFragment fragment = new ClockListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mPresenter = new ClockListPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_clock, container, false);
        initView();
        mPresenter.start();
        return rootView;
    }

    private void initView() {
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(ClockListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showRecyclerView(List<AlarmTime> time) {
        mAdapter = new ClockListAdapter(getContext(), time);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
