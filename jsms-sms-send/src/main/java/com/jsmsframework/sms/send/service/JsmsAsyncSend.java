package com.jsmsframework.sms.send.service;

import java.util.List;

public interface JsmsAsyncSend<T> {

    /**
     * 应用启动时, 初始化发送线程
     * @param url4Json json子协议发送短信的接口
     * @param url4Form get/post子协议发送短信的接口
     * @param errDataPath 重试后,仍无法发送的数据保存地址
     * @param sendThreadName 发送的线程名称
     */
    public void initSendThread(String url4Json,String url4Form,String errDataPath,String sendThreadName);

    /*public void putIntoSendQueue(T jsmsAccessObj);*/

    /*public void putIntoSendQueue(T jsmsAccessObj,int tryTimes);*/

    public void putListIntoSendQueue(List<T> jsmsAccessObjList);

    /**
     *
     * @param jsmsAccessObjList
     * @param tryPutTimes 尝试put到队列中的次数, 如果为1 , 则失败不会重试
     */
    public void putListIntoSendQueue(List<T> jsmsAccessObjList, int tryPutTimes);

}
