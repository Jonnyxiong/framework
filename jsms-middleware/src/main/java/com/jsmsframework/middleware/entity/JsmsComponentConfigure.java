package com.jsmsframework.middleware.entity;

import java.util.Date;

/**
 * @description 组件配置信息表
 * @author huangwenjie
 * @date 2017-11-04
 */
public class JsmsComponentConfigure {
    
    // 
    private Integer id;
    // 组件id，唯一，规则：机房节点2位+组件类型2位+组件IP第三段+组件IP第四段 ,最长10位,一个IP上只放一个同类型组件
    private Integer componentId;
    // 组件类型，00：smsp_c2s，01：smsp_access，02：smsp_send，03：smsp_report，04：smsp_audit，05：smsp_charge，06：smsp_consumer，07：smsp_reback，2位
    private String componentType;
    // 组件名称
    private String componentName;
    // 组件IP地址
    private String hostIp;
    // 所属机房节点，10：北京亦庄，11：北京兆维，12：北京互联港湾，2位
    private Integer nodeId;
    // 连接redis线程数量
    private Integer redisThreadNum;
    // SGIP状态报告开关，0：关闭，1：开启
    private Integer sgipReportSwitch;
    // mq_id用于关联smsp_c2s组件与MQ上行队列
    private Integer mqId;
    // 更新时间
    private Date updateDate;
    // 组件开关，0：关闭，1：开启，状态为关闭时，组件不从MQ中消费数据
    private Integer componentSwitch;
    // 黑名单加载开关，0：关闭，1：开启状态为开启时，smsp_consumer（access）组件从系统黑名单表和通道黑名单表中加载黑名单信息到redis:black_list组件类型为06时此值可设置，其它类型时值为0
    private Integer blackListSwitch;
    
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
    
    public String getComponentType() {
        return componentType;
    }
    
    public void setComponentType(String componentType) {
        this.componentType = componentType ;
    }
    
    public String getComponentName() {
        return componentName;
    }
    
    public void setComponentName(String componentName) {
        this.componentName = componentName ;
    }
    
    public String getHostIp() {
        return hostIp;
    }
    
    public void setHostIp(String hostIp) {
        this.hostIp = hostIp ;
    }
    
    public Integer getNodeId() {
        return nodeId;
    }
    
    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId ;
    }
    
    public Integer getRedisThreadNum() {
        return redisThreadNum;
    }
    
    public void setRedisThreadNum(Integer redisThreadNum) {
        this.redisThreadNum = redisThreadNum ;
    }
    
    public Integer getSgipReportSwitch() {
        return sgipReportSwitch;
    }
    
    public void setSgipReportSwitch(Integer sgipReportSwitch) {
        this.sgipReportSwitch = sgipReportSwitch ;
    }
    
    public Integer getMqId() {
        return mqId;
    }
    
    public void setMqId(Integer mqId) {
        this.mqId = mqId ;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate ;
    }
    
    public Integer getComponentSwitch() {
        return componentSwitch;
    }
    
    public void setComponentSwitch(Integer componentSwitch) {
        this.componentSwitch = componentSwitch ;
    }
    
    public Integer getBlackListSwitch() {
        return blackListSwitch;
    }
    
    public void setBlackListSwitch(Integer blackListSwitch) {
        this.blackListSwitch = blackListSwitch ;
    }
    
}