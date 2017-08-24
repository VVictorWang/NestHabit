package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.view.DragTextView;

public class AlarmActivity extends BaseActivity {


    private TextView time;
    private TextView textView;
    private android.widget.ImageView imagecancel;
    private TextView textcancel;
    private android.widget.ImageView imagepullof;
    private com.victor.nesthabit.view.DragTextView dragtext;
    private ImageView imagesnap;
    public static final String TAG = "@victor AlarmActivity";


    @Override
    protected BasePresenter getPresnter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alarm;
    }

    @Override
    protected Activity getActivity() {
        return AlarmActivity.this;
    }

    @Override
    protected void initView() {
        this.imagesnap = (ImageView) findViewById(R.id.image_snap);
        this.dragtext = (DragTextView) findViewById(R.id.drag_text);
        this.imagecancel = (ImageView) findViewById(R.id.image_cancel);
        this.textView = (TextView) findViewById(R.id.textView);
        this.time = (TextView) findViewById(R.id.time);
    }


    @Override
    protected void initEvent() {
        dragtext.setDragListenner(new DragTextView.DragListenner() {
            @Override
            public void onNodeSelect(int positionX) {
                if (positionX >= imagesnap.getLeft() - 10) {
                    Log.d(TAG, "平移");
                }
            }
        });
    }
}
