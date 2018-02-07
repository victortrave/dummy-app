package com.example.victorardianto.myapplication.widget.scroll;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;

/**
 * Created by victorardianto on 06/02/18.
 */

public class FakeHorizontalScrollBar extends FrameLayout {

    private View vView;
    private Animation fadeOut;

    private int contentWidth, scrollWidth;
    private float ratio;

    public FakeHorizontalScrollBar(@NonNull Context context) {
        this(context, null);
    }

    public FakeHorizontalScrollBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FakeHorizontalScrollBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        vView = new View(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 16);
        vView.setLayoutParams(params);
        vView.setBackgroundColor(Color.parseColor("#808080"));

        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(1000);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                vView.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        addView(vView);
        vView.startAnimation(fadeOut);
        setTranslationX(0);
    }

    public void setContentWidth(int width) {
        contentWidth = width;
        updateScrollBar();
    }

    public void scroll(int x) {
        vView.setVisibility(VISIBLE);
        if (ratio < 1) {
            setTranslationX(x * ratio);
        }
        vView.startAnimation(fadeOut);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        scrollWidth = w;
        updateScrollBar();
    }

    private void updateScrollBar() {
        ratio = 1.0f * scrollWidth / contentWidth;
        if (ratio > 1) {
            ratio = 1;
        }

        float lengthFloat = scrollWidth * ratio;
        int len = (int) lengthFloat;

        vView.setMinimumWidth(len);
        vView.getLayoutParams().width = len;
    }

}
