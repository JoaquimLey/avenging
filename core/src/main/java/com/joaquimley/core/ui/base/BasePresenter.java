package com.joaquimley.core.ui.base;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the PresenterView that
 * can be accessed from the children classes by calling getPresenterView().
 */
public class BasePresenter<T extends BasePresenterView> implements Presenter<T> {

    private T mPresenterView;

    @Override
    public void attachView(T presenterView) {
        mPresenterView = presenterView;
    }

    @Override
    public void detachView() {
        mPresenterView = null;
    }

    public boolean isViewAttached() {
        return mPresenterView != null;
    }

    public T getPresenterView() {
        return mPresenterView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new PresenterViewNotAttachedException();
    }

    public static class PresenterViewNotAttachedException extends RuntimeException {
        public PresenterViewNotAttachedException() {
            super("Please call Presenter.attachView(presenterView) before" +
                    " requesting data to the Presenter");
        }
    }
}

