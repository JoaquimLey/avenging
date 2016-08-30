/*
 * Copyright (c) Joaquim Ley 2016. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
}
