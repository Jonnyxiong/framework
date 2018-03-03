package com.jsmsframework.middleware.entity;

import java.util.Date;

/**
 * @description MQ配置表
 * @author huangwenjie
 * @date 2017-11-04
 */
public class JsmsMqConfigure {
    
    // 唯一mq_id，递增
    private Integer mqId;
    // MQ中间件id,标识MQ组件
    private Integer middlewareId;
    // MQ队列
    private String mqQueue;
    // MQ交换
    private String mqExchange;
    // MQ路由key
    private String mqRoutingkey;
    // 名称
    private String queueName;
    // 消息类型，00：DB消息，01：移动行业，02：移动营销，03：联通行业，04：联通营销，05：电信行业，06：电信营销，07：行业，08：营销，11：异常移动行业，12：异常移动营销，13：异常联通行业，14：异常联通营销，15：异常电信行业，16：异常电信营销，20：通道消息，21：上行消息
    private String messageType;
    // 描述
    private String remark;
    // 更新时间
    private Date updateDate;
    
    public Integer getMqId() {
        return mqId;
    }
    
    public void setMqId(Integer mqId) {
        this.mqId = mqId ;
    }
    
    public Integer getMiddlewareId() {
        return middlewareId;
    }
    
    public void setMiddlewareId(Integer middlewareId) {
        this.middlewareId = middlewareId ;
    }
    
    public String getMqQueue() {
        return mqQueue;
    }
    
    public void setMqQueue(String mqQueue) {
        this.mqQueue = mqQueue ;
    }
    
    public String getMqExchange() {
        return mqExchange;
    }
    
    public void setMqExchange(String mqExchange) {
        this.mqExchange = mqExchange ;
    }
    
    public String getMqRoutingkey() {
        return mqRoutingkey;
    }
    
    public void setMqRoutingkey(String mqRoutingkey) {
        this.mqRoutingkey = mqRoutingkey ;
    }
    
    public String getQueueName() {
        return queueName;
    }
    
    public void setQueueName(String queueName) {
        this.queueName = queueName ;
    }
    
    public String getMessageType() {
        return messageType;
    }
    
    public void setMessageType(String messageType) {
        this.messageType = messageType ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate ;
    }
    
}