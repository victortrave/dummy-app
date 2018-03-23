package com.example.victorardianto.myapplication.widget.zoomable;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;

import com.example.victorardianto.myapplication.widget.TwoDScrollFrameLayout;

/**
 * Created by victorardianto on 02/02/18.
 */
public class ZoomableTwoDScrollFrameLayout extends TwoDScrollFrameLayout {

    private final static String TAG = ZoomableTwoDScrollFrameLayout.class.getSimpleName();
    /**
     * The constant TWO_DSCROLL_VIEW_CAN_HOST_ONLY_ONE_DIRECT_CHILD.
     */
    public static final String TWO_DSCROLL_VIEW_CAN_HOST_ONLY_ONE_DIRECT_CHILD = "TwoDScrollView can host only one direct child";

    private GestureDetector gestureDetector;
    private ScaleGestureDetector mScaleDetector;

    private float mScale = 1f;
    private float mMinimumScale = 0.5f;
    private float mMaximumScale = 1.5f;

    private ZoomEngine mEngine;
    private Matrix mMatrix = new Matrix();
    private float[] mMatrixValues = new float[9];
    private RectF mChildRect = new RectF();

    /**
     * Instantiates a new Zoomable two d scroll frame layout.
     *
     * @param context the context
     */
    public ZoomableTwoDScrollFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new Zoomable two d scroll frame layout.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public ZoomableTwoDScrollFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Instantiates a new Zoomable two d scroll frame layout.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public ZoomableTwoDScrollFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ZoomEngine, defStyleAttr, 0);
//        // Support deprecated overScrollable
//        boolean overScrollHorizontal, overScrollVertical;
//        if (a.hasValue(R.styleable.ZoomEngine_overScrollable)) {
//            overScrollHorizontal = a.getBoolean(R.styleable.ZoomEngine_overScrollable, true);
//            overScrollVertical = a.getBoolean(R.styleable.ZoomEngine_overScrollable, true);
//        } else {
//            overScrollHorizontal = a.getBoolean(R.styleable.ZoomEngine_overScrollHorizontal, true);
//            overScrollVertical = a.getBoolean(R.styleable.ZoomEngine_overScrollVertical, true);
//        }
//        boolean overPinchable = a.getBoolean(R.styleable.ZoomEngine_overPinchable, true);
//        boolean hasChildren = a.getBoolean(R.styleable.ZoomEngine_hasClickableChildren, false);
//        float minZoom = a.getFloat(R.styleable.ZoomEngine_minZoom, -1);
//        float maxZoom = a.getFloat(R.styleable.ZoomEngine_maxZoom, -1);
//        @ZoomEngine.ZoomType int minZoomMode = a.getInteger(
//                R.styleable.ZoomEngine_minZoomType, ZoomEngine.TYPE_ZOOM);
//        @ZoomEngine.ZoomType int maxZoomMode = a.getInteger(
//                R.styleable.ZoomEngine_maxZoomType, ZoomEngine.TYPE_ZOOM);
//        a.recycle();
//
//        mEngine = new ZoomEngine(context, this, this);
//        mEngine.setOverScrollHorizontal(overScrollHorizontal);
//        mEngine.setOverScrollVertical(overScrollVertical);
//        mEngine.setOverPinchable(overPinchable);
//        if (minZoom > -1) mEngine.setMinZoom(minZoom, minZoomMode);
//        if (maxZoom > -1) mEngine.setMaxZoom(maxZoom, maxZoomMode);
//        setHasClickableChildren(hasChildren);
    }

    @Override
    protected void initTwoDScrollView() {
        super.initTwoDScrollView();
        gestureDetector = new GestureDetector(getContext(), new GestureListener());
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 0) {
            throw new IllegalStateException(TWO_DSCROLL_VIEW_CAN_HOST_ONLY_ONE_DIRECT_CHILD);
        }

        super.addView(child);

        createScaleGestureDetector(child);
    }

    @Override
    public void addView(View child, int index) {
        if (getChildCount() > 0) {
            throw new IllegalStateException(TWO_DSCROLL_VIEW_CAN_HOST_ONLY_ONE_DIRECT_CHILD);
        }

        super.addView(child, index);

        createScaleGestureDetector(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException(TWO_DSCROLL_VIEW_CAN_HOST_ONLY_ONE_DIRECT_CHILD);
        }

        super.addView(child, params);

        createScaleGestureDetector(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException(TWO_DSCROLL_VIEW_CAN_HOST_ONLY_ONE_DIRECT_CHILD);
        }

        super.addView(child, index, params);

        createScaleGestureDetector(child);
    }

    private void createScaleGestureDetector(View childLayout) {
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener()
        {
            @Override
            public boolean onScale(ScaleGestureDetector detector)
            {
                float scale = 1 - detector.getScaleFactor();

                float prevScale = mScale;
                mScale += scale;

                if (mScale < mMinimumScale) {
                    mScale = mMinimumScale;
                }

                if (mScale > mMaximumScale) {
                    mScale = mMaximumScale;
                }

                ScaleAnimation scaleAnimation = new ScaleAnimation(1f / prevScale, 1f / mScale,
                        1f / prevScale, 1f / mScale,
                        detector.getFocusX(), detector.getFocusY());
                scaleAnimation.setDuration(0);
                scaleAnimation.setFillAfter(true);
                childLayout.startAnimation(scaleAnimation);

                return true;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        mScaleDetector.onTouchEvent(event);

        event.setLocation((getScrollX() + event.getX()) * mScale, (getScrollY() + event.getY()) * mScale);
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * Gets minimum scale.
     *
     * @return the minimum scale
     */
    public float getMinimumScale() {
        return mMinimumScale;
    }

    /**
     * Gets maximum scale.
     *
     * @return the maximum scale
     */
    public float getMaximumScale() {
        return mMaximumScale;
    }

    /**
     * Sets minimum scale.
     *
     * @param minimumScale the minimum scale
     */
    public void setMinimumScale(float minimumScale) {
        if (minimumScale > 0 && minimumScale < mMaximumScale) {
            mMinimumScale = minimumScale;
        }
    }

    /**
     * Sets maximum scale.
     *
     * @param maximumScale the maximum scale
     */
    public void setMaximumScale(float maximumScale) {
        if (maximumScale > mMinimumScale) {
            mMaximumScale = maximumScale;
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // double tap fired.
            return true;
        }
    }
}
