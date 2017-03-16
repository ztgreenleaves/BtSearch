package com.example.ztgreenleaves.search.util;


import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ztgreenleaves on 2017/3/14.
 */

public class HttpUtil {
    public static String sendGet(String url) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String htmlContent = null;
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            htmlContent = EntityUtils.toString(httpResponse.getEntity());
            Log.d("page", "翻页网址长度为:" + htmlContent.length());
            Log.d("page", "翻页网址内容为:" + htmlContent);
        }
        return htmlContent;
    }

    public static String sendPostToKittyBt(String keyword) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://btkitty.bid/");
        HttpResponse httpResponse = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("keyword", keyword));
        params.add(new BasicNameValuePair("hidden", "true"));

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");
        httpPost.setEntity(formEntity);
        httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            Header[] headers = httpResponse.getAllHeaders();
            for (Header header : headers) {
                Log.d("data", header.getName() + "=" + header.getValue());
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            Log.d("data", "获取到网页内容");
            return EntityUtils.toString(httpEntity, "UTF-8");
        }
        return null;
    }

    public static String getSearchTips(String keyword) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        String url = "https://sp0.baidu.com/5a1Fazu8AA54nxGko9WTAnF6hhy/su?wd=";
        HttpGet httpGet = new HttpGet(url + keyword);
        httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        String jsText = null;
        jsText = EntityUtils.toString(httpEntity);
        return jsText;
    }
}
