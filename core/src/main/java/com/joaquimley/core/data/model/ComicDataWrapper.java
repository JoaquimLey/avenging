package com.joaquimley.core.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComicDataWrapper extends BaseDataWrapper {

    @JsonProperty("data")
    public ComicDataContainer mData;

    public ComicDataWrapper() {
    }

    public ComicDataContainer getData() {
        return mData;
    }

    public void setData(ComicDataContainer data) {
        mData = data;
    }
}
