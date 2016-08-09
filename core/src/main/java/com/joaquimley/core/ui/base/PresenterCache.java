package com.joaquimley.core.ui.base;

import android.util.Log;

import java.util.HashMap;

public class PresenterCache { // TODO: 24/07/16 Must make this work

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
