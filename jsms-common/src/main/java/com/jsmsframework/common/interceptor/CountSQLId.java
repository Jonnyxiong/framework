package com.jsmsframework.common.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分页拦截去对应的count语句id
 * @author : Niu.T
 * @date: 2017年03月15 14:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CountSQLId {
    /**
     *
     * @return
     */
    public String value();

}
