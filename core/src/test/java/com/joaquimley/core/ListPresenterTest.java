package com.joaquimley.core;

import android.text.TextUtils;

import com.joaquimley.core.data.DataManager;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.data.model.DataContainer;
import com.joaquimley.core.data.model.DataWrapper;
import com.joaquimley.core.ui.base.RemoteCallback;
import com.joaquimley.core.ui.list.ListContract;
import com.joaquimley.core.ui.list.ListPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class ListPresenterTest {

    @Mock
    private DataManager mDataManager;

    @Mock
    private ListContract.ListView mView;

    @Captor
    private ArgumentCaptor<RemoteCallback<DataWrapper<List<CharacterMarvel>>>> mGetCharactersListCallbackCaptor;

    private ListPresenter mPresenter;

    @Before
    public void setUp() {
        mockStatic(TextUtils.class);
        mPresenter = new ListPresenter(mDataManager);
        mPresenter.attachView(mView);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void initialListRequested_Success() {

        String searchQuery = null;

        mPresenter.onInitialListRequested();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        List<CharacterMarvel> results = asList(new CharacterMarvel(), new CharacterMarvel());
        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        when(TextUtils.isEmpty(searchQuery)).thenReturn(true);

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery), mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showCharacters(response.getData().getResults());
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void initialListRequested_Unauthorized() {

        String searchQuery = null;

        mPresenter.onInitialListRequested();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery), mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void initialListRequested_Failed() {

        String searchQuery = null;

        mPresenter.onInitialListRequested();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        Throwable error = new Throwable("Unknown error");

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery), mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());
    }

    @Test
    public void characterSearched_Success() {

        String searchQuery = "query";

        mPresenter.onCharacterSearched(searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        List<CharacterMarvel> results = asList(new CharacterMarvel(), new CharacterMarvel());
        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        when(TextUtils.isEmpty(searchQuery)).thenReturn(false);

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery), mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showSearchedCharacters(response.getData().getResults());
    }

    @Test
    public void characterSearched_NoResult() {

        String searchQuery = "query";

        mPresenter.onCharacterSearched(searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        List<CharacterMarvel> results = Collections.emptyList();
        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery), mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showEmpty();
    }

    @After
    public void tearDown() {
        mPresenter.detachView();
    }

}
