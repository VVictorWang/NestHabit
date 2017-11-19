package com.victor.nesthabit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by victor on 7/19/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class DragTextView extends android.support.v7.widget.AppCompatTextView {

    private int lastX;
    private int lastY;
    private int originLeft, originRight;
    private int originTop, originBottom;
    private DragListenner mDragListenner;

    public DragTextView(Context context) {
        super(context);
    }

    public DragTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom()
                        + offsetY);
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
