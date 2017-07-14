package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.utils.LogUtils;
import com.victor.nesthabit.view.DragCircleImageView;

import butterknife.BindView;

public class AlarmActivity extends BaseActivity {

    @BindView(R.id.slide_image)
    DragCircleImageView slideImage;
    @BindView(R.id.text_cancel)
    TextView textCancel;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alarm;
    }

    @Override
    protected Activity getActivityToPush() {
        return AlarmActivity.this;
    }

    @Override
    protected void initView() {
        slideImage = (DragCircleImageView) findViewById(R.id.slide_image);
        textCancel = (TextView) findViewById(R.id.text_cancel);
        slideImage.setDragListenner(new DragCircleImageView.DragListenner() {
            @Override
            public void onNodeSelect(int positionX) {
                if (positionX >= textCancel.getLeft()) {
                    LogUtils.d("Main: ", "平移");
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }
}
