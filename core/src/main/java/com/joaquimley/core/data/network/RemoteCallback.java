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

import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RemoteCallback<T> implements Callback<T> {
    @Override
    public final void onResponse(Call<T> call, Response<T> response) {
        switch (response.code()) {
            case HttpsURLConnection.HTTP_OK:
            case HttpsURLConnection.HTTP_CREATED:
            case HttpsURLConnection.HTTP_ACCEPTED:
            case HttpsURLConnection.HTTP_NOT_AUTHORITATIVE:
                if (response.body() != null) {
                    onSuccess(response.body());
                }
                break;

            case HttpURLConnection.HTTP_UNAUTHORIZED:
                onUnauthorized();
                break;

            default:
                onFailed(new Throwable("Default " + response.code() + " " + response.message()));
        }
    }

    @Override
    public final void onFailure(Call<T> call, Throwable t) {
        onFailed(t);
    }

    public abstract void onSuccess(T response);

    public abstract void onUnauthorized();

    public abstract void onFailed(Throwable throwable);
}
