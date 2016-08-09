package com.joaquimley.core.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterComicDataContainer {

    @JsonProperty("available")
    protected int mAvailable;
    @JsonProperty("collectionURI")
    protected String mCollectionUri;
    @JsonProperty("returned")
    protected int mReturned;
    @JsonProperty("items")
    public List<Comic> mItems = new ArrayList<>();


    public CharacterComicDataContainer() {
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
