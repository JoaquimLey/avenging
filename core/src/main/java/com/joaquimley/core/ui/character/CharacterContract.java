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

package com.joaquimley.core.ui.character;

import com.joaquimley.core.ui.base.RemoteView;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.data.model.Comic;

import java.util.List;

public interface CharacterContract {

    interface ViewActions {

        void onCharacterRequested(Long characterId);

        void onCharacterComicsRequested(Long characterId, int limit);

        void onCharacterSeriesRequested(Long characterId, int limit);

        void onCharacterStoriesRequested(Long characterId, int limit);

        void onCharacterEventsRequested(Long characterId, int limit);
    }

    interface CharacterView extends RemoteView {

        void showCharacter(CharacterMarvel character);

        void showComicList(List<Comic> comicList);

        void showSeriesList(List<Comic> seriesList);

        void showStoriesList(List<Comic> storiesList);

        void showEventsList(List<Comic> eventsList);
    }
}
