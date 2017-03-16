package com.example.ztgreenleaves.search.model;

/**
 * Created by ztgreenleaves on 2017/3/16.
 */

public interface ISearchResultBiz {
    void getNextPage(String nextPageUrl, OnNextPageListener onNextPageListener);
}
