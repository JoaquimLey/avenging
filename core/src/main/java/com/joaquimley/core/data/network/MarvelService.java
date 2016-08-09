package com.joaquimley.core.data.network;

import android.support.annotation.Nullable;

import com.joaquimley.core.data.model.CharacterDataWrapper;
import com.joaquimley.core.data.model.ComicDataWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelService {

    /**
     * Retrieve list of characters
     */
    @GET("characters")
    Call<CharacterDataWrapper> getCharacters(@Query("apikey") String publicKey,
                                             @Query("hash") String md5Digest,
                                             @Query("ts") long timestamp,
                                             @Nullable @Query("offset") Integer offset,
                                             @Nullable @Query("limit") Integer limit,
                                             @Nullable @Query("nameStartsWith") String searchQuery);

    /**
     * Retrieve character by given Id
     */
    @GET("characters/{characterId}")
    Call<CharacterDataWrapper> getCharacter(@Path("characterId") long characterId,
                                            @Query("apikey") String publicKey,
                                            @Query("hash") String md5Digest,
                                            @Query("ts") long timestamp);

    /**
     * Retrieve list of comics by character Id
     */
    @GET("characters/{characterId}/{comicType}")
    Call<ComicDataWrapper> getCharacterComics(@Path("characterId") long characterId,
                                              @Path("comicType") String comicType,
                                              @Query("offset") Integer offset,
                                              @Query("limit") Integer limit,
                                              @Query("apikey") String publicKey,
                                              @Query("hash") String md5Digest,
                                              @Query("ts") long timestamp);
}


