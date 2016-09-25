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

import android.support.annotation.Nullable;

import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.data.model.Comic;
import com.joaquimley.core.data.model.DataWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelService {

    /**
     * Retrieve list of characters
     */
    @GET("characters")
    Call<DataWrapper<List<CharacterMarvel>>> getCharacters(@Query("apikey") String publicKey,
                                                           @Query("hash") String md5Digest,
                                                           @Query("ts") long timestamp,
                                                           @Nullable @Query("offset") Integer offset,
                                                           @Nullable @Query("limit") Integer limit,
                                                           @Nullable @Query("nameStartsWith") String searchQuery);

    /**
     * Retrieve character by given Id
     */
    @GET("characters/{characterId}")
    Call<DataWrapper<List<CharacterMarvel>>> getCharacter(@Path("characterId") long characterId,
                                                          @Query("apikey") String publicKey,
                                                          @Query("hash") String md5Digest,
                                                          @Query("ts") long timestamp);

    /**
     * Retrieve list of comics by character Id
     */
    @GET("characters/{characterId}/{comicType}")
    Call<DataWrapper<List<Comic>>> getCharacterComics(@Path("characterId") long characterId,
                                                      @Path("comicType") String comicType,
                                                      @Query("offset") Integer offset,
                                                      @Query("limit") Integer limit,
                                                      @Query("apikey") String publicKey,
                                                      @Query("hash") String md5Digest,
                                                      @Query("ts") long timestamp);
}


