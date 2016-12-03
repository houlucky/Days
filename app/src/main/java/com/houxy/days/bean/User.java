package com.houxy.days.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Houxy on 2016/9/5.
 */
public class User extends BmobUser {

    private String avatar = "";
    private Boolean sex;
    private String nick;
    private BmobRelation contacts;
    private String motto;
    public User(){}

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void  setSex(Boolean sex){
        this.sex = sex;
    }

    public Boolean getSex(){
        return sex;
    }

    public void setNickName(String nick){
        this.nick = nick;
    }

    public String getNickName(){
        return nick;
    }

    public void setRelation(BmobRelation relation) {
        this.contacts = relation;
    }

    public BmobRelation getRelation() {
        return contacts;
    }


    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getMotto() {
        return motto;
    }


    @Override
    public String toString() {
        return "name : " + getUsername() + " ObjectId : " + getObjectId() + " nick : " + getNickName()
                + " sex : " +getSex() + " motto : " + getMotto();

    }
}
