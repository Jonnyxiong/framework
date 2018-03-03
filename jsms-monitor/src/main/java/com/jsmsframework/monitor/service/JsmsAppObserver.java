package com.jsmsframework.monitor.service;

import com.jsmsframework.monitor.pojo.JsmsAppServerInfo;

import java.util.List;

public interface JsmsAppObserver {

    /**
     * 解析 应用运行响应结果
     * @param responeStr 响应字符串
     * @return
     */
    JsmsAppServerInfo analyzeRunningResult(String responeStr);

    /**
     * 观察应用执行情况
     * @return
     */
    List<JsmsAppServerInfo> observerAppServer();
    /**
     * 观察应用执行情况
     * @param appServerInfoList
     * @return
     */
    List<JsmsAppServerInfo> observerAppServer(List<JsmsAppServerInfo> appServerInfoList);

}
