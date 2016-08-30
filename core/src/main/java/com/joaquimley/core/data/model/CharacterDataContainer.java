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

package com.joaquimley.core.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CharacterDataContainer extends BaseDataContainer {

    @JsonProperty("results")
    public List<CharacterMarvel> mResults = new ArrayList<>();

    public CharacterDataContainer() {
    }

    public List<CharacterMarvel> getResults() {
        return mResults;
    }

    public void setResults(List<CharacterMarvel> results) {
        mResults = results;
    }
}
