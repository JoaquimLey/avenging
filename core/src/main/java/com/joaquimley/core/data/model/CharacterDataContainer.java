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
