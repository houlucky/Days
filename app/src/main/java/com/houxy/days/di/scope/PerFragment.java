package com.houxy.days.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Houxy on 2016/11/27.
 * <p>
 * Info :  PerFragment.java
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}
