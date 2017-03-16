package com.example.ztgreenleaves.search.bean;

import java.io.Serializable;

/**
 * Created by ztgreenleaves on 2017/3/14.
 */

public class SearchResultItemBean implements Serializable {
    private String fileNum;
    private String fileNamePrefix;
    private String fileNameKey;
    private String fileNamePostfix;
    private String fileSize;
    private String fileUpTime;
    private String fileVelocity;
    private String fileHot;
    private String fileMagnet;

    @Override
    public String toString() {
        return "SearchItem [fileNum=" + fileNum + ", fileName_prefix=" + fileNamePrefix + ", fileName_key="
                + fileNameKey + ", fileName_postfix=" + fileNamePostfix + ", fileSize=" + fileSize + ", fileUpTime="
                + fileUpTime + ", fileVelocity=" + fileVelocity + ", fileHot=" + fileHot + ", fileMagnet=" + fileMagnet
                + "]";
    }

    public String getFileNum() {
        return fileNum;
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum;
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    public String getFileNameKey() {
        return fileNameKey;
    }

    public void setFileNameKey(String fileNameKey) {
        this.fileNameKey = fileNameKey;
    }

    public String getFileNamePostfix() {
        return fileNamePostfix;
    }

    public void setFileNamePostfix(String fileNamePostfix) {
        this.fileNamePostfix = fileNamePostfix;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileUpTime() {
        return fileUpTime;
    }

    public void setFileUpTime(String fileUpTime) {
        this.fileUpTime = fileUpTime;
    }

    public String getFileVelocity() {
        return fileVelocity;
    }

    public void setFileVelocity(String fileVelocity) {
        this.fileVelocity = fileVelocity;
    }

    public String getFileHot() {
        return fileHot;
    }

    public void setFileHot(String fileHot) {
        this.fileHot = fileHot;
    }

    public String getFileMagnet() {
        return fileMagnet;
    }

    public void setFileMagnet(String fileMagnet) {
        this.fileMagnet = fileMagnet;
    }



}
