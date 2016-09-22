package com.houxy.days.modules.special.bean;

import java.util.List;

/**
 * Created by Houxy on 2016/9/22.
 */

public class ResultList<T> {

    public List<T> list;
    public Integer rows;


    public void setList(List<T> list) {
        this.list = list;
    }


    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
