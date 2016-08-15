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

