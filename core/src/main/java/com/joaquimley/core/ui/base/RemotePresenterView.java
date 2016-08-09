package com.joaquimley.core.ui.base;

public interface RemotePresenterView extends BasePresenterView {

    void showProgress();

    void hideProgress();

    void showError(String errorMessage);

    void showEmpty();

    void showMessageLayout(boolean show);

}
