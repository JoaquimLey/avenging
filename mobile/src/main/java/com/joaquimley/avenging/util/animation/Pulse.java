package com.joaquimley.avenging.util.animation;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Pulsing/Loading effect {@link AlphaAnimation}
 */
public class Pulse extends AlphaAnimation {

    private static final long DEFAULT_DURATION = 1200;
    private static final float DEFAULT_FROM_ALPHA = 0.3f;
    private static final float DEFAULT_TO_ALPHA = 0.6f;


    public Pulse() {
        super(DEFAULT_FROM_ALPHA, DEFAULT_TO_ALPHA);
        setDuration(DEFAULT_DURATION);
        setInterpolator(new LinearInterpolator());
        setRepeatCount(INFINITE);
        setRepeatMode(Animation.REVERSE);
    }

    public Pulse(float fromAlpha, float toAlpha, long duration) {
        super(fromAlpha, toAlpha);
        setDuration(duration);
        setInterpolator(new LinearInterpolator());
        setRepeatCount(INFINITE);
        setRepeatMode(Animation.REVERSE);
    }

}
