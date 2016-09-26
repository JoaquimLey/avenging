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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterComicWrapper {

    @JsonProperty("available")
    protected int mAvailable;
    @JsonProperty("collectionURI")
    protected String mCollectionUri;
    @JsonProperty("returned")
    protected int mReturned;
    @JsonProperty("items")
    public List<Comic> mItems = new ArrayList<>();


    public CharacterComicWrapper() {
    }


    public int getAvailable() {
        return mAvailable;
    }

    public void setAvailable(int available) {
        mAvailable = available;
    }

    public String getCollectionUri() {
        return mCollectionUri;
    }

    public void setCollectionUri(String collectionUri) {
        mCollectionUri = collectionUri;
    }

    public int getReturned() {
        return mReturned;
    }

    public void setReturned(int returned) {
        mReturned = returned;
    }

    public List<Comic> getItems() {
        return mItems;
    }

    public void setItems(List<Comic> items) {
        mItems = items;
    }
}
