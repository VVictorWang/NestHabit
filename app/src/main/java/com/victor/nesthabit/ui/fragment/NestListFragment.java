package com.victor.nesthabit.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.Constants;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.ui.activity.AddNestActivity;
import com.victor.nesthabit.ui.activity.ProfileActivity;
import com.victor.nesthabit.ui.adapter.NestListFragAdapter;
import com.victor.nesthabit.ui.base.BaseFragment;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.NestListContract;
import com.victor.nesthabit.ui.presenter.NestListPresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.PrefsUtils;
import com.victor.nesthabit.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;


public class NestListFragment extends BaseFragment implements NestListContract.View {
    public static final String TAG = "@victor NestList";
    private static final String ARG_PARAM1 = "userId";
    private RecyclerView mRecyclerView;
    private FloatingActionButton add;
    private CircleImageView setting;
    private NestListFragAdapter mNestListFragAdapter;
    private NestListContract.Presenter mPresenter;
    private List<NestInfo> mNestInfos;

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
        mPresenter = new NestListPresenter(this);
    }

    public void addData(NestInfo nestInfo) {
        mNestListFragAdapter.OnDataAdded(nestInfo);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(NestListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_bird_cage;
    }


    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.birdcage_recyclerview);
        (rootView.findViewById(R.id.right_text)).setVisibility(View.INVISIBLE);
        add = (FloatingActionButton) rootView.findViewById(R.id.add);
        setting = (CircleImageView) rootView.findViewById(R.id.setting);
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
    protected void initEvent() {

    }


    @Override
    public void showRecyclerview(List<NestInfo> mBirdCageInfos) {
        Log.d(TAG, "showrecylerview");
    }

    @Override
    public long getUserId() {
        return PrefsUtils.getLongValue(mActivity, Constants.USER_ID, -1);
    }

}
