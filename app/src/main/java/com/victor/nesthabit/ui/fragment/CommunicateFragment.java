package com.victor.nesthabit.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.ChatInfo;
import com.victor.nesthabit.ui.adapter.CommunicateAdapter;
import com.victor.nesthabit.ui.base.BaseFragment;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.CommunicateContract;
import com.victor.nesthabit.ui.presenter.CommunicatePresenter;

import java.util.List;

/**
 * Created by victor on 7/20/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class CommunicateFragment extends BaseFragment implements CommunicateContract.View {

    public static final String NESTID = "nestid";
    public static final String TAG = "@victor Communicate";
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private Button send;
    private CommunicateAdapter mCommunicateAdapter;
    private CommunicateContract.Presenter mPresenter;
    private String id = null;

    public static CommunicateFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(NESTID, id);
        CommunicateFragment fragment = new CommunicateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(NESTID);
            Log.d(TAG, "id: " + id);
        }
        mPresenter = new CommunicatePresenter(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(CommunicateContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_communicate;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mEditText = (EditText) rootView.findViewById(R.id.input_text);
        send = (Button) rootView.findViewById(R.id.send);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mCommunicateAdapter = new CommunicateAdapter(mActivity, mRecyclerView);
        mRecyclerView.setAdapter(mCommunicateAdapter);
    }

    @Override
    protected void initEvent() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(s)) {
                    send.setEnabled(false);
                } else {
                    send.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        send.setOnClickListener(v -> mPresenter.sendMessage());
    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    @Override
    public String getMessage() {
        return mEditText.getText().toString();
    }

    @Override
    public String getNestId() {
        return id;
    }

    @Override
    public void addItem(ChatInfo item) {
        if (mCommunicateAdapter != null) {
            mCommunicateAdapter.addItem(item);
        }
    }

    public void clearData() {
        if (mCommunicateAdapter != null) {
            mCommunicateAdapter.clearData();
        }
    }
    @Override
    public void addAll(List<ChatInfo> items) {
        mCommunicateAdapter.addAll(items);
    }

    @Override
    public void setEditText(String text) {
        mEditText.setText(text);
    }

    @Override
    public void showToast(String des) {
        Toast.makeText(getActivity(), des, Toast.LENGTH_SHORT).show();
    }

}
