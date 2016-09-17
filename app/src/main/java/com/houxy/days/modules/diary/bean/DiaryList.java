package com.houxy.days.modules.diary.bean;

import java.util.List;


/**
 * Created by Houxy on 2016/9/12.
 */
public class DiaryList<T> {

    public List<T> list;
    public Integer rows;


    public void setDiaryList(List<T> list) {
        this.list = list;
    }


    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
