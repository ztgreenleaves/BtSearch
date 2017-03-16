package com.example.ztgreenleaves.search.model;

/**
 * Created by ztgreenleaves on 2017/3/14.
 */

public interface OnSearchListener {
    void onBegin();
    void onFinish();
    void onSuccess(String htmlContent);
    void onError(String errorInfo);
}
