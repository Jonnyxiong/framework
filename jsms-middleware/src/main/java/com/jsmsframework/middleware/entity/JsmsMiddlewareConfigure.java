package com.jsmsframework.middleware.entity;

import java.util.Date;

/**
 * @description 中间件配置信息表
 * @author huangwenjie
 * @date 2017-11-04
 */
public class JsmsMiddlewareConfigure {
    
    // 中间件id，唯一，规则：机房节点2位+中间件类型1位+序号1位+组件IP第三段+组件IP第四段,最长10位; 序号1位，同种类型从0开始，递增(当一个IP上同种类型组件有多个时)
    private Integer middlewareId;
    // 中间件业务类型，0：redis，1：MQ_c2s_io，2：MQ_send_io，3：MQ_c2s_db，4：MQ_send_db，5：kafka, 6：redis_over_rate 值为6时，用来记录超频数据
    private Integer middlewareType;
    // 中间件名称
    private String middlewareName;
    // 中间件地址
    private String hostIp;
    // 中间件端口
    private String port;
    // rabbitMQ的控制台端口
    private String consolePort;
    // 用户名
    private String userName;
    // 密码
    private String passWord;
    // 所属机房节点，10：北京亦庄，11：北京兆维，12：北京互联港湾，2位
    private Integer nodeId;
    // 更新时间
    private Date updateDate;
    // 更新人
    private String updater;
    
    public Integer getMiddlewareId() {
        return middlewareId;
    }
    
    public void setMiddlewareId(Integer middlewareId) {
        this.middlewareId = middlewareId ;
    }
    
    public Integer getMiddlewareType() {
        return middlewareType;
    }
    
    public void setMiddlewareType(Integer middlewareType) {
        this.middlewareType = middlewareType ;
    }
    
    public String getMiddlewareName() {
        return middlewareName;
    }
    
    public void setMiddlewareName(String middlewareName) {
        this.middlewareName = middlewareName ;
    }
    
    public String getHostIp() {
        return hostIp;
    }
    
    public void setHostIp(String hostIp) {
        this.hostIp = hostIp ;
    }
    
    public String getPort() {
        return port;
    }
    
    public void setPort(String port) {
        this.port = port ;
    }
    
    public String getConsolePort() {
        return consolePort;
    }
    
    public void setConsolePort(String consolePort) {
        this.consolePort = consolePort ;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName ;
    }
    
    public String getPassWord() {
        return passWord;
    }
    
    public void setPassWord(String passWord) {
        this.passWord = passWord ;
    }
    
    public Integer getNodeId() {
        return nodeId;
    }
    
    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId ;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate ;
    }
    
    public String getUpdater() {
        return updater;
    }
    
    public void setUpdater(String updater) {
        this.updater = updater ;
    }
    
}