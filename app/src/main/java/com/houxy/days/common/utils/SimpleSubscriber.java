package com.houxy.days.common.utils;

import rx.Subscriber;

/**
 * Created by Houxy on 2016/11/30.
 * <p>
 * Info :  SimpleSubscriber.java
 */
public abstract class SimpleSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }
}
