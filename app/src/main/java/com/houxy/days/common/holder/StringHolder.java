package com.houxy.days.common.holder;

/**
 * Created by Houxy on 2016/9/5.
 */
public class StringHolder {

    private String string;
    private int startIndex;
    private int endIndex;


    public StringHolder(String string, int startIndex, int endIndex){
        this.string = string;
        this.startIndex = startIndex;
        this.endIndex= endIndex;
    }


    public int getEndIndex() {
        return endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public String getString() {
        return string;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "src : " + getString() + " start : " + getStartIndex() + " end : " + getEndIndex();
    }
}
