package com.joaquimley.core;

import android.text.TextUtils;

import com.joaquimley.core.data.DataManager;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.data.model.DataContainer;
import com.joaquimley.core.data.model.DataWrapper;
import com.joaquimley.core.data.network.RemoteCallback;
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
    public void initialCharacterListRequested_Success() {

        String searchQuery = null;
        List<CharacterMarvel> results = asList(new CharacterMarvel(), new CharacterMarvel());

        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onInitialListRequested();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        when(TextUtils.isEmpty(searchQuery)).thenReturn(true);

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showCharacters(response.getData().getResults());
    }

    @Test
    public void initialCharacterListRequested_NoResult() {

        String searchQuery = null;
        List<CharacterMarvel> results = Collections.emptyList();

        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onInitialListRequested();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        when(TextUtils.isEmpty(searchQuery)).thenReturn(true);

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery), mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showEmpty();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void initialCharacterListRequested_Unauthorized() {

        String searchQuery = null;

        mPresenter.onInitialListRequested();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        when(TextUtils.isEmpty(searchQuery)).thenReturn(true);

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void initialCharacterListRequested_Failed() {

        String searchQuery = null;

        mPresenter.onInitialListRequested();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        Throwable error = new Throwable("Unknown error");

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void listEndReachedNoSearchQuery_Success() {

        String searchQuery = null;
        int offset = 2;
        int limit = 30;
        List<CharacterMarvel> results = asList(new CharacterMarvel(), new CharacterMarvel());

        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onListEndReached(offset, limit, searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        when(TextUtils.isEmpty(searchQuery)).thenReturn(true);

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showCharacters(response.getData().getResults());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void listEndReachedNoSearchQuery_NoResult() {

        String searchQuery = null;
        int offset = 2;
        int limit = 30;

        List<CharacterMarvel> results = Collections.emptyList();

        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onListEndReached(offset, limit, searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        when(TextUtils.isEmpty(searchQuery)).thenReturn(true);

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showEmpty();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void listEndReachedNoSearchQuery_Unauthorized() {

        String searchQuery = null;
        int offset = 2;
        int limit = 30;

        mPresenter.onListEndReached(offset, limit, searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void listEndReachedNoSearchQuery_Failed() {

        String searchQuery = null;
        int offset = 2;
        int limit = 30;

        mPresenter.onListEndReached(offset, limit, searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        Throwable error = new Throwable("Unknown error");

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());
    }

    @Test
    public void listEndReachedSearchQuery_Success() {

        String searchQuery = "query";
        int offset = 2;
        int limit = 30;

        List<CharacterMarvel> results = asList(new CharacterMarvel(), new CharacterMarvel());

        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onListEndReached(offset, limit, searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        when(TextUtils.isEmpty(searchQuery)).thenReturn(false);

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showSearchedCharacters(response.getData().getResults());
    }

    @Test
    public void listEndReachedSearchQuery_NoResult() {

        String searchQuery = "query";
        int offset = 2;
        int limit = 30;

        List<CharacterMarvel> results = Collections.emptyList();

        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onListEndReached(offset, limit, searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        when(TextUtils.isEmpty(searchQuery)).thenReturn(false);
        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showEmpty();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void listEndReachedSearchQuery_Unauthorized() {

        String searchQuery = "query";
        int offset = 2;
        int limit = 30;

        mPresenter.onListEndReached(offset, limit, searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void listEndReachedSearchQuery_Failed() {

        String searchQuery = "query";
        int offset = 2;
        int limit = 30;

        mPresenter.onListEndReached(offset, limit, searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        Throwable error = new Throwable("Unknown error");

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());
    }

    @Test
    public void characterListSearched_Success() {

        String searchQuery = "query";

        List<CharacterMarvel> results = asList(new CharacterMarvel(), new CharacterMarvel());

        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterSearched(searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        when(TextUtils.isEmpty(searchQuery)).thenReturn(false);

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showSearchedCharacters(response.getData().getResults());
    }

    @Test
    public void characterSearched_NoResult() {

        String searchQuery = "query";
        List<CharacterMarvel> results = Collections.emptyList();

        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterSearched(searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        when(TextUtils.isEmpty(searchQuery)).thenReturn(false);

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showEmpty();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterListSearchedRequested_Unauthorized() {

        String searchQuery = "query";

        when(TextUtils.isEmpty(searchQuery)).thenReturn(false);
        mPresenter.onCharacterSearched(searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();
    }

    @SuppressWarnings({"ConstantConditions", "ThrowableInstanceNeverThrown"})
    @Test
    public void characterListSearchedRequested_Failed() {

        String searchQuery = "query";
        when(TextUtils.isEmpty(searchQuery)).thenReturn(false);
        mPresenter.onCharacterSearched(searchQuery);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        Throwable error = new Throwable("Unknown error");

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());
    }

    @After
    public void tearDown() {
        mPresenter.detachView();
    }

}
