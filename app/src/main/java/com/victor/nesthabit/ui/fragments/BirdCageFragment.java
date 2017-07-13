package com.victor.nesthabit.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapters.BirdCaseFragAdapter;
import com.victor.nesthabit.ui.contracts.BirdCageContract;
import com.victor.nesthabit.data.BirdCageInfo;
import com.victor.nesthabit.ui.presenter.BirdCagePresenter;

import java.util.List;


public class BirdCageFragment extends Fragment implements BirdCageContract.View{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Activity mActivity;

    private RecyclerView mRecyclerView;


    private BirdCaseFragAdapter mBirdCaseFragAdapter;

    private BirdCageContract.Presenter mPresenter;
    private View rootview;

    @Override
    public void setPresenter(BirdCageContract.Presenter presenter) {
        mPresenter = presenter;
    }


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
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

    }



    @Override
    public void showRecyclerview(List<BirdCageInfo> mBirdCageInfos) {
        mBirdCaseFragAdapter = new BirdCaseFragAdapter(mActivity, mRecyclerView, mBirdCageInfos);
        mRecyclerView.setAdapter(mBirdCaseFragAdapter);
        mBirdCaseFragAdapter.notifyDataSetChanged();
    }
}
