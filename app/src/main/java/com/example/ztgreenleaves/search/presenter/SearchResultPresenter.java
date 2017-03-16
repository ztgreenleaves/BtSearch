package com.example.ztgreenleaves.search.presenter;

import android.os.Handler;
import android.util.Log;

import com.example.ztgreenleaves.search.bean.SearchResultItemBean;
import com.example.ztgreenleaves.search.model.ISearchResultBiz;
import com.example.ztgreenleaves.search.model.OnNextPageListener;
import com.example.ztgreenleaves.search.model.SearchResultBiz;
import com.example.ztgreenleaves.search.util.RegexUtil;
import com.example.ztgreenleaves.search.view.ISearchResultView;

import java.util.List;

/**
 * Created by ztgreenleaves on 2017/3/16.
 */

public class SearchResultPresenter {
    private ISearchResultView mSearchResultView;
    private ISearchResultBiz mSearchResultBiz;
    private Handler handler = new Handler();

    public SearchResultPresenter(ISearchResultView mSearchResultView) {
        this.mSearchResultView = mSearchResultView;
        mSearchResultBiz = new SearchResultBiz();
    }

    public void loadNextPage(final List<SearchResultItemBean> list, String url, int pageNum,
                             final String keyword) {
        Log.d("page", "需要加载第 " + pageNum + " 页");
        String nextUrl = url + pageNum + "/0/0.html";
        Log.d("page", "网址为:" + nextUrl);
        mSearchResultBiz.getNextPage(nextUrl, new OnNextPageListener() {
            @Override
            public void onBegin() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSearchResultView.showLoadingDialog();
                    }
                });
            }

            @Override
            public void onSuccess(String htmlContent) {
                htmlContent = htmlContent.replaceAll("\n", "");
                List<SearchResultItemBean> tempList = RegexUtil.matchBtInfo(htmlContent, keyword.length());
                Log.d("page", "页面加载列表:" + tempList.toString());
                list.addAll(tempList);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSearchResultView.showNextPage(list);
                    }
                });

            }

            @Override
            public void onError(String errorInfo) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSearchResultView.showNoMorePage();
                    }
                });
            }

            @Override
            public void onFinish() {
                mSearchResultView.hideLoadingDialog();
            }
        });

    }
}
