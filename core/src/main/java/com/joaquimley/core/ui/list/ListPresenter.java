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

package com.joaquimley.core.ui.list;

import com.joaquimley.core.AvengingApplication;
import com.joaquimley.core.data.MarvelDataManager;
import com.joaquimley.core.data.model.CharacterDataWrapper;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPresenter extends BasePresenter<ListPresenterView> {

    private static final int ITEM_REQUEST_INITIAL_OFFSET = 0;
    private static final int ITEM_REQUEST_LIMIT = 6;

    private final MarvelDataManager mDataManager;
    private List<CharacterMarvel> mCharacterList;

    public ListPresenter() {
        mDataManager = new MarvelDataManager(AvengingApplication.getInstance().getMarvelService());
        initItems();
    }

    public ListPresenter(MarvelDataManager dataManager) {
        mDataManager = dataManager;
        initItems();
    }

    private void initItems() {
        mCharacterList = new ArrayList<>();
    }

    @Override
    public void detachView() {
        super.detachView();
        mCharacterList = null;
    }

    public void getCharacters() {
        getCharacters(ITEM_REQUEST_INITIAL_OFFSET, ITEM_REQUEST_LIMIT, false, null);
    }

    public void getCharacters(String query) {
        getCharacters(ITEM_REQUEST_INITIAL_OFFSET, ITEM_REQUEST_LIMIT, false, query);
    }

    public void getCharacters(Integer offSet, Boolean isLoadMore) {
        getCharacters(offSet, ITEM_REQUEST_LIMIT, isLoadMore, null);
    }

    public void getCharacters(Integer offSet, Boolean isLoadMore, String query) {
        getCharacters(offSet, ITEM_REQUEST_LIMIT, isLoadMore, query == null || query.isEmpty() ? null : query);
    }

    public void getCharacters(Integer offSet, Integer limit, Boolean isLoadMore, final String queryString) {
        checkViewAttached();
        getPresenterView().showMessageLayout(false);

        /**
         * Initial request or "refresh" action, reset all states
         */
        if (offSet == ITEM_REQUEST_INITIAL_OFFSET) {
            mCharacterList = new ArrayList<>();
        }

        if (mCharacterList != null && !mCharacterList.isEmpty() && mCharacterList.size() >= offSet + 1) {
            getPresenterView().showCharacters(mCharacterList);
            return;
        }

        final Call<CharacterDataWrapper> request = mDataManager.getCharactersList(offSet, limit, queryString);

        if (!isLoadMore) {
            getPresenterView().showProgress();
        }

        request.enqueue(new Callback<CharacterDataWrapper>() {
            @Override
            public void onResponse(Call<CharacterDataWrapper> call, Response<CharacterDataWrapper> response) {
                getPresenterView().hideProgress();
                switch (response.code()) {
                    case 200:
                        mCharacterList.addAll(response.body().getData().getResults());
                        if (mCharacterList.isEmpty()) {
                            getPresenterView().showEmpty();
                            return;
                        }

                        if(queryString != null && !queryString.isEmpty()) {
                            getPresenterView().showSearchedCharacters(response.body().getData().getResults());
                            return;
                        }
                        getPresenterView().showCharacters(response.body().getData().getResults());
                        break;

                    default:
                        getPresenterView().showError(response.message());
                }
            }

            @Override
            public void onFailure(Call<CharacterDataWrapper> call, Throwable t) {
                getPresenterView().hideProgress();
                getPresenterView().showError(t.getMessage());
            }
        });
    }
}