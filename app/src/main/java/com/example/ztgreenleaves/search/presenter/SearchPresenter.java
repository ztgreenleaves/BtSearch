package com.example.ztgreenleaves.search.presenter;

import android.os.Handler;
import android.util.Log;

import com.example.ztgreenleaves.search.SearchResultActivity;
import com.example.ztgreenleaves.search.bean.SearchResultItemBean;
import com.example.ztgreenleaves.search.model.ISearchBiz;
import com.example.ztgreenleaves.search.model.OnSearchListener;
import com.example.ztgreenleaves.search.model.SearchBiz;
import com.example.ztgreenleaves.search.util.RegexUtil;
import com.example.ztgreenleaves.search.view.ISearchView;

import java.util.List;

/**
 * Created by ztgreenleaves on 2017/3/14.
 */

public class SearchPresenter {
    private ISearchView mSearchView;
    private ISearchBiz mSearchBiz;
    private Handler handler = new Handler();

    public SearchPresenter(ISearchView mSearchView) {
        Log.d("data", "Presenter构造方法成功");
        this.mSearchView = mSearchView;
        mSearchBiz = new SearchBiz();
    }

    public void search() {
        Log.d("data", "进入search()方法");
        final String keyword = mSearchView.getKeyword();
        mSearchBiz.doSearch(keyword, new OnSearchListener() {
            @Override
            public void onBegin() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSearchView.showLoadingDialog();
                    }
                });

            }

            @Override
            public void onFinish() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSearchView.hideLoadingDialog();
                    }
                });
            }

            @Override
            public void onSuccess(final String htmlContent) {
                Log.d("data", "handler运行里");
                String resStr = htmlContent.replaceAll("\n", "");
                int lengthOfKey = keyword.length();
                final List<SearchResultItemBean> list = RegexUtil.matchBtInfo(resStr, lengthOfKey);
                Log.d("data", list.toString());
                String p = RegexUtil.getToatalPage(resStr);
//                int page = Integer.parseInt(RegexUtil.getToatalPage(resStr));
                Log.d("data", "总页数：" + p);
                final String pageUrl = RegexUtil.getUrl(resStr);
                Log.d("data", "翻页网址：" + pageUrl);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSearchView.setSearchViewBack(keyword);
                        SearchResultActivity.actionStart(mSearchView.getContext(), list, pageUrl, keyword);
                    }
                });
            }

            @Override
            public void onError(final String errorInfo) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSearchView.hideLoadingDialog();
                        mSearchView.showErrorInfo(errorInfo);
                    }
                });
            }
        });
    }

    public void searchTips() {

        final String keyword = mSearchView.getKeyword();
        mSearchBiz.doSearchTip(keyword, new OnSearchListener() {
            @Override
            public void onBegin() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(String htmlContent) {
                List<String> list = null;
                list = RegexUtil.matchSearchTips(htmlContent);
                if (list == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mSearchView.setTipListInVisibility();
                        }
                    });
                    return;
                }
                final List<String> finalList = list;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSearchView.setAdapter(finalList);
                    }
                });
            }

            @Override
            public void onError(String errorInfo) {

            }
        });
    }


}
