package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.ui.adapter.MemberListAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.MemberListContract;
import com.victor.nesthabit.ui.presenter.MemberListPresenter;
import com.victor.nesthabit.util.ActivityManager;

public class MemberListActivity extends BaseActivity implements MemberListContract.View {

    private View toolbar;
    private android.support.v7.widget.RecyclerView list;
    private ImageView back;
    private TextView title;
    private TextView manage;
    private boolean isOwner;
    private String nestId;
    private MemberListContract.Presenter mPresenter;
    private MemberListAdapter mMemberListAdapter;

    @Override
    protected BasePresenter getPresnter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = new MemberListPresenter(this);
        if (getIntent() != null) {
            nestId = getIntent().getStringExtra("nestId");
            isOwner = getIntent().getBooleanExtra("isOwner", false);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Activity getActivity() {
        return MemberListActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_list;
    }

    @Override
    protected void initView() {
        this.list = (RecyclerView) findViewById(R.id.list);
        this.toolbar = findViewById(R.id.toolbar);
        setToolbar();
        mMemberListAdapter = new MemberListAdapter(MemberListActivity.this, false, false,
                isOwner, nestId);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);
        list.setAdapter(mMemberListAdapter);
    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(MemberListActivity.this);
            }
        });

    }

    private void setToolbar() {
        back = (ImageView) (toolbar.findViewById(R.id.back));
        title = (TextView) (toolbar.findViewById(R.id.title_text));
        manage = (TextView) (toolbar.findViewById(R.id.right_text));
        title.setText(getString(R.string.member_list));
        manage.setText(getString(R.string.manage));
    }

    @Override
    public void setPresenter(MemberListContract.Presenter presenter) {
        mPresenter = presenter;
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
    public void addItem(UserInfo userInfo) {
        mMemberListAdapter.addItem(userInfo);
    }
}
