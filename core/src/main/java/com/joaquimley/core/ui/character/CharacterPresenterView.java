package com.joaquimley.core.ui.character;

import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.data.model.Comic;
import com.joaquimley.core.ui.base.RemotePresenterView;

import java.util.List;

public interface CharacterPresenterView extends RemotePresenterView {

    void showCharacter(CharacterMarvel character);

    void showComicList(List<Comic> comicList);

    void showSeriesList(List<Comic> seriesList);

    void showStoriesList(List<Comic> storiesList);

    void showEventsList(List<Comic> eventsList);

    void showError(String message);
}
