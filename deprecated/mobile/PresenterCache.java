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

package com.joaquimley.core.ui.base;

import android.util.Log;

import java.util.HashMap;

@Deprecated
public class PresenterCache {

    public static final String TAG = PresenterCache.class.getSimpleName();

    private static PresenterCache sInstance = null;

    private HashMap<String, BasePresenterView> mPresenters;

    private PresenterCache() {
    }

    public static PresenterCache getInstance() {
        if (sInstance == null) {
            sInstance = new PresenterCache();
        }
        return sInstance;
    }

    /**
     * Returns a presenter instance that will be stored and
     * survive configuration changes
     *
     * @param tag           A unique tag to identify the presenter
     * @param presenterType A factory to create the presenter
     *                      if it doesn't exist yet
     * @param <T>           The presenter type
     * @return The presenter
     */
    @SuppressWarnings("unchecked") // Handled internally
    protected <T extends BasePresenterView> T getPresenter(String tag, T presenterType) {
        if (mPresenters == null) {
            mPresenters = new HashMap<>();
        }

        T presenterView = null;
        try {
            presenterView = (T) mPresenters.get(tag);
        } catch (ClassCastException e) {
            Log.w(TAG, "Duplicate Presenter with tag: " + tag + ". This could " +
                    "cause issues with state.");
        }

        if (presenterView == null) {
//            presenterView = presenterType.createPresenter(); // FIXME: 24/07/16 Ensure is required
            mPresenters.put(tag, presenterView);
        }
        return presenterView;
    }

    /**
     * Remove the presenter associated with the given tag
     *
     * @param who A unique tag to identify the presenter
     */
    public final void removePresenter(String who) {
        if (mPresenters != null) {
            mPresenters.remove(who);
        }
    }

    public static PresenterCache makePresenterCache() {
        return getInstance();
    }
}
