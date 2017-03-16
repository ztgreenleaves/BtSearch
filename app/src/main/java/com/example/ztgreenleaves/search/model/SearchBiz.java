package com.example.ztgreenleaves.search.model;

import android.text.TextUtils;
import android.util.Log;

import com.example.ztgreenleaves.search.util.HttpUtil;

/**
 * Created by ztgreenleaves on 2017/3/14.
 */

public class SearchBiz implements ISearchBiz {

    @Override
    public void doSearch(final String keyword, final OnSearchListener onSearchListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                onSearchListener.onBegin();
                Log.d("data", "doSearch()里，准备开始抓取网页");
                try {
                    String responseStr = null;
                    responseStr = HttpUtil.sendPostToKittyBt(keyword);

                    if (!TextUtils.isEmpty(responseStr)) {
                        Log.d("data", "网页长度:" + responseStr.length());
                        Log.d("data", responseStr);
                        onSearchListener.onSuccess(responseStr);
                    } else {
                        Log.d("data", "网页空内容");
                        onSearchListener.onError("搜索无结果!换个关键词试试");
                    }
                    onSearchListener.onFinish();
                } catch (Exception e) {
                    Log.d("data", "抓取失败");
                    e.printStackTrace();
                    onSearchListener.onError("搜索失败!");
                }
            }
        }).start();
    }

    @Override
    public void doSearchTip(final String keyword, final OnSearchListener onSearchListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsText = null;
                    jsText = HttpUtil.getSearchTips(keyword);
                    onSearchListener.onSuccess(jsText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
