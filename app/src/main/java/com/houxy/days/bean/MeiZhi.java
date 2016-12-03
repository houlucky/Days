package com.houxy.days.bean;

import java.io.Serializable;

/**
 * Created by Houxy on 2016/9/23.
 */

public class MeiZhi implements Serializable{


    /**
     * _id : 57e477fa421aa95bc338987d
     * createdAt : 2016-09-23T08:31:54.365Z
     * desc : 9-23
     * publishedAt : 2016-09-23T11:38:57.170Z
     * source : chrome
     * type : 福利
     * url : http://ww3.sinaimg.cn/large/610dc034jw1f837uocox8j20f00mggoo.jpg
     * used : true
     * who : daimajia
     */

    private String desc;
    private String type;
    private String url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
