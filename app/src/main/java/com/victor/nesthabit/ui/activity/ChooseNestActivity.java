package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.repository.NestRepository;
import com.victor.nesthabit.repository.UserRepository;
import com.victor.nesthabit.ui.adapter.ChooseNestAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.NetWorkBoundUtils;
import com.victor.nesthabit.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChooseNestActivity extends BaseActivity {
    private RecyclerView mRecyclerView;


    private ChooseNestAdapter mNestListFragAdapter;
    private TextView finish;
    private List<NestInfo> mnestInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mNestListFragAdapter = new ChooseNestAdapter(getActivity(), mRecyclerView);
        mRecyclerView.setAdapter(mNestListFragAdapter);
        initData();
    }

    @Override
    protected BasePresenter getPresnter() {
        return null;
    }

    private void setToolbar() {
        (findViewById(R.id.setting)).setVisibility(View.INVISIBLE);
        finish.setText(getString(R.string.choose_nest));
    }

    public void initData() {
        UserRepository userRepository = UserRepository.getInstance();
        userRepository.getUserInfo(Utils.getUserId(), new NetWorkBoundUtils.CallBack<UserInfo>() {
            @Override
            public void callSuccess(Observable<UserInfo> result) {
                result.subscribeOn(Schedulers.io())
                        .subscribe(userInfo -> {
                            for (String nestId : userInfo.getJoined_nests()) {
                                NestRepository.getInstance().loadNestInfo(nestId, new
                                        NetWorkBoundUtils.CallBack<NestInfo>() {


                                            @Override
                                            public void callSuccess(Observable<NestInfo> result) {
                                                result.observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(nestInfo -> mNestListFragAdapter
                                                                .addData(nestInfo));
                                            }

                                            @Override
                                            public void callFailure(String errorMessage) {

                                            }
                                        });
                            }
                        });
            }

            @Override
            public void callFailure(String errorMessage) {

            }
        });
    }

    @Override
    protected Activity getActivity() {
        return ChooseNestActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bird_cage;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.birdcage_recyclerview);
        findViewById(R.id.add).setVisibility(View.GONE);
        finish = (TextView) findViewById(R.id.right_text);
        setToolbar();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void initEvent() {
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (mNestListFragAdapter != null && mNestListFragAdapter.getNestName() != null) {
                    intent.putExtra("nestname", mNestListFragAdapter.getNestName());
                    intent.putExtra("nestid", mNestListFragAdapter.getNestId());
                    setResult(123, intent);
                } else {
                    setResult(122);
                }
                ActivityManager.finishActivity(getActivity());
            }
        });
    }
}