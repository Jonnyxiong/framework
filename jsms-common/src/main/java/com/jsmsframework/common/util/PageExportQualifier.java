package com.jsmsframework.common.util;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分页拦截去对应的count语句, 是否是简单的sql
 * @author : Niu.T
 * @date: 2017年03月15 14:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageExportQualifier {
    /**
     * 标识, 修改字符, 每个类型中不能重复
     */
    public String value();
}
