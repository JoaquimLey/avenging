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

import com.joaquimley.core.BuildConfig;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Constants for Api
 */
public class MarvelServiceConfig {

    public static final int HTTP_CONNECT_TIMEOUT = 6000;
    public static final int HTTP_READ_TIMEOUT = 10000;
    public static final String MD5 = "MD5";
    public static final String MD5_DIGEST_FIRST_CHAR = "0";

    /**
     * Builds the required API "hash" parameter (timeStamp + privateKey + publicKey)
     *
     * @param timeStamp Current timeStamp
     * @return MD5 hash string
     */
    public static String buildMd5AuthParameter(long timeStamp) {

        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] messageDigest = md.digest((timeStamp + BuildConfig.PRIVATE_KEY + BuildConfig.PUBLIC_KEY).getBytes());
            BigInteger number = new BigInteger(1, messageDigest);

            String md5 = number.toString(16);
            while (md5.length() < 32) {
                md5 = MD5_DIGEST_FIRST_CHAR + md5;
            }
            return md5;

        } catch (NoSuchAlgorithmException e) {
            Log.e("MarvelServiceConfig", "Error hashing required parameters: " + e.getMessage());
            return "";
        }
    }

}

/**
 * -- Request response list --
 * 409	Limit greater than 100.
 * 409	Limit invalid or below 1.
 * 409	Invalid or unrecognized parameter.
 * 409	Empty parameter.
 * 409	Invalid or unrecognized ordering parameter.
 * 409	Too many values sent to a multi-value list filter.
 * 409	Invalid value passed to filter.
 * <p/>
 * <p/>
 * -- Authorization --
 * 409	Missing API Key	Occurs when the apikey parameter is not included with a request.
 * 409	Missing Hash	Occurs when an apikey parameter is included with a request, a ts parameter is present, but no hash parameter is sent. Occurs on server-side applications only.
 * 409	Missing Timestamp	Occurs when an apikey parameter is included with a request, a hash parameter is present, but no ts parameter is sent. Occurs on server-side applications only.
 * 401	Invalid Referer	Occurs when a referrer which is not valid for the passed apikey parameter is sent.
 * 401	Invalid Hash	Occurs when a ts, hash and apikey parameter are sent but the hash is not valid per the above hash generation rule.
 * 405	Method Not Allowed	Occurs when an API endpoint is accessed using an HTTP verb which is not allowed for that endpoint.
 * 403	Forbidden
 * <p/>
 * -- Authorization --
 * 409	Missing API Key	Occurs when the apikey parameter is not included with a request.
 * 409	Missing Hash	Occurs when an apikey parameter is included with a request, a ts parameter is present, but no hash parameter is sent. Occurs on server-side applications only.
 * 409	Missing Timestamp	Occurs when an apikey parameter is included with a request, a hash parameter is present, but no ts parameter is sent. Occurs on server-side applications only.
 * 401	Invalid Referer	Occurs when a referrer which is not valid for the passed apikey parameter is sent.
 * 401	Invalid Hash	Occurs when a ts, hash and apikey parameter are sent but the hash is not valid per the above hash generation rule.
 * 405	Method Not Allowed	Occurs when an API endpoint is accessed using an HTTP verb which is not allowed for that endpoint.
 * 403	Forbidden
 * <p/>
 * -- Authorization --
 * 409	Missing API Key	Occurs when the apikey parameter is not included with a request.
 * 409	Missing Hash	Occurs when an apikey parameter is included with a request, a ts parameter is present, but no hash parameter is sent. Occurs on server-side applications only.
 * 409	Missing Timestamp	Occurs when an apikey parameter is included with a request, a hash parameter is present, but no ts parameter is sent. Occurs on server-side applications only.
 * 401	Invalid Referer	Occurs when a referrer which is not valid for the passed apikey parameter is sent.
 * 401	Invalid Hash	Occurs when a ts, hash and apikey parameter are sent but the hash is not valid per the above hash generation rule.
 * 405	Method Not Allowed	Occurs when an API endpoint is accessed using an HTTP verb which is not allowed for that endpoint.
 * 403	Forbidden
 * <p/>
 * -- Authorization --
 * 409	Missing API Key	Occurs when the apikey parameter is not included with a request.
 * 409	Missing Hash	Occurs when an apikey parameter is included with a request, a ts parameter is present, but no hash parameter is sent. Occurs on server-side applications only.
 * 409	Missing Timestamp	Occurs when an apikey parameter is included with a request, a hash parameter is present, but no ts parameter is sent. Occurs on server-side applications only.
 * 401	Invalid Referer	Occurs when a referrer which is not valid for the passed apikey parameter is sent.
 * 401	Invalid Hash	Occurs when a ts, hash and apikey parameter are sent but the hash is not valid per the above hash generation rule.
 * 405	Method Not Allowed	Occurs when an API endpoint is accessed using an HTTP verb which is not allowed for that endpoint.
 * 403	Forbidden
 */

/**
 -- Authorization --
 409	Missing API Key	Occurs when the apikey parameter is not included with a request.
 409	Missing Hash	Occurs when an apikey parameter is included with a request, a ts parameter is present, but no hash parameter is sent. Occurs on server-side applications only.
 409	Missing Timestamp	Occurs when an apikey parameter is included with a request, a hash parameter is present, but no ts parameter is sent. Occurs on server-side applications only.
 401	Invalid Referer	Occurs when a referrer which is not valid for the passed apikey parameter is sent.
 401	Invalid Hash	Occurs when a ts, hash and apikey parameter are sent but the hash is not valid per the above hash generation rule.
 405	Method Not Allowed	Occurs when an API endpoint is accessed using an HTTP verb which is not allowed for that endpoint.
 403	Forbidden

 */