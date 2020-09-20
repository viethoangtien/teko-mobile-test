package com.teko.hoangviet.androidtest.custom.gesture;

import android.view.MotionEvent;
import android.view.View;


public class OnDragTouchListener implements View.OnTouchListener {

    /**
     * Callback used to indicate when the drag is finished
     */
    public interface OnDragActionListener {
        /**
         * Called when drag event is started
         *
         * @param view The view dragged
         */
        void onDragStart(View view);

        /**
         * Called when drag event is completed
         *
         * @param view The view dragged
         */
        void onDragEnd(View view, boolean isClickDetected);
    }

    public interface OnClickDetectedListener {
        void onClickDetected(boolean isClickDetected);
    }

    private OnClickDetectedListener onClickDetectedListener;

    public long lastTouchTime = System.currentTimeMillis();

    private View mView;
    private View mParent;
    private boolean isDragging;
    private boolean isInitialized = false;

    private int width;
    private float xWhenAttached;
    private float maxLeft;
    private float maxRight;
    private float dX;

    private int height;
    private float yWhenAttached;
    private float maxTop;
    private float maxBottom;
    private float dY;

    private int paddingView = 0;

    private boolean isPreventDrag = false;

    private OnDragActionListener mOnDragActionListener;

    public OnDragTouchListener(View view) {
        this(view, (View) view.getParent(), null);
    }

    public OnDragTouchListener(View view, View parent, int maxBottom) {
        this(view, parent, null);
        this.maxBottom = maxBottom;
    }

    public OnDragTouchListener(View view, OnDragActionListener onDragActionListener) {
        this(view, (View) view.getParent(), onDragActionListener);
    }

    public OnDragTouchListener(View view, View parent, OnDragActionListener onDragActionListener) {
        initListener(view, parent);
        setOnDragActionListener(onDragActionListener);
    }

    public void setOnDragActionListener(OnDragActionListener onDragActionListener) {
        mOnDragActionListener = onDragActionListener;
    }

    public void initListener(View view, View parent) {
        mView = view;
        mParent = parent;
        isDragging = false;
        isInitialized = false;
    }

    public void updateBounds() {
        updateViewBounds();
        updateParentBounds();
        isInitialized = true;
    }

    public void updateViewBounds() {
        width = mView.getWidth();
        xWhenAttached = mView.getX();
        dX = 0;

        height = mView.getHeight();
        yWhenAttached = mView.getY();
        dY = 0;
    }

    public void updateParentBounds() {
        maxLeft = 0 - paddingView;
        maxRight = maxLeft + mParent.getWidth() + paddingView + paddingView;

        maxTop = 0 - paddingView;
//        maxBottom = maxTop + mParent.getHeight() + paddingView + paddingView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(isPreventDrag){
            return true;
        }
        if (isDragging) {
            float[] bounds = new float[4];
            // LEFT
            bounds[0] = event.getRawX() + dX;
            if (bounds[0] < maxLeft) {
                bounds[0] = maxLeft;
            }
            // RIGHT
            bounds[2] = bounds[0] + width;
            if (bounds[2] > maxRight) {
                bounds[2] = maxRight;
                bounds[0] = bounds[2] - width;
            }
            // TOP
            bounds[1] = event.getRawY() + dY;
            if (bounds[1] < maxTop) {
                bounds[1] = maxTop;
            }
            // BOTTOM
            bounds[3] = bounds[1] + height;
            if (bounds[3] > maxBottom) {
                bounds[3] = maxBottom;
                bounds[1] = bounds[3] - height;
            }

            switch (event.getAction()) {
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    boolean isClickDetected = false;
                    long now = System.currentTimeMillis();
                    if (now - lastTouchTime < 200L) {
                        if (onClickDetectedListener != null) {
                            isClickDetected = true;
                            onClickDetectedListener.onClickDetected(true);
                        }
                    }
                    lastTouchTime = now;
                    onDragFinish(isClickDetected);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mView.animate().y(bounds[1]).setDuration(0).start();
                    if (mOnDragActionListener != null) {
                        mOnDragActionListener.onDragStart(mView);
                    }
                    break;
            }
            return true;
        } else {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastTouchTime = System.currentTimeMillis();
                    isDragging = true;
                    if (!isInitialized) {
                        updateBounds();
                    }
                    dX = v.getX() - event.getRawX();
                    dY = v.getY() - event.getRawY();

                    return true;
            }
        }
        return false;
    }

    private void onDragFinish(boolean isClickDetected) {
        if (mOnDragActionListener != null) {
            mOnDragActionListener.onDragEnd(mView, isClickDetected);
        }

        dX = 0;
        dY = 0;
        isDragging = false;
    }

    public void setPaddingView(int paddingView) {
        this.paddingView = paddingView;
    }

    public void setPreventDrag(boolean preventDrag) {
        isPreventDrag = preventDrag;
    }

    public void setOnClickDetectedListener(OnClickDetectedListener onClickDetectedListener) {
        this.onClickDetectedListener = onClickDetectedListener;
    }
}
