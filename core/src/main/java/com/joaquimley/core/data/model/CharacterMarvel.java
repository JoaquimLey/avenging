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

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for Marvel character
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterMarvel implements Parcelable {

    @JsonProperty(value = "id")
    private long mId;
    @JsonProperty(value = "name")
    private String mName;
    @JsonProperty(value = "description")
    private String mDescription;
    @JsonProperty(value = "thumbnail")
    private Image mThumbnail;
    @JsonProperty(value = "comics")
    private CharacterComicWrapper mComics;
    @JsonProperty(value = "series")
    private CharacterComicWrapper mSeries;
    @JsonProperty(value = "stories")
    private CharacterComicWrapper mStories;
    @JsonProperty(value = "events")
    private CharacterComicWrapper mEvents;
    @JsonProperty(value = "urls")
    private List<Url> mUrls;

    public CharacterMarvel() {
    }

    /**
     * START of custom methods
     */

    public String getImageUrl() {
        return mThumbnail.buildCompleteImagePath();
    }

    @Override
    public String toString() {
        return mName;
    }

    /**
     * END of custom methods
     */

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Image getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        mThumbnail = thumbnail;
    }

    public CharacterComicWrapper getComics() {
        return mComics;
    }

    public void setComics(CharacterComicWrapper comics) {
        mComics = comics;
    }

    public CharacterComicWrapper getSeries() {
        return mSeries;
    }

    public void setSeries(CharacterComicWrapper series) {
        mSeries = series;
    }

    public CharacterComicWrapper getStories() {
        return mStories;
    }

    public void setStories(CharacterComicWrapper stories) {
        mStories = stories;
    }

    public CharacterComicWrapper getEvents() {
        return mEvents;
    }

    public void setEvents(CharacterComicWrapper events) {
        mEvents = events;
    }

    public List<Url> getUrls() {
        return mUrls;
    }

    public void setUrls(List<Url> urls) {
        mUrls = urls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterMarvel)) return false;
        CharacterMarvel that = (CharacterMarvel) o;
        return mId == that.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeParcelable(mThumbnail, flags);
        dest.writeList(mUrls);
    }

    protected CharacterMarvel(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mDescription = in.readString();
        mThumbnail = in.readParcelable(Image.class.getClassLoader());
        mUrls = new ArrayList<>();
        in.readList(mUrls, Url.class.getClassLoader());
    }

    public static final Parcelable.Creator<CharacterMarvel> CREATOR = new Parcelable.Creator<CharacterMarvel>() {
        @Override
        public CharacterMarvel createFromParcel(Parcel source) {
            return new CharacterMarvel(source);
        }

        @Override
        public CharacterMarvel[] newArray(int size) {
            return new CharacterMarvel[size];
        }
    };
}
