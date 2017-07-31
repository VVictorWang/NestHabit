package com.victor.nesthabit.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.ui.activity.AddNestActivity;
import com.victor.nesthabit.ui.activity.ProfileActivity;
import com.victor.nesthabit.ui.adapter.NestListFragAdapter;
import com.victor.nesthabit.ui.contract.NestListContract;
import com.victor.nesthabit.ui.presenter.NestListPresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.PrefsUtils;
import com.victor.nesthabit.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;


public class NestListFragment extends Fragment implements NestListContract.View {
    private static final String ARG_PARAM1 = "userId";
    private static final String ARG_PARAM2 = "param2";


    private Activity mActivity;

    private RecyclerView mRecyclerView;
    private FloatingActionButton add;
    private CircleImageView setting;


    private NestListFragAdapter mNestListFragAdapter;

    private NestListContract.Presenter mPresenter;
    private View rootview;
    private List<NestInfo> mNestInfos;
    public static final String TAG = "@victor NestList";

    @Override
    public void setPresenter(NestListContract.Presenter presenter) {
        mPresenter = presenter;
    }


    public static NestListFragment newInstance(long userId) {
        NestListFragment fragment = new NestListFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
        mActivity = this.getActivity();
        mPresenter = new NestListPresenter(this);
        Log.d(TAG, "onCreate");
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
        Log.d(TAG, "onCreateView");
        return rootview;
    }


    private void initView() {
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.birdcage_recyclerview);
        (rootview.findViewById(R.id.right_text)).setVisibility(View.INVISIBLE);
        add = (FloatingActionButton) rootview.findViewById(R.id.add);
        setting = (CircleImageView) rootview.findViewById(R.id.setting);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mNestInfos = new ArrayList<>();
        mNestListFragAdapter = new NestListFragAdapter(mActivity, mRecyclerView, mNestInfos);
        mRecyclerView.setAdapter(mNestListFragAdapter);
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
        Log.d(TAG, "showrecylerview");
//        if (mNestListFragAdapter != null) {
//            mNestListFragAdapter.addData(mBirdCageInfos);
//            Log.d(TAG, "add");
//        }
    }

    @Override
    public long getUserId() {
        return PrefsUtils.getLongValue(mActivity, GlobalData.USER_ID, -1);
    }
}
