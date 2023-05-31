package com.github.vikramhalder.click;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;

@SuppressWarnings("unused")
public class BackgroundClickEffectView extends RelativeLayout implements View.OnClickListener, View.OnTouchListener {
    private AnimationEnd animationEnd;
    private int lottieAnimationViewNumber = 0;
    private final float[] lastTouchPoint = {0, 0};
    private LottieAnimationView viewAnyPointClickAnim1;
    private LottieAnimationView viewAnyPointClickAnim2;
    private LottieAnimationView viewAnyPointClickAnim3;

    public BackgroundClickEffectView(Context context) {
        super(context);
        this.init(context, null);
    }

    public BackgroundClickEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public BackgroundClickEffectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        LayoutInflater.from(context).inflate(R.layout.effect_background_layout, this, true);
        this.findViewById(R.id.viewAnyPointClick).setOnClickListener(this);
        this.findViewById(R.id.viewAnyPointClick).setOnTouchListener(this);

        @SuppressLint({"CustomViewStyleable", "Recycle"}) final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BackgroundTouchEffect, 0, 0);
        final int rawEffect = a.getInt(R.styleable.BackgroundTouchEffect_lottieRes, R.raw.lottie_click_effect);

        viewAnyPointClickAnim1 = findViewById(R.id.viewAnyPointClickAnim1);
        viewAnyPointClickAnim1.setAnimation(rawEffect);

        viewAnyPointClickAnim2 = findViewById(R.id.viewAnyPointClickAnim2);
        viewAnyPointClickAnim2.setAnimation(rawEffect);

        viewAnyPointClickAnim3 = findViewById(R.id.viewAnyPointClickAnim3);
        viewAnyPointClickAnim3.setAnimation(rawEffect);

        viewAnyPointClickAnim1.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animationEnd != null) {
                    animationEnd.setAnimationEnd(viewAnyPointClickAnim1);
                }
            }
        });
        viewAnyPointClickAnim2.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animationEnd != null) {
                    animationEnd.setAnimationEnd(viewAnyPointClickAnim2);
                }
            }
        });
        viewAnyPointClickAnim3.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animationEnd != null) {
                    animationEnd.setAnimationEnd(viewAnyPointClickAnim2);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        lottieAnimationViewNumber++;
        LottieAnimationView view = viewAnyPointClickAnim3;
        if (lottieAnimationViewNumber == 1) {
            view = viewAnyPointClickAnim1;
        } else if (lottieAnimationViewNumber == 2) {
            view = viewAnyPointClickAnim2;
        } else if (lottieAnimationViewNumber == 3) {
            lottieAnimationViewNumber = 0;
        }
        float width = (view.getWidth() / 2f);
        view.setX(lastTouchPoint[0] - width);
        view.setY(lastTouchPoint[1] - width);
        view.setVisibility(View.VISIBLE);
        view.playAnimation();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        lastTouchPoint[0] = event.getX();
        lastTouchPoint[1] = event.getY();
        return false;
    }

    public void setAnimationEnd(AnimationEnd animationEnd) {
        this.animationEnd = animationEnd;
    }

    public interface AnimationEnd {
        void setAnimationEnd(LottieAnimationView view);
    }
}
