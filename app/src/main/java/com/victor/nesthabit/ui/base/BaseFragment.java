package com.victor.nesthabit.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by victor on 8/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = mActivity.getLayoutInflater().inflate(getLayout(), null);
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        initView();
        initEvent();
        if (getPresenter() != null) {
            getPresenter().start();
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().unscribe();
        }
    }

    abstract protected BasePresenter getPresenter();

    abstract protected int getLayout();

    abstract protected void initView();

    abstract protected void initEvent();

    protected void showToast(String description) {
        Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
    }
}
