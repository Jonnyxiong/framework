package com.jsmsframework.finance.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description OEM代理返点比例配置
 * @author huangwenjie
 * @date 2017-08-09
 */
public class JsmsOemAgentRebate {
    
    // 参数id,自增加
    private Integer id;
    // 季度消耗最小数量 (包含)，单位：条，如：T>=3000000
    private Integer startLine;
    // 季度消耗最大数量（不包含），单位：条，如：T<6000000
    private Integer endLine;
    // 激励返点比例，如：0.02
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
    
    public Integer getStartLine() {
        return startLine;
    }
    
    public void setStartLine(Integer startLine) {
        this.startLine = startLine ;
    }
    
    public Integer getEndLine() {
        return endLine;
    }
    
    public void setEndLine(Integer endLine) {
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