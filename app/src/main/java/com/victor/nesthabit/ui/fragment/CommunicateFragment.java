package com.victor.nesthabit.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.SendMessageResponse;
import com.victor.nesthabit.ui.adapter.CommunicateAdapter;
import com.victor.nesthabit.ui.contract.CommunicateContract;
import com.victor.nesthabit.ui.presenter.CommunicatePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 7/20/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class CommunicateFragment extends Fragment implements CommunicateContract.View {
    private View rootView;
    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private Button send;
    private CommunicateAdapter mCommunicateAdapter;
    private CommunicateContract.Presenter mPresenter;
    private String id = null;
    public static final String NESTID = "nestid";
    public static final String TAG = "@victor Communicate";

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
        mActivity = getActivity();
        mPresenter = new CommunicatePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = mActivity.getLayoutInflater().inflate(R.layout.fragment_communicate, null);
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        initView();
        initEvent();
        mPresenter.start();
        return rootView;
    }

    private void initView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mEditText = (EditText) rootView.findViewById(R.id.input_text);
        send = (Button) rootView.findViewById(R.id.send);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        List<SendMessageResponse> communicateItems = new ArrayList<>();
        SendMessageResponse item = new SendMessageResponse();
        item.setTime(System.currentTimeMillis());
        item.setType(CommunicateAdapter.DATE_TYPE);
        communicateItems.add(item);
        mCommunicateAdapter = new CommunicateAdapter(mActivity, communicateItems);
        mRecyclerView.setAdapter(mCommunicateAdapter);
    }

    private void initEvent() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")) {
                    send.setEnabled(false);
                } else
                    send.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sendMessage();
            }
        });
    }

    @Override
    public void setPresenter(CommunicateContract.Presenter presenter) {
        mPresenter = presenter;
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
    public void addItem(SendMessageResponse item) {
        if (mCommunicateAdapter != null) {
            mCommunicateAdapter.addItem(item);
        }
    }

    @Override
    public void setEditText(String text) {
        mEditText.setText(text);
    }

    @Override
    public void showToast(String des) {
        Toast.makeText(getActivity(), des, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unscribe();
    }
}
