package com.jsmsframework.monitor.service;

import com.jsmsframework.monitor.pojo.JsmsAppServerInfo;
import com.jsmsframework.monitor.pojo.JsmsDataSource;

import java.util.List;

public interface JsmsAppRunningSubject {

    /**
     * 判断应用是否运行, 以及访问数据库是否正常
     *
     * @param token
     * @return
     */
    JsmsAppServerInfo isRunning(String token);

    /**
     * 判断应用是否运行, 以及访问数据库是否正常
     *
     * @param token
     * @param dataBaseList
     * @return
     */
    JsmsAppServerInfo isRunning(String token, List<JsmsDataSource> dataBaseList);
}
