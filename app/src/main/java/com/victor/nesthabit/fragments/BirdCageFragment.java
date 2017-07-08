package com.victor.nesthabit.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victor.nesthabit.R;
import com.victor.nesthabit.adapters.BirdCaseFragAdapter;
import com.victor.nesthabit.data.BirdCageInfo;

import java.util.ArrayList;
import java.util.List;


public class BirdCageFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Activity mActivity;

    private RecyclerView mRecyclerView;

    private List<BirdCageInfo> mBirdCageInfos = new ArrayList<>();

    private BirdCaseFragAdapter mBirdCaseFragAdapter;
    private View rootview;

    public static BirdCageFragment newInstance(String param1, String param2) {
        BirdCageFragment fragment = new BirdCageFragment();
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
        mActivity = this.getActivity();
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
        initData();

        // Inflate the layout for this fragment
        return rootview;
    }


    private void initView() {
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.birdcage_recyclerview);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            BirdCageInfo info = new BirdCageInfo();
            info.setDay_insist(7);
            info.setDay_total(100);
            info.setInfo("早起的鸟儿有虫吃");
            info.setPeople(15);
            mBirdCageInfos.add(info);
        }

        mBirdCaseFragAdapter = new BirdCaseFragAdapter(mActivity, mRecyclerView, mBirdCageInfos);
        mRecyclerView.setAdapter(mBirdCaseFragAdapter);
        mBirdCaseFragAdapter.notifyDataSetChanged();
    }



}
