package com.joaquimley.core;

import android.app.Application;

import com.joaquimley.core.data.network.MarvelService;
import com.joaquimley.core.data.network.MarvelServiceFactory;
import com.squareup.leakcanary.LeakCanary;

public class AvengingApplication extends Application {

    private static AvengingApplication sInstance;
    private static MarvelService sMarvelService;
//    private static PresenterCache sPresenterCache;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        sInstance = this;
        sMarvelService = MarvelServiceFactory.makeMarvelService(true);
//        sPresenterCache = PresenterCache.makePresenterCache();
    }

    public static AvengingApplication getInstance() {
        if (sInstance == null) {
            sInstance = new AvengingApplication();
        }
        return sInstance;
    }

    public MarvelService getMarvelService() {
        if (sMarvelService == null) {
            sMarvelService = MarvelServiceFactory.makeMarvelService(BuildConfig.DEBUG);
        }
        return sMarvelService;
    }
}
