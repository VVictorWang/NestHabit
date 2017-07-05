package com.victor.nesthabit.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.fragments.RecordFragment;
import com.victor.nesthabit.listenners.DragListenner;
import com.victor.nesthabit.utils.ActivityManager;
import com.victor.nesthabit.utils.LogUtils;
import com.victor.nesthabit.view.DragCircleImageView;

public class AlarmActivity extends AppCompatActivity {

    private android.widget.TextView textView;
    private com.victor.nesthabit.view.DragCircleImageView slideimage;
    private Handler handler =  new Handler() {
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ActivityManager.getInstance().pushActivity(AlarmActivity.this);
        this.slideimage = (DragCircleImageView) findViewById(R.id.slide_image);
        this.textView = (TextView) findViewById(R.id.text_cancel);
        slideimage.setDragListenner(new DragListenner() {
            @Override
            public void onNodeSelect(int positionX) {
                if (positionX >= textView.getLeft()) {
                    LogUtils.d("Main: ", "平移");
                }
            }
        });
        initEvent();
    }

    private void initEvent() {
    }
}
