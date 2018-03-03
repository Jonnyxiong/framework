package com.jsmsframework.monitor.pojo;

import java.util.List;

/**
 * App 应用服务
 */
public class JsmsAppServerInfo {
    /**
     * 接收服务应用监控的接口
     */
    private String monitorUrl;
    /**
     * 应用是否需要检查
     */
    private boolean isNeedCheck = true;
    /**
     * 应用是否异常, 不需要检测或同时满足以下三个条件为: 无异常 false
     * (1)需要检测
     * (2)并且app 运行正常
     * (3)且需要检测的数据库连接正常
     */
    private boolean isAppException;
    /**
     * 应用描述, 如: 用户中心, 运营平台, 调度系统 ...
     */
    private String appServerDesc;
    /**
     * 是否运行, 请求失败为不运行
     */
    private boolean isAppRunning;
    /**
     * 用户运行信息
     */
    private String appRunningInfo;

    private List<JsmsDataSource.DBConnection> dbConnectionList;

    public JsmsAppServerInfo() {
    }

    public JsmsAppServerInfo(boolean isAppRunning,boolean isAppException, String appRunningInfo) {
        this.isAppRunning = isAppRunning;
        this.isAppException = isAppException;
        this.appRunningInfo = appRunningInfo;
    }

    public JsmsAppServerInfo(boolean isAppRunning, String appRunningInfo, List<JsmsDataSource.DBConnection> dbConnectionList) {
        this.isAppRunning = isAppRunning;
        this.appRunningInfo = appRunningInfo;
        this.dbConnectionList = dbConnectionList;
    }

    public JsmsAppServerInfo(String monitorUrl, String appServerDesc,boolean isNeedCheck) {
        this.monitorUrl = monitorUrl;
        this.appServerDesc = appServerDesc;
        this.isNeedCheck = isNeedCheck;
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }

    public boolean isNeedCheck() {
        return isNeedCheck;
    }

    public void setNeedCheck(boolean needCheck) {
        isNeedCheck = needCheck;
    }

    public String getAppServerDesc() {
        return appServerDesc;
    }

    public void setAppServerDesc(String appServerDesc) {
        this.appServerDesc = appServerDesc;
    }

    public boolean isAppRunning() {
        return isAppRunning;
    }

    public void setAppRunning(boolean appRunning) {
        isAppRunning = appRunning;
    }

    public boolean isAppException() {
        return isAppException;
    }

    public void setAppException(boolean appException) {
        isAppException = appException;
    }

    public String getAppRunningInfo() {
        return appRunningInfo;
    }

    public void setAppRunningInfo(String appRunningInfo) {
        this.appRunningInfo = appRunningInfo;
    }

    public List<JsmsDataSource.DBConnection> getDbConnectionList() {
        return dbConnectionList;
    }

    public void setDbConnectionList(List<JsmsDataSource.DBConnection> dbConnectionList) {
        this.dbConnectionList = dbConnectionList;
    }

}
