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
import android.support.annotation.StringDef;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comic extends CharacterComic implements Parcelable {

    public static final String COMIC_TYPE_COMICS = "comics";
    public static final String COMIC_TYPE_SERIES = "series";
    public static final String COMIC_TYPE_STORIES = "stories";
    public static final String COMIC_TYPE_EVENTS = "events";

    @StringDef({COMIC_TYPE_COMICS, COMIC_TYPE_SERIES, COMIC_TYPE_STORIES, COMIC_TYPE_EVENTS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {}

    @JsonProperty("id")
    private Integer mId;
    @JsonProperty("title")
    private String mTitle;
    @JsonProperty("description")
    private Object mDescription;
    @JsonProperty("startYear")
    private Integer mStartYear;
    @JsonProperty("endYear")
    private Integer mEndYear;
    @JsonProperty("rating")
    private String mRating;
    @Type
    @JsonProperty("type")
    private String mType;
    @JsonProperty("modified")
    private String mModified;
    @JsonProperty(value = "thumbnail")
    private Image mThumbnail;
    @JsonProperty(value = "images")
    private List<Image> mImageList;

    public Comic() {
    }

    public String getThumbnailUrl() {
        if (mThumbnail != null) {
            return mThumbnail.buildCompleteImagePath();
        }
        return "";
    }

    public String getImageUrl(int index) {
        if (mImageList != null && mImageList.size() >= index) {
            return mImageList.get(index).buildCompleteImagePath();
        }
        return "";
    }

    public ArrayList<String> getImageUrlList() {
        ArrayList<String> results = new ArrayList<>();
        if (mImageList != null) {
            for (int i = 0; i < mImageList.size(); i++) {
                results.add(getImageUrl(i));
            }
        }
        return results;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Object getDescription() {
        return mDescription;
    }

    public void setDescription(Object description) {
        mDescription = description;
    }

    public Integer getStartYear() {
        return mStartYear;
    }

    public void setStartYear(Integer startYear) {
        mStartYear = startYear;
    }

    public Integer getEndYear() {
        return mEndYear;
    }

    public void setEndYear(Integer endYear) {
        mEndYear = endYear;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public String getType() {
        return mType;
    }

    public void setType(@Type String type) {
        mType = type;
    }

    public String getModified() {
        return mModified;
    }

    public void setModified(String modified) {
        mModified = modified;
    }

    public Image getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        mThumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mResourceUri);
        dest.writeParcelable(this.mThumbnail, flags);
        dest.writeTypedList(this.mImageList);
    }

    protected Comic(Parcel in) {
        this.mId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mTitle = in.readString();
        this.mResourceUri = in.readString();
        this.mThumbnail = in.readParcelable(Image.class.getClassLoader());
        this.mImageList = in.createTypedArrayList(Image.CREATOR);
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel source) {
            return new Comic(source);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };
}
