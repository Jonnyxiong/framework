package com.jsmsframework.monitor.service;

import com.alibaba.fastjson.JSON;
import com.jsmsframework.common.util.HttpUtil;
import com.jsmsframework.monitor.pojo.JsmsAppServerInfo;
import com.jsmsframework.monitor.util.JsmsMonitorToken;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class JsmsAppObserverImpl implements JsmsAppObserver {
    private static final int TEST_CONNECTION_RETURN_NUM = 1;
    private static final String TEST_CONNECTION_SQL = "SELECT " + TEST_CONNECTION_RETURN_NUM;
    private static final String DB_TYPE_REGEX = "(?<=\\:)[^:]+";
    private static final String JDBC_URL_REGEX = "(?<=\\://)[^/]+";
    private static final String DB_NAME_REGEX = "(?<=/)[^/]+(?=\\?)";
    /**
     * 被监控应用的接口信息, 在发起监控的应用配置, 如: smsa-task ,配置: 应用描述 + 监控接口(没有被过滤器拦截,通过token校验)
     */
    private List<JsmsAppServerInfo> appServerInfoList;

    public void setAppServerInfoList(List<JsmsAppServerInfo> appServerInfoList) {
        this.appServerInfoList = appServerInfoList;
    }

    private static final Logger logger = LoggerFactory.getLogger(JsmsAppObserverImpl.class);

    /**
     * token生成工具
     */
    private String generateToken() {
        return JsmsMonitorToken.generateToken(DateTime.now());
    }

    @Override
    public JsmsAppServerInfo analyzeRunningResult(String responeStr) {
        logger.debug("服务器应用及数据库连接监控响应结果解析 ------> start , responeStr = {}", responeStr);

        if (StringUtils.isBlank(responeStr)) {
            return new JsmsAppServerInfo(false,true, "应用未响应");
        }
        JsmsAppServerInfo responseBody = null;
        try {
            responseBody = JSON.parseObject(responeStr, JsmsAppServerInfo.class);
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder("解析结果失败!返回结果:");
            stringBuilder.append(responeStr);
            logger.debug("解析应用运行结果失败, 结果 --> {} 错误 --> {}", responeStr, e);
            responseBody = new JsmsAppServerInfo(false,true, stringBuilder.toString());
        }
        logger.debug("服务器应用及数据库连接监控响应结果解析 ------> end");
        return responseBody;
    }

    @Override
    public List<JsmsAppServerInfo> observerAppServer() {
        if(this.appServerInfoList == null){
            throw new IllegalArgumentException("appServerInfoList 没有初始化或赋值");
        }
        return observerAppServer(this.appServerInfoList);
    }
    @Override
    public List<JsmsAppServerInfo> observerAppServer(List<JsmsAppServerInfo> appServerInfoList) {

        logger.debug("服务器应用及数据库连接监控 ------> start, 监控的应用信息 --> {}", JSON.toJSONString(appServerInfoList));
        if (appServerInfoList == null || appServerInfoList.isEmpty()) {
            return null;
        }
        // 创建任务集合
        List<FutureTask<JsmsAppServerInfo>> taskList = new ArrayList<FutureTask<JsmsAppServerInfo>>();
        // 创建线程池
        ExecutorService exec = Executors.newFixedThreadPool(appServerInfoList.size());
        for (final JsmsAppServerInfo jsmsAppServerInfo : appServerInfoList) {
            // 传入Callable对象创建FutureTask对象
            FutureTask<JsmsAppServerInfo> ft = new FutureTask<JsmsAppServerInfo>(new Callable<JsmsAppServerInfo>() {
                @Override
                public JsmsAppServerInfo call() {
                    JsmsAppServerInfo runInfo;
                    if (jsmsAppServerInfo.isNeedCheck()){
                        runInfo = requestServer(jsmsAppServerInfo.getMonitorUrl());
                        runInfo.setMonitorUrl(jsmsAppServerInfo.getMonitorUrl());
                        runInfo.setAppServerDesc(jsmsAppServerInfo.getAppServerDesc());
                    }else{
                        runInfo = jsmsAppServerInfo;
                    }
                    return runInfo;
                }
            });
            taskList.add(ft);
            // 提交给线程池执行任务，也可以通过exec.invokeAll(taskList)一次性提交所有任务;
            exec.submit(ft);
        }

        logger.debug("所有线程提交请求完毕, 主线程接着干其他事情！");

        // 开始统计各计算线程计算结果
        List<JsmsAppServerInfo> jsmsAppServerInfoList = new ArrayList<>();
        for (FutureTask<JsmsAppServerInfo> ft : taskList) {
            try {
                //FutureTask的get方法会自动阻塞,直到获取计算结果为止
                jsmsAppServerInfoList.add(ft.get());
            } catch (InterruptedException e) {
                logger.error("异步线程中断异常 ---> {}", e);
            } catch (ExecutionException e) {
                logger.error("异步线程执行异常 ---> {}", e);
            }
        }
        // 关闭线程池
        exec.shutdown();
        logger.debug("服务器应用及数据库连接监控 ------> end");
        return jsmsAppServerInfoList;
    }

    private JsmsAppServerInfo requestServer(String url) {
        String resultJson = null;
        StringBuilder token = new StringBuilder("token=");
        if (url.startsWith("https")) {
            logger.debug("使用https协议请求短信接口");
            // 线上
            resultJson = HttpUtil.httpPost(url, token.append(generateToken()).toString(), true);
        } else {
            logger.debug("使用http协议请求短信接口");
            resultJson = HttpUtil.httpPost(url, token.append(generateToken()).toString(), false);
        }
        logger.debug("响应结果 --> {}", resultJson);
        return this.analyzeRunningResult(resultJson);
    }

}
