package com.joaquimley.core.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComicDataContainer extends BaseDataContainer {

    @JsonProperty("code")
    protected int mCode;
    @JsonProperty("code")
    protected String mStatus;
    @JsonProperty("results")
    private List<Comic> mResults = new ArrayList<>();

    public ComicDataContainer() {
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public List<Comic> getResults() {
        return mResults;
    }

    public void setResults(List<Comic> results) {
        mResults = results;
    }

    public Integer getOffset() {
        return mOffset;
    }

    public void setOffset(Integer offset) {
        mOffset = offset;
    }

    public Integer getLimit() {
        return mLimit;
    }

    public void setLimit(Integer limit) {
        mLimit = limit;
    }

    public Integer getTotal() {
        return mTotal;
    }

    public void setTotal(Integer total) {
        mTotal = total;
    }

    public Integer getCount() {
        return mCount;
    }

    public void setCount(Integer count) {
        mCount = count;
    }
}
