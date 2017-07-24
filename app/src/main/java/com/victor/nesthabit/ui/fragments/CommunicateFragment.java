package com.victor.nesthabit.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.CommunicateItem;
import com.victor.nesthabit.ui.adapters.CommunicateAdapter;
import com.victor.nesthabit.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by victor on 7/20/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class CommunicateFragment extends Fragment {
    private View rootView;
    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private Button send;
    private CommunicateAdapter mCommunicateAdapter;

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
            rootView = mActivity.getLayoutInflater().inflate(R.layout.fragment_communicate, null);
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        initView();
        initEvent();
        return rootView;
    }

    private void initView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mEditText = (EditText) rootView.findViewById(R.id.input_text);
        send = (Button) rootView.findViewById(R.id.send);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        List<CommunicateItem> communicateItems = new ArrayList<>();
        CommunicateItem item = new CommunicateItem();
        item.setDate(DateUtils.DatetoString(DateUtils.getCurDate()));
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
                String text = mEditText.getText().toString();
                CommunicateItem communicateItem = new CommunicateItem();
                communicateItem.setMessage(text);
                communicateItem.setType(CommunicateAdapter.RIGHT_TYPR);
                if (mCommunicateAdapter != null) {
                    mCommunicateAdapter.addItem(communicateItem);
                }
                mEditText.setText("");
            }
        });
    }
}
