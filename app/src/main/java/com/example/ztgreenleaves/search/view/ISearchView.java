package com.example.ztgreenleaves.search.view;

import android.content.Context;

import java.util.List;

/**
 * Created by ztgreenleaves on 2017/3/14.
 */

public interface ISearchView {
    String getKeyword();
    Context getContext();
    void showLoadingDialog();
    void hideLoadingDialog();
    void showErrorInfo(String errorInfo);
    void setSearchViewBack(String keyword);
    void setAdapter(List<String> list);
    void setTipListInVisibility();
    void setTipListVisibility();
}
