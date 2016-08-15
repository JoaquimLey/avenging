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

package com.joaquimley.core.data;

import com.joaquimley.core.BuildConfig;
import com.joaquimley.core.data.model.CharacterDataWrapper;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.data.model.Comic;
import com.joaquimley.core.data.model.ComicDataWrapper;
import com.joaquimley.core.data.network.MarvelService;
import com.joaquimley.core.data.network.MarvelServiceConfig;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Api abstraction
 */
public class MarvelDataManager {

    private final MarvelService mMarvelService;

    public MarvelDataManager(MarvelService marvelService) {
        mMarvelService = marvelService;
    }

    public Call<CharacterDataWrapper> getCharactersList(int offSet, int limit, String searchQuery) {
        long timeStamp = System.currentTimeMillis();
        return mMarvelService.getCharacters(BuildConfig.PUBLIC_KEY,
                MarvelServiceConfig.buildMd5AuthParameter(timeStamp), timeStamp, offSet, limit, searchQuery);
    }

    public Call<CharacterDataWrapper> getCharacter(long characterId) {
        long timeStamp = System.currentTimeMillis();
        return mMarvelService.getCharacter(characterId, BuildConfig.PUBLIC_KEY,
                MarvelServiceConfig.buildMd5AuthParameter(timeStamp), timeStamp);
    }

    public Call<ComicDataWrapper> getComics(long characterId, Integer offset, Integer limit) {
        return getComicListByType(characterId, Comic.COMIC_TYPE_COMICS, offset, limit);
    }

    public Call<ComicDataWrapper> getSeries(long characterId, Integer offset, Integer limit) {
        return getComicListByType(characterId, Comic.COMIC_TYPE_SERIES, offset, limit);
    }

    public Call<ComicDataWrapper> getStories(long characterId, Integer offset, Integer limit) {
        return getComicListByType(characterId, Comic.COMIC_TYPE_STORIES, offset, limit);
    }

    public Call<ComicDataWrapper> getEvents(long characterId, Integer offset, Integer limit) {
        return getComicListByType(characterId, Comic.COMIC_TYPE_EVENTS, offset, limit);
    }

    /**
     * Base request to prevent boilerplate
     *
     * @param id        {@link CharacterMarvel} Id
     * @param comicType Which {@link Comic.Type} list should be requested
     * @return A simple {@link Retrofit} call with preset parameters
     */
    private Call<ComicDataWrapper> getComicListByType(long id, String comicType, Integer offset, Integer limit) {
        long timeStamp = System.currentTimeMillis();
        return mMarvelService.getCharacterComics(id, comicType, offset, limit, BuildConfig.PUBLIC_KEY,
                MarvelServiceConfig.buildMd5AuthParameter(timeStamp), timeStamp);
    }
}
