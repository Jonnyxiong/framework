package com.jsmsframework.product.entity;

import java.math.BigDecimal;

/**
 * @description 国际短信费率表
 * @author huangwenjie
 * @date 2017-11-27
 */
public class JsmsClientTariff {
    
    // 自增序列
    private Integer id;
    // 计费前缀
    private Integer prefix;
    // 国家地区名称
    private String areaname;
    // 国际名称
    private String intername;
    // 国际简码
    private String intercode;
    // 计费费率  = 厘 * 1000000
    private BigDecimal fee;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Integer getPrefix() {
        return prefix;
    }
    
    public void setPrefix(Integer prefix) {
        this.prefix = prefix ;
    }
    
    public String getAreaname() {
        return areaname;
    }
    
    public void setAreaname(String areaname) {
        this.areaname = areaname ;
    }
    
    public String getIntername() {
        return intername;
    }
    
    public void setIntername(String intername) {
        this.intername = intername ;
    }
    
    public String getIntercode() {
        return intercode;
    }
    
    public void setIntercode(String intercode) {
        this.intercode = intercode ;
    }
    
    public BigDecimal getFee() {
        return fee;
    }
    
    public void setFee(BigDecimal fee) {
        this.fee = fee ;
    }
    
}