package com.example.ztgreenleaves.search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ztgreenleaves.search.presenter.SearchPresenter;
import com.example.ztgreenleaves.search.view.ISearchView;

import java.util.List;

/**
 * Created by ztgreenleaves on 2017/3/14.
 */

public class SearchActivity extends Activity implements ISearchView {
    private SearchView searchView;
    private ListView tipsList;
    private String keyword;
    private List<String> tipList;
    private SearchPresenter searchPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        Log.d("data", "presenter初始化前");
        searchPresenter = new SearchPresenter(this);
        searchView = (SearchView) findViewById(R.id.search_bar);
        tipsList = (ListView) findViewById(R.id.search_tips);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在加载...");
        progressDialog.setCancelable(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("data", "成功提交搜索");
                setKeyword(query);
                searchPresenter.search();
                Log.d("data", "presenter.search()后");
                searchView.setIconified(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    tipsList.setVisibility(View.VISIBLE);
                    setKeyword(newText);
                    searchPresenter.searchTips();
                } else {
                    tipsList.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        tipsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("data", "点击位置：" + position);
                String tip = tipList.get(position);
                setSearchViewBack(tip);
            }
        });
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String getKeyword() {
        return keyword;
    }

    @Override
    public Context getContext() {
        return SearchActivity.this;
    }

    @Override
    public void showLoadingDialog() {
        progressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showErrorInfo(String errorInfo) {
        Toast.makeText(SearchActivity.this, errorInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setSearchViewBack(String keyword) {
        searchView.setQuery(keyword, false);
    }

    @Override
    public void setAdapter(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,
                android.R.layout.simple_list_item_1, list);
        this.tipList = list;
        tipsList.setAdapter(adapter);
    }

    @Override
    public void setTipListInVisibility() {
        tipsList.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setTipListVisibility() {
        tipsList.setVisibility(View.VISIBLE);
    }


}
