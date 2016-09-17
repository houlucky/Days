package com.houxy.days.base;

/**
 * Created by Houxy on 2016/9/5.
 */
public class ImageHolder {

    private String url;
    private int startIndex;
    private int endIndex;


    public ImageHolder(String url, int startIndex, int endIndex){
        this.url = url;
        this.startIndex = startIndex;
        this.endIndex= endIndex;
    }


    public int getEndIndex() {
        return endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public String getUrl() {
        return url;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "src : " + getUrl() + " start : " + getStartIndex() + " end : " + getEndIndex();
    }
}
