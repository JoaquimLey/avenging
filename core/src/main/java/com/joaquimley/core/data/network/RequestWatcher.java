package com.joaquimley.core.data.network;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Request manager, TODO: Needs refactor -> study a better extendable class for better async control
 */
public class RequestWatcher {

    private static final String TAG = RequestWatcher.class.getSimpleName();

    private List<Call> mRequestsList = new ArrayList<>();

    public void detach() {
        for (int i = 0; i < mRequestsList.size(); i++) {
            mRequestsList.get(i).cancel();
            mRequestsList.remove(i);
        }
    }

    public boolean subscribe(Call request) {
        if (request.isExecuted()) {
            Log.w(TAG, "Request is already in execution, " + request.toString());
            return false;
        }
        mRequestsList.add(request);
        return true;
    }

    public void unsubscribe(Call request) {
        request.cancel();
        if (mRequestsList != null) {
            mRequestsList.remove(request);
        }
    }
}
