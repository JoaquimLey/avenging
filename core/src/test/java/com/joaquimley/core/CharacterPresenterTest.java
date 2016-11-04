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

package com.joaquimley.core;

import com.joaquimley.core.data.DataManager;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.data.model.Comic;
import com.joaquimley.core.data.model.DataContainer;
import com.joaquimley.core.data.model.DataWrapper;
import com.joaquimley.core.data.network.RemoteCallback;
import com.joaquimley.core.ui.character.CharacterContract;
import com.joaquimley.core.ui.character.CharacterPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class CharacterPresenterTest {

    @Mock
    private DataManager mDataManager;

    @Mock
    private Character mCharacter;

    @Mock
    private CharacterContract.CharacterView mView;

    @Captor
    private ArgumentCaptor<RemoteCallback<DataWrapper<List<CharacterMarvel>>>> mGetCharacterCallbackCaptor;

    @Captor
    private ArgumentCaptor<RemoteCallback<DataWrapper<List<Comic>>>> mGetComicListCallbackCaptor;

    private CharacterPresenter mPresenter;

    @Before
    public void setUp() {
        mPresenter = new CharacterPresenter(mDataManager);
        mPresenter.attachView(mView);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void characterRequested_Success() {

        long characterId = 1L;

        List<CharacterMarvel> results = Collections.singletonList(new CharacterMarvel());
        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterRequested(characterId);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();


        verify(mDataManager).getCharacter(anyLong(), mGetCharacterCallbackCaptor.capture());
        mGetCharacterCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showCharacter(response.getData().getResults()
                .get(CharacterPresenter.SINGLE_ITEM_INDEX));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void characterRequested_SameId() {

        Long characterId = 1L;
        CharacterMarvel characterMarvel = new CharacterMarvel();
        characterMarvel.setId(characterId);
        List<CharacterMarvel> results = Collections.singletonList(characterMarvel);
        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterRequested(characterId);
        verify(mDataManager).getCharacter(anyLong(), mGetCharacterCallbackCaptor.capture());
        mGetCharacterCallbackCaptor.getValue().onSuccess(response);
        verify(mView).showCharacter(response.getData().getResults()
                .get(CharacterPresenter.SINGLE_ITEM_INDEX));

        mPresenter.onCharacterRequested(characterId);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showCharacter(response.getData().getResults()
                .get(CharacterPresenter.SINGLE_ITEM_INDEX));
        verify(mView, Mockito.times(1)).showProgress();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterRequested_Unauthorized() {

        long characterId = 1L;

        mPresenter.onCharacterRequested(characterId);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getCharacter(anyLong(), mGetCharacterCallbackCaptor.capture());
        mGetCharacterCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterRequested_Empty() {

        long characterId = 1L;
        List<CharacterMarvel> results = Collections.emptyList();
        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterRequested(characterId);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getCharacter(anyLong(), mGetCharacterCallbackCaptor.capture());
        mGetCharacterCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(anyString());
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterRequested_Failed() {

        long characterId = 1L;

        mPresenter.onCharacterRequested(characterId);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        Throwable error = new Throwable("Unknown error");

        verify(mDataManager).getCharacter(anyLong(), mGetCharacterCallbackCaptor.capture());
        mGetCharacterCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());
    }

    @Test
    public void characterComicListRequested_Success() {

        long characterId = 1L;
        int limit = 30;

        List<Comic> results = asList(new Comic(), new Comic());
        DataContainer<List<Comic>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterComicsRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getComics(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showComicList(response.getData().getResults());
    }

    @Test
    public void characterComicListRequested_NoResult() {

        long characterId = 1L;
        int limit = 30;

        mPresenter.onCharacterComicsRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        List<Comic> results = Collections.emptyList();
        DataContainer<List<Comic>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response = new DataWrapper<>();
        response.setData(data);

        verify(mDataManager).getComics(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(anyString());
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterComicListRequested_Unauthorized() {

        long characterId = 1L;
        int limit = 30;

        mPresenter.onCharacterComicsRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getComics(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterComicListRequested_Failed() {

        long characterId = 1L;
        int limit = 30;

        mPresenter.onCharacterComicsRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        Throwable error = new Throwable("Unknown error");

        verify(mDataManager).getComics(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());
    }

    @Test
    public void characterSeriesListRequested_Success() {

        long characterId = 1L;
        int limit = 30;

        List<Comic> results = asList(new Comic(), new Comic());
        DataContainer<List<Comic>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterSeriesRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getSeries(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showSeriesList(response.getData().getResults());
    }

    @Test
    public void characterSeriesListRequested_NoResult() {

        long characterId = 1L;
        int limit = 30;

        mPresenter.onCharacterSeriesRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        List<Comic> results = Collections.emptyList();
        DataContainer<List<Comic>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response = new DataWrapper<>();
        response.setData(data);

        verify(mDataManager).getSeries(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(anyString());
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterSeriesListRequested_Unauthorized() {

        long characterId = 1L;
        int limit = 30;

        mPresenter.onCharacterSeriesRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getSeries(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterSeriesListRequested_Failed() {

        long characterId = 1L;
        int limit = 30;

        mPresenter.onCharacterSeriesRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        Throwable error = new Throwable("Unknown error");

        verify(mDataManager).getSeries(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());
    }


    @Test
    public void characterStoriesListRequested_Success() {

        long characterId = 1L;
        int limit = 30;

        List<Comic> results = asList(new Comic(), new Comic());
        DataContainer<List<Comic>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterStoriesRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getStories(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showStoriesList(response.getData().getResults());
    }

    @Test
    public void characterStoriesListRequested_NoResult() {

        long characterId = 1L;
        int limit = 30;

        mPresenter.onCharacterStoriesRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        List<Comic> results = Collections.emptyList();
        DataContainer<List<Comic>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response = new DataWrapper<>();
        response.setData(data);

        verify(mDataManager).getStories(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(anyString());
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterStoriesListRequested_Unauthorized() {

        long characterId = 1L;
        int limit = 30;

        mPresenter.onCharacterStoriesRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getStories(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterStoriesListRequested_Failed() {

        long characterId = 1L;
        int limit = 30;

        mPresenter.onCharacterStoriesRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        Throwable error = new Throwable("Unknown error");

        verify(mDataManager).getStories(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());
    }

    @Test
    public void characterEventsListRequested_Success() {

        long characterId = 1L;
        int limit = 30;

        List<Comic> results = asList(new Comic(), new Comic());
        DataContainer<List<Comic>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterEventsRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getEvents(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showEventsList(response.getData().getResults());
    }

    @Test
    public void characterEventsListRequested_NoResult() {

        long characterId = 1L;
        int limit = 30;

        mPresenter.onCharacterEventsRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        List<Comic> results = Collections.emptyList();
        DataContainer<List<Comic>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response = new DataWrapper<>();
        response.setData(data);

        verify(mDataManager).getEvents(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(anyString());
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterEventsListRequested_Unauthorized() {

        long characterId = 1L;
        int limit = 30;

        mPresenter.onCharacterEventsRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getEvents(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterEventsListRequested_Failed() {

        long characterId = 1L;
        int limit = 30;

        mPresenter.onCharacterEventsRequested(characterId, limit);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        Throwable error = new Throwable("Unknown error");

        verify(mDataManager).getEvents(anyLong(), anyInt(), anyInt(), mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());
    }


    @After
    public void tearDown() {
        mPresenter.detachView();
    }

}
