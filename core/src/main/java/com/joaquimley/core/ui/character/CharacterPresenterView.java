package com.joaquimley.core.ui.character;

import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.ui.base.RemotePresenterView;

public interface CharacterPresenterView extends RemotePresenterView {

    void showCharacter(CharacterMarvel character);
}
