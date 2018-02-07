package com.example.victorardianto.myapplication.widget.scroll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;

import com.example.victorardianto.myapplication.R;

/**
 * Created by victorardianto on 06/02/18.
 */

public class FakeScrollBar extends FrameLayout {

    private View vView;
    private Animation fadeOut;

    private int contentHeight, scrollHeight;
    private float ratio;

    public FakeScrollBar(@NonNull Context context) {
        this(context, null);
    }

    public FakeScrollBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FakeScrollBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        vView = new View(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(16, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        setTranslationY(0);
    }

    public void setContentHeight(int height) {
        contentHeight = height;
        updateScrollBar();
    }

    public void scroll(int y) {
        vView.setVisibility(VISIBLE);
        if (ratio < 1) {
            setTranslationY(y * ratio);
        }
        vView.startAnimation(fadeOut);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        scrollHeight = h;
        updateScrollBar();
    }

    private void updateScrollBar() {
        ratio = 1.0f * scrollHeight / contentHeight;
        if (ratio > 1) {
            ratio = 1;
        }

        float lengthFloat = scrollHeight * ratio;
        vView.getLayoutParams().height = (int)lengthFloat;
    }

}
