package com.houxy.days.bean;

/**
 * Created by Houxy on 2016/9/23.
 */

public class Result<T> {


    /**
     * error : false
     * results : [{"_id":"57e477fa421aa95bc338987d","createdAt":"2016-09-23T08:31:54.365Z","desc":"9-23","publishedAt":"2016-09-23T11:38:57.170Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f837uocox8j20f00mggoo.jpg","used":true,"who":"daimajia"}]
     */

    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public T getResults() {
        return results;
    }
}
