package com.victor.nesthabit.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.ui.activity.AddNestActivity;
import com.victor.nesthabit.ui.activity.ProfileActivity;
import com.victor.nesthabit.ui.adapters.BirdCaseFragAdapter;
import com.victor.nesthabit.ui.contract.BirdCageContract;
import com.victor.nesthabit.ui.presenter.BirdCagePresenter;
import com.victor.nesthabit.utils.ActivityManager;
import com.victor.nesthabit.view.CircleImageView;

import java.util.List;


public class BirdCageFragment extends Fragment implements BirdCageContract.View {
    private static final String ARG_PARAM1 = "userId";
    private static final String ARG_PARAM2 = "param2";

    private long userId = -1;

    private Activity mActivity;

    private RecyclerView mRecyclerView;
    private FloatingActionButton add;
    private CircleImageView setting;


    private BirdCaseFragAdapter mBirdCaseFragAdapter;

    private BirdCageContract.Presenter mPresenter;
    private View rootview;

    @Override
    public void setPresenter(BirdCageContract.Presenter presenter) {
        mPresenter = presenter;
    }


    public static BirdCageFragment newInstance(long userId) {
        BirdCageFragment fragment = new BirdCageFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getLong(ARG_PARAM1);
        }
        mActivity = this.getActivity();
        mPresenter = new BirdCagePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootview == null) {
            rootview = mActivity.getLayoutInflater().inflate(R.layout.fragment_bird_cage, null);
        } else {
            ViewGroup parent = (ViewGroup) rootview.getParent();
            if (parent != null) {
                parent.removeView(rootview);
            }
        }
        initView();
        mPresenter.start();
        // Inflate the layout for this fragment
        return rootview;
    }


    private void initView() {
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.birdcage_recyclerview);
        add = (FloatingActionButton) rootview.findViewById(R.id.add);
        setting = (CircleImageView) rootview.findViewById(R.id.setting);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(mActivity, AddNestActivity.class);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(mActivity, ProfileActivity.class);
            }
        });
    }


    @Override
    public void showRecyclerview(List<NestInfo> mBirdCageInfos) {
        mBirdCaseFragAdapter = new BirdCaseFragAdapter(mActivity, mRecyclerView, mBirdCageInfos);
        mRecyclerView.setAdapter(mBirdCaseFragAdapter);
        mBirdCaseFragAdapter.notifyDataSetChanged();
    }

    @Override
    public long getUserId() {
        return userId;
    }
}
