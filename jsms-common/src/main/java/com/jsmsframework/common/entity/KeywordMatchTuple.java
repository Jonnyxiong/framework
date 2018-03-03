package com.jsmsframework.common.entity;

/**
 * Created by xiongfenglin on 2017/11/27.
 *
 * @author: xiongfenglin

 * 关键字匹配信息元组
 * @param <A> keyword 匹配到的关键字
 * @param <B> 关键字在被匹配文本中的开始位置
 * @param <C> 关键字在被匹配文本中的结束位置
 */
public class KeywordMatchTuple<A, B, C> {

    public final A keyword;
    public final B start;
    public final C end;

    public KeywordMatchTuple(A a, B b, C c) {
        keyword = a;
        start = b;
        end = c;
    }

    public A getKeyword() {
        return keyword;
    }

    public B getStart() {
        return start;
    }

    public C getEnd() {
        return end;
    }
}