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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.joaquimley.core.data.DataManager;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.data.model.DataWrapper;
import com.joaquimley.core.ui.base.BasePresenter;
import com.joaquimley.core.data.network.RemoteCallback;

import java.util.List;

public class ListPresenter extends BasePresenter<ListContract.ListView> implements ListContract.ViewActions {

    private static final int ITEM_REQUEST_INITIAL_OFFSET = 0;
    private static final int ITEM_REQUEST_LIMIT = 6;

    private final DataManager mDataManager;

    public ListPresenter(@NonNull DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onInitialListRequested() {
        getCharacters(ITEM_REQUEST_INITIAL_OFFSET, ITEM_REQUEST_LIMIT, null);
    }

    @Override
    public void onListEndReached(Integer offset, @Nullable Integer limit, String searchQuery) {
        getCharacters(offset, limit == null ? ITEM_REQUEST_LIMIT : limit, searchQuery);
    }

    @Override
    public void onCharacterSearched(String searchQuery) {
        getCharacters(ITEM_REQUEST_INITIAL_OFFSET, ITEM_REQUEST_LIMIT, searchQuery);
    }

    private void getCharacters(Integer offset, Integer limit, final String searchQuery) {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        mDataManager.getCharactersList(offset, limit, searchQuery,
                new RemoteCallback<DataWrapper<List<CharacterMarvel>>>() {
                    @Override
                    public void onSuccess(DataWrapper<List<CharacterMarvel>> response) {
                        if (!isViewAttached()) return;
                        mView.hideProgress();
                        List<CharacterMarvel> responseResults = response.getData().getResults();
                        if (responseResults.isEmpty()) {
                            mView.showEmpty();
                            return;
                        }

                        if (TextUtils.isEmpty(searchQuery)) {
                            mView.showCharacters(responseResults);
                        } else {
                            mView.showSearchedCharacters(responseResults);
                        }
                    }

                    @Override
                    public void onUnauthorized() {
                        if (!isViewAttached()) return;
                        mView.hideProgress();
                        mView.showUnauthorizedError();
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (!isViewAttached()) return;
                        mView.hideProgress();
                        mView.showError(throwable.getMessage());
                    }
                });
    }
}