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
import com.joaquimley.core.ui.base.RemoteCallback;
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
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class CharacterPresenterTest {

    @Mock
    private DataManager mDataManager;

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

        List<CharacterMarvel> results = Collections.singletonList(new CharacterMarvel());
        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterRequested(anyLong());

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();


        verify(mDataManager).getCharacter(anyLong(), mGetCharacterCallbackCaptor.capture());
        mGetCharacterCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showCharacter(response.getData().getResults()
                .get(CharacterPresenter.SINGLE_ITEM_INDEX));
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterRequested_Unauthorized() {

        mPresenter.onCharacterRequested(anyLong());

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
    public void characterRequested_Failed() {

        mPresenter.onCharacterRequested(anyLong());

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

        List<Comic> results = asList(new Comic(), new Comic());
        DataContainer<List<Comic>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterComicsRequested(anyLong(), anyInt());

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

        mPresenter.onCharacterComicsRequested(anyLong(), anyInt());

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
        inOrder.verify(mView).showEmpty();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterComicListRequested_Unauthorized() {

        mPresenter.onCharacterComicsRequested(anyLong(), anyInt());

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

        mPresenter.onCharacterComicsRequested(anyLong(), anyInt());

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

        List<Comic> results = asList(new Comic(), new Comic());
        DataContainer<List<Comic>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterSeriesRequested(anyLong(), anyInt());

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

        mPresenter.onCharacterSeriesRequested(anyLong(), anyInt());

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
        inOrder.verify(mView).showEmpty();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterSeriesListRequested_Unauthorized() {

        mPresenter.onCharacterSeriesRequested(anyLong(), anyInt());

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

        mPresenter.onCharacterSeriesRequested(anyLong(), anyInt());

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

        List<Comic> results = asList(new Comic(), new Comic());
        DataContainer<List<Comic>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterStoriesRequested(anyLong(), anyInt());

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

        mPresenter.onCharacterStoriesRequested(anyLong(), anyInt());

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
        inOrder.verify(mView).showEmpty();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterStoriesListRequested_Unauthorized() {

        mPresenter.onCharacterStoriesRequested(anyLong(), anyInt());

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

        mPresenter.onCharacterStoriesRequested(anyLong(), anyInt());

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

        List<Comic> results = asList(new Comic(), new Comic());
        DataContainer<List<Comic>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterEventsRequested(anyLong(), anyInt());

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

        mPresenter.onCharacterEventsRequested(anyLong(), anyInt());

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
        inOrder.verify(mView).showEmpty();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterEventsListRequested_Unauthorized() {

        mPresenter.onCharacterEventsRequested(anyLong(), anyInt());

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

        mPresenter.onCharacterEventsRequested(anyLong(), anyInt());

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
