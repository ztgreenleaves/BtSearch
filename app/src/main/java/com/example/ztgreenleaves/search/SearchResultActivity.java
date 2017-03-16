package com.example.ztgreenleaves.search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ztgreenleaves.search.adapter.SearchResultAdapter;
import com.example.ztgreenleaves.search.bean.SearchResultItemBean;
import com.example.ztgreenleaves.search.presenter.SearchResultPresenter;
import com.example.ztgreenleaves.search.view.ISearchResultView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ztgreenleaves on 2017/3/14.
 */

public class SearchResultActivity extends Activity implements ISearchResultView {

    private List<SearchResultItemBean> list;
    private SearchResultPresenter searchResultPresenter;
    private ListView listView;
    private ProgressDialog progressDialog;
    private boolean isListRear = false;
    private String keyword;
    private int pageNum = 1;
    private int restartPosition = 0;
    private String pageUrl = null;

    public static void actionStart(Context context, List<SearchResultItemBean> list, String pageUrl,
                                   String keyword) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra("pageUrl", pageUrl);//url格式如:http://btkitty\.bid/search/.*?/ + page/0/0.html
        intent.putExtra("search_result_list", (Serializable) list);
        intent.putExtra("keyword", keyword);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_layout);
        searchResultPresenter = new SearchResultPresenter(SearchResultActivity.this);
        list = (List<SearchResultItemBean>) getIntent().getSerializableExtra("search_result_list");
        pageUrl = getIntent().getStringExtra("pageUrl");
        keyword = getIntent().getStringExtra("keyword");
        listView = (ListView) findViewById(R.id.list_view);
        progressDialog = new ProgressDialog(SearchResultActivity.this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在加载下一页...");
        SearchResultAdapter adapter = new SearchResultAdapter(SearchResultActivity.this, R.layout.search_result_item_layout, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResultItemBean itemBean = list.get(position);

                ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, itemBean.getFileMagnet()));
                Toast.makeText(SearchResultActivity.this, "磁力链复制成功", Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE && isListRear) {
                    pageNum++;
                    searchResultPresenter.loadNextPage(list, pageUrl, pageNum, keyword);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.d("count", "visibleItemCount:" + visibleItemCount + " totalItemCount:" + totalItemCount);
                if (totalItemCount < 10) {//防止条数正好全部在屏幕内，而无限刷新

                    isListRear = false;
                    return;
                }
                if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    isListRear = true;
                    restartPosition = firstVisibleItem;
                } else {
                    isListRear = false;
                }
            }
        });
    }


    @Override
    public void showNextPage(List<SearchResultItemBean> list) {
        SearchResultAdapter adapter = new SearchResultAdapter(SearchResultActivity.this,
                R.layout.search_result_item_layout, list);
        listView.setAdapter(adapter);
        listView.setSelection(restartPosition + 1);
//        Log.d("page", "加载下一页完毕");
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
    public void showNoMorePage() {
        Toast.makeText(SearchResultActivity.this, "没有更多了！", Toast.LENGTH_SHORT).show();
    }
}
