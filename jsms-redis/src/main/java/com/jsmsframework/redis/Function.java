package com.jsmsframework.redis;

/**
  * @Description: 定义函数接口，用于执行具体的方法逻辑
  * @Author: tanjiangqiang
  * @Date: 2017/11/13 - 11:02
  * @param <E>
  *            代表输入
  * @param <T>
  *            代表输出
  *
  */
public interface Function<E, T> {
    public T execute(E e);
}
