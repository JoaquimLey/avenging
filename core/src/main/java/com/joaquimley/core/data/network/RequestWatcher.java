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
