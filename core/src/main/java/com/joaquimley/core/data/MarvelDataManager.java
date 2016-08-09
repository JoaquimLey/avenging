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
