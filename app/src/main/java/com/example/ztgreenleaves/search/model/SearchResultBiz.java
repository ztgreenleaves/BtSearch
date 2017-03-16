package com.example.ztgreenleaves.search.model;

import android.text.TextUtils;

import com.example.ztgreenleaves.search.util.HttpUtil;

/**
 * Created by ztgreenleaves on 2017/3/16.
 */

public class SearchResultBiz implements ISearchResultBiz {
    @Override
    public void getNextPage(final String nextPageUrl, final OnNextPageListener onNextPageListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                onNextPageListener.onBegin();
                String htmlContent = null;
                try {
                    htmlContent = HttpUtil.sendGet(nextPageUrl);
                    if (!TextUtils.isEmpty(htmlContent)) {
                        onNextPageListener.onFinish();
                        onNextPageListener.onSuccess(htmlContent);
                    } else {
                        onNextPageListener.onError("没有下一页了");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
