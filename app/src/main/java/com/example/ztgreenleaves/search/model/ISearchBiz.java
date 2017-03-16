package com.example.ztgreenleaves.search.model;

/**
 * Created by ztgreenleaves on 2017/3/14.
 */

public interface ISearchBiz {
    void doSearch(String keyword, OnSearchListener onSearchListener);
    void doSearchTip(String keyword, OnSearchListener onSearchListener);
}
