package com.example.ztgreenleaves.search.view;

import com.example.ztgreenleaves.search.bean.SearchResultItemBean;

import java.util.List;

/**
 * Created by ztgreenleaves on 2017/3/14.
 */

public interface ISearchResultView {
//    void actionStart(Context context);
    void showNextPage(List<SearchResultItemBean> list);//提前加载下一页内容
    void showLoadingDialog();
    void hideLoadingDialog();
    void showNoMorePage();
}
