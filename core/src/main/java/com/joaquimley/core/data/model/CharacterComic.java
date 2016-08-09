package com.joaquimley.core.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterComic {

    @JsonProperty("name")
    protected String mName;
    @JsonProperty("resourceURI")
    protected String mResourceUri;

    public CharacterComic() {
    }

    public int getIdFromResourceUri() {
        return mResourceUri != null ? Integer.parseInt(mResourceUri.substring(mResourceUri.lastIndexOf("/") + 1)) : -1;
    }

    public String getResourceUri() {
        return mResourceUri;
    }

    public void setResourceUri(String resourceUri) {
        mResourceUri = resourceUri;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }


}
