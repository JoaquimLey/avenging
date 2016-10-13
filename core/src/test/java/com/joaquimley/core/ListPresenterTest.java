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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
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

    @Test
    public void initialListRequested() {

        mPresenter.onInitialListRequested();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        List<CharacterMarvel> results = Arrays.asList(new CharacterMarvel(), new CharacterMarvel());
        DataContainer<List<CharacterMarvel>> data = new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<CharacterMarvel>> response = new DataWrapper<>();
        response.setData(data);

        when(TextUtils.isEmpty(anyString())).thenReturn(true);

        verify(mDataManager).getCharactersList(anyInt(), anyInt(), eq((String) null), mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showCharacters(response.getData().getResults());
    }

    @After
    public void tearDown() {
        mPresenter.detachView();
    }

}
