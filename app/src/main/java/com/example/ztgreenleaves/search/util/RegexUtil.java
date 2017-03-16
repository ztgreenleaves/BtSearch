package com.example.ztgreenleaves.search.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.ztgreenleaves.search.bean.SearchResultItemBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ztgreenleaves on 2017/3/14.
 */

public class RegexUtil {
    public static List<SearchResultItemBean> matchBtInfo(String response, int lengthOfKey) {
        List<SearchResultItemBean> list = new ArrayList<SearchResultItemBean>();
//        response = response.replaceAll("\n", "");
        String titleRegex = "<strong>(.*?)</strong><a.*?>(.*?)((<b>(.*?)</b>){" + lengthOfKey
                + "})(.*?)</a>.*?((<span>(.*?)</span>.*?){6})";
        Pattern pattern = Pattern.compile(titleRegex);
        Matcher matcher = pattern.matcher(response);
        while (matcher.find()) {
            String num = matcher.group(1);
            String prefix = matcher.group(2).replaceAll("<b>|</b>", "");
            String key = matcher.group(3).replaceAll("<b>|</b>", "");
            String postfix = matcher.group(6).replaceAll("<b>|</b>", "");
            String fileInfo = matcher.group(7);
            SearchResultItemBean item = new SearchResultItemBean();
            item.setFileNum(num);

            key = prefix + key + postfix;
            item.setFileNameKey(key);
            //不使用前后缀字段，只使用关键词字段
//            item.setFileNamePrefix(prefix);
//            item.setFileNamePostfix(postfix);

            // System.out.println("---序号:" + num);
            // System.out.println("---整体名字: " + prefix + key + postfix);
            // System.out.println("---关键词前缀:" + prefix);
            // System.out.println("---搜索关键词:" + matcher.group(3));
            // // System.out.println("---关键词2:" + matcher.group(4));
            // // System.out.println("---关键词3:" + matcher.group(5));
            // System.out.println("---关键词后缀:" + postfix);
            // System.out.println("---文件信息:" + fileInfo);
            getInfo(item, fileInfo);

            System.out.println(item.toString());
            list.add(item);
            System.out.println("----------------");
            // String keyInput = getName(key);
            // System.out.println("---提取后的-搜索关键词:" + keyInput);

        }
        return list;
    }

    /**
     * 该方法不好用
     */
    public static String getToatalPage(String response) {
//        response = response.replaceAll("\n", "");
        String regex = "共(.*?)页";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            String page = matcher.group(1);
            Log.d("data", "方法内部测得总页数：" + page);
            return page;
        }
        return null;
    }

    public static String getUrl(String response) {
//        response = response.replaceAll("\n", "");
        String regex = "(http://btkitty\\.bid/search/.*?/)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @Nullable
    public static List<String> matchSearchTips(String jsText) {
        Pattern pattern = Pattern.compile("\\[(.*?)]");
        Matcher matcher = pattern.matcher(jsText);
        List<String> list = new ArrayList<String>();
        String shortText = null;
        if (matcher.find()) {
            shortText = matcher.group(1);
            Log.d("data", "【】中内容为:" + shortText);
        }
        if (TextUtils.isEmpty(shortText)) {
            return null;
        }
        pattern = Pattern.compile("\"(.*?)\"");
        matcher = pattern.matcher(shortText);

        while (matcher.find()) {
            list.add(matcher.group(1));
        }
        if (list.size() != 0)
            return list;
        else
            return null;
    }

    public static void getInfo(SearchResultItemBean item, String fileInfo) {
        Pattern pattern = Pattern.compile("<b>(.*?)</b>");
        Matcher matcher = pattern.matcher(fileInfo);
        List<String> strs = new ArrayList<String>();
        int i = 0;
        while (matcher.find()) {
            strs.add(matcher.group(1));
        }
        // System.out.println(strs.toString());
        item.setFileUpTime(strs.get(0));
        item.setFileSize(strs.get(1));
        // item.setFileNum(strs.get(2));
        item.setFileVelocity(strs.get(3));
        item.setFileHot(strs.get(4));
        pattern = Pattern.compile("<a href='(.*?)'");
        matcher = pattern.matcher(fileInfo);
        if (matcher.find()) {
            item.setFileMagnet(matcher.group(1));
        }
    }
}
