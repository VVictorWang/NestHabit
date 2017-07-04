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
//
//    private TextView cancel;

    //    private SlideView mSlideView;
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
//        mSlideLayout = (SlideLayout) findViewById(R.id.slide);
//        mSlideLayout.setMainHandler(handler);
        this.slideimage = (DragCircleImageView) findViewById(R.id.slide_image);
        this.textView = (TextView) findViewById(R.id.text_cancel);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                RecordFragment recordFragment = RecordFragment.newInstance(0);
                recordFragment.show(transaction, "hello");
            }
        });
        slideimage.setDragListenner(new DragListenner() {
            @Override
            public void onNodeSelect(int positionX) {
                if (positionX >= textView.getLeft()) {
                    LogUtils.d("Main: ", "平移");

                }
            }
        });
//        this.cancel = (TextView) findViewById(R.id.cancel);
//        mSlideView = (SlideView) findViewById(R.id.slide);
//        mSlideView.setOnUnLockListener(new OnUnLockListener() {
//            @Override
//            public void setUnLocked(boolean lock) {
//                if (lock) {
//                    Toast.makeText(AlarmActivity.this, "好", Toast.LENGTH_SHORT);
//                }
//            }
//        });

        initEvent();
    }

    private void initEvent() {
//        if (slideimage.getLeft() == cancel.getLeft()) {
//            ActivityManager.finishActivity(AlarmActivity.this);
//        }
    }
}
