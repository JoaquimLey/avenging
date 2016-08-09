package com.joaquimley.core.ui.base;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the BasePresenterView type that wants to be attached with.
 */
public interface Presenter<V extends BasePresenterView> {

    void attachView(V presenterView);

    void detachView();
}