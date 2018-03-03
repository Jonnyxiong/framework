package com.jsmsframework.middleware.entity;

import java.util.Date;

/**
 * @description 组件与MQ关联表
 * @author huangwenjie
 * @date 2017-11-04
 */
public class JsmsComponentRefMq {
    
    // 唯一id，递增
    private Integer id;
    // 组件id
    private Integer componentId;
    // 消息类型，00：DB消息，01：移动行业，02：移动营销，03：联通行业，04：联通营销，05：电信行业，06：电信营销，07：行业，08：营销，11：异常移动行业，12：异常移动营销，13：异常联通行业，14：异常联通营销，15：异常电信行业，16：异常电信营销，20：通道消息，21：上行消息
    private String messageType;
    // 组件使用MQ的模式 0：生产者，1：消费者
    private Integer mode;
    // mq_id
    private Integer mqId;
    // 队列消息取出速率，条/秒;当action=1、2时使用
    private Integer getRate;
    //权重 权重针对消息类型相同的记录
    private Integer weight;
    // 描述
    private String remark;
    // 更新时间
    private Date updateDate;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Integer getComponentId() {
        return componentId;
    }
    
    public void setComponentId(Integer componentId) {
        this.componentId = componentId ;
    }
    
    public String getMessageType() {
        return messageType;
    }
    
    public void setMessageType(String messageType) {
        this.messageType = messageType ;
    }
    
    public Integer getMode() {
        return mode;
    }
    
    public void setMode(Integer mode) {
        this.mode = mode ;
    }
    
    public Integer getMqId() {
        return mqId;
    }
    
    public void setMqId(Integer mqId) {
        this.mqId = mqId ;
    }
    
    public Integer getGetRate() {
        return getRate;
    }
    
    public void setGetRate(Integer getRate) {
        this.getRate = getRate ;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}