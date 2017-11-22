package com.victor.nesthabit.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.PunchInfo;
import com.victor.nesthabit.ui.adapter.DaKaWallAdapater;
import com.victor.nesthabit.ui.base.BaseFragment;
import com.victor.nesthabit.ui.base.BasePresenter;

/**
 * Created by victor on 7/20/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class DaKaWallFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private DaKaWallAdapater mDaKaWallAdapater;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_daka_wall;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mDaKaWallAdapater = new DaKaWallAdapater(mActivity);
        mRecyclerView.setAdapter(mDaKaWallAdapater);
    }

    public void addItem(PunchInfo punchInfo) {
        mDaKaWallAdapater.addItem(punchInfo);
    }

    @Override
    protected void initEvent() {

    }
}
