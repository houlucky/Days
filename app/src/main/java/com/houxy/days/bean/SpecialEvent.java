package com.houxy.days.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Houxy on 2016/9/19.
 */
public class SpecialEvent extends BmobObject{

    private String eventName;
    private String date;
    private String eventCategory;
    private String repeatType;
    private User eventOwner;

    public SpecialEvent(){
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public User getEventOwner() {
        return eventOwner;
    }

    public void setEventOwner(User eventOwner) {
        this.eventOwner = eventOwner;
    }
}
