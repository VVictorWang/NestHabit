package com.victor.nesthabit.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.RankItem;
import com.victor.nesthabit.ui.adapter.RankAdapter;
import com.victor.nesthabit.ui.base.BaseFragment;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.RankContract;
import com.victor.nesthabit.ui.presenter.RankPresnter;

/**
 * Created by victor on 7/20/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class RankTotalFragment extends BaseFragment implements RankContract.View {
    public static final int TOTAL_TYPE = 0;
    public static final int CONSTANT_TYPE = 1;
    private static final String NESTID = "nestid";
    private static final String TYPE = "isTotal";
    private RecyclerView mRecyclerView;
    private RankAdapter mRankAdapter;

    private String nestId = null;
    private int type = TOTAL_TYPE;

    private RankContract.Presenter mPresenter;

    public static RankTotalFragment newInstance(String nestId, int type) {
        Bundle args = new Bundle();
        args.putString(NESTID, nestId);
        args.putInt(TYPE, type);
        RankTotalFragment fragment = new RankTotalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nestId = getArguments().getString(NESTID);
            type = getArguments().getInt(TYPE);
        }
        mPresenter = new RankPresnter(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(RankContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_rank_total;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRankAdapter = new RankAdapter(mActivity);
        mRecyclerView.setAdapter(mRankAdapter);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    @Override
    public String getNestId() {
        return nestId;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void addItem(RankItem rankItem) {
        if (mRankAdapter != null) {
            mRankAdapter.addItem(rankItem);
        }
    }
}
