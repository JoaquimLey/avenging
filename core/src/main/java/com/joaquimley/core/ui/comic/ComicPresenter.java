package com.joaquimley.core.ui.comic;

import com.joaquimley.core.AvengingApplication;
import com.joaquimley.core.data.MarvelDataManager;
import com.joaquimley.core.data.model.Comic;
import com.joaquimley.core.data.model.ComicDataWrapper;
import com.joaquimley.core.data.network.RequestWatcher;
import com.joaquimley.core.ui.base.BasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicPresenter extends BasePresenter<ComicPresenterView> {

    private MarvelDataManager mDataManager;
    private RequestWatcher mRequestWatcher;

    private List<Comic> mComicList;
    private List<Comic> mSeriesList;
    private List<Comic> mStoriesList;
    private List<Comic> mEventsList;

    public ComicPresenter() {
        mDataManager = new MarvelDataManager(AvengingApplication.getInstance().getMarvelService());
        initItems();
    }

    public ComicPresenter(MarvelDataManager dataManager) {
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
        mComicList = null;
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
        if (!mRequestWatcher.subscribe(request)) {
            return;
        }

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
        if (!mRequestWatcher.subscribe(request)) {
            return;
        }

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
        if (!mRequestWatcher.subscribe(request)) {
            return;
        }

        request.enqueue(new Callback<ComicDataWrapper>() {
            @Override
            public void onResponse(Call<ComicDataWrapper> call, Response<ComicDataWrapper> response) {
                mRequestWatcher.unsubscribe(request);
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
        if (!mRequestWatcher.subscribe(request)) {
            return;
        }

        request.enqueue(new Callback<ComicDataWrapper>() {
            @Override
            public void onResponse(Call<ComicDataWrapper> call, Response<ComicDataWrapper> response) {
                mRequestWatcher.unsubscribe(request);
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