package com.victor.nesthabit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by victor on 7/3/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 *
 * 可以拖拽的圆形imageview
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

    private void init() {
        originLeft = getLeft();
        originRight = getLeft() + getWidth();
        originTop = getTop();
        originBottom = getTop() + getHeight();
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
    public interface DragListenner {
        void onNodeSelect(int positionX);
    }
}
