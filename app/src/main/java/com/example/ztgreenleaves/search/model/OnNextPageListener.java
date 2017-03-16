package com.example.ztgreenleaves.search.model;

/**
 * Created by ztgreenleaves on 2017/3/16.
 */

public interface OnNextPageListener {
    void onBegin();
    void onSuccess(String htmlContent);
    void onError(String errorInfo);
    void onFinish();
}
