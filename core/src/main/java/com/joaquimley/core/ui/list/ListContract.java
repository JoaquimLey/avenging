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

package com.joaquimley.core.ui.list;

import com.joaquimley.core.ui.base.RemoteView;
import com.joaquimley.core.data.model.CharacterMarvel;

import java.util.List;

public interface ListContract {

    interface ViewActions {
        void onInitialListRequested();

        void onListEndReached(Integer offset, Integer limit, String searchQuery);

        void onCharacterSearched(String searchQuery);
    }

    interface ListView extends RemoteView {

        void showCharacters(List<CharacterMarvel> characterList);

        void showSearchedCharacters(List<CharacterMarvel> characterList);
    }
}
