package com.joaquimley.core.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "offset",
        "limit",
        "total",
        "count",
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDataContainer {

    @JsonProperty("offset")
    protected Integer mOffset;
    @JsonProperty("limit")
    protected Integer mLimit;
    @JsonProperty("total")
    protected Integer mTotal;
    @JsonProperty("count")
    protected Integer mCount;

    public BaseDataContainer() {
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
