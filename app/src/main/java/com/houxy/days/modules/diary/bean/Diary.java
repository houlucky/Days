package com.houxy.days.modules.diary.bean;

import android.util.Log;

import com.houxy.days.modules.main.bean.User;

import cn.bmob.v3.BmobObject;

/**
 * Created by Houxy on 2016/9/12.
 */
public class Diary extends BmobObject{

    String content;
    String postTime;
    User author;


    public Diary(){}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    @Override
    public String toString() {

        return "content : " + getContent() + " postTime : " + getPostTime() + " author : " + author.toString();
    }
}
