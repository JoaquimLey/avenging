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

package com.joaquimley.core.ui.character;

import com.joaquimley.core.AvengingApplication;
import com.joaquimley.core.data.MarvelDataManager;
import com.joaquimley.core.data.model.CharacterDataWrapper;
import com.joaquimley.core.data.model.Comic;
import com.joaquimley.core.data.model.ComicDataWrapper;
import com.joaquimley.core.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterPresenter extends BasePresenter<CharacterPresenterView> {

    private static final int SINGLE_ITEM_INDEX = 0;

    private final MarvelDataManager mDataManager;

    private List<Comic> mComicList;
    private List<Comic> mSeriesList;
    private List<Comic> mStoriesList;
    private List<Comic> mEventsList;

    public CharacterPresenter() {
        mDataManager = new MarvelDataManager(AvengingApplication.getInstance().getMarvelService());
        mComicList  = new ArrayList<>();
        mSeriesList = new ArrayList<>();
        mStoriesList = new ArrayList<>();
        mEventsList = new ArrayList<>();
    }

    @Override
    public void detachView() {
        mComicList = null;
        mSeriesList = null;
        mStoriesList = null;
        mEventsList = null;
        super.detachView();
    }

    public void getCharacter(long id) {
        checkViewAttached();
        final Call<CharacterDataWrapper> request = mDataManager.getCharacter(id);
        getPresenterView().showMessageLayout(false);
        getPresenterView().showProgress();
        request.enqueue(new Callback<CharacterDataWrapper>() {
            @Override
            public void onResponse(Call<CharacterDataWrapper> call, Response<CharacterDataWrapper> response) {
                switch (response.code()) {
                    case 200:
                        getPresenterView().hideProgress();
                        if (response.body().getData().getResults().isEmpty()) {
                            getPresenterView().showEmpty();
                            return;
                        }
                        getPresenterView().showCharacter(response.body().getData().getResults().get(SINGLE_ITEM_INDEX));
                        break;

                    default:
                        getPresenterView().hideProgress();
                        getPresenterView().showError("Server error: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CharacterDataWrapper> call, Throwable t) {
                getPresenterView().hideProgress();
                getPresenterView().showError("Failed: " + t.getMessage());
            }
        });
    }

    public void getComicList(long id) {
        getComicList(id, null, null);
    }

    public void getComicList(long id, int limit) {
        getComicList(id, null, limit);
    }

    public void getComicList(long id, Integer offset, Integer limit) {
        checkViewAttached();
        if (mComicList != null) {
            getPresenterView().showComicList(mComicList);
            return;
        }

        final Call<ComicDataWrapper> request = mDataManager.getComics(id, offset, limit);

        request.enqueue(new Callback<ComicDataWrapper>() {
            @Override
            public void onResponse(Call<ComicDataWrapper> call, Response<ComicDataWrapper> response) {
                switch (response.code()) {
                    case 200:
                        mComicList = response.body().getData().getResults();
                        if (response.body().getData().getResults().isEmpty()) {
                            return;
                        }
                        getPresenterView().showComicList(mComicList);
                        break;

                    default:
                        getPresenterView().showError("Server error: " + response.code() + " "
                                + response.message());
                }
            }

            @Override
            public void onFailure(Call<ComicDataWrapper> call, Throwable t) {
                if (getPresenterView() != null) {
                    getPresenterView().showError(t.toString());
                }
            }
        });
    }

    public void getSeriesList(long id) {
        getSeriesList(id, null, null);
    }

    public void getSeriesList(long id, int limit) {
        getSeriesList(id, null, limit);
    }

    public void getSeriesList(long id, Integer offset, Integer limit) {
        checkViewAttached();
        if (mSeriesList != null) {
            getPresenterView().showComicList(mSeriesList);
            return;
        }

        final Call<ComicDataWrapper> request = mDataManager.getSeries(id, offset, limit);

        request.enqueue(new Callback<ComicDataWrapper>() {
            @Override
            public void onResponse(Call<ComicDataWrapper> call, Response<ComicDataWrapper> response) {
                switch (response.code()) {
                    case 200:
                        mSeriesList = response.body().getData().getResults();
                        if (response.body().getData().getResults().isEmpty()) {
                            return;
                        }
                        getPresenterView().showSeriesList(mSeriesList);
                        break;

                    default:
                        getPresenterView().showError("Server error: " + response.code() + " "
                                + response.message());
                }
            }

            @Override
            public void onFailure(Call<ComicDataWrapper> call, Throwable t) {
                if (getPresenterView() != null) {
                    getPresenterView().showError("Failed: " + t.getMessage());
                }
            }
        });
    }

    public void getStoriesList(long id) {
        getStoriesList(id, null, null);
    }

    public void getStoriesList(long id, int limit) {
        getStoriesList(id, null, limit);
    }

    public void getStoriesList(long id, Integer offset, Integer limit) {
        checkViewAttached();
        if (mStoriesList != null) {
            getPresenterView().showComicList(mStoriesList);
            return;
        }

        final Call<ComicDataWrapper> request = mDataManager.getStories(id, offset, limit);

        request.enqueue(new Callback<ComicDataWrapper>() {
            @Override
            public void onResponse(Call<ComicDataWrapper> call, Response<ComicDataWrapper> response) {
                switch (response.code()) {
                    case 200:
                        mStoriesList = response.body().getData().getResults();
                        if (response.body().getData().getResults().isEmpty()) {
                            return;
                        }
                        getPresenterView().showStoriesList(mStoriesList);
                        break;

                    default:
                        getPresenterView().showError("Server error: " + response.code() + " "
                                + response.message());
                }
            }

            @Override
            public void onFailure(Call<ComicDataWrapper> call, Throwable t) {
                if (getPresenterView() != null) {
                    getPresenterView().showError("Failed: " + t.getMessage());
                }
            }
        });
    }

    public void getEventsList(long id) {
        getEventsList(id, null, null);
    }

    public void getEventsList(long id, int limit) {
        getEventsList(id, null, limit);
    }

    public void getEventsList(long id, Integer offset, Integer limit) {
        checkViewAttached();
        if (mEventsList != null) {
            getPresenterView().showComicList(mEventsList);
            return;
        }

        final Call<ComicDataWrapper> request = mDataManager.getEvents(id, offset, limit);

        request.enqueue(new Callback<ComicDataWrapper>() {
            @Override
            public void onResponse(Call<ComicDataWrapper> call, Response<ComicDataWrapper> response) {
                switch (response.code()) {
                    case 200:
                        mEventsList = response.body().getData().getResults();
                        if (response.body().getData().getResults().isEmpty()) {
                            return;
                        }
                        getPresenterView().showEventsList(mEventsList);
                        break;

                    default:
                        getPresenterView().showError("Server error: " + response.code() + " "
                                + response.message());
                }
            }

            @Override
            public void onFailure(Call<ComicDataWrapper> call, Throwable t) {
                if (getPresenterView() != null) {
                    getPresenterView().showError("Failed: " + t.getMessage());
                }
            }
        });
    }
}