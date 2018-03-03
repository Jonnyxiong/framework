package com.jsmsframework.finance.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 代理返点比例配置
 * @author huangwenjie
 * @date 2017-08-09
 */
public class JsmsAgentRebate {
    
    // 序号,主键自增
    private Integer id;
    // 季度消耗最小金额(包含),单位:万元	如: T>=30
    private BigDecimal startLine;
    // 季度消耗最大金额(不包含),单位:万元	如: T<60
    private BigDecimal endLine;
    // 激励返点比例	如: 2%
    private BigDecimal rebateProportion;
    // 更新者
    private String updator;
    // 更新时间
    private Date updateTime;
    // 备注
    private String remark;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public BigDecimal getStartLine() {
        return startLine;
    }
    
    public void setStartLine(BigDecimal startLine) {
        this.startLine = startLine ;
    }
    
    public BigDecimal getEndLine() {
        return endLine;
    }
    
    public void setEndLine(BigDecimal endLine) {
        this.endLine = endLine ;
    }
    
    public BigDecimal getRebateProportion() {
        return rebateProportion;
    }
    
    public void setRebateProportion(BigDecimal rebateProportion) {
        this.rebateProportion = rebateProportion ;
    }
    
    public String getUpdator() {
        return updator;
    }
    
    public void setUpdator(String updator) {
        this.updator = updator ;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
}