package com.victor.nesthabit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.victor.nesthabit.listenners.DragListenner;

/**
 * Created by victor on 7/3/17.
 */

public class DragCircleImageView extends CircleImageView {

    private int lastX;
    private int lastY;
    private int originLeft,originRight;
    private int originTop,originBottom;

    private boolean drawCircle = false;
    private DragListenner mDragListenner;
    private float originX;
    private float originY;
    public DragCircleImageView(Context context) {
        super(context);
    }

    public DragCircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
    }
//    private int mRippleWidth = 50;
//    private Paint mRipplePaint = new Paint();
//    public void setAnim() {
//        drawCircle = true;
//        ValueAnimator va = ValueAnimator.ofInt(0, 100);
//        va.setDuration(300 * 2);
//        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = (int) animation.getAnimatedValue();
//                mRippleWidth = value;
//                mRipplePaint.setStrokeWidth(value);
//                invalidate();
//            }
//        });
//        va.start();
//    }

    private void init() {
        originLeft = getLeft();
        originRight = getLeft() + getWidth();
        originTop = getTop();
        originBottom = getTop() + getHeight();
//        originX = getLeft() + getX();
//
//        originY = getY()+getTop();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
//                drawCircle = true;
//                setAnim();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                break;
            case MotionEvent.ACTION_UP:
                mDragListenner.onNodeSelect(getLeft());
                layout(originLeft, originTop, originRight, originBottom);
                break;
            default:
                break;
        }
        return true;

    }



    public void setDragListenner(DragListenner listenner) {
        mDragListenner = listenner;
    }
}
