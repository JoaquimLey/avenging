package com.joaquimley.core.ui.comic;

import com.joaquimley.core.data.model.Comic;
import com.joaquimley.core.ui.base.BasePresenterView;

import java.util.List;

public interface ComicPresenterView extends BasePresenterView {

    void showComicList(List<Comic> comicList);

    void showSeriesList(List<Comic> seriesList);

    void showStoriesList(List<Comic> storiesList);

    void showEventsList(List<Comic> eventsList);

    void showError(String message);

}