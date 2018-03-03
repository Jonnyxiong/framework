package com.jsmsframework.channel.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 通道属性区间权重配置表
 * @author huangwenjie
 * @date 2017-09-28
 */
public class JsmsChannelAttributeWeightConfig {
    
    // 序号，自增长
    private Integer id;
    // 类型，0：通道成功率区间，1：通道价格区间
    private Integer type;
    // 属性，0：短信类型－验证码；1：短信类型－通知；2：短信类型－营销；3：短信类型－告警；4：发送号码所属运营商－移动；5：发送号码所属运营商－联通；6：发送号码所属运营商－电信 type=0时，ex_value=0、1、2、3，type=1时，ex_value=4、5、6
    private Integer exValue;
    // 起始区间（包含），如：T>=0 type = 1时，保留小数后后2位，取值范围[0，101) type = 2时，单位：元，保留小数点后4位
    private BigDecimal startLine;
    // 结束区间（不包含），如：T<101  type = 1时，保留小数后后2位，取值范围[0，101) type = 2时，单位：元，保留小数点后4位, 取值范围：[0，1]
    private BigDecimal endLine;
    // 区间权重，取值范围[0，100]
    private BigDecimal weight;
    // 更新者，对应t_sms_user表中id字段
    private Long updator;
    // 更新时间
    private Date updateDate;
    // 备注
    private String remark;

    private  String region;

    private  String allWeight;

    public String getAllWeight() {
        return allWeight;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setAllWeight(String allWeight) {
        this.allWeight = allWeight;
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type ;
    }
    
    public Integer getExValue() {
        return exValue;
    }
    
    public void setExValue(Integer exValue) {
        this.exValue = exValue ;
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
    
    public BigDecimal getWeight() {
        return weight;
    }
    
    public void setWeight(BigDecimal weight) {
        this.weight = weight ;
    }
    
    public Long getUpdator() {
        return updator;
    }
    
    public void setUpdator(Long updator) {
        this.updator = updator ;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
}