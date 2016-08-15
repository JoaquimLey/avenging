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

import com.joaquimley.core.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Marvel Service interface factory methods
 */
public class MarvelServiceFactory {

    public static MarvelService makeMarvelService() {
        return makeMarvelService(makeOkHttpClient(false));
    }

    public static MarvelService makeMarvelService(boolean withLoggingInterceptor) {
        return makeMarvelService(makeOkHttpClient(withLoggingInterceptor));
    }

    public static MarvelService makeMarvelService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.PRODUCTION_ENDPOINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(MarvelService.class);
    }

    public static OkHttpClient makeOkHttpClient(boolean withLoggingInterceptor) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
        httpClientBuilder.connectTimeout(MarvelServiceConfig.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(MarvelServiceConfig.HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        if(withLoggingInterceptor) {
            httpClientBuilder.addInterceptor(makeLoggingInterceptor());
        }
        return httpClientBuilder.build();
    }

    public static HttpLoggingInterceptor makeLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
}