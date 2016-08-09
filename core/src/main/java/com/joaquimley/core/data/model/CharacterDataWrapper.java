package com.joaquimley.core.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterDataWrapper extends BaseDataWrapper {

    @JsonProperty("data")
    public CharacterDataContainer mData;

    public CharacterDataWrapper(){
    }

    public CharacterDataContainer getData() {
        return mData;
    }

    public void setData(CharacterDataContainer data) {
        mData = data;
    }
}
