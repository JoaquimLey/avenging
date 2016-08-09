package com.joaquimley.core.ui.list;

import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.ui.base.RemotePresenterView;

import java.util.List;

public interface ListPresenterView extends RemotePresenterView {

    void showCharacters(List<CharacterMarvel> characterList);

    void showSearchedCharacters(List<CharacterMarvel> characterList);
}
