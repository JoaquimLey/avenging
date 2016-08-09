package com.joaquimley.core.ui.character;

import com.joaquimley.core.AvengingApplication;
import com.joaquimley.core.data.MarvelDataManager;
import com.joaquimley.core.data.model.CharacterDataWrapper;
import com.joaquimley.core.data.network.RequestWatcher;
import com.joaquimley.core.ui.base.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterPresenter extends BasePresenter<CharacterPresenterView> {

    private static final int SINGLE_ITEM_INDEX = 0;

    private final MarvelDataManager mDataManager;
    private RequestWatcher mRequestWatcher;

    public CharacterPresenter() {
        mDataManager = new MarvelDataManager(AvengingApplication.getInstance().getMarvelService());
        initItems();
    }

    public CharacterPresenter(MarvelDataManager dataManager) {
        mDataManager = dataManager;
        initItems();
    }

    private void initItems() {
        mRequestWatcher = new RequestWatcher();
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mRequestWatcher != null) {
            mRequestWatcher.detach();
        }
        mRequestWatcher = null;
    }

    public void getCharacter(long id) {
        checkViewAttached();
        final Call<CharacterDataWrapper> request = mDataManager.getCharacter(id);
        if (!mRequestWatcher.subscribe(request)) {
            return;
        }
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
}